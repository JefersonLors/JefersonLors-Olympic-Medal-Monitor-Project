#!/bin/bash

# URL do endpoint de saúde do Spring Boot
URL="http://springboot-app:8085/actuator/health"

# Tempo de espera entre as tentativas
WAIT_TIME=10

# Função para verificar se o serviço está saudável
check_health() {
  curl -s --head --request GET "$URL" | grep "200 OK" > /dev/null
}

echo "Aguardando o serviço Spring Boot ficar saudável..."

# Loop até que o serviço Spring Boot esteja saudável
until check_health; do
  echo "O serviço Spring Boot não está saudável. Aguardando $WAIT_TIME segundos..."
  sleep $WAIT_TIME
done

echo "O serviço Spring Boot está saudável. Iniciando o script de inicialização do banco de dados..."

# Executar o script SQL
psql -h postgres -U postgres -d dbcountry_sport_medal -f /docker-entrypoint-initdb.d/init.sql
