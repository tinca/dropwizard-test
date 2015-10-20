# dropwizard-test
Exploring Dropwizard technology

The project builds with gradle (used with 2.7). After clone build the project from its root:
gradle shadowJar

then run:
java -jar build/libs/dwtest-SNAPSHOT-1.0.jar server src/main/resources/config.yaml

and access the following from a browser:
http://localhost:8080/artist/<an artist name>
http://localhost:8080/bio/<an artist name>

<an artist name> example: Jaco%Pastorius
