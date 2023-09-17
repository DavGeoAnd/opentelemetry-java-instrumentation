/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.javaagent.instrumentation.akkahttp

import io.opentelemetry.api.common.AttributeKey
import io.opentelemetry.instrumentation.testing.junit.http.{
  AbstractHttpServerTest,
  HttpServerTestOptions,
  ServerEndpoint
}

import java.util
import java.util.Collections
import java.util.function.{Function, Predicate}

abstract class AbstractHttpServerInstrumentationTest
    extends AbstractHttpServerTest[Object] {

  override protected def configure(
      options: HttpServerTestOptions
  ): Unit = {
    options.setTestCaptureHttpHeaders(false)
    options.setHttpAttributes(
      new Function[ServerEndpoint, util.Set[AttributeKey[_]]] {
        override def apply(v1: ServerEndpoint): util.Set[AttributeKey[_]] =
          Collections.emptySet()
      }
    )
    options.setHasResponseCustomizer(
      new Predicate[ServerEndpoint] {
        override def test(t: ServerEndpoint): Boolean =
          t != ServerEndpoint.EXCEPTION
      }
    )
    // instrumentation does not create a span at all
    options.disableTestNonStandardHttpMethod
  }
}
