package com.example.simplelogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PostAdapter extends ArrayAdapter<Post> {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

    public PostAdapter(Context context, List<Post> posts) {
        super(context, 0, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Post post = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_post, parent, false);
        }

        TextView textViewAuthor = convertView.findViewById(R.id.textViewAuthor);
        TextView textViewContent = convertView.findViewById(R.id.textViewContent);
        TextView textViewDate = convertView.findViewById(R.id.textViewDate);

        textViewAuthor.setText(post.getAuthor());
        textViewContent.setText(post.getContent());
        textViewDate.setText(dateFormat.format(post.getDate()));

        return convertView;
    }
}