package com.xvjialing.administrator.talentpool.ac;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xvjialing.administrator.talentpool.R;
import com.xvjialing.administrator.talentpool.utils.SharedPreferencesUtils;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class ActivityRecentVisitor extends AppCompatActivity {

    @ViewById(R.id.ac_recentVisitor_img_back)
    ImageView img_back;

    @ViewById(R.id.ac_recentVisitor_ll_noData)
    LinearLayout ll_noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_recent_visitor);
    }

    @Click(R.id.ac_recentVisitor_img_back)
    public void back(){
        finish();
    }

    @Override
    protected void onStart() {
        if (TextUtils.equals(SharedPreferencesUtils.getParam(ActivityRecentVisitor.this, "ac_recentVisitor_noDataTag", "0").toString(), "1")) {
            ll_noData.setVisibility(View.GONE);
        } else {
            ll_noData.setVisibility(View.VISIBLE);
        }
        super.onStart();
    }
}
