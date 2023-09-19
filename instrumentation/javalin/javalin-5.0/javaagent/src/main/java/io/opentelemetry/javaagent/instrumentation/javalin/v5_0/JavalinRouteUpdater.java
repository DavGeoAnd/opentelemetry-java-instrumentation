package io.opentelemetry.javaagent.instrumentation.javalin.v5_0;

import io.opentelemetry.context.Context;
import io.opentelemetry.instrumentation.api.instrumenter.http.HttpServerRoute;
import io.opentelemetry.instrumentation.api.instrumenter.http.HttpServerRouteSource;
import jakarta.annotation.Nullable;

public final class JavalinRouteUpdater {

  public static void updateHttpRoute(@Nullable String javalinServletContext) {
    if (javalinServletContext != null) {
      Context context = Context.current();
      HttpServerRoute.update(
          context, HttpServerRouteSource.CONTROLLER, javalinServletContext);
    }
  }

  private JavalinRouteUpdater() {
  }
}
