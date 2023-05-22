aws rds-data execute-statement \
    --resource-arn "arn:aws:rds:eu-west-2:289458270091:cluster:safecontractchangesdemoinfrastack-dbecc37780-jgfnhj3is9sp" \
    --database "safecontractchangesdemo" \
    --secret-arn "arn:aws:secretsmanager:eu-west-2:289458270091:secret:dbSecret8003E3A7-ckuEHl61dLQq-d4QOJe" \
    --sql "`cat $1`"