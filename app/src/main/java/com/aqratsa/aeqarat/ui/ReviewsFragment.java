package com.aqratsa.aeqarat.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.adapters.ReviewsAdapter;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.aqratsa.aeqarat.utils.Constants.LOCALE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewsFragment extends Fragment {

    @BindView(R.id.reviews)
    RecyclerView mReviews;

    private ReviewsAdapter mReviewsAdapter;

    public ReviewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
        String locale = SharedPrefUtil.getInstance(getActivity()).read(LOCALE, Locale.getDefault().getLanguage());
        if (locale.equals("ar"))
            view.setRotation(-180);
        ButterKnife.bind(this, view);

        /*mReviewsAdapter = new ReviewsAdapter(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        mReviews.setLayoutManager(layoutManager);
        mReviews.setAdapter(mReviewsAdapter);
        mReviews.setHasFixedSize(true);*/

        return view;
    }

    public static ReviewsFragment newInstance(){
        return new ReviewsFragment();
    }
}
