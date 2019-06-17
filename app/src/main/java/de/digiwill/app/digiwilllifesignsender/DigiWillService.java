package de.digiwill.app.digiwilllifesignsender;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import okhttp3.FormBody;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DigiWillService {
    private OkHttpClient client;
    private CookieManager cookieManager;
    private JavaNetCookieJar cookieJar;


    public DigiWillService() {
        cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        cookieJar = new JavaNetCookieJar(cookieManager);
        client = new OkHttpClient.Builder().cookieJar(cookieJar).build();
    }

    public String getCsrfToken() throws IOException {

        String url = "https://digiwill.robinkuck.de/api/csrf";
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String jsonString = response.body().string();
        JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
        String token =  json.get("token").toString();
        return token.substring(1, token.length()-1);
    }

    public boolean login(String username, String password) throws IOException {
        String url = "https://digiwill.robinkuck.de/";
        String token = this.getCsrfToken();
        RequestBody formBody = new FormBody.Builder()
                .add("_csrf", token)
                .add("username", username)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Response response = client.newCall(request).execute();
        String htmlCode = response.body().string();
        return htmlCode.contains("dropdown");
    }

    public boolean isLoggedIn() throws IOException {
        String url = "https://digiwill.robinkuck.de/";
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String htmlCode = response.body().string();
        return htmlCode.contains("dropdown");
    }

    public boolean sendLifeSign() throws IOException {
        String url = "https://digiwill.robinkuck.de/lifeSign";
        String token = this.getCsrfToken();
        RequestBody formBody = new FormBody.Builder()
                .add("_csrf", token)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        client.newCall(request).execute();
        return true;
    }
}