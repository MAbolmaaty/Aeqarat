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
import com.aqratsa.aeqarat.models.requests_user.Request;
import com.aqratsa.aeqarat.utils.interfaces.RealEstateClickHandler;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.aqratsa.aeqarat.utils.Constants.BASE_URL;
import static com.aqratsa.aeqarat.utils.Constants.REAL_ESTATE_RENT;
import static com.aqratsa.aeqarat.utils.Constants.REAL_ESTATE_SALE;

public class RequestsUserAdapter extends RecyclerView.Adapter<RequestsUserAdapter.AdapterViewHolder> {

    private Context mContext;
    private List<Request> mListRequests;
    private RealEstateClickHandler mClickHandler;

    public RequestsUserAdapter(Context context,
                               List<Request> listRequests,
                               RealEstateClickHandler clickHandler) {
        mContext = context;
        mListRequests = listRequests;
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_real_estate_owned, parent, false);
        view.setFocusable(true);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        Glide.with(mContext).load(BASE_URL + mListRequests.get(position).getAkar().getPhoto()).into(holder.mRealEstate);
        holder.mTitle.setText(mListRequests.get(position).getAkar().getTitle());
        holder.mAddress.setText(mListRequests.get(position).getAkar().getFull_address());
        if (mListRequests.get(position).getAkar().getService().equals(REAL_ESTATE_SALE)){
            holder.mStatus.setText(R.string.for_sale);
            holder.mAmount.setText(mContext.getString(R.string.price_amount, mListRequests.get(position).getAkar().getTotal_amount()));
        } else if(mListRequests.get(position).getAkar().getService().equals(REAL_ESTATE_RENT)){
            holder.mStatus.setText(R.string.for_rent);
            holder.mAmount.setText(mContext.getString(R.string.price_amount, mListRequests.get(position).getAkar().getPrice_for_month()));
        }

    }

    @Override
    public int getItemCount() {
        return mListRequests.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.realEstate)
        ImageView mRealEstate;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.address)
        TextView mAddress;
        @BindView(R.id.status)
        TextView mStatus;
        @BindView(R.id.amount)
        TextView mAmount;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickHandler.onClick(getAdapterPosition());
        }
    }
}
