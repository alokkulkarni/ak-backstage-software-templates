FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
# RUN ./mvnw dependency:resolve

COPY src ./src

ENV PORT ${{ values.port }}
EXPOSE ${{ values.port }}

CMD ["./mvnw", "spring-boot:run"]
