FROM openjdk:8
EXPOSE 8080
ADD target/banktransaction-0.0.1-SNAPSHOT.jar banktransaction-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","banktransaction-0.0.1-SNAPSHOT.jar"]