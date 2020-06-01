# download and install Java OpenJDK 8
FROM openjdk:8
# get path to .jar file with our Spring app built with Maven
ARG JAR_FILE=target/*.jar
# copy .jar file with our application to the Docker container
COPY ${JAR_FILE} app.jar
# run app inside the container
#RUN java -jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]