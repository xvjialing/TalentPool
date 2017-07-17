package com.xvjialing.administrator.talentpool.fg;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xvjialing.administrator.talentpool.R;
import com.xvjialing.administrator.talentpool.ac.ActivityJobCollection_;
import com.xvjialing.administrator.talentpool.ac.ActivityQualityTest_;
import com.xvjialing.administrator.talentpool.ac.ActivityResume_;
import com.xvjialing.administrator.talentpool.ac.ActivitySetting_;
import com.xvjialing.administrator.talentpool.ac.ActivitySuggestion_;
import com.xvjialing.administrator.talentpool.utils.SharedPreferencesUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment
public class FragmentMe extends Fragment {

    private static final int REQUEST_PERMISSION_CAMERA_CODE = 1;
    @ViewById(R.id.fg_me_rl_resume)
    RelativeLayout relativeLayout_resume;
    @ViewById(R.id.fg_me_img_avater)
    CircleImageView circleImageView_avater;
    @ViewById(R.id.fg_me_rl_setting)
    RelativeLayout rl_setting;
    @ViewById(R.id.fg_me_rl_qualityTest)
    RelativeLayout rl_qualityTest;
    @ViewById(R.id.fg_me_tv_recent_status)
    TextView tv_recent_status;
    @ViewById(R.id.fg_me_tv_username)
    TextView tv_username;
    @ViewById(R.id.fg_me_img_resume_redPoint)
    ImageView img_resume_redPoint;
    @ViewById(R.id.fg_me_img_qualityTest_redPoint)
    ImageView img_qualityTest_redPoint;
    @ViewById(R.id.fg_me_rl_suggestion)
    RelativeLayout rl_suggestion;
    @ViewById(R.id.fg_me_rl_collection)
    RelativeLayout rl_collection;

    public FragmentMe() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fg_me, container, false);
    }

    @Click(R.id.fg_me_rl_resume)
    public void clickResume(){
        SharedPreferencesUtils.setParam(getContext(), "resumeRedPointTag", "1");
        Intent intent=new Intent(getContext(), ActivityResume_.class);
        startActivity(intent);
    }

    @Click(R.id.fg_me_rl_collection)
    public void jobCollection() {
        startActivity(new Intent(getContext(), ActivityJobCollection_.class));
    }

    @Click(R.id.fg_me_rl_setting)
    public void setting(){
        Intent intent=new Intent(getContext(), ActivitySetting_.class);
        startActivity(intent);
    }

    @Click(R.id.fg_me_rl_qualityTest)
    public void qualityTest() {
        SharedPreferencesUtils.setParam(getContext(), "qualityTestRedPointTag", "1");
        startActivity(new Intent(getContext(), ActivityQualityTest_.class));
    }

    @Click(R.id.fg_me_img_avater)
    public void changeAvater() {
        startActivity(new Intent(getContext(), ActivityResume_.class));
    }

    //意见反馈
    @Click(R.id.fg_me_rl_suggestion)
    public void suggestion() {
        startActivity(new Intent(getContext(), ActivitySuggestion_.class));
    }


    @AfterViews
    public void setAvater() {
        String avaterImgTag =SharedPreferencesUtils.getParam(getContext(), "avaterImgTag","0")+"";
        if (avaterImgTag =="1") {
            String avaterUri = SharedPreferencesUtils.getParam(getContext(), "avaterUri", "fefwefw").toString();
            if (avaterUri != null) {
                circleImageView_avater.setImageURI(null);
                circleImageView_avater.setImageURI(Uri.parse(avaterUri));
            }
        }

    }

    @Override
    public void onStart() {
        tv_username.setText(SharedPreferencesUtils.getParam(getContext(),"resume_name","用户名").toString());
        tv_recent_status.setText(SharedPreferencesUtils.getParam(getContext(),"resume_recentStatus","当前状态").toString());


        String avaterImgTag=SharedPreferencesUtils.getParam(getContext(),"avaterImgTag","0").toString();
        if (avaterImgTag=="1"){
            String avaterUri=SharedPreferencesUtils.getParam(getContext(),"avaterUri","fefwefw").toString();
            if (avaterUri!=null){
                circleImageView_avater.setImageURI(null);
                circleImageView_avater.setImageURI(Uri.parse(avaterUri));
            }
        }

        if (TextUtils.equals(SharedPreferencesUtils.getParam(getContext(), "resumeRedPointTag", "0").toString(), "1")) {
            img_resume_redPoint.setVisibility(View.GONE);
        } else {
            img_resume_redPoint.setVisibility(View.VISIBLE);
        }

        if (TextUtils.equals(SharedPreferencesUtils.getParam(getContext(), "qualityTestRedPointTag", "0").toString(), "1")) {
            img_qualityTest_redPoint.setVisibility(View.GONE);
        } else {
            img_qualityTest_redPoint.setVisibility(View.VISIBLE);
        }
        super.onStart();
    }

    @Override
    public void onResume() {
        tv_username.setText(SharedPreferencesUtils.getParam(getContext(),"resume_name","用户名").toString());
        tv_recent_status.setText(SharedPreferencesUtils.getParam(getContext(),"resume_recentStatus","当前状态").toString());


        String avaterImgTag=SharedPreferencesUtils.getParam(getContext(),"avaterImgTag","0")+"";
        if (avaterImgTag=="0"){
            String avaterUri=SharedPreferencesUtils.getParam(getContext(),"avaterUri","fefwefw").toString();
            if (avaterUri!=null){
                circleImageView_avater.setImageURI(null);
                circleImageView_avater.setImageURI(Uri.parse(avaterUri));
            }
        }

        if (TextUtils.equals(SharedPreferencesUtils.getParam(getContext(), "resumeRedPointTag", "0").toString(), "1")) {
            img_resume_redPoint.setVisibility(View.GONE);
        } else {
            img_resume_redPoint.setVisibility(View.VISIBLE);
        }

        if (TextUtils.equals(SharedPreferencesUtils.getParam(getContext(), "qualityTestRedPointTag", "0").toString(), "1")) {
            img_qualityTest_redPoint.setVisibility(View.GONE);
        } else {
            img_qualityTest_redPoint.setVisibility(View.VISIBLE);
        }

        super.onResume();
    }
}
