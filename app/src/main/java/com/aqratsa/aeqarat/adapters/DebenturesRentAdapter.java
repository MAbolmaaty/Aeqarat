package com.aqratsa.aeqarat.adapters;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aqratsa.aeqarat.R;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class DebenturesRentAdapter extends RecyclerView.Adapter<DebenturesRentAdapter.AdapterViewHolder> {

    public interface ItemOnClickHandler {
        void onClick(int position);
    }

    public interface  ShowClickHandler {
        void onClick(int position);
    }

    private Context mContext;
    private ItemOnClickHandler mClickHandler;
    private ShowClickHandler mShowClickHandler;

    public DebenturesRentAdapter(Context context, ItemOnClickHandler clickHandler,
                                 ShowClickHandler showClickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
        mShowClickHandler = showClickHandler;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_debenture_rent, parent, false);
        view.setFocusable(true);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.userImage)
        CircleImageView mUserImage;
        @BindView(R.id.username)
        TextView mUserName;
        @BindView(R.id.userType)
        TextView mUserType;
        @BindView(R.id.expand)
        ImageView mExpand;
        @BindView(R.id.debentureType)
        TextView mDebentureType;
        @BindView(R.id.dateCollect)
        TextView mDateCollect;
        @BindView(R.id.debentureValue)
        TextView mDebentureValue;
        @BindView(R.id.textView_debentureType)
        TextView mTextViewDebentureType;
        @BindView(R.id.textView_dateCollect)
        TextView mTextViewDateCollect;
        @BindView(R.id.textView_debentureValue)
        TextView mTextViewDebentureValue;
        @BindView(R.id.debentureShow)
        Button mDebentureShow;
        @BindView(R.id.debentureDownload)
        Button mDebentureDownload;
        @BindView(R.id.divider)
        View mDivider;

        private boolean mCollapsed = true;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this::onClick);
            mDebentureShow.setOnClickListener(view ->
                    mShowClickHandler.onClick(getAdapterPosition()));
        }

        @Override
        public void onClick(View view) {
            mClickHandler.onClick(getAdapterPosition());
            if (mCollapsed){
                view.setEnabled(false);
                mExpand.animate().rotation(-90);
                controlDetails(View.INVISIBLE);
                translateDetails(-itemView.getHeight(), false);
                translateDetails(0, true);
                new Handler().postDelayed(() -> AdapterViewHolder.this.controlDetails(View.VISIBLE), 296);
                new Handler().postDelayed(() -> {
                    mCollapsed = false;
                    view.setEnabled(true);
                }, 496);
            } else {
                view.setEnabled(false);
                mExpand.animate().rotation(90);
                translateDetails(-itemView.getHeight(), true);
                new Handler().postDelayed(() -> controlDetails(View.INVISIBLE), 96);
                new Handler().postDelayed(() -> AdapterViewHolder.this.controlDetails(View.GONE), 196);
                new Handler().postDelayed(() -> {
                    mCollapsed = true;
                    view.setEnabled(true);
                }, 496);
            }
        }

        private void controlDetails(int visibility) {
            if (visibility == View.VISIBLE) {
                mDebentureType.setVisibility(View.VISIBLE);
                mDateCollect.setVisibility(View.VISIBLE);
                mDebentureValue.setVisibility(View.VISIBLE);
                mTextViewDebentureType.setVisibility(View.VISIBLE);
                mTextViewDateCollect.setVisibility(View.VISIBLE);
                mTextViewDebentureValue.setVisibility(View.VISIBLE);
                mDebentureShow.setVisibility(View.VISIBLE);
                mDebentureDownload.setVisibility(View.VISIBLE);
            } else if (visibility == View.INVISIBLE) {
                mDebentureType.setVisibility(View.INVISIBLE);
                mDateCollect.setVisibility(View.INVISIBLE);
                mDebentureValue.setVisibility(View.INVISIBLE);
                mTextViewDebentureType.setVisibility(View.INVISIBLE);
                mTextViewDateCollect.setVisibility(View.INVISIBLE);
                mTextViewDebentureValue.setVisibility(View.INVISIBLE);
                mDebentureShow.setVisibility(View.INVISIBLE);
                mDebentureDownload.setVisibility(View.INVISIBLE);
            } else if (visibility == View.GONE) {
                mDebentureType.setVisibility(View.GONE);
                mDateCollect.setVisibility(View.GONE);
                mDebentureValue.setVisibility(View.GONE);
                mTextViewDebentureType.setVisibility(View.GONE);
                mTextViewDateCollect.setVisibility(View.GONE);
                mTextViewDebentureValue.setVisibility(View.GONE);
                mDebentureShow.setVisibility(View.GONE);
                mDebentureDownload.setVisibility(View.GONE);
            }
        }

        private void translateDetails(int value, boolean animation) {
            if (animation) {
                mDebentureType.animate().translationY(value);
                mDateCollect.animate().translationY(value);
                mDebentureValue.animate().translationY(value);
                mTextViewDebentureType.animate().translationY(value);
                mTextViewDateCollect.animate().translationY(value);
                mTextViewDebentureValue.animate().translationY(value);
                mDebentureShow.animate().translationY(value);
                mDebentureDownload.animate().translationY(value);
            } else {
                mDebentureType.setTranslationY(value);
                mDateCollect.setTranslationY(value);
                mDebentureValue.setTranslationY(value);
                mTextViewDebentureType.setTranslationY(value);
                mTextViewDateCollect.setTranslationY(value);
                mTextViewDebentureValue.setTranslationY(value);
                mDebentureShow.setTranslationY(value);
                mDebentureDownload.setTranslationY(value);
            }
        }
    }
}
