package com.xvjialing.administrator.talentpool.ac;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.xvjialing.administrator.talentpool.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class ActivityFeedback extends AppCompatActivity {

    @ViewById(R.id.ac_feedBack_img_back)
    ImageView img_back;

    @ViewById(R.id.ac_feedBack_btn_submit)
    Button btn_submit;

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_feedback);
    }

    @Click(R.id.ac_feedBack_img_back)
    public void back(){
        finish();
    }

    @Click(R.id.ac_feedBack_btn_submit)
    public void submit(){
        pDialog = new ProgressDialog(ActivityFeedback.this);
        pDialog.setMessage("正在提交..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
        Handler handler=new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                pDialog.dismiss();
                finish();
            }
        };
        handler.postDelayed(runnable,500);

    }
}
