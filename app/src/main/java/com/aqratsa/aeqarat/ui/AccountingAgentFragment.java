package com.aqratsa.aeqarat.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aqratsa.aeqarat.R;
import com.aqratsa.aeqarat.adapters.AccountingAgentAdapter;
import com.aqratsa.aeqarat.utils.SharedPrefUtil;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.aqratsa.aeqarat.utils.Constants.LOCALE;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountingAgentFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.toolbarAction1)
    ImageView mBack;
    @BindView(R.id.toolbarTitle1)
    TextView mTitle;
    @BindView(R.id.arrow)
    ImageView mArrow;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.from)
    TextView mFrom;
    @BindView(R.id.to)
    TextView mTo;

    private String mDate;

    public AccountingAgentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accounting_agent, container, false);
        ButterKnife.bind(this, view);
        String locale = SharedPrefUtil.getInstance(getActivity()).read(LOCALE, Locale.getDefault().getLanguage());
        if (locale.equals("ar")) {
            mBack.setImageResource(R.drawable.ic_arrow_ar);
            mArrow.setImageResource(R.drawable.ic_arrow_3_ar);
        } else {
            mBack.setImageResource(R.drawable.ic_arrow);
            mArrow.setImageResource(R.drawable.ic_arrow_3);
        }
        mTitle.setText(R.string.details_accounting);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        loadAccounting();
        return view;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        if (mDate.equals("FROM")){
            mFrom.setText(date);
        } else if (mDate.equals("TO")){
            mTo.setText(date);
        }
    }

    @OnClick(R.id.toolbarAction1)
    public void back() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.from)
    public void from(){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setLocale(Locale.ENGLISH);
        datePickerDialog.show(getFragmentManager(), "Datepickerdialog");
        mDate = "FROM";
    }

    @OnClick(R.id.to)
    public void to(){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setLocale(Locale.ENGLISH);
        datePickerDialog.show(getFragmentManager(), "Datepickerdialog");
        mDate = "TO";
    }

    private void loadAccounting() {
        AccountingAgentAdapter adapter;
        adapter = new AccountingAgentAdapter(getActivity(), position -> {
        }, position -> {
        });
        mRecyclerView.setAdapter(adapter);
    }
}
