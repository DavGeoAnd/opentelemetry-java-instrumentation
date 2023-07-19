package io.opentelemetry.javaagent.instrumentation.javalin.v5_0;

import static net.bytebuddy.matcher.ElementMatchers.hasSuperType;
import static net.bytebuddy.matcher.ElementMatchers.isInterface;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.not;
import static net.bytebuddy.matcher.ElementMatchers.takesArgument;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;
import io.opentelemetry.javaagent.extension.instrumentation.TypeTransformer;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

public class HandlerInstrumentation implements TypeInstrumentation {
  @Override
  public ElementMatcher<TypeDescription> typeMatcher() {
    return hasSuperType(named("io.javalin.http.Handler")).and(not(isInterface()));
  }

  @Override
  public void transform(TypeTransformer transformer) {
    transformer.applyAdviceToMethod(
        named("handle").and(takesArgument(0, named("io.javalin.http.Context"))),
        this.getClass().getName() + "$HandlerAdapterAdvice");
  }

  @SuppressWarnings("unused")
  public static class HandlerAdapterAdvice {

    @Advice.OnMethodExit(suppress = Throwable.class)
    public static void routeMatchEnricher(
        @Advice.This Handler handler,
        @Advice.Argument(0) Context ctx) {
      JavalinRouteUpdater.updateHttpRoute(ctx.endpointHandlerPath());
    }
  }

}
