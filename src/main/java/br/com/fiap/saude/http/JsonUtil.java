package br.com.fiap.saude.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonUtil {

    private static final Gson GSON = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd") // mapeia java.util.Date se usar
            .create();

    public static <T> T fromJson(InputStream is, Class<T> clazz) throws IOException {
        try (Reader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return GSON.fromJson(r, clazz);
        }
    }

    public static void send(HttpExchange exchange, int status, Object body) throws IOException {
        byte[] json = GSON.toJson(body).getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(status, json.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(json);
        }
    }
}
