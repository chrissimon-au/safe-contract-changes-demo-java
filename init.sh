#!/bin/bash

# Initialize Next.js Client App
npx create-next-app --ts --src-dir --tailwind --eslint --app --import-alias "@/*" safe-contract-changes-demo-app
mv safe-contract-changes-demo-app app

# Initialize Java Server Api
spring init --build maven --dependencies=web,webflux,data-jpa,postgresql,liquibase -g "au.chrissimon" --package-name "au.chrissimon.safecontractchangesdemo" -n Demo -a SafeContractChangesDemo server --description "Demo of making safe contract changes.  See https://github.com/chrissimon-au/safe-contract-changes-demo-java."

# Initialize Database
PGPASSWORD=postgres psql -h db -U postgres -c "CREATE DATABASE safecontractchangesdemo;"