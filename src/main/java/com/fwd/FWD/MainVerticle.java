package com.fwd.FWD;

import io.netty.handler.codec.http.HttpHeaderValues;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class MainVerticle extends AbstractVerticle {
  public static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);
  public static final String NAME = "name";

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.exceptionHandler(error -> LOG.error("Unhandled : ", error));

    vertx.deployVerticle(new MainVerticle(), ar -> {
      if(ar.failed()){
        LOG.error("Failed to deploy", ar.cause());
        return;
      }
      LOG.info("Deployed {}", MainVerticle.class.getName());
    });
  }

  /**
   *
   * @return an random between 0 and 10
   */
  public static long generateRandomBetweenZeroToTen() {
    return Math.round(Math.random() * 10);
  }

  @Override
  public void start(Promise<Void> startPromise) {
    final Router restApi = Router.router(vertx);

    restApi.get("/band")
      .handler(CorsHandler.create("*")
        .allowedMethod(HttpMethod.GET)
        .allowedMethod(HttpMethod.OPTIONS)
          .allowedHeader("Access-Control-Request-Method")
          .allowedHeader("Access-Control-Allow-Credentials")
          .allowedHeader("Access-Control-Allow-Origin")
          .allowedHeader("Access-Control-Allow-Headers")
        )
      .handler(context -> {
      final JsonArray response = new JsonArray();

      ArrayList<Instrument> instrumentArrayList = new ArrayList<>();

      for (int i = 0; i < generateRandomBetweenZeroToTen(); i++) {
        instrumentArrayList.add(new Piano(i));
      }
      for (int i = 0; i < generateRandomBetweenZeroToTen(); i++) {
        instrumentArrayList.add(new Drum(i));
      }

      instrumentArrayList.forEach(ft -> {
        response.add(
          new JsonObject().put(NAME, ft.getClass().getSimpleName()));
      });

      LOG.info("Path {} responds with {} ", context.normalizedPath(), response.encodePrettily());
      context.response()
        .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
        .end(response.toBuffer());
    });

    vertx.createHttpServer().requestHandler(restApi)
      .exceptionHandler(error -> LOG.error("HTTP Server error : " + error))
      .listen(8900, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        LOG.info("HTTP server started on port 8900");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
