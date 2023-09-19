package io.opentelemetry.javaagent.instrumentation.javalin.v5_0;

import com.google.auto.service.AutoService;
import io.opentelemetry.javaagent.extension.instrumentation.InstrumentationModule;
import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;
import java.util.List;

import static java.util.Collections.singletonList;

@AutoService(InstrumentationModule.class)
public class JavalinInstrumentationModule extends InstrumentationModule {

  public JavalinInstrumentationModule() {
    super("javalin", "javalin-5.0");
  }

  @Override
  public List<TypeInstrumentation> typeInstrumentations() {
    return singletonList(new HandlerInstrumentation());
  }

  @Override
  public int order() {
    return 1;
  }
}
