FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

ARG PG_JDBC_VER=42.7.8

COPY src ./src
RUN mkdir -p lib && \
    curl -L -o lib/postgresql.jar https://jdbc.postgresql.org/download/postgresql-${PG_JDBC_VER}.jar

RUN find src -name "*.java" > sources.txt && \
    mkdir -p out && \
    javac -cp lib/postgresql.jar -d out @sources.txt


FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/out /app/out
COPY --from=build /app/lib/postgresql.jar /app/lib/

EXPOSE 8081

CMD [ "java", "-cp", "/app/out:/app/lib/postgresql.jar", "app.apotek.App" ]