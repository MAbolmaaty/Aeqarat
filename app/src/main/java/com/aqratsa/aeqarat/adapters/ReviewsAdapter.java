package com.aqratsa.aeqarat.adapters;

import android.content.Context;
import android.os.Handler;
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

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.AdapterViewHolder> {

    private Context mContext;

    public ReviewsAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_review, parent, false);
        view.setFocusable(true);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.sentiment)
        TextView mTextViewSentiment;
        @BindView(R.id.arrow)
        ImageView mImageViewArrow;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mTextViewSentiment.setTag(0);

            mImageViewArrow.setOnClickListener(v -> {
                if (mTextViewSentiment.getTag().equals(0)) {
                    mTextViewSentiment.animate().translationY(0);
                    mTextViewSentiment.setVisibility(View.VISIBLE);
                    mTextViewSentiment.setTag(1);
                    mImageViewArrow.animate().rotation(-90);
                } else {
                    mTextViewSentiment.animate().translationY(-1000);
                    new Handler().postDelayed(() -> {
                        mTextViewSentiment.setVisibility(View.GONE);
                    }, 208);

                    mTextViewSentiment.setTag(0);
                    mImageViewArrow.animate().rotation(90);
                }
            });

            itemView.setOnClickListener(v -> {
                if (mTextViewSentiment.getTag().equals(0)) {
                    mTextViewSentiment.animate().translationY(0);
                    mTextViewSentiment.setVisibility(View.VISIBLE);
                    mTextViewSentiment.setTag(1);
                    mImageViewArrow.animate().rotation(-90);
                } else {
                    mTextViewSentiment.animate().translationY(-1000);
                    new Handler().postDelayed(() -> {
                        mTextViewSentiment.setVisibility(View.GONE);
                    }, 208);

                    mTextViewSentiment.setTag(0);
                    mImageViewArrow.animate().rotation(90);
                }
            });
        }
    }
}
