FROM openjdk:11-jre-slim
ARG BUILD_JAR=build/libs/*.jar
ARG APPLICATION_JAR=epg-provider.jar
COPY ${BUILD_JAR} ${APPLICATION_JAR}
EXPOSE 8080
USER daemon
ENTRYPOINT exec java ${JAVA_OPTS} -jar epg-provider.jar $0 $@
