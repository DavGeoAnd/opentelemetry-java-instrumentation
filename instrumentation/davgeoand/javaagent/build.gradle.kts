plugins {
  id("otel.javaagent-instrumentation")
  id ("java")
}

muzzle {
  pass {
    group.set("io.javalin")
    module.set("javalin")
    versions.set("[5.0.0,)")
    assertInverse.set(true)
  }
}

dependencies {
  library("io.javalin:javalin:5.0.0")
  compileOnly("io.opentelemetry.javaagent:opentelemetry-javaagent-extension-api")

  testInstrumentation(project(":instrumentation:jetty:jetty-11.0:javaagent"))

  testLibrary("io.javalin:javalin:5.0.0")
  testImplementation("com.linecorp.armeria:armeria:1.24.2")
}
