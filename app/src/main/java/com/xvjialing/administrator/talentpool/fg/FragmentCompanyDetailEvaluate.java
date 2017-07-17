package com.xvjialing.administrator.talentpool.fg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xvjialing.administrator.talentpool.R;
import com.xvjialing.administrator.talentpool.utils.SharedPreferencesUtils;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;


@EFragment
public class FragmentCompanyDetailEvaluate extends Fragment{

    public FragmentCompanyDetailEvaluate() {
        // Required empty public constructor
    }

    @ViewById(R.id.fg_companyDetailEveluate_ll_noData)
    LinearLayout ll_noData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fg_company_detail_evaluate, container, false);
    }

    @Override
    public void onStart() {
        if (TextUtils.equals(SharedPreferencesUtils.getParam(getContext(), "fg_companyEvaluate_noDataTag", "0").toString(), "1")) {
            ll_noData.setVisibility(View.GONE);
        } else {
            ll_noData.setVisibility(View.VISIBLE);
        }
        super.onStart();
    }
}

