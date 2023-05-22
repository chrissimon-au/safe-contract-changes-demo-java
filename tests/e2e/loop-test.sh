#!/bin/bash
# This script is for helping detect intermittent test failures
# It will run the tests in a loop forever, and output how many successful runs before 
# failures
idx=1
export REPEAT_COUNT=10
while echo '======================' && echo Run: $idx - `date` && npx playwright test --reporter line;
do
    ((idx+=1))
done
echo Exited due to error during run $idx
