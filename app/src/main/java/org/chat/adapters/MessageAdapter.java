package org.chat.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.chat.R;
import org.chat.models.Chat;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;//8
    public static final int MSG_TYPE_RIGHT = 1;//8

    private Context mContext;
    private List<Chat> mChats;
    private String mImageURL;

    FirebaseUser firebaseUser;


    public MessageAdapter(Context ctx, List<Chat> chats, String imageURL) {
        this.mContext = ctx;
        this.mChats = chats;
        this.mImageURL = imageURL;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView showMessage;
        public ImageView profileImage;
        public TextView textSeen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            showMessage = itemView.findViewById(R.id.show_message);
            profileImage = itemView.findViewById(R.id.profile_image);
            textSeen = itemView.findViewById(R.id.text_seen);

            Log.i(showMessage.toString(), " class ViewHolder");
            Log.i(profileImage.toString(), " class ViewHolder");
        }
    }


    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup root, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(
                    R.layout.chat_item_right, root, false);

            Log.i(view.toString(), " onCreateViewHolder");

            return new MessageAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(
                    R.layout.chat_item_left, root, false);

            Log.i(view.toString(), " onCreateViewHolder");

            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Chat chat = mChats.get(position);

        holder.showMessage.setText(chat.getMessage());

        Log.i(holder.showMessage.toString(), " onBindViewHolder");

        if (mImageURL.equals("default")) {

            holder.profileImage.setImageResource(R.drawable.icon_48);

        } else {

            Glide.with(mContext).load(mImageURL).into(holder.profileImage);

        }


        if (position == mChats.size() - 1) {

            if (chat.isIsseen()) {
                holder.textSeen.setText("SEEN");
            } else {
                holder.textSeen.setText("delivered");
            }

        } else {
            holder.textSeen.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }


    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (mChats.get(position).getSender().equals(firebaseUser.getUid())) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}
