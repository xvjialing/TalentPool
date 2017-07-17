package com.xvjialing.administrator.talentpool.ac;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xvjialing.administrator.talentpool.R;
import com.xvjialing.administrator.talentpool.json.AppHttp;
import com.xvjialing.administrator.talentpool.json.JSONParser;
import com.xvjialing.administrator.talentpool.utils.SharedPreferencesUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@EActivity
public class ActivityFriend extends AppCompatActivity {

    @ViewById(R.id.ac_friend_img_back)
    ImageView img_back;

    @ViewById(R.id.ac_friend_tv_username)
    TextView tv_username;

    @ViewById(R.id.ac_friend_tv_adrress)
    TextView tv_address;

    @ViewById(R.id.ac_friend_tv_position)
    TextView tv_position;

    @ViewById(R.id.ac_friend_tv_company)
    TextView tv_company;

    @ViewById(R.id.ac_friend_tv_skill)
    TextView tv_skill;

    @ViewById(R.id.ac_friend_img_setting)
    ImageView img_setting;

    @ViewById(R.id.ac_friend_btn_friend)
    Button btn_friend;

    private Context mContext=ActivityFriend.this;
    private String friendId,status;
    private static final String url_friend_add = AppHttp.Url + "renadd";
    private static final String url_friend_show = AppHttp.Url + "friendshow";
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_friend);
        friendId=SharedPreferencesUtils.getParam(mContext,"ren_id","")+"";
        status=SharedPreferencesUtils.getParam(mContext,"status","0")+"";
        Log.i("ren_id+status",friendId+"+"+status);
    }


    public void compeleteInfo(){
        tv_username.setText(SharedPreferencesUtils.getParam(mContext,"name","用户名").toString());
        tv_address.setText(SharedPreferencesUtils.getParam(mContext,"province","地址").toString());
        tv_position.setText(SharedPreferencesUtils.getParam(mContext,"position","职位").toString());
        tv_company.setText(SharedPreferencesUtils.getParam(mContext,"companyname","公司名称").toString());
        tv_skill.setText(SharedPreferencesUtils.getParam(mContext,"skill","技能").toString());
    }

    @Click(R.id.ac_friend_img_back)
    public void back(){
        finish();
    }

    @Click(R.id.ac_friend_img_setting)
    public void setting(){
        startActivity(new Intent(mContext,ActivityFriendSetting_.class));
    }

    @Click(R.id.ac_friend_btn_friend)
    public void btn_friend(){
        if (TextUtils.equals("0",status)){
            new ListData().execute();
            status="1";
        }else {
            startActivity(new Intent(ActivityFriend.this,ActivitySendMessage_.class));
        }

    }

    private class ListData extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(ActivityFriend.this);
            pDialog.setMessage("正在加载..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            JSONParser jsonParser=new JSONParser();
            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("renId",friendId));
            params1.add(new BasicNameValuePair("status","1"));
            JSONObject jsonObject=jsonParser.makeHttpRequest(url_friend_add,"POST",params1);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            btn_friend.setText("发消息");
            btn_friend.setBackgroundColor(getResources().getColor(R.color.obj_on));
            pDialog.dismiss();
            super.onPostExecute(s);
        }
    }

    @Override
    protected void onStart() {
        compeleteInfo();
        if (TextUtils.equals("0",status)){
            btn_friend.setText("加好友");
            btn_friend.setBackgroundColor(getResources().getColor(R.color.red));
        }else {
            btn_friend.setText("发消息");
            btn_friend.setBackgroundColor(getResources().getColor(R.color.obj_on));
        }
        super.onStart();
    }

    @Override
    protected void onResume() {
        compeleteInfo();
        if (TextUtils.equals("0",status)){
            btn_friend.setText("加好友");
            btn_friend.setBackgroundColor(getResources().getColor(R.color.red));
        }else {
            btn_friend.setText("发消息");
            btn_friend.setBackgroundColor(getResources().getColor(R.color.obj_on));
        }
        super.onResume();
    }
}
