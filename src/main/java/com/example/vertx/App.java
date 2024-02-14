package com.example.vertx;

import io.vertx.core.Vertx;

public class App {
    public static void main(String[] args) {
        // Create an instance of Vert.x
        Vertx vertx = Vertx.vertx();

        // Deploy the MainVerticle class
        vertx.deployVerticle(new MainVerticle(), ar -> {
            if (ar.succeeded()) {
                System.out.println("MainVerticle deployed successfully!");
            } else {
                System.err.println("Failed to deploy MainVerticle: " + ar.cause().getMessage());
            }
        });
    }
}
