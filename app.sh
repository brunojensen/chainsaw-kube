#!/bin/bash

source deployment-pipeline

function init_token() {
  TOKEN=$(curl -sL -d 'client_id=bank-platform' \
    -d 'username=admin' \
    -d 'password=admin' \
    -d 'grant_type=password' \
    -d 'client_secret=1f9c95ab-2661-4f46-a540-630e16f198ec' \
    $(minikube service keycloak --url 2>&1 | head -n 1)/auth/realms/master/protocol/openid-connect/token \
    | jq -r .access_token)
    echo "access granted"
}

function setup_url() {
  URL=$(minikube service --url "$2")
  echo $URL
}

if [ "$1" == "use" ];
then
  init_token
  setup_url $1 $2
  if [ "$3" == "get" ];
  then
    echo "curl $URL$4"
    curl -svL $URL$4 -H 'Authorization: Bearer '$TOKEN | jq .
  elif [ "$3" == "post" ];
  then
    JSON=$(jq -n --arg b "$bar" '"$3"')
    echo "curl $URL$4 --data $JSON"
    curl -svL -XPOST $URL$4 -H 'Content-type: application/json' -H 'Authorization: Bearer '$TOKEN --data "$JSON"
  fi
elif [ "$1" == "install" ];
then
  install
else

  echo "Application test:"
  echo ""
  echo "Usage: "
  echo "./app.sh install"
  echo "./app.sh use [kube svc name] get /accounts"
  echo "./app.sh use [kube svc name] get /account/[id]"
  echo "./app.sh use [kube svc name] post /account/[id] [json]"
  echo "./app.sh use [kube svc name] get /bank/[id]"

fi
