package com.aqratsa.aeqarat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.models.real_estate.Bid;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class BidsAdapter extends RecyclerView.Adapter<BidsAdapter.AdapterViewHolder> {

    private Context mContext;
    private List<Bid> mBetsList;

    public BidsAdapter(Context context, List<Bid> betsList) {
        mContext = context;
        mBetsList = betsList;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_bid, parent, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        Glide.with(mContext).load(mBetsList.get(position).getPhoto()).into(holder.mUserImage);
        holder.mUsername.setText(mBetsList.get(position).getName());
        holder.mDate.setText(mBetsList.get(position).getCreated_at().split(" ")[0]);
        holder.mBid.setText(mContext.getResources().getString(R.string.price_amount, mBetsList.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return mBetsList.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.userImage)
        CircleImageView mUserImage;
        @BindView(R.id.username)
        TextView mUsername;
        @BindView(R.id.date)
        TextView mDate;
        @BindView(R.id.bid)
        TextView mBid;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void swapData(List<Bid> listbets) {
        mBetsList = listbets;
        this.notifyDataSetChanged();
    }
}
