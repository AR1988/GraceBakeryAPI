FROM maven:3.8.3-openjdk-17 as build
WORKDIR /workspace/app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src src
RUN mvn -DskipTests=true clean package
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

COPY --from=build /workspace/app/target/dependency/BOOT-INF/lib /app/lib
COPY --from=build /workspace/app/target/dependency/META-INF /app/META-INF
COPY --from=build /workspace/app/target/dependency/BOOT-INF/classes /app

EXPOSE 8100
ENTRYPOINT ["java", "-cp", "app:app/lib/*", "ait.cohort46.GraceBackeryAPI"]
