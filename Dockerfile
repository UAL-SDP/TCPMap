FROM openjdk:15-slim-buster
WORKDIR /app
ADD ./out/artifacts/TCPMap_jar/TCPMap.jar /app
CMD ["java","-jar","TCPMap.jar","4242","5000"]