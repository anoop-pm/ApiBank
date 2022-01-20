From openjdk:8
copy ./target/banktransaction-0.0.1-SNAPSHOT.jar banktransaction-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","banktransaction-0.0.1-SNAPSHOT.jar"]