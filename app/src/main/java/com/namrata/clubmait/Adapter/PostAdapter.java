package com.namrata.clubmait.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.namrata.clubmait.Model.Post;
import com.namrata.clubmait.Model.User;
import com.namrata.clubmait.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapter extends FirebaseRecyclerAdapter<Post,PostAdapter.Viewholder>{
    private Context mContext;
    private List<Post> mPosts;

    private FirebaseUser firebaseUser;
    public PostAdapter(@NonNull FirebaseRecyclerOptions<Post> options){super(options);}
//    public PostAdapter(Context mContext, List<Post> mPosts) {
//        this.mContext = mContext;
//        this.mPosts = mPosts;
//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new PostAdapter.Viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position,@NonNull Post model) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        // Post post = mPosts.get(position);
        Picasso.get().load(model.getImageurl()).into(holder.postImage);
        holder.description.setText(model.getDescription());



//                if (user.getImageurl().equals("default")) {
//                    holder.imageProfile.setImageResource(R.mipmap.ic_launcher);
//                } else {
//                    Picasso.get().load(user.getImageurl()).placeholder(R.mipmap.ic_launcher).into(holder.imageProfile);
//                }
//                holder.username.setText(user.getName());
//                holder.author.setText(user.getName());



                Toast.makeText(mContext, "adapter not getting post", Toast.LENGTH_SHORT).show();

    }

//    @Override
//    public int getItemCount() {
//        return mPosts.size();
//    }

    public class Viewholder extends RecyclerView.ViewHolder{
        public ImageView imageProfile;
        public ImageView postImage;
        public ImageView like;
        public ImageView comment;
        public ImageView send;

        public TextView username;
        public TextView noOfLikes;
        public TextView author;
        public TextView noOfComments;
        public TextView description;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageProfile = itemView.findViewById(R.id.image_profile);
            postImage = itemView.findViewById(R.id.post_image);
            like = itemView.findViewById(R.id.like);
            comment = itemView.findViewById(R.id.comment);
            send = itemView.findViewById(R.id.send);
            Toast.makeText(mContext, "i am getting post", Toast.LENGTH_SHORT).show();

            username = itemView.findViewById(R.id.username);
            noOfLikes = itemView.findViewById(R.id.no_of_likes);
            author = itemView.findViewById(R.id.author);
            noOfComments = itemView.findViewById(R.id.no_of_comments);
            description = itemView.findViewById(R.id.description);
        }
    }
}
