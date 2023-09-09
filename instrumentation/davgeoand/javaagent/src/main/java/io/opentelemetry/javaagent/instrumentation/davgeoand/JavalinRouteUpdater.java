package io.opentelemetry.javaagent.instrumentation.davgeoand;

import io.opentelemetry.context.Context;
import io.opentelemetry.instrumentation.api.instrumenter.http.HttpRouteHolder;
import io.opentelemetry.instrumentation.api.instrumenter.http.HttpRouteSource;
import javax.annotation.Nullable;

public final class JavalinRouteUpdater {

  public static void updateHttpRoute(@Nullable String javalinServletContext) {
    if (javalinServletContext != null) {
      Context context = Context.current();
      HttpRouteHolder.updateHttpRoute(
          context, HttpRouteSource.CONTROLLER, javalinServletContext);
    }
  }

  private JavalinRouteUpdater() {}
}
