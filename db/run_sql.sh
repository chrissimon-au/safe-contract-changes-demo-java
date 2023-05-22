aws rds-data execute-statement \
    --resource-arn "arn:aws:rds:eu-west-3:289458270091:cluster:safecontractchangesdemoinfrastack-dbecc37780-nzamspvybcqm" \
    --database "safecontractchangesdemo" \
    --secret-arn "arn:aws:secretsmanager:eu-west-3:289458270091:secret:dbSecret8003E3A7-upch0lGmGlCh-IVXI6o" \
    --sql "`cat $1`" \
    --profile sandbox-chris