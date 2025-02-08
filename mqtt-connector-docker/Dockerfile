# Build kafka stage
FROM maven:3.8.3-openjdk-17 AS kafka-builder

WORKDIR /app

RUN curl https://dlcdn.apache.org/kafka/3.9.0/kafka-3.9.0-src.tgz > ./kafka-src.tgz && \
    tar -xvf ./kafka-src.tgz && \
    rm ./kafka-src.tgz && \
    cd kafka-3.9.0-src && \
    ./gradlew jar

# Build connector stage
FROM maven:3.8.3-openjdk-17 AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests


# Runtime stage
FROM openjdk:17-jdk-slim AS runtime 

WORKDIR /app

COPY --from=builder /app/target/*.jar ./connectors/
COPY --from=kafka-builder /app/kafka-3.9.0-src ./kafka
COPY config/kafka-connect /app/config

RUN ls /
RUN ls /app

ENTRYPOINT [ "/app/kafka/bin/connect-standalone.sh", "/app/config/connect-standalone.properties", "/app/config/connect-mqtt-source.properties" ]