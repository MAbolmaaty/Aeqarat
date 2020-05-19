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
import com.aqratsa.aeqarat.models.favorites.RealEstate;
import com.aqratsa.aeqarat.utils.interfaces.RealEstateClickHandler;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.aqratsa.aeqarat.utils.Constants.BASE_URL;
import static com.aqratsa.aeqarat.utils.Constants.REAL_ESTATE_RENT;
import static com.aqratsa.aeqarat.utils.Constants.REAL_ESTATE_SALE;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.AdapterViewHolder> {

    public interface DeleteHandler{
        void onClick(int position);
    }

    private Context mContext;
    private List<RealEstate> mListRealEstates;
    private RealEstateClickHandler mClickHandler;
    private DeleteHandler mDeleteHandler;

    public FavoritesAdapter(Context context,
                            List<RealEstate> listRealEstates,
                            RealEstateClickHandler clickHandler,
                            DeleteHandler deleteHandler) {
        mContext = context;
        mListRealEstates = listRealEstates;
        mClickHandler = clickHandler;
        mDeleteHandler = deleteHandler;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_favorite, parent, false);
        view.setFocusable(true);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        if (mListRealEstates.get(position).getDetails().getPhoto() != null){
            Glide.with(mContext).load(BASE_URL + mListRealEstates
                    .get(position).getDetails().getPhoto()).into(holder.mRealEstate);
        }
        holder.mTitle.setText(mListRealEstates.get(position).getDetails().getTitle());
        if (mListRealEstates.get(position).getDetails().getService().equals(REAL_ESTATE_SALE)) {
            holder.mStatus.setText(mContext.getString(R.string.for_sale));
            holder.mAmount.setText(mContext.getString(R.string.price_amount, mListRealEstates.get(position).getDetails().getTotal_amount()));
        } else if (mListRealEstates.get(position).getDetails().getService().equals(REAL_ESTATE_RENT)) {
            holder.mStatus.setText(mContext.getString(R.string.for_rent));
            holder.mAmount.setText(mContext.getString(R.string.price_amount, mListRealEstates.get(position).getDetails().getPrice_for_month()));
        }
        holder.maddress.setText(mListRealEstates.get(position).getDetails().getFull_address());

    }

    @Override
    public int getItemCount() {
        return mListRealEstates.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.realEstate)
        ImageView mRealEstate;
        @BindView(R.id.delete)
        ImageView mDelete;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.status)
        TextView mStatus;
        @BindView(R.id.address)
        TextView maddress;
        @BindView(R.id.amount)
        TextView mAmount;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this::onClick);
            mDelete.setOnClickListener(v -> mDeleteHandler.onClick(getAdapterPosition()));
        }

        @Override
        public void onClick(View v) {
            mClickHandler.onClick(getAdapterPosition());
        }
    }
}
