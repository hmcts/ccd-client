#!/bin/sh

IMPORTER_USERNAME=ccd-importer@server.net
IMPORTER_PASSWORD=Password12
IDAM_URI=http://localhost:5000
REDIRECT_URI=https://localhost:3000/receiver
CLIENT_SECRET=12345678

curl  https://raw.githubusercontent.com/hmcts/ccd-definition-store-api/master/aat/src/resource/CCD_CNP_27.xlsx  -o CCD_CNP_27.xlsx

userToken=$(sh scripts/idam-authenticate.sh ${IMPORTER_USERNAME} ${IMPORTER_PASSWORD} ${IDAM_URI} ${REDIRECT_URI} ${CLIENT_SECRET})
serviceToken=$(curl --silent -X POST http://localhost:4552/testing-support/lease -d '{"microservice":"cmc_claim_store"}' -H 'content-type: application/json')

curl ${CURL_OPTS}  -X POST\
  http://localhost:4451/import \
  -H "Authorization: Bearer ${userToken}" \
  -H "ServiceAuthorization: Bearer ${serviceToken}" \
  -F 'file=@CCD_CNP_27.xlsx'

rm CCD_CNP_27.xlsx

echo
