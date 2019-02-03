package org.chat.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.chat.activities.MessageActivity;
import org.chat.R;
import org.chat.models.Chat;
import org.chat.models.User;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUsers;
    private boolean isChat;
    String lastMessageStr;

    public UsersAdapter(Context ctx, List<User> users, boolean isChat) {
        this.mContext = ctx;
        this.mUsers = users;
        this.isChat = isChat;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public ImageView profileImage;
        private ImageView imageOnline;
        private TextView lastMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profileImage = itemView.findViewById(R.id.profile_image_circle_user_item);
            imageOnline = itemView.findViewById(R.id.image_online);
            lastMessage = itemView.findViewById(R.id.last_message);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, viewGroup, false);
        return new UsersAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User user = mUsers.get(position);

        holder.username.setText(user.getUsername());

        if (user.getImageURL().equals("default")) {
            holder.profileImage.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mContext).load(user.getImageURL()).into(holder.profileImage);
        }


        if (isChat) {
            setLastMessage(user.getId(), holder.lastMessage);
        } else {
            holder.lastMessage.setVisibility(View.GONE);
        }


        if (isChat) {

            if (user.getStatus().equals("online")) {
                holder.imageOnline.setVisibility(View.VISIBLE);
            } else {
                holder.imageOnline.setVisibility(View.GONE);
            }
        } else {
            holder.imageOnline.setVisibility(View.GONE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("userId", user.getId());// "userid"
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }


    private void setLastMessage(final String userId, final TextView lastMessageTV) {
        lastMessageStr = "default";
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(firebaseUser.getUid())
                            & chat.getSender().equals(userId)
                            | chat.getReceiver().equals(userId)
                            & chat.getSender().equals(firebaseUser.getUid())) {
                        lastMessageStr = chat.getMessage();
                    }
                }

                switch (lastMessageStr) {
                    case "default":
                        lastMessageTV.setText(mContext.getString(R.string.no_message));
                        break;
                    default:
                        lastMessageTV.setText(lastMessageStr);
                        break;
                }

                lastMessageStr = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
