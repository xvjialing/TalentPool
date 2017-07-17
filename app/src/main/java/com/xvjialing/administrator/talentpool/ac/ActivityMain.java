package com.xvjialing.administrator.talentpool.ac;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xvjialing.administrator.talentpool.R;
import com.xvjialing.administrator.talentpool.fg.FragmentConnection_;
import com.xvjialing.administrator.talentpool.fg.FragmentHome_;
import com.xvjialing.administrator.talentpool.fg.FragmentMe_;
import com.xvjialing.administrator.talentpool.fg.FragmentMessage_;
import com.xvjialing.administrator.talentpool.utils.SharedPreferencesUtils;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class ActivityMain extends AppCompatActivity {

    @ViewById(R.id.rl_bottom_home)
    RelativeLayout rl_bottom_home;
    @ViewById(R.id.rl_bottom_connection)
    RelativeLayout rl_bottom_connection;
    @ViewById(R.id.rl_bottom_message)
    RelativeLayout rl_bottom_message;
    @ViewById(R.id.rl_bottom_me)
    RelativeLayout rl_bottom_me;
    private FragmentHome_ fragmentHome;
    private FragmentConnection_ fragmentConnection;
    private FragmentMessage_ fragmentMessage;
    private FragmentMe_ fragmentMe;
    private long mExitTime = 0;
    private Context mContext = ActivityMain.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);

        select(0);
    }

    @Click(R.id.rl_bottom_home)
    public void clickHome(){
        select(0);
    }

    @Click(R.id.rl_bottom_connection)
    public void clickConnection(){
        select(1);
    }

    @Click(R.id.rl_bottom_message)
    public void clickMessage(){
        select(2);
    }

    @Click(R.id.rl_bottom_me)
    public void clickMe(){
        select(3);
    }

    private void select(int i) {
        FragmentManager fm=this.getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        switch (i){
            case 0:
                fragmentHome=new FragmentHome_();
                ft.replace(R.id.fl_main_content,fragmentHome);

                rl_bottom_home.setSelected(true);
                rl_bottom_connection.setSelected(false);
                rl_bottom_message.setSelected(false);
                rl_bottom_me.setSelected(false);
                break;
            case 1:
                fragmentConnection=new FragmentConnection_();
                ft.replace(R.id.fl_main_content,fragmentConnection);

                rl_bottom_home.setSelected(false);
                rl_bottom_connection.setSelected(true);
                rl_bottom_message.setSelected(false);
                rl_bottom_me.setSelected(false);
                break;
            case 2:
                fragmentMessage=new FragmentMessage_();
                ft.replace(R.id.fl_main_content,fragmentMessage);

                rl_bottom_home.setSelected(false);
                rl_bottom_connection.setSelected(false);
                rl_bottom_message.setSelected(true);
                rl_bottom_me.setSelected(false);
                break;
            case 3:
                fragmentMe=new FragmentMe_();
                ft.replace(R.id.fl_main_content,fragmentMe);

                rl_bottom_home.setSelected(false);
                rl_bottom_connection.setSelected(false);
                rl_bottom_message.setSelected(false);
                rl_bottom_me.setSelected(true);
                break;
        }
        ft.commit();
    }

    @Override
    protected void onStart() {
        if (TextUtils.equals(SharedPreferencesUtils.getParam(mContext, "compeleteInfoTag", "0").toString(), "0")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("温馨提示");
            builder.setMessage("前往完善简历信息");
            builder.setPositiveButton("前往", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    startActivity(new Intent(ActivityMain.this, ActivityResume_.class));
                    SharedPreferencesUtils.setParam(mContext, "compeleteInfoTag", "1");
                }
            });
            builder.setNegativeButton("不了", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    SharedPreferencesUtils.setParam(mContext, "compeleteInfoTag", "1");
                }
            });
            builder.show();
        }
        super.onStart();
    }

    //快速双击退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            System.exit(0);
        }
    }
}
