#!/bin/bash

# Esperar até o MongoDB estar pronto
until nc -z db 27017; do
  echo "Aguardando MongoDB..."
  sleep 3
done
echo "MongoDB está pronto!"

# Limpar, compilar e empacotar o projeto
mvn clean
mvn package

# Iniciar a aplicação
java -jar target/reactive-flashcards-1.0.0.jar
