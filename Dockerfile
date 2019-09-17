FROM openjdk:8
EXPOSE 8081
ADD /target/wityorestaurant-0.0.1-SNAPSHOT.jar wityorestaurant-springboot.jar
ENTRYPOINT ["java","-jar","wityorestaurant-springboot.jar"]
