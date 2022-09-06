FROM adoptopenjdk/openjdk11:latest

# location of build jar file
ARG JAR_FILE=target/*.jar

# copy the jar file with name apifootball
COPY ${JAR_FILE} apifootball.jar

# run the jar file inside the container
ENTRYPOINT ["java","-jar","/apifootball.jar"]