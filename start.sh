#!/bin/bash

JAVA_OPTS="-Dcatalina.base=/usr/share/dropwizard -Done-jar.silent=true $JAVA_OPTS"

echo $JAVA_OPTS
java $JAVA_OPTS -jar book-library-all.jar server config.yml

