FROM openjdk:8-alpine

ARG VERSION

LABEL version="${VERSION}"

RUN echo "${VERSION}" > /VERSION

ADD target/*.jar app.jar

ENTRYPOINT java \
			-Djava.security.egd=file:/dev/.urandom \
			-jar /app.jar 
