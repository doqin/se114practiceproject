package com.example.simplelogin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Post {
    private String author;
    private String content;
    private Date date;
    private List<String> hiddenByUsers;

    public Post(String author, String content) {
        this.author = author;
        this.content = content;
        this.date = new Date();
        this.hiddenByUsers = new ArrayList<>();
    }

    public Post(String author, String content, long dateMillis, List<String> hiddenByUsers) {
        this.author = author;
        this.content = content;
        this.date = new Date(dateMillis);
        this.hiddenByUsers = hiddenByUsers;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public void hideForUser(String username) {
        if (!hiddenByUsers.contains(username)) {
            hiddenByUsers.add(username);
        }
    }

    public boolean isHiddenForUser(String username) {
        return hiddenByUsers.contains(username);
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("author", author);
        jsonObject.put("content", content);
        jsonObject.put("date", date.getTime());
        JSONArray hiddenArray = new JSONArray(hiddenByUsers);
        jsonObject.put("hiddenByUsers", hiddenArray);
        return jsonObject;
    }

    public static Post fromJSONObject(JSONObject jsonObject) throws JSONException {
        String author = jsonObject.getString("author");
        String content = jsonObject.getString("content");
        long dateMillis = jsonObject.getLong("date");
        JSONArray hiddenArray = jsonObject.getJSONArray("hiddenByUsers");
        List<String> hiddenByUsers = new ArrayList<>();
        for (int i = 0; i < hiddenArray.length(); i++) {
            hiddenByUsers.add(hiddenArray.getString(i));
        }
        return new Post(author, content, dateMillis, hiddenByUsers);
    }
}