package com.aqratsa.aeqarat.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.models.slides.Slide;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.aqratsa.aeqarat.utils.Constants.BASE_URL;

public class SlidesAdapter extends SliderViewAdapter<SlidesAdapter.AdapterViewHolder> {

    private Context mContext;
    private List<Slide> mListSlides;

    public SlidesAdapter(Context context, List<Slide> listSlides) {
        mContext = context;
        mListSlides = listSlides;
    }

    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_slide, null);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder viewHolder, int position) {
        if (position == 2) {
            viewHolder.mTitle.setTextColor(Color.BLACK);
            viewHolder.mDescription.setTextColor(Color.BLACK);
            Glide.with(mContext).load(R.drawable.logo_aeqarat_v4).into(viewHolder.mLogo);
        } else {
            viewHolder.mTitle.setTextColor(mContext.getResources().getColor(R.color.yellow));
            viewHolder.mDescription.setTextColor(mContext.getResources().getColor(R.color.yellow));
            viewHolder.mLogo.setVisibility(View.INVISIBLE);
        }
        if (mListSlides.get(position).getImage() != null) {
            Glide.with(mContext).load(BASE_URL + mListSlides.get(position).getImage()).into(viewHolder.mBackground);
            viewHolder.mLogo.setVisibility(View.INVISIBLE);
        } else {
            Glide.with(mContext).load(mListSlides.get(position).getLocal_image()).into(viewHolder.mBackground);
        }

        viewHolder.mTitle.setText(mListSlides.get(position).getTitle());
        if (mListSlides.get(position).getDescription() != null)
            viewHolder.mDescription.setText(Html.fromHtml(mListSlides.get(position).getDescription()));
    }

    @Override
    public int getCount() {
        return mListSlides.size();
    }

    class AdapterViewHolder extends SliderViewAdapter.ViewHolder {

        @BindView(R.id.background)
        ImageView mBackground;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.description)
        TextView mDescription;
        @BindView(R.id.logo)
        ImageView mLogo;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
