package com.example.parstagramjs.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parstagramjs.Post;
import com.example.parstagramjs.PostsAdapter;
import com.example.parstagramjs.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PostFragment extends Fragment {

    public static final String TAG = "PostFragment";
    private RecyclerView rvPosts;
    private PostsAdapter adapter;
    private List<Post> allPosts;

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvPosts = view.findViewById(R.id.rvPosts);

        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(getContext(), allPosts);

        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        Log.i(TAG, "Calling queryPosts()");
        queryPosts();
    }

    private void queryPosts() {
        Log.i(TAG, "Inside queryPosts()");
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        Log.i(TAG, "After getting query");
        query.include(Post.KEY_USER);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if ( e != null ) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }

                // Retrieving posts was successful
                for ( Post post : posts ) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }
}