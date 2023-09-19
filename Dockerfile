FROM public.ecr.aws/docker/library/eclipse-temurin:17.0.7_7-jre-alpine

ADD /javaagent/build/libs/opentelemetry-javaagent-1.30.0.jar /otel-agent/java-otel.jar

ENV JAVA_OPTS='-javaagent:/otel-agent/java-otel.jar'

# metrics
ENV OTEL_METRICS_EXPORTER=none

# logs
ENV OTEL_LOGS_EXPORTER=none

# traces
ENV OTEL_TRACES_EXPORTER=none

#gradle javaagent clean assemble

#docker image rm dgandalcio/java-otel-agent:17.0.7_7-1.30.0;docker build -t dgandalcio/java-otel-agent:17.0.7_7-1.30.0 .
#docker image push dgandalcio/java-otel-agent:17.0.7_7-1.30.0

#aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 396607284401.dkr.ecr.us-east-1.amazonaws.com
#docker image rm 396607284401.dkr.ecr.us-east-1.amazonaws.com/java-otel-agent:17.0.7_7-1.30.0;docker build -t 396607284401.dkr.ecr.us-east-1.amazonaws.com/java-otel-agent:17.0.7_7-1.30.0 .
#docker image push 396607284401.dkr.ecr.us-east-1.amazonaws.com/java-otel-agent:17.0.7_7-1.30.0
