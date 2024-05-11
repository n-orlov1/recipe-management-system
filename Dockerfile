FROM eclipse-temurin:17-jdk-alpine
COPY build/libs/*.jar recipe-management-system.jar
ENTRYPOINT ["java","-jar","/recipe-management-system.jar"]