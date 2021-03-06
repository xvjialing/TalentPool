package com.xvjialing.administrator.talentpool.ac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.xvjialing.administrator.talentpool.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class ActivityDeliverFeedbak extends AppCompatActivity {

    @ViewById(R.id.ac_deliverFeedback_img_back)
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_deliver_feedbak);
    }

    @Click(R.id.ac_deliverFeedback_img_back)
    public void back(){
        finish();
    }
}
