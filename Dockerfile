FROM openjdk:11-jre-slim
WORKDIR /app
ADD target/todo-1.0.0-SNAPSHOT.jar /app/
CMD ["java","-jar", "todo-1.0.0-SNAPSHOT.jar"]