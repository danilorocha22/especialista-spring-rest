FROM amazoncorretto:17-alpine3.20

WORKDIR /app

COPY target/*.jar /app/api.jar
COPY wait-for-it.sh /wait-for-it.sh

RUN apk add --no-cache bash
RUN chmod +x /wait-for-it.sh

EXPOSE 8080

CMD ["java", "-jar", "api.jar"]