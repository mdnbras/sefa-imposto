FROM openjdk:17-jdk-alpine
COPY build/libs/imposto-1.0.0.jar /usr/local/lib/imposto-1.0.0.jar
ENTRYPOINT [ "java", "-Dspring.profiles.active=default", "-jar", "/usr/local/lib/imposto-1.0.0.jar" ]