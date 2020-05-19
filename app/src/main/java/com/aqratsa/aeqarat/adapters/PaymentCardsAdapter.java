package com.aqratsa.aeqarat.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.models.payment_cards.PaymentCard;
import com.aqratsa.aeqarat.utils.interfaces.DeleteClickHandler;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.aqratsa.aeqarat.utils.Constants.DEFAULT;
import static com.aqratsa.aeqarat.utils.Constants.NOT_DEFAULT;

public class PaymentCardsAdapter extends RecyclerView.Adapter<PaymentCardsAdapter.AdapterViewHolder> {

    public interface DefaultClickHandler{
        void onClick(int position);
    }

    private Context mContext;
    private ArrayList<PaymentCard> mListPaymentCards;
    private DeleteClickHandler mDeleteHandler;
    private DefaultClickHandler mDefaultClickHandler;
    private ArrayList<TextView> mListStatuses = new ArrayList<>();

    public PaymentCardsAdapter(Context context, ArrayList<PaymentCard> listPaymentCards,
                               DeleteClickHandler deleteHandler, DefaultClickHandler defaultClickHandler) {
        mContext = context;
        mListPaymentCards = listPaymentCards;
        mDeleteHandler = deleteHandler;
        mDefaultClickHandler = defaultClickHandler;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_payment_card, parent, false);

        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        PaymentCard paymentCard = mListPaymentCards.get(position);
        holder.mTitle.setText(mContext.getString(R.string.card_order, String.valueOf(position + 1)));
        mListStatuses.add(holder.mStatus);
        if (paymentCard.getStatus() != null) {
            holder.mStatus.setVisibility(View.VISIBLE);
            if (paymentCard.getStatus().equals(DEFAULT))
                holder.mStatus.setText(R.string.default_card);
            else if (paymentCard.getStatus().equals(NOT_DEFAULT)) {
                holder.mStatus.setText(R.string.default_card);
                holder.mStatus.setBackgroundResource(R.drawable.bg_rectangle_surrounded_corners_18);
                holder.mStatus.setTextColor(Color.BLACK);
            }
        } else {
            holder.mStatus.setVisibility(View.INVISIBLE);
        }
        if (paymentCard.getCard_num() != null) {
            holder.mCardNumber.setText(paymentCard.getCard_num());
        } else {
            holder.mCardNumber.setText(R.string.empty);
        }
        if (paymentCard.getType() != null) {
            holder.mCardType.setText(R.string.visa);
        } else {
            holder.mCardType.setText(R.string.empty);
        }
        if (paymentCard.getCsc_num() != null) {
            holder.mCVV.setText(paymentCard.getCsc_num());
        } else {
            holder.mCVV.setText(R.string.empty);
        }
        if (paymentCard.getExpire_date() != null) {
            holder.mExpirationDate.setText(paymentCard.getExpire_date());
        } else {
            holder.mExpirationDate.setText(R.string.empty);
        }
    }

    @Override
    public int getItemCount() {
        return mListPaymentCards.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.status)
        TextView mStatus;
        @BindView(R.id.delete)
        ImageView mDelete;
        @BindView(R.id.cardNumber)
        TextView mCardNumber;
        @BindView(R.id.cardType)
        TextView mCardType;
        @BindView(R.id.cvv)
        TextView mCVV;
        @BindView(R.id.expirationDate)
        TextView mExpirationDate;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mDelete.setOnClickListener(v -> mDeleteHandler.onClick(getAdapterPosition()));
            mStatus.setOnClickListener(v -> {
                for (TextView status : mListStatuses){
                    status.setBackgroundResource(R.drawable.bg_rectangle_surrounded_corners_18);
                    status.setTextColor(Color.BLACK);
                }
                mDefaultClickHandler.onClick(AdapterViewHolder.this.getAdapterPosition());
                mStatus.setBackgroundResource(R.drawable.bg_rectangle_corners_18);
                mStatus.setBackgroundTintList(ContextCompat.getColorStateList(mContext, R.color.darkGrey));
                mStatus.setTextColor(Color.WHITE);
            });
        }
    }
}
