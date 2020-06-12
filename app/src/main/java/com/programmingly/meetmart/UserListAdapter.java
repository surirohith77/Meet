package com.programmingly.meetmart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MYVIEWHOLDER> {

    List<UserList> list;

    public UserListAdapter(List<UserList> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MYVIEWHOLDER onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.getlist_rec,parent,false);

        return new MYVIEWHOLDER(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MYVIEWHOLDER holder, int position) {

        UserList userList = list.get(position);

        holder.tvMSg.setText(userList.post_text);
        holder.tvCat.setText("Post created"+userList.post_created+
                " \n post updated"+userList.post_updated+
                        "\n Post status"+userList.post_status+
                        "\n Post added by"+userList.post_added_by+
                        "\n Post category"+userList.post_category+
                        "\n Post id"+userList.post_id
                );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MYVIEWHOLDER extends RecyclerView.ViewHolder {

        TextView tvCat, tvMSg;

        public MYVIEWHOLDER(@NonNull View itemView) {
            super(itemView);

            tvMSg = itemView.findViewById(R.id.tvMsg);
            tvCat = itemView.findViewById(R.id.tvCategory);
        }
    }
}
