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
import com.google.android.material.bottomsheet.BottomSheetDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountingAgentAdapter extends RecyclerView.Adapter<AccountingAgentAdapter.AdapterViewHolder> {

    public interface ItemOnClickHandler {
        void onClick(int position);
    }

    public interface CollectedOnClickHandler{
        void onClick(int position);
    }

    private Context mContext;
    private ItemOnClickHandler mClickHandler;
    private CollectedOnClickHandler mCollectedHandler;

    public AccountingAgentAdapter(Context context, ItemOnClickHandler clickHandler,
                                  CollectedOnClickHandler collectedHandler) {
        mContext = context;
        mClickHandler = clickHandler;
        mCollectedHandler = collectedHandler;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_accounting_admin, parent, false);
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
        @BindView(R.id.date)
        TextView mDate;
        @BindView(R.id.status)
        TextView mStatus;
        @BindView(R.id.expand)
        ImageView mExpand;
        @BindView(R.id.isCollected)
        ImageView mIsCollected;
        @BindView(R.id.collected)
        Button mCollected;
        @BindView(R.id.view_income)
        View mViewIncome;
        @BindView(R.id.textView_income)
        TextView mTextViewIncome;
        @BindView(R.id.income)
        TextView mIncome;
        @BindView(R.id.sar_income)
        TextView mSarIncome;
        @BindView(R.id.view_expenses)
        View mViewExpenses;
        @BindView(R.id.textView_expenses)
        TextView mTextViewExpenses;
        @BindView(R.id.expenses)
        TextView mExpenses;
        @BindView(R.id.sar_expenses)
        TextView mSarExpenses;
        @BindView(R.id.view_profit)
        View mViewProfit;
        @BindView(R.id.textView_profit)
        TextView mTextViewProfit;
        @BindView(R.id.profit)
        TextView mProfit;
        @BindView(R.id.sar_profit)
        TextView mSarProfit;

        private boolean mCollapsed = true;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this::onClick);
            mCollected.setOnClickListener(view -> {
                mCollectedHandler.onClick(getAdapterPosition());
                BottomSheetDialog dialog = new BottomSheetDialog(mContext);
                dialog.setContentView(R.layout.dialog_collected_confirm);
                dialog.show();
                ((ImageView)dialog.getWindow().findViewById(R.id.action1)).setImageResource(R.drawable.ic_close);
                dialog.getWindow().findViewById(R.id.action1).setOnClickListener(view12 -> dialog.dismiss());
                ((TextView)dialog.getWindow().findViewById(R.id.title)).setText(R.string.confirm);
                dialog.getWindow().findViewById(R.id.confirm).setOnClickListener(view1 -> {
                    dialog.dismiss();
                    mCollected.setVisibility(View.INVISIBLE);
                    mIsCollected.setVisibility(View.VISIBLE);
                });
                dialog.getWindow().findViewById(R.id.cancel).setOnClickListener(view1 -> {
                    dialog.dismiss();
                });
            });
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
                new Handler().postDelayed(() -> AdapterViewHolder.this.controlDetails(View.VISIBLE), 196);
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

        private void controlDetails(int visibility){
            if (visibility == View.VISIBLE){
                mViewIncome.setVisibility(View.VISIBLE);
                mTextViewIncome.setVisibility(View.VISIBLE);
                mIncome.setVisibility(View.VISIBLE);
                mSarIncome.setVisibility(View.VISIBLE);
                mViewExpenses.setVisibility(View.VISIBLE);
                mTextViewExpenses.setVisibility(View.VISIBLE);
                mExpenses.setVisibility(View.VISIBLE);
                mSarExpenses.setVisibility(View.VISIBLE);
                mViewProfit.setVisibility(View.VISIBLE);
                mTextViewProfit.setVisibility(View.VISIBLE);
                mProfit.setVisibility(View.VISIBLE);
                mSarProfit.setVisibility(View.VISIBLE);
            } else if (visibility == View.INVISIBLE){
                mViewIncome.setVisibility(View.INVISIBLE);
                mTextViewIncome.setVisibility(View.INVISIBLE);
                mIncome.setVisibility(View.INVISIBLE);
                mSarIncome.setVisibility(View.INVISIBLE);
                mViewExpenses.setVisibility(View.INVISIBLE);
                mTextViewExpenses.setVisibility(View.INVISIBLE);
                mExpenses.setVisibility(View.INVISIBLE);
                mSarExpenses.setVisibility(View.INVISIBLE);
                mViewProfit.setVisibility(View.INVISIBLE);
                mTextViewProfit.setVisibility(View.INVISIBLE);
                mProfit.setVisibility(View.INVISIBLE);
                mSarProfit.setVisibility(View.INVISIBLE);
            } else if (visibility == View.GONE){
                mViewIncome.setVisibility(View.GONE);
                mTextViewIncome.setVisibility(View.GONE);
                mIncome.setVisibility(View.GONE);
                mSarIncome.setVisibility(View.GONE);
                mViewExpenses.setVisibility(View.GONE);
                mTextViewExpenses.setVisibility(View.GONE);
                mExpenses.setVisibility(View.GONE);
                mSarExpenses.setVisibility(View.GONE);
                mViewProfit.setVisibility(View.GONE);
                mTextViewProfit.setVisibility(View.GONE);
                mProfit.setVisibility(View.GONE);
                mSarProfit.setVisibility(View.GONE);
            }
        }

        private void translateDetails(int value, boolean animation){
            if (animation){
                mViewIncome.animate().translationY(value);
                mTextViewIncome.animate().translationY(value);
                mIncome.animate().translationY(value);
                mSarIncome.animate().translationY(value);
                mViewExpenses.animate().translationY(value);
                mTextViewExpenses.animate().translationY(value);
                mExpenses.animate().translationY(value);
                mSarExpenses.animate().translationY(value);
                mViewProfit.animate().translationY(value);
                mTextViewProfit.animate().translationY(value);
                mProfit.animate().translationY(value);
                mSarProfit.animate().translationY(value);
            } else {
                mViewIncome.setTranslationY(value);
                mTextViewIncome.setTranslationY(value);
                mIncome.setTranslationY(value);
                mSarIncome.setTranslationY(value);
                mViewExpenses.setTranslationY(value);
                mTextViewExpenses.setTranslationY(value);
                mExpenses.setTranslationY(value);
                mSarExpenses.setTranslationY(value);
                mViewProfit.setTranslationY(value);
                mTextViewProfit.setTranslationY(value);
                mProfit.setTranslationY(value);
                mSarProfit.setTranslationY(value);
            }
        }
    }
}
