FROM amazoncorretto:11.0.8
WORKDIR /usr/share/dropwizard

COPY build/libs/book-library-all.jar book-library-all.jar
COPY config.yml config.yml
COPY start.sh start.sh
RUN chmod +x start.sh
ENTRYPOINT ["/usr/share/dropwizard/start.sh"]