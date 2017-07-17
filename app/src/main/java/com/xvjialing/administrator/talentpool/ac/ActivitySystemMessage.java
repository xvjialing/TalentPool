package com.xvjialing.administrator.talentpool.ac;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xvjialing.administrator.talentpool.R;
import com.xvjialing.administrator.talentpool.utils.SharedPreferencesUtils;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class ActivitySystemMessage extends AppCompatActivity {

    @ViewById(R.id.ac_systemMessage_img_back)
    ImageView img_back;

    @ViewById(R.id.ac_systemMessage_ll_firstMessage)
    LinearLayout ll_firstMessage;

    @ViewById(R.id.ac_systemMessage_ll_feedback)
    LinearLayout ll_feedback;

    @ViewById(R.id.ac_systemMessage_tv_feedbackTime)
    TextView tv_feedbackTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_system_message);
    }

    @Click(R.id.ac_systemMessage_img_back)
    public void back() {
        finish();
    }

    @Click(R.id.ac_systemMessage_ll_firstMessage)
    public void firstTest() {
        startActivity(new Intent(ActivitySystemMessage.this, ActivityFirstInterviewTest_.class));
        SharedPreferencesUtils.setParam(ActivitySystemMessage.this, "feedbackMessageTag", "1");
    }

    @Click(R.id.ac_systemMessage_ll_feedback)
    public void feedBack(){
        startActivity(new Intent(ActivitySystemMessage.this,ActivityFeedback_.class));
    }

    @Override
    protected void onStart() {
        if (TextUtils.equals(SharedPreferencesUtils.getParam(ActivitySystemMessage.this, "feedbackMessageTag", "0").toString(), "1")) {
            tv_feedbackTime.setVisibility(View.VISIBLE);
            ll_feedback.setVisibility(View.VISIBLE);
        }
        super.onStart();
    }
}
