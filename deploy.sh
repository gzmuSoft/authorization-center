#!/usr/bin/env bash
docker-compose down
gradle clean shadowJar
cd authorization-center-ui
yarn install
yarn build
cd ../authorization-server
gradle clean bootJar
cd ..
docker-compose up --build
