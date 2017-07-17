package com.xvjialing.administrator.talentpool.ac;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.xvjialing.administrator.talentpool.R;
import com.xvjialing.administrator.talentpool.utils.SharedPreferencesUtils;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class ActivityRemarks extends AppCompatActivity {

    @ViewById(R.id.ac_remarks_img_back)
    ImageView img_back;

    @ViewById(R.id.ac_remarks_et_remarksText)
    EditText et_remarksText;

    @ViewById(R.id.ac_remarks_btn_preservation)
    Button btn_preservation;

    private String i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_remarks);
    }

    @Click(R.id.ac_remarks_img_back)
    public void back() {
        finish();
    }

    @Click(R.id.ac_remarks_btn_preservation)
    public void preservation() {
        String remarksText = et_remarksText.getText().toString();
        i=SharedPreferencesUtils.getParam(ActivityRemarks.this,"jobTag",1).toString();
        SharedPreferencesUtils.setParam(ActivityRemarks.this, "remarksContent"+i, remarksText);
        finish();
    }

    @Override
    protected void onStart() {
        et_remarksText.setText(SharedPreferencesUtils.getParam(ActivityRemarks.this, "remarksContent"+i, "").toString());
        super.onStart();
    }
}
