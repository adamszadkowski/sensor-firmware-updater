FROM ghcr.io/graalvm/graalvm-ce:java11-21.1.0 AS graalvm

WORKDIR /src
COPY ./ /src

RUN gu install native-image
RUN ./gradlew clean build

RUN native-image \
  -H:Class=info.szadkowski.sensor.firmware.updater.ApplicationKt \
  -H:Name=application \
  --no-fallback \
  -cp /src/build/layers/libs/*.jar:/src/build/layers/resources:/src/build/layers/application.jar

FROM frolvlad/alpine-glibc:alpine-3.12

RUN apk update && apk add libstdc++

COPY --from=graalvm /src/application /app/application

EXPOSE 80

ENTRYPOINT ["/app/application"]
