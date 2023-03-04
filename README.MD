## Stack:
- Java 11 
- Gradle 6.8.3
- Dropwizard 2.1.5

## Build:

From the root of the project run ***gradle shadowJar***. It will
create inside ***/build/libs*** folder the book-library-all.jar
which will be included all the external dependencies and config.yml file.

## Run app locally:

From ***/build/libs*** folder where you should have already created .jar
run the following ***java -jar book-library-all.jar***. It will start the server
and run the app on the localhost on port, which is defined in **config.yml** file.

## Versions:

Plugin and dependency versions are in ***gradle.properties*** file.

***settings.gradle*** is used to resolve version for plugins by the _pluginManagement_.

***build.gradle*** inject the versions for dependencies from ***gradle.properties***.

## API:

If all the previous steps were done correctly you should be able 
to send GET request on your ***localhost:8081/booklibrary/book***.




    

