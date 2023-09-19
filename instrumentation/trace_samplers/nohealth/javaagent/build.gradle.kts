plugins {
  id("otel.javaagent-instrumentation")
  id ("java")
}


dependencies {
  implementation("io.opentelemetry:opentelemetry-extension-trace-propagators")
  implementation("io.opentelemetry:opentelemetry-sdk-trace")
}
