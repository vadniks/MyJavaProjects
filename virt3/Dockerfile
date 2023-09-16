
FROM openjdk:22-ea-11-bookworm as build

RUN echo a
LABEL name=__HIDDEN__
RUN mkdir /build /app
WORKDIR /build
COPY src ./src
ADD pom.xml .
COPY mvnw .
COPY .mvn ./.mvn
RUN ./mvnw package
RUN cp target/*.jar /app/app.jar

FROM openjdk:22-ea-11-bookworm
COPY --from=build /app /app
WORKDIR /app
EXPOSE 8080
ENV dbPort=5432
USER root
VOLUME /aaa
RUN mkdir /files
RUN /usr/bin/apt install wget
RUN wget __HIDDEN__ -O /files/emblem.png
CMD uname -a
ONBUILD RUN echo Build and launch were made by __HIDDEN__
ENTRYPOINT ["java", "-jar", "app.jar"]
