package com.xvjialing.administrator.talentpool.ac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xvjialing.administrator.talentpool.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class ActivitySetting extends AppCompatActivity {

    @ViewById(R.id.ac_setting_img_back)
    ImageView img_back;

    @ViewById(R.id.ac_setting_rl_clearBuffer)
    RelativeLayout rl_clearBuffer;

    @ViewById(R.id.ac_setting_btn_logout)
    Button btn_logout;

    @ViewById(R.id.ac_setting_rl_modifyPassword)
    RelativeLayout rl_modifyPassword;

    @ViewById(R.id.ac_setting_rl_privacy)
    RelativeLayout rl_privacy;

    @ViewById(R.id.ac_setting_rl_blackList)
    RelativeLayout rl_blackList;

    @ViewById(R.id.ac_setting_rl_checkupdate)
    RelativeLayout rl_checkupdate;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_setting);
    }

    @AfterViews
    public void initSharedPreferences() {
        preferences = getSharedPreferences("share_date", MODE_APPEND);

        editor = preferences.edit();
    }

    @Click(R.id.ac_setting_img_back)
    public void back(){
        finish();
    }

    @Click(R.id.ac_setting_rl_clearBuffer)
    public void clearBuffer() {
        editor.clear();
        editor.commit();
        Toast.makeText(ActivitySetting.this, "缓存清除成功", Toast.LENGTH_SHORT).show();
    }

    @Click(R.id.ac_setting_btn_logout)
    public void logout() {
        startActivity(new Intent(ActivitySetting.this, ActivityLogin.class));
        finish();
    }

    @Click(R.id.ac_setting_rl_modifyPassword)
    public void modifyPassword() {
        startActivity(new Intent(ActivitySetting.this, ActivityModifyPassword_.class));
    }

    @Click(R.id.ac_setting_rl_privacy)
    public void privacy() {
        startActivity(new Intent(ActivitySetting.this, ActivityPrivacy_.class));
    }

    @Click(R.id.ac_setting_rl_blackList)
    public void blackLis() {
        startActivity(new Intent(ActivitySetting.this, ActivityBlackList_.class));
    }

    @Click(R.id.ac_setting_rl_checkupdate)
    public void checkupdate(){

    }
}
