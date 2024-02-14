package com.example.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MainVerticle extends AbstractVerticle {

    private static final String metaData = "meta.txt";

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        router.post("/createTable").handler(this::createTableHandler);
        router.post("/insertData").handler(this::insertDataHandler);

        HttpServer server = vertx.createHttpServer();
        server.requestHandler(router).listen(8080, http -> {
            if (http.succeeded()) {
                System.out.println("HTTP server started on port 8080");
                startPromise.complete();
            } else {
                startPromise.fail(http.cause());
            }
        });
    }

    private void createTableHandler(RoutingContext routingContext) {
        CreateTableRequest request = routingContext.getBodyAsJson().mapTo(CreateTableRequest.class);
        String tableName = request.getTableName();
        List<Column> columns = request.getColumns();

        try {
            FileWriter metaFile = new FileWriter(metaData, true);
            metaFile.write(tableName + ":");
            for (Column column : columns) {
                metaFile.write(column.getName() + ":" + column.getType() + ",");
            }
            metaFile.write("\n");
            metaFile.close();
            routingContext.response().setStatusCode(200).end("Table created successfully");
        } catch (IOException e) {
            e.printStackTrace();
            routingContext.response().setStatusCode(500).end("Error creating table");
        }
    }

    private void insertDataHandler(RoutingContext routingContext) {
        InsertDataRequest request = routingContext.getBodyAsJson().mapTo(InsertDataRequest.class);
        String tableName = request.getTableName();
        List<Object> values = request.getValues();

        try {
            FileWriter dataFile = new FileWriter(tableName + ".txt", true);
            for (Object val : values) {
                dataFile.write(val.toString() + ",");
            }
            dataFile.write("\n");
            dataFile.close();
            routingContext.response().setStatusCode(200).end("Data Inserted successfully");
        } catch (IOException e) {
            e.printStackTrace();
            routingContext.response().setStatusCode(500).end("Error inserting data");
        }
    }
}
