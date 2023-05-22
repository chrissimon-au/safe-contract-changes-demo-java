aws rds-data execute-statement \
    --resource-arn "arn:aws:rds:eu-west-3:289458270091:cluster:safecontractchangesdemoinfrastack-dbecc37780-w8asy5qjka3u" \
    --database "safecontractchangesdemo" \
    --secret-arn "arn:aws:secretsmanager:eu-west-3:289458270091:secret:dbSecret8003E3A7-QJmtnJjFA0qA-UsCIq6" \
    --sql "`cat $1`" \
    --profile sandbox-chris