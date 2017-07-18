package com.xvjialing.administrator.talentpool.ac;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xvjialing.administrator.talentpool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ActivityAboutUs extends AppCompatActivity {

    @BindView(R.id.ac_aboutUs_img_back)
    ImageView acAboutUsImgBack;
    @BindView(R.id.ac_aboutUs_tv_titlebar)
    TextView acAboutUsTvTitlebar;
    @BindView(R.id.ac_aboutUs_rl_titlebar)
    RelativeLayout acAboutUsRlTitlebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_about_us);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.ac_aboutUs_img_back)
    public void onViewClicked() {
        finish();
    }
}
