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
import com.aqratsa.aeqarat.models.search.response.RealEstate;
import com.aqratsa.aeqarat.utils.interfaces.RealEstateClickHandler;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.aqratsa.aeqarat.utils.Constants.BASE_URL;
import static com.aqratsa.aeqarat.utils.Constants.REAL_ESTATE_RENT;
import static com.aqratsa.aeqarat.utils.Constants.REAL_ESTATE_SALE;

public class RealEstatesAgentAdapter extends RecyclerView.Adapter<RealEstatesAgentAdapter.AdapterViewHolder> {

    private Context mContext;
    private List<RealEstate> mListRealEstates;
    private RealEstateClickHandler mClickHandler;

    public RealEstatesAgentAdapter(Context context,
                                   List<RealEstate> listRealEstates,
                                   RealEstateClickHandler clickHandler) {
        mContext = context;
        mListRealEstates = listRealEstates;
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_real_estate_admin, parent, false);
        view.setFocusable(true);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        if (mListRealEstates.get(position).getPhoto() != null)
            Glide.with(mContext).load(BASE_URL + mListRealEstates.get(position).getPhoto()).into(holder.mRealEstate);
        holder.mTitle.setText(mListRealEstates.get(position).getTitle());
        holder.mAddress.setText(mListRealEstates.get(position).getFull_address());
        if (mListRealEstates.get(position).getService().equals(REAL_ESTATE_SALE)) {
            holder.mStatus.setText(mContext.getString(R.string.for_sale));
        } else if (mListRealEstates.get(position).getService().equals(REAL_ESTATE_RENT)) {
            holder.mStatus.setText(mContext.getString(R.string.for_rent));
        }
        if (mListRealEstates.get(position).getService().equals(REAL_ESTATE_SALE)) {
            holder.mAmount.setText(mContext.getString(R.string.price_amount, mListRealEstates.get(position).getTotal_amount()));
        } else if (mListRealEstates.get(position).getService().equals(REAL_ESTATE_RENT)){
            if (mListRealEstates.get(position).getPrice_for_month() != null) {
                holder.mAmount.setText(String.format(mContext.getResources().getString(R.string.price_amount), mListRealEstates.get(position).getPrice_for_month()));
            } else if (mListRealEstates.get(position).getPrice_for_3month() != null) {
                holder.mAmount.setText(String.format(mContext.getResources().getString(R.string.price_amount), mListRealEstates.get(position).getPrice_for_3month()));
            } else if (mListRealEstates.get(position).getPrice_for_6month() != null) {
                holder.mAmount.setText(String.format(mContext.getResources().getString(R.string.price_amount), mListRealEstates.get(position).getPrice_for_6month()));
            } else if (mListRealEstates.get(position).getPrice_for_12month() != null) {
                holder.mAmount.setText(String.format(mContext.getResources().getString(R.string.price_amount), mListRealEstates.get(position).getPrice_for_12month()));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mListRealEstates.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.realEstate)
        ImageView mRealEstate;
        @BindView(R.id.category)
        TextView mCategory;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.address)
        TextView mAddress;
        @BindView(R.id.status)
        TextView mStatus;
        @BindView(R.id.check)
        View mCheck;
        @BindView(R.id.amount)
        TextView mAmount;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            mClickHandler.onClick(getAdapterPosition());
        }
    }
}
