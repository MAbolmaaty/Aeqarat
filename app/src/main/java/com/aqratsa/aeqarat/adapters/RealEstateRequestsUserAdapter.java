package com.aqratsa.aeqarat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.models.real_estate_requests_user.Request;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.aqratsa.aeqarat.utils.Constants.BASE_URL;

public class RealEstateRequestsUserAdapter extends RecyclerView.Adapter<RealEstateRequestsUserAdapter.AdapterViewHolder>{

    public interface RequestOnClickHandler{
        void onClick(int position);
    }

    private Context mContext;
    private List<Request> mListRealEstateRequests;
    private RequestOnClickHandler mClickHandler;

    public RealEstateRequestsUserAdapter(Context context, List<Request> listRealEstateRequests,
                                         RequestOnClickHandler clickHandler) {
        mContext = context;
        mListRealEstateRequests = listRealEstateRequests;
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_real_estate_request_user,
                parent, false);
        view.setFocusable(true);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        Request request = mListRealEstateRequests.get(position);
        Glide.with(mContext)
                .load(BASE_URL + request.getUser().getPhoto())
                .into(holder.mUserImage);
        if (request.getDateExit() != null){
            holder.mRequestType.setText(R.string.request_termination);
        } else {
            holder.mRequestType.setText(R.string.request_maintenance);
        }
        holder.mUserName.setText(request.getUser().getName());
        holder.mDetails.setText(R.string.request_maintenance);
    }

    @Override
    public int getItemCount() {
        return mListRealEstateRequests.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.userImage)
        CircleImageView mUserImage;
        @BindView(R.id.requestType)
        TextView mRequestType;
        @BindView(R.id.userName)
        TextView mUserName;
        @BindView(R.id.details)
        TextView mDetails;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View view) {
            mClickHandler.onClick(getAdapterPosition());
        }
    }
}
