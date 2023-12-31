/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.javaagent.instrumentation.mongo.v4_0;

import static net.bytebuddy.matcher.ElementMatchers.isMethod;
import static net.bytebuddy.matcher.ElementMatchers.isPublic;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.takesArgument;

import com.mongodb.internal.async.SingleResultCallback;
import io.opentelemetry.javaagent.bootstrap.Java8BytecodeBridge;
import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;
import io.opentelemetry.javaagent.extension.instrumentation.TypeTransformer;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

final class BaseClusterInstrumentation implements TypeInstrumentation {

  @Override
  public ElementMatcher<TypeDescription> typeMatcher() {
    return named("com.mongodb.internal.connection.BaseCluster");
  }

  @Override
  public void transform(TypeTransformer transformer) {
    transformer.applyAdviceToMethod(
        isMethod()
            .and(isPublic())
            .and(named("selectServerAsync"))
            .and(takesArgument(0, named("com.mongodb.selector.ServerSelector")))
            .and(takesArgument(1, named("com.mongodb.internal.async.SingleResultCallback"))),
        this.getClass().getName() + "$SingleResultCallbackArg1Advice");

    transformer.applyAdviceToMethod(
        isMethod()
            .and(isPublic())
            .and(named("selectServerAsync"))
            .and(takesArgument(0, named("com.mongodb.selector.ServerSelector")))
            .and(takesArgument(2, named("com.mongodb.internal.async.SingleResultCallback"))),
        this.getClass().getName() + "$SingleResultCallbackArg2Advice");
  }

  @SuppressWarnings("unused")
  public static class SingleResultCallbackArg1Advice {

    @Advice.OnMethodEnter(suppress = Throwable.class)
    public static void wrapCallback(
        @Advice.Argument(value = 1, readOnly = false) SingleResultCallback<Object> callback) {
      callback = new SingleResultCallbackWrapper(Java8BytecodeBridge.currentContext(), callback);
    }
  }

  @SuppressWarnings("unused")
  public static class SingleResultCallbackArg2Advice {

    @Advice.OnMethodEnter(suppress = Throwable.class)
    public static void wrapCallback(
        @Advice.Argument(value = 2, readOnly = false) SingleResultCallback<Object> callback) {
      callback = new SingleResultCallbackWrapper(Java8BytecodeBridge.currentContext(), callback);
    }
  }
}
