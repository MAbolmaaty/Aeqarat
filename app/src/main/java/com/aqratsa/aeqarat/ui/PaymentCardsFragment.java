package com.aqratsa.aeqarat.ui;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.adapters.PaymentCardsAdapter;
import com.aqratsa.aeqarat.models.login.response.LoginModelResponse;
import com.aqratsa.aeqarat.models.payment_card_delete.PaymentCardDeleteModelResponse;
import com.aqratsa.aeqarat.models.payment_cards.PaymentCard;
import com.aqratsa.aeqarat.models.payment_cards.PaymentCardsModelResponse;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;
import com.aqratsa.aeqarat.utils.interfaces.DeleteClickHandler;
import com.aqratsa.aeqarat.viewmodels.LoginViewModel;
import com.aqratsa.aeqarat.viewmodels.PaymentCardDefaultViewModel;
import com.aqratsa.aeqarat.viewmodels.PaymentCardDeleteViewModel;
import com.aqratsa.aeqarat.viewmodels.PaymentCardsViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aqratsa.aeqarat.ui.MainActivity.loadFragment;
import static com.aqratsa.aeqarat.utils.Constants.LOCALE;
import static com.aqratsa.aeqarat.utils.Constants.SUCCESS;
import static com.aqratsa.aeqarat.utils.Constants.USER_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentCardsFragment extends Fragment {


    @BindView(R.id.noCards)
    TextView mNoCards;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress)
    ProgressBar mProgress;

    private PaymentCardsViewModel mViewModelPaymentCards;
    private PaymentCardsAdapter mAdapterCards;
    private PaymentCardDeleteViewModel mViewModelDelete;
    private PaymentCardDefaultViewModel mViewModelDefaultCard;
    private ArrayList<PaymentCard> mListCards = new ArrayList<>();
    private Toast mToast;
    private String mUserId;
    private String mLocale;

    public PaymentCardsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_cards, container, false);
        ButterKnife.bind(this, view);

        new Handler().postDelayed(() -> {
            if (getActivity() != null) {
                mViewModelPaymentCards = ViewModelProviders.of(this).get(PaymentCardsViewModel.class);
                mViewModelDelete = ViewModelProviders.of(this).get(PaymentCardDeleteViewModel.class);
                mViewModelDefaultCard = ViewModelProviders.of(PaymentCardsFragment.this).get(PaymentCardDefaultViewModel.class);

                mLocale = SharedPrefUtil.getInstance(getActivity()).read(LOCALE, Locale.getDefault().getLanguage());
                if (mLocale.equals("ar"))
                    view.setRotation(-180);

                LinearLayoutManager layoutManager =
                        new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setLayoutManager(layoutManager);

                mUserId = SharedPrefUtil.getInstance(getContext()).read(USER_ID, null);
                if (mUserId == null) {
                    LoginViewModel viewModelLogin = ViewModelProviders.of(getActivity()).get(LoginViewModel.class);
                    viewModelLogin.getUser().observe(PaymentCardsFragment.this, loginModelResponse -> {
                        mUserId = loginModelResponse.getUser().getId();
                        getPaymentCards();
                    });
                } else {
                    getPaymentCards();
                }
            }
        }, 296);

        return view;
    }

    @OnClick(R.id.addNewCard)
    public void addNewCard() {
        loadFragment(PaymentCardsFragment.this.getActivity().getSupportFragmentManager(),
                new PaymentCardAddFragment(), true);
    }

    private void getPaymentCards(){
        mViewModelPaymentCards.paymentCards(mUserId);
        mViewModelPaymentCards.getPaymentCards().observe(this, paymentCardsModelResponse -> {
            if (paymentCardsModelResponse.getKey().equals(SUCCESS)){
                mListCards.clear();
                mListCards.addAll(Arrays.asList(paymentCardsModelResponse.getPaymentCards()));
                if (mListCards.size() < 1)
                    mNoCards.setVisibility(View.VISIBLE);
                else
                    mNoCards.setVisibility(View.INVISIBLE);
                mAdapterCards = new PaymentCardsAdapter(getActivity(), mListCards, position -> {
                    mViewModelDelete.deleteCard(mListCards.get(position).getId());
                    mViewModelDelete.isLoading().observe(PaymentCardsFragment.this, loading -> {
                        if (loading) {
                            mProgress.setVisibility(View.VISIBLE);
                        } else {
                            mProgress.setVisibility(View.INVISIBLE);
                        }
                    });
                    mViewModelDelete.getResult().observe(PaymentCardsFragment.this, paymentCardDeleteModelResponse -> {
                        if (paymentCardDeleteModelResponse.getKey().equals(SUCCESS)) {
                            mViewModelPaymentCards.paymentCards(mUserId);
                            mListCards.remove(position);
                            mAdapterCards.notifyDataSetChanged();
                        }
                    });
                }, position -> mViewModelDefaultCard.defaultCard(mListCards.get(position).getId()));

                mRecyclerView.setAdapter(mAdapterCards);
            } else {
                mToast = Toast.makeText(getActivity(), R.string.something_went_wrong, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
        mViewModelPaymentCards.isLoading().observe(this, loading -> {
            if (loading){
                mProgress.setVisibility(View.VISIBLE);
            } else {
                mProgress.setVisibility(View.INVISIBLE);
            }
        });
        mViewModelPaymentCards.failure().observe(this, failure -> {
            if (failure){
                if (mToast != null)
                    mToast.cancel();
                mToast = Toast.makeText(getActivity(), R.string.connection_to_server_lost, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }

    public static PaymentCardsFragment newInstance() {
        return new PaymentCardsFragment();
    }
}
