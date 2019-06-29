#!/bin/bash

source deployment-pipeline

function init_token() {
  TOKEN=$(curl -sL -d 'client_id=bank-platform' -d 'username=admin' -d 'password=admin' -d 'grant_type=password' $(minikube service keycloak --url)/auth/realms/master/protocol/openid-connect/token | jq -r .access_token)
}

function get_gateway_url() {
  echo "trying to get gateway-proxy url... in case doesn't work, please run minikube service list and edit me"
  URL=http://192.168.99.100:30242
  # URL=$(minikube service --url gateway-proxy 2>&1 | head -n 1)

}

init_token
get_gateway_url

if [ "$1" == "get" ];
then
  echo "curl $URL/v1$2"
  curl -svL $URL/v1$2 -H 'Authorization: Bearer '$TOKEN | jq .
elif [ "$1" == "post" ];
then
  JSON=$(jq -n --arg b "$bar" '"$3"')
  echo "curl $URL/v1$2 --data $JSON"
  curl -svL -XPOST $URL/v1$2 -H 'Content-type: application/json' -H 'Authorization: Bearer '$TOKEN --data "$JSON"
elif [ "$1" == "install" ];
then
  install
else

  echo "Application test:"
  echo ""
  echo "Usage: "
  echo "./app.sh install"
  echo "./app.sh get /accounts"
  echo "./app.sh get /account/[id]"
  echo "./app.sh post /account/[id] [json]"
  echo "./app.sh get /bank/[id]"

fi
