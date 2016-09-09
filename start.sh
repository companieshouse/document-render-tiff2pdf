#!/bin/bash

# Start script for Java-Tiff2PDF Service

if [ "$1" == "ubic" ]
then
    source ~/.chs_env/global_env

    PORT=${TIFF2PDF_SERVICE_LISTEN:=9090}
else
    PORT=$1
    CONFIG_URL=$2
    ENVIRONMENT=$3
    APP_NAME=$4

    APP_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
    source /etc/profile

    # Download config file
    echo "Downloading environment from: $CONFIG_URL/$ENVIRONMENT/$APP_MONIKER"
    wget -O "$APP_DIR"/private_env ${CONFIG_URL}/${ENVIRONMENT}/private_env
    wget -O "$APP_DIR"/global_env "$CONFIG_URL/"$ENVIRONMENT/global_env
    wget -O "$APP_DIR"/app_env "$CONFIG_URL/"$ENVIRONMENT/"${APP_NAME}/"env
    source $APP_DIR/private_env
    source $APP_DIR/global_env
    source $APP_DIR/app_env

fi

java -jar target/Java-Tiff2PDF-jar-with-dependencies.jar 
