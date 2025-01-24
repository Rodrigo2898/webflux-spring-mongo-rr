FROM maven:3.9.6-eclipse-temurin-21

RUN apt-get update && apt-get install -qq -y --no-recommends

ENV INSTALL_PATH /reactive-flashcards

RUN mkdir $INSTALL_PATH

WORKDIR $INSTALL_PATH

COPY . .