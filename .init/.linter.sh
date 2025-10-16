#!/bin/bash
cd /home/kavia/workspace/code-generation/stealthguard-app-protector-176147/android_frontend_app
./gradlew lint
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi

