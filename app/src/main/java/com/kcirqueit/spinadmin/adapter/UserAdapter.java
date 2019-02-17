package com.kcirqueit.spinadmin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kcirqueit.spinadmin.R;
import com.kcirqueit.spinadmin.UserDetailsActivty;
import com.kcirqueit.spinadmin.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private List<User> userList;

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_user_item,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        User user = userList.get(position);
        String userImage = user.getPhotoUrl();
        if (userImage.equals("default")) {
            holder.userIv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.user_avater));
        } else {
            Picasso.get().load(user.getPhotoUrl()).placeholder(R.drawable.user_avater)
                    .into(holder.userIv);
        }

        holder.userNameTv.setText(user.getUserName());


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        
        @BindView(R.id.user_iv)
        CircleImageView userIv;

        @BindView(R.id.user_name_tv)
        TextView userNameTv;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.user_layout)
        public void showUserDetails() {
            Intent intent = new Intent(context, UserDetailsActivty.class);
            intent.putExtra("user", userList.get(getAdapterPosition()));
            context.startActivity(intent);
        }

    }

    public interface ItemClickListener{
        void onClick(View view);
    }
}
