#!/usr/bin/env bash
# Deploy to the git submodule

set -euo pipefail

npm run css-min-build

npm run html-build

echo "Commit the public submodule"

pushd public
datetime=$(date --iso-8601=second)
submodule_message="Deploy website on $datetime"
git add . # this is also adding new files
git commit -m "$submodule_message"
popd

echo "Commit this repo"
parent_message="Commit public submodule on $datetime"
git commit -am "$parent_message"
