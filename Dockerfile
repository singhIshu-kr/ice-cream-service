FROM java:8-jre-alpine

WORKDIR /app

COPY ./build/libs/* .

EXPOSE 80

CMD ["/bin/sh", "-c", "java -jar *.jar"]
