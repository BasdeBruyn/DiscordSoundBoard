FROM qnerd/rpi-maven AS build

COPY pom.xml /home/app/
RUN ls /home/app
RUN mvn -f /home/app/pom.xml dependency:go-offline
RUN mvn -f /home/app/pom.xml de.qaware.maven:go-offline-maven-plugin:resolve-dependencies

COPY src /home/app/src
RUN mvn -f /home/app/pom.xml package

FROM dordoka/rpi-java8
COPY --from=build /home/app/target/SoundBoardBot-1.0.jar /usr/local/lib/SoundBoardBot.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/SoundBoardBot.jar"]
