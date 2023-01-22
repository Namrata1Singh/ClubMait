package com.namrata.clubmait.Fragments;

import android.graphics.ColorSpace;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.namrata.clubmait.Adapter.UserAdapter;
import com.namrata.clubmait.Model.User;
import com.namrata.clubmait.R;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<User> mUsers;
    private UserAdapter userAdapter;

    //private RecyclerView recyclerViewTags;
    private List<String> mHashTags;
    private List<String> mHashTagsCount;
    // rivate TagAdapter tagAdapter;
    private EditText search_bar;
    private ImageView search;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_users);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        mUsers = new ArrayList<>();
//        userAdapter = new UserAdapter(getContext() , mUsers , true);
//        recyclerView.setAdapter(userAdapter);

        search_bar = view.findViewById(R.id.search_bar);
        search = view.findViewById(R.id.search);
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users"), User.class)
                        .build();
        userAdapter = new UserAdapter(options);
        recyclerView.setAdapter(userAdapter);
//       search.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               Toast.makeText(getContext(),search_bar.getText().toString(), Toast.LENGTH_SHORT).show();
//               String str = search_bar.getText().toString();
//               searchUser(str);
//           }
//       });
//        search_bar.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String str = search_bar.getText().toString();
//                searchUser(str);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//               // filter(s.toString());
//            }
//        });

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        userAdapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        userAdapter.stopListening();
    }
//    private void readTags() {
//
//        FirebaseDatabase.getInstance().getReference().child("HashTags").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                mHashTags.clear();
//                mHashTagsCount.clear();
//
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    mHashTags.add(snapshot.getKey());
//                    mHashTagsCount.add(snapshot.getChildrenCount() + "");
//                }
//
//                //tagAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }

//    private void readUsers() {
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (TextUtils.isEmpty(search_bar.getText().toString())){
//                    mUsers.clear();
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                        User user = snapshot.getValue(User.class);
//                        mUsers.add(user);
//                    }
//
//                    userAdapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }

    private void searchUser (String s) {
        Query query = FirebaseDatabase.getInstance().getReference().child("Users")
                .orderByChild("username").startAt(s).endAt(s + "\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(getContext(),"final working", Toast.LENGTH_SHORT).show();
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    mUsers.add(user);
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"Not working", Toast.LENGTH_SHORT).show();
            }
        });
    }



//    private void filter (String text) {
//        List<String> mSearchTags = new ArrayList<>();
//        List<String> mSearchTagsCount = new ArrayList<>();
//
//        for (String s : mHashTags) {
//            if (s.toLowerCase().contains(text.toLowerCase())){
//                mSearchTags.add(s);
//                mSearchTagsCount.add(mHashTagsCount.get(mHashTags.indexOf(s)));
//            }
//        }
//
//        tagAdapter.filter(mSearchTags , mSearchTagsCount);
//    }
}
