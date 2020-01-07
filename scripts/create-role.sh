#!/bin/bash

AUTH_TOKEN=$(curl -X POST "http://localhost:5000/loginUser" -H "accept: application/json" -H "Content-Type: application/x-www-form-urlencoded" -d "password=Ref0rmIsFun&username=idamowner%40hmcts.net" 2>/dev/null | jq -r '.api_auth_token')

curl -X POST "http://localhost:5000/roles" -H "accept: */*" -H "authorization: AdminApiAuthToken $AUTH_TOKEN" -H "Content-Type: application/json" -d "{ \"assignableRoles\": [], \"conflictingRoles\": [], \"description\": \"caseworker-autotest1\", \"id\": \"caseworker-autotest1\", \"linkedRoles\": [], \"name\": \"caseworker-autotest1\"}"

echo DONE



