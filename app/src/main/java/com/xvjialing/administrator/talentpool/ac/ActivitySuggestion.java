package com.xvjialing.administrator.talentpool.ac;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.xvjialing.administrator.talentpool.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class ActivitySuggestion extends AppCompatActivity {

    @ViewById(R.id.ac_suggestion_img_back)
    ImageView img_back;
    @ViewById(R.id.ac_suggestion_btn_submit)
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_suggestion);
    }

    @Click(R.id.ac_suggestion_img_back)
    public void back() {
        finish();
    }

    @Click(R.id.ac_suggestion_btn_submit)
    public void submit() {
        finish();
    }

}
