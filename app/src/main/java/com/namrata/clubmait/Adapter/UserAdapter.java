package com.namrata.clubmait.Adapter;

import android.content.Intent;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import com.namrata.clubmait.Fragments.ProfileFragment;
import com.namrata.clubmait.MainActivity;
import com.namrata.clubmait.Model.User;
import com.namrata.clubmait.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends FirebaseRecyclerAdapter<User,UserAdapter.ViewHolder> {
    private Context mContext;
    private List<User> mUsers;
    private boolean isFargment;

    private FirebaseUser firebaseUser;
    public UserAdapter(@NonNull FirebaseRecyclerOptions<User> options){super(options);}
//    public UserAdapter(Context mContext, List<User> mUsers, boolean isFargment) {
//        this.mContext = mContext;
//        this.mUsers = mUsers;
//        this.isFargment = isFargment;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item , parent , false);
        return new UserAdapter.ViewHolder(view);
    }

//    @Override
//    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
//
//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//
//        final User user = mUsers.get(position);
//      //  holder.btnFollow.setVisibility(View.VISIBLE);
//
//
//        holder.username.setText(user.getName());
//        holder.fullname.setText(user.getBio());
//        Picasso.get().load(user.getImageurl()).placeholder(R.mipmap.ic_launcher).into(holder.imageProfile);
//
//    //    isFollowed(user.getId() , holder.btnFollow);
//
//        if (user.getId().equals(firebaseUser.getUid())){
//            holder.btnFollow.setVisibility(View.GONE);
//        }

//        holder.btnFollow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (holder.btnFollow.getText().toString().equals(("follow"))){
//                    FirebaseDatabase.getInstance().getReference().child("Follow").
//                            child((firebaseUser.getUid())).child("following").child(user.getId()).setValue(true);
//
//                    FirebaseDatabase.getInstance().getReference().child("Follow").
//                            child(user.getId()).child("followers").child(firebaseUser.getUid()).setValue(true);
//
//                    addNotification(user.getId());
//                } else {
//                    FirebaseDatabase.getInstance().getReference().child("Follow").
//                            child((firebaseUser.getUid())).child("following").child(user.getId()).removeValue();
//
//                    FirebaseDatabase.getInstance().getReference().child("Follow").
//                            child(user.getId()).child("followers").child(firebaseUser.getUid()).removeValue();
//                }
//            }
//        });

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isFargment) {
//                    mContext.getSharedPreferences("PROFILE", Context.MODE_PRIVATE).edit().putString("profileId", user.getId()).apply();
//
//                    ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
//                } else {
//                    Intent intent = new Intent(mContext, MainActivity.class);
//                    intent.putExtra("publisherId", user.getId());
//                    mContext.startActivity(intent);
//                }
//            }
//        });

 //   }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull User model) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
       // Toast.makeText(mContext, "i am adapter", Toast.LENGTH_SHORT).show();
      //        final User user = mUsers.get(position);
        holder.username.setText(model.getName());
        holder.fullname.setText(model.getBio());
        Picasso.get().load(model.getImageurl()).placeholder(R.mipmap.ic_launcher).into(holder.imageProfile);
    }

//    private void isFollowed(final String id, final Button btnFollow) {
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
//                .child("following");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.child(id).exists())
//                    btnFollow.setText("following");
//                else
//                    btnFollow.setText("follow");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }

//    @Override
//    public int getItemCount() {
//        return mUsers.size();
//    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public CircleImageView imageProfile;
        public TextView username;
        public TextView fullname;
        public Button btnFollow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageProfile = (CircleImageView)itemView.findViewById(R.id.image_profile);
            username = itemView.findViewById(R.id.username);
            fullname = itemView.findViewById(R.id.fullname);
         //   btnFollow = itemView.findViewById(R.id.btn_follow);
        }
    }

//    private void addNotification(String userId) {
//        HashMap<String, Object> map = new HashMap<>();
//
//        map.put("userid", userId);
//        map.put("text", "started following you.");
//        map.put("postid", "");
//        map.put("isPost", false);
//
//        FirebaseDatabase.getInstance().getReference().child("Notifications").child(firebaseUser.getUid()).push().setValue(map);
//    }
}
