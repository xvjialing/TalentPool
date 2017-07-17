package com.xvjialing.administrator.talentpool.ac;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.xvjialing.administrator.talentpool.R;
import com.xvjialing.administrator.talentpool.bean.LoginBean;
import com.xvjialing.administrator.talentpool.json.AppHttp;
import com.xvjialing.administrator.talentpool.utils.NetUtils;
import com.xvjialing.administrator.talentpool.utils.RequestUtils;
import com.xvjialing.administrator.talentpool.utils.SharedPreferencesUtils;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


@EActivity
public class ActivityLogin extends AppCompatActivity {

    private static final String url_login = AppHttp.Url + "login";
    private static final String TAG=ActivityLogin.class.getSimpleName();
    @ViewById(R.id.et_userName)
    EditText editText_userName;
    @ViewById(R.id.et_pwd)
    EditText editText_pwd;
    @ViewById(R.id.btn_login)
    Button button_login;
    @ViewById(R.id.ac_login_tv_register)
    TextView textView_register;
    private ProgressDialog pDialog;
    private String userName;
    private String password;
    private String userid;
    private RequestUtils requestUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_login);
    }

    @Override
    protected void onStart() {
        editText_userName.setText(SharedPreferencesUtils.getParam(ActivityLogin.this,"username","").toString());
        editText_pwd.setText(SharedPreferencesUtils.getParam(ActivityLogin.this,"pwd","").toString());
        super.onStart();
    }

    @Override
    protected void onResume() {
        editText_userName.setText(SharedPreferencesUtils.getParam(ActivityLogin.this, "username", "").toString());
        editText_pwd.setText(SharedPreferencesUtils.getParam(ActivityLogin.this, "pwd", "").toString());
        super.onResume();
    }

    @Click(R.id.btn_login)
    public void login(){
        userName=editText_userName.getText().toString();
        password=editText_pwd.getText().toString();

        if (TextUtils.equals(userName,"")||TextUtils.equals(password,"")){
            Toast.makeText(ActivityLogin.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
        }else {

            if (NetUtils.isConnected(ActivityLogin.this)){
                pDialog = new ProgressDialog(ActivityLogin.this);
                pDialog.setMessage("正在登陆..");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
                Map<String,String> params=new HashMap<>();
                params.put("user",userName);
                params.put("pwd",password);
                requestUtils = new RequestUtils(params,url_login);
                requestUtils.request().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {
                                pDialog.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                pDialog.dismiss();
                                Toast.makeText(ActivityLogin.this,"请求失败",Toast.LENGTH_SHORT).show();
                                Log.d(TAG,e.getMessage());
                            }

                            @Override
                            public void onNext(String s) {
                                Log.d(TAG,s);
                                LoginBean loginBean= JSON.parseObject(s,LoginBean.class);
                                if (TextUtils.equals("0",String.valueOf(loginBean.getLp()))){
                                    userid=loginBean.getData().getId().get(0).getId().toString();
                                    userName=loginBean.getData().getId().get(0).getUsername().toString();
                                    password=loginBean.getData().getId().get(0).getPassword().toString();
                                    SharedPreferencesUtils.setParam(ActivityLogin.this, "userId", userid);
                                    SharedPreferencesUtils.setParam(ActivityLogin.this,"username",userName);
                                    SharedPreferencesUtils.setParam(ActivityLogin.this,"pwd",password);
                                    Intent intent = new Intent(ActivityLogin.this, ActivityMain_.class);
                                    startActivity(intent);

                                    finish();
                                }
                            }
                        });
            }else {
                Toast.makeText(getApplicationContext(), "当前没有可用网络！", Toast.LENGTH_LONG).show();
            }

        }

    }


    @Click(R.id.ac_login_tv_register)
    public void register(){
        Intent intent=new Intent(ActivityLogin.this,ActivityRegister_.class);
        startActivity(intent);
    }

}
