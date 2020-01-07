#!/bin/bash

IMPORTER_USERNAME=$1
IMPORTER_PASSWORD=$2
IDAM_URI=$3
REDIRECT_URI=$4
CLIENT_SECRET=$5

code=$(curl ${CURL_OPTS} -u "${IMPORTER_USERNAME}:${IMPORTER_PASSWORD}" -XPOST "${IDAM_URI}/oauth2/authorize?redirect_uri=${REDIRECT_URI}&response_type=code&client_id=cmc_citizen" -d ""| jq -r .code)

curl ${CURL_OPTS} -H "Content-Type: application/x-www-form-urlencoded" -u "cmc_citizen:${CLIENT_SECRET}" -XPOST "${IDAM_URI}/oauth2/token?code=${code}&redirect_uri=${REDIRECT_URI}&grant_type=authorization_code" -d ""| jq -r .access_token
