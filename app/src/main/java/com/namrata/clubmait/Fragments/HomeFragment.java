package com.namrata.clubmait.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.namrata.clubmait.Adapter.PostAdapter;
import com.namrata.clubmait.Adapter.UserAdapter;
import com.namrata.clubmait.Model.Post;
import com.namrata.clubmait.Model.User;
import com.namrata.clubmait.R;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private RecyclerView recyclerViewPosts;
    private PostAdapter postAdapter;
    private List<Post> postList;
    private List<String> followingList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewPosts = view.findViewById(R.id.recycler_view_posts);
        recyclerViewPosts.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        linearLayoutManager.setStackFromEnd(true);
//        linearLayoutManager.setReverseLayout(true);
        recyclerViewPosts.setLayoutManager(linearLayoutManager);
//        postList = new ArrayList<>();
//        //   postAdapter = new PostAdapter(getContext(), postList);
//        recyclerViewPosts.setAdapter(postAdapter);
//        followingList = new ArrayList<>();
//        checkFollowingUsers();
//        postAdapter = new PostAdapter(getContext(), postList);
//        Toast.makeText(getContext(), "shhh", Toast.LENGTH_SHORT).show();
        FirebaseRecyclerOptions<Post> options =
                new FirebaseRecyclerOptions.Builder<Post>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Posts"), Post.class)
                        .build();
        postAdapter = new PostAdapter(options);
        recyclerViewPosts.setAdapter(postAdapter);
        return view;
    }
    private void checkFollowingUsers() {

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance()
                .getCurrentUser().getUid()).child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(getContext(), "j getting post", Toast.LENGTH_SHORT).show();
                followingList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    followingList.add(snapshot.getKey());
                }
                followingList.add(FirebaseAuth.getInstance().getCurrentUser().getUid());
              //  readPosts();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "not getting post", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void readPosts() {
    FirebaseDatabase.getInstance().getReference().child("Posts").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Toast.makeText(getContext(), "getting post", Toast.LENGTH_SHORT).show();
            postList.clear();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                Post post = snapshot.getValue(Post.class);

                for (String id : followingList) {
                    if (post.getPublisher().equals(id)){
                        postList.add(post);
                    }
                }
            }
            postAdapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(getContext(), "not getting post", Toast.LENGTH_SHORT).show();

        }
    });

}
}