package com.xvjialing.administrator.talentpool.ac;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xvjialing.administrator.talentpool.R;
import com.xvjialing.administrator.talentpool.json.AppHttp;
import com.xvjialing.administrator.talentpool.utils.NetUtils;
import com.xvjialing.administrator.talentpool.utils.SharedPreferencesUtils;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@EActivity
public class ActivityRegister extends AppCompatActivity {

    private static final String url = AppHttp.Url_IP + "opensns/index.php?s=/Home/rencai/register";
    @ViewById(R.id.ac_register_et_username)
    EditText et_username;
    @ViewById(R.id.ac_register_et_pwd)
    EditText et_pwd;
    @ViewById(R.id.ac_register_et_checkPwd)
    EditText et_checkPwd;
    @ViewById(R.id.ac_register_btn_register)
    Button btn_register;
    private OkHttpClient okHttpClient;
    private String json;
    private JSONObject jsonObject;
    private ProgressDialog pDialog;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_register);
    }

    @Click(R.id.ac_register_btn_register)
    public void clickRegister() {

        if (TextUtils.equals("", et_username.getText()) || TextUtils.equals("", et_pwd.getText())) {
            Toast.makeText(ActivityRegister.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            pDialog = new ProgressDialog(ActivityRegister.this);
            pDialog.setMessage("正在注册..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

            if (TextUtils.equals(et_pwd.getText(), et_checkPwd.getText())) {
                if (NetUtils.isConnected(ActivityRegister.this)) {

                    okHttpClient = new OkHttpClient();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            FormBody formBody = new FormBody.Builder()
                                    .add("user", et_username.getText().toString())
                                    .add("pwd", et_pwd.getText().toString())
                                    .build();
                            Request request = new Request.Builder()
                                    .url(url)
                                    .post(formBody)
                                    .build();
                            okHttpClient.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {

                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    json = response.body().string();
                                    Log.i("test", json);
                                    try {
                                        jsonObject = new JSONObject(json);
                                        String s = jsonObject.getInt("lp") + "";

                                        userId = jsonObject.getJSONObject("data").getInt("id") + "";
                                        Log.i("fwefwefwe", String.valueOf(userId));

                                        Log.i("test", s);
                                        if (TextUtils.equals(s, "0")) {
                                            SharedPreferences preferences = getSharedPreferences("share_date", MODE_APPEND);
                                            SharedPreferences.Editor editor = preferences.edit();
                                            editor.clear();
                                            editor.commit();
                                            SharedPreferencesUtils.setParam(ActivityRegister.this, "username", et_username.getText());
                                            SharedPreferencesUtils.setParam(ActivityRegister.this, "pwd", et_pwd.getText());
                                            SharedPreferencesUtils.setParam(ActivityRegister.this, "userId", userId);
                                            pDialog.dismiss();
                                            startActivity(new Intent(ActivityRegister.this, ActivityCompleteInfo_.class));
                                            finish();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                        }
                    }).start();

                } else {
                    Toast.makeText(ActivityRegister.this, "当前没有可用网络！", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(ActivityRegister.this, "两次密码不相同", Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        }


    }

}
