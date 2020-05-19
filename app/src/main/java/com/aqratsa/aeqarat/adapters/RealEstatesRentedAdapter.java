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
import com.aqratsa.aeqarat.models.real_estates_rented.RealEstate;
import com.aqratsa.aeqarat.models.real_estates_rented.RentedRealEstates;
import com.aqratsa.aeqarat.utils.interfaces.RealEstateClickHandler;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.aqratsa.aeqarat.utils.Constants.BASE_URL;
import static com.aqratsa.aeqarat.utils.Constants.REAL_ESTATE_RENT;
import static com.aqratsa.aeqarat.utils.Constants.REAL_ESTATE_SALE;

public class RealEstatesRentedAdapter extends RecyclerView.Adapter<RealEstatesRentedAdapter.AdapterViewHolder> {

    private Context mContext;
    private List<RentedRealEstates> mRentedRealEstates;
    private RealEstateClickHandler mClickHandler;

    public RealEstatesRentedAdapter(Context context,
                                    List<RentedRealEstates> rentedRealEstates,
                                    RealEstateClickHandler clickHandler) {
        mContext = context;
        mRentedRealEstates = rentedRealEstates;
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_rented_real_estate, parent, false);
        view.setFocusable(true);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        RealEstate realEstate = mRentedRealEstates.get(position).getRealEstate();
        Glide.with(mContext).load(BASE_URL + realEstate.getPhoto()).into(holder.mRealEstate);
        holder.mTitle.setText(realEstate.getTitle());
        holder.mAddress.setText(realEstate.getFull_address());
        if (realEstate.getService().equals(REAL_ESTATE_SALE)){
            holder.mStatus.setText(mContext.getString(R.string.for_sale));
        } else if (realEstate.getService().equals(REAL_ESTATE_RENT)){
            holder.mStatus.setText(mContext.getString(R.string.for_rent));
        }
    }

    @Override
    public int getItemCount() {
        return mRentedRealEstates.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            mClickHandler.onClick(getAdapterPosition());
        }
    }
}
