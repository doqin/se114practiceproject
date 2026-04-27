package com.example.simplelogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PostsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "SocialMediaPrefs";
    private static final String POSTS_KEY = "posts_data";

    private List<Post> allPosts;
    private List<Post> visiblePosts;
    private PostAdapter adapter;
    private EditText editTextPost;
    private ListView listViewPosts;
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        currentUsername = getIntent().getStringExtra("username");
        if (currentUsername == null) {
            currentUsername = "Anonymous";
        }

        allPosts = loadPosts();
        visiblePosts = new ArrayList<>();
        adapter = new PostAdapter(this, visiblePosts);

        editTextPost = findViewById(R.id.editTextPost);
        listViewPosts = findViewById(R.id.listViewPosts);
        Button buttonPost = findViewById(R.id.buttonPost);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewPosts.setAdapter(adapter);
        registerForContextMenu(listViewPosts);

        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editTextPost.getText().toString().trim();
                if (!content.isEmpty()) {
                    Post newPost = new Post(currentUsername, content);
                    allPosts.add(newPost);
                    savePosts();
                    updateVisiblePosts();
                    editTextPost.setText("");
                }
            }
        });

        if (allPosts.isEmpty()) {
            allPosts.add(new Post("Alice", "Hello world!"));
            allPosts.add(new Post("Bob", "Android is great."));
            savePosts();
        }
        updateVisiblePosts();
    }

    private void updateVisiblePosts() {
        visiblePosts.clear();
        for (Post post : allPosts) {
            if (!post.isHiddenForUser(currentUsername)) {
                visiblePosts.add(post);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void savePosts() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray jsonArray = new JSONArray();
        for (Post post : allPosts) {
            try {
                jsonArray.put(post.toJSONObject());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        editor.putString(POSTS_KEY, jsonArray.toString());
        editor.apply();
    }

    private List<Post> loadPosts() {
        List<Post> posts = new ArrayList<>();
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(POSTS_KEY, null);
        if (json != null) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    posts.add(Post.fromJSONObject(jsonArray.getJSONObject(i)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return posts;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.optionProfile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("username", currentUsername);
            startActivity(intent);
            return true;
        } else if (id == R.id.optionSortByDate) {
            Collections.sort(visiblePosts, new Comparator<Post>() {
                @Override
                public int compare(Post p1, Post p2) {
                    return p2.getDate().compareTo(p1.getDate());
                }
            });
            adapter.notifyDataSetChanged();
            return true;
        } else if (id == R.id.optionSortByAuthor) {
            Collections.sort(visiblePosts, new Comparator<Post>() {
                @Override
                public int compare(Post p1, Post p2) {
                    return p1.getAuthor().compareToIgnoreCase(p2.getAuthor());
                }
            });
            adapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.post_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Post selectedPost = visiblePosts.get(info.position);

        int id = item.getItemId();
        if (id == R.id.contextDetail) {
            Toast.makeText(this, "Detail of: " + selectedPost.getContent(), Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.contextHide) {
            selectedPost.hideForUser(currentUsername);
            savePosts();
            updateVisiblePosts();
            Toast.makeText(this, "Post hidden", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onContextItemSelected(item);
    }
}