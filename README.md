# dropwizard-test
Exploring Dropwizard technology
===============================

The project builds with gradle (2.7+). If gradle is not installed run gradlew to boot it. Build the project from its root:
```sh
gradle shadowJar
```

First create database:
```sh
java -jar build/libs/dwtest-1.0-SNAPSHOT-all.jar db migrate src/main/resources/config.yaml
```

Then run the fatjar:
```sh
java -jar build/libs/dwtest-1.0-SNAPSHOT-all.jar server src/main/resources/config.yaml
```

Access the following links from a browser:<BR>
```
http://localhost:8080/artist/"an artist name"<BR>
http://localhost:8080/bio/an artist name"<BR>
http://localhost:8080/artistView/"an artist name"<BR>
http://localhost:8080/bioView/an artist name"
```
<P>
"an artist name" example: Jaco%20Pastorius
