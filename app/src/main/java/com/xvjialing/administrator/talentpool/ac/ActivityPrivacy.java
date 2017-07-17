package com.xvjialing.administrator.talentpool.ac;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Switch;

import com.xvjialing.administrator.talentpool.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class ActivityPrivacy extends AppCompatActivity {

    @ViewById(R.id.ac_privacy_img_back)
    ImageView img_back;

    @ViewById(R.id.ac_privacy_switch_anonymous)
    Switch switch_anonymous;

    @ViewById(R.id.ac_privacy_switch_closeResume)
    Switch switch_closeResume;

    @ViewById(R.id.ac_privacy_switch_hiddenContacts)
    Switch switch_hiddenContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_privacy);
    }

    @Click(R.id.ac_privacy_img_back)
    public void back() {
        finish();
    }
//
//    @AfterViews
//    public void initSwitch(){
//        switch_anonymous.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    SharedPreferencesUtils.setParam(ActivityPrivacy.this,"switchAnonymousTag","1");
//                }else {
//                    SharedPreferencesUtils.setParam(ActivityPrivacy.this,"switchAnonymousTag","0");
//                }
//
//            }
//        });
//
//        switch_closeResume.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    SharedPreferencesUtils.setParam(ActivityPrivacy.this,"switchCloseResumeTag","1");
//                }else {
//                    SharedPreferencesUtils.setParam(ActivityPrivacy.this,"switchCloseResumeTag","0");
//                }
//
//            }
//        });
//
//        switch_hiddenContacts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    SharedPreferencesUtils.setParam(ActivityPrivacy.this,"switchHiddenContactsTag","1");
//                }else {
//                    SharedPreferencesUtils.setParam(ActivityPrivacy.this,"switchHiddenContactsTag","0");
//                }
//
//            }
//        });
//    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (TextUtils.equals(SharedPreferencesUtils.getParam(ActivityPrivacy.this,
//                "switchAnonymousTag","0").toString(),"1")){
//            switch_anonymous.setChecked(true);
//        }else {
//            switch_anonymous.setChecked(false);
//        }
//
//        if (TextUtils.equals(SharedPreferencesUtils.getParam(ActivityPrivacy.this,
//                "switchCloseResumeTag","0").toString(),"1")){
//            switch_closeResume.setChecked(true);
//        }else {
//            switch_closeResume.setChecked(false);
//        }
//
//        if (TextUtils.equals(SharedPreferencesUtils.getParam(ActivityPrivacy.this,
//                "switchHiddenContactsTag","0").toString(),"1")){
//            switch_hiddenContacts.setChecked(true);
//        }else {
//            switch_hiddenContacts.setChecked(false);
//        }
//    }
}
