package com.aqratsa.aeqarat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aqratsa.aeqarat.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.AdapterViewHolder> {

    private Context mContext;

    public RequestsAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_request, parent, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.userImage)
        CircleImageView mUserImage;
        @BindView(R.id.requestType)
        TextView mRequestType;
        @BindView(R.id.username)
        TextView mUsername;
        @BindView(R.id.duration)
        TextView mDuration;
        @BindView(R.id.show)
        ImageView mShow;
        @BindView(R.id.time)
        ImageView mTime;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
