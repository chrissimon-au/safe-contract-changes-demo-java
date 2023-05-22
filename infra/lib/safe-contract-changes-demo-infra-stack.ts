import * as cdk from 'aws-cdk-lib';
import { DockerImage, ILocalBundling, CfnOutput } from 'aws-cdk-lib';
import { Construct } from 'constructs';
import { CloudFrontToS3 } from '@aws-solutions-constructs/aws-cloudfront-s3';
import { BucketDeployment, Source } from 'aws-cdk-lib/aws-s3-deployment';
import { ApplicationLoadBalancedFargateService } from 'aws-cdk-lib/aws-ecs-patterns';
import * as ecs from 'aws-cdk-lib/aws-ecs';
import { ServerlessCluster } from 'aws-cdk-lib/aws-rds';
import * as rds from 'aws-cdk-lib/aws-rds';
import * as ec2 from 'aws-cdk-lib/aws-ec2';
import { spawnSync } from "child_process";
import * as path from "path";
import { Platform } from 'aws-cdk-lib/aws-ecr-assets';
import { ApplicationProtocol } from 'aws-cdk-lib/aws-elasticloadbalancingv2';
import { ViewerProtocolPolicy } from 'aws-cdk-lib/aws-cloudfront';

const getFullPath = (relativePath: string) => 
  path.join(process.env.npm_config_local_prefix ?? __dirname, relativePath);

export class SafeContractChangesDemoInfraStack extends cdk.Stack {
  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    const vpc = new ec2.Vpc(this, 'vpc');

    const db = new ServerlessCluster(this, 'db', {
      engine: rds.DatabaseClusterEngine.auroraPostgres({
        version: rds.AuroraPostgresEngineVersion.VER_13_9
      }),
      vpc,
      defaultDatabaseName: 'safecontractchangesdemo',
      enableDataApi: true,
      removalPolicy: cdk.RemovalPolicy.DESTROY,
    });

    const serverSg = new ec2.SecurityGroup(this, 'serverSg', {vpc});

    db.connections.allowDefaultPortFrom(serverSg);

    const server = new ApplicationLoadBalancedFargateService(this, 'server', {
      vpc,
      memoryLimitMiB: 4096,
      cpu:2048,
      taskImageOptions: {
        image: ecs.ContainerImage.fromAsset(getFullPath('../server'), {
          platform: Platform.LINUX_AMD64,
        }),
        containerName: 'server',
        environment: {
          SPRING_DATASOURCE_URL: `jdbc:postgresql://${db.clusterEndpoint.hostname}:${db.clusterEndpoint.port}/safecontractchangesdemo`,
          SPRING_DATASOURCE_USERNAME: db.secret!.secretValueFromJson('username').unsafeUnwrap(),
          SPRING_DATASOURCE_PASSWORD: db.secret!.secretValueFromJson('password').unsafeUnwrap(),
        },
        containerPort: 8080,
      },
      securityGroups: [serverSg],
      desiredCount: 1,
      healthCheckGracePeriod: cdk.Duration.seconds(180),
    });

    server.targetGroup.configureHealthCheck({
      healthyThresholdCount: 2,
      unhealthyThresholdCount: 3,
      interval: cdk.Duration.seconds(10),
    });

    server.targetGroup.setAttribute('deregistration_delay.timeout_seconds', '5');

    const cfnToS3 = new CloudFrontToS3(this, 'SpaHosting', {
      insertHttpSecurityHeaders: false,
      cloudFrontDistributionProps: {
        defaultBehavior: {
          viewerProtocolPolicy: ViewerProtocolPolicy.ALLOW_ALL
        }
      }
    });

    const contentPath = getFullPath("../app");

    new BucketDeployment(this, 'appDeploy', {
      destinationBucket: cfnToS3.s3Bucket!,
      distribution: cfnToS3.cloudFrontWebDistribution,
      sources: [
        Source.asset(contentPath, {
            bundling: {
                local: this.localBuilderFactory(contentPath, "out"),
                image: DockerImage.fromRegistry("node:lts"),
            },
            exclude: [ ".next", "out" ],
        }),
        Source.jsonData('config.json', {
          SERVER_URL: `http://${server.loadBalancer.loadBalancerDnsName}`
        })
      ],

    });

    new CfnOutput(this, "distribution", {
      value: cfnToS3.cloudFrontWebDistribution.domainName,
    });
    new CfnOutput(this, "distributionUrl", {
        value: `https://${cfnToS3.cloudFrontWebDistribution.domainName}`,
    });
    new CfnOutput(this, "distributionId", {
        value: cfnToS3.cloudFrontWebDistribution.distributionId,
    });

  }

  localBuilderFactory(projectPath: string, bundlePath: string): ILocalBundling {
    return {
        tryBundle(outDir: string) {
            const contentPath = projectPath;
            const distPath = path.join(contentPath, bundlePath);
            const cpCmd = `cp ${distPath}/* ${outDir} -R`;
            console.log({ contentPath, distPath, outDir, cpCmd });
            const { error } = spawnSync("bash", [
                "-c",
                `npm install && npm run build && ${cpCmd}`
            ], {
                cwd: contentPath,
                stdio: "inherit",
            });
            console.log(error);
            if (error) {
                throw error;
            }
            return true;
        },
        
    };
  }
}
