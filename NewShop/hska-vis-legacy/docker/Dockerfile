FROM maven:3.5.4-jdk-8-alpine as builder

COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn clean package

FROM tomcat:8-jdk8
COPY --from=builder /target/EShop-1.0.0.war /usr/local/tomcat/webapps/EShop.war
COPY ./conf/tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
ENV JPDA_ADDRESS="8000"
ENV JPDA_TRANSPORT="dt_socket"

EXPOSE 8080 8000
ENTRYPOINT ["catalina.sh", "jpda", "run"]