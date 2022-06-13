#!/usr/bin/env bash

set -e

PACT_BROKER_BASE_URL=https://pact.grossweber.com

version="$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)"

docker run \
       --rm \
       pactfoundation/pact-cli:latest \
       broker \
       can-i-deploy \
       --broker-base-url "$PACT_BROKER_BASE_URL" \
       --pacticipant "Travel Expenses Backend" \
       --version "$version"