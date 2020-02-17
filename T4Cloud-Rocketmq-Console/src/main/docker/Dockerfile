FROM java:8
VOLUME /tmp
ADD T4Cloud-Rocketmq-Console-*.jar T4Cloud-Rocketmq-Console.jar
RUN sh -c 'touch /T4Cloud-Rocketmq-Console.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /T4Cloud-Rocketmq-Console.jar" ]
