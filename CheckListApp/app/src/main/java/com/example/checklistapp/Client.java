package com.example.checklistapp;

import android.util.JsonReader;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Client {
    private static Client SINGLETON;

    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.S'Z'[z]";

    public static final String IP = "192.168.1.68";
    public static final String APIURL = "http://" + IP + ":8080/CheckList/resources/";
    public static final String LOGIN_URL = APIURL + "auth/login";
    public static final String CURRENT_USER_URL = APIURL + "auth/currentuser";
    public static final String CREATE_USER_URL = APIURL + "auth/create";

    /** JWT token returned from login */
    String token;

    public static synchronized Client getSingleton() {
        if(SINGLETON == null) {
            SINGLETON = new Client();
        }

        return SINGLETON;
    }

    protected User user;

    public HttpURLConnection getSecureConnection(String url) throws IOException {
        HttpURLConnection result = (HttpURLConnection) new URL(url).openConnection();
        result.setRequestProperty("Authorization", "Bearer " + token);
        result.setConnectTimeout(3000);
        return result;
    }

    public User login(String userid, String password) throws IOException {
        HttpURLConnection con = null;
        try {
            URL url = new URL(LOGIN_URL + "?userid=" + userid + "&password=" + password);
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(3000);
            token = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            user = getCurrentUser();
            con.getInputStream().close(); // Why?
        } finally {
            if(con != null) con.disconnect();
        }

        return user;
    }

    public User getCurrentUser() throws IOException {
        User result;
        HttpURLConnection con = null;
        try {
            con = getSecureConnection(CURRENT_USER_URL);
            JsonReader jr = new JsonReader(new InputStreamReader(new BufferedInputStream(con.getInputStream())));
            user = loadUser(jr);
            con.getInputStream().close(); // Why?
        } finally {
            if(con != null) con.disconnect();
        }

        return user;
    }

    public User createUser(String userid, String password) throws IOException {
        User result;

        String urlParameters = "userid=" + userid + "&password=" + password;

        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        HttpURLConnection con = null;

        URL url = new URL(CREATE_USER_URL);
        con = (HttpURLConnection) url.openConnection();

        con.setDoOutput(true);
        con.setInstanceFollowRedirects(false);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("charset", "utf-8");
        con.setRequestProperty("Content-Length", Integer.toString(postDataLength));
        con.setUseCaches(false);

        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.write(postData);
        }
        // Read JSON result
        try (JsonReader jr = new JsonReader(new InputStreamReader(new BufferedInputStream(con.getInputStream())))) {
            result = loadUser(jr);
            con.getInputStream().close(); // Why?
        } finally {
            if (con != null) con.disconnect();
        }
        return result;
    }

    private User loadUser(JsonReader jr) throws IOException {
        User result = new User();

        jr.beginObject();
        while (jr.hasNext()) {
            switch (jr.nextName()) {
                case "userid":
                    result.setUserid(jr.nextString());
                    break;
                default:
                    jr.skipValue();
            }
        }
        jr.endObject();

        return result;
    }
}
