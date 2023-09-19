import io.javalin.Javalin;

public class TestJavalinApplication {

  public static void initJavalin(int port) {
    Javalin javalin = Javalin.create();
    javalin.get("/", ctx -> ctx.result("Hello World"));
    javalin.get("/param/{param}", ctx -> ctx.result("Hello " + ctx.pathParam("param")));
    javalin.get("/exception/{param}", ctx -> {
      throw new IllegalStateException(ctx.pathParam("param"));
    });
    javalin.get("/stop", ctx -> javalin.stop());
//    javalin.after(Context::endpointHandlerPath);
    javalin.start(port);
  }

  private TestJavalinApplication() {}
}
