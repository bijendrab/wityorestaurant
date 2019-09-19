#FROM openjdk:8
#EXPOSE 8081
#VOLUME /tmp
#ARG JAR_FILE
#ENV _JAVA_OPTIONS "-Xms256m -Xmx512m -Djava.awt.headless=true"
#COPY ${JAR_FILE} /opt/wityorestaurant.jar
#WORKDIR /opt
#ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/opt/wityorestaurant.jar"]

### BUILD image

FROM mysql
ENV MYSQL_ROOT_PASSWORD=litu

ENV MYSQL_DATA_DIR=/var/lib/mysql \
    MYSQL_RUN_DIR=/run/mysqld \
    MYSQL_LOG_DIR=/var/log/mysql


RUN /usr/bin/mysqld_safe start
RUN mysql -u root -p${MYSQL_ROOT_PASSWORD} -e "CREATE DATABASE wityorestaurant"


FROM maven:3-jdk-11 as builder

# create app folder for sources

RUN mkdir -p /build

WORKDIR /build

COPY pom.xml /build

#Download all required dependencies into one layer

RUN mvn -B dependency:resolve dependency:resolve-plugins

#Copy source code

COPY src /build/src

# Build application

RUN mvn package

FROM openjdk:11-slim as runtime

EXPOSE 8081

#Set app home folder

ENV APP_HOME /app

#Possibility to set JVM options (https://www.oracle.com/technetwork/java/javase/tech/vmoptions-jsp-140102.html)

ENV JAVA_OPTS="-Xms256m -Xmx512m -Djava.awt.headless=true"

#Create base app folder

RUN mkdir $APP_HOME

#Create folder to save configuration files

RUN mkdir $APP_HOME/config

#Create folder with application logs

RUN mkdir $APP_HOME/log

VOLUME $APP_HOME/log

VOLUME $APP_HOME/config

WORKDIR $APP_HOME

#Copy executable jar file from the builder image

COPY --from=builder /build/target/*.jar wityorestaurant.jar

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar wityorestaurant.jar" ]

#Second option using shell form:

#ENTRYPOINT exec java $JAVA_OPTS -jar app.jar $0 $@
