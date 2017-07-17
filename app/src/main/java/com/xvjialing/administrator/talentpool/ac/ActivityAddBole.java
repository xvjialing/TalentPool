package com.xvjialing.administrator.talentpool.ac;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.xvjialing.administrator.talentpool.R;
import com.xvjialing.administrator.talentpool.json.AppHttp;
import com.xvjialing.administrator.talentpool.utils.SharedPreferencesUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@EActivity
public class ActivityAddBole extends AppCompatActivity {

    @ViewById(R.id.ac_bole_img_back)
    ImageView img_back;
    @ViewById(R.id.ac_bole_et_boleName)
    EditText et_boleName;
    @ViewById(R.id.ac_bole_sp_relation)
    Spinner sp_relation;
    @ViewById(R.id.ac_bole_et_phone)
    EditText et_phone;
    @ViewById(R.id.ac_bole_btn_submit)
    Button btn_submit;
    private static final String url_add = AppHttp.Url_IP + "opensns/index.php?s=/Home/rencai/bole";
    private static final String url_edit = AppHttp.Url_IP + "opensns/index.php?s=/Home/rencai/updatebole";
    private List<String> list = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private String str_boleName;
    private String str_relation;
    private String str_phone;
    private OkHttpClient okHttpClient,okHttpClient2;
    private String userId, projectId,boleId;
    private String json;
    private JSONArray jsonArray;
    private JSONObject jsonObject;

    private Context mContext=ActivityAddBole.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_add_bole);
    }

    @Override
    protected void onStart() {
        if (TextUtils.equals(SharedPreferencesUtils.getParam(mContext,
                "bole_AddOrEditTag","0").toString(), "0")){

        }else {
            et_boleName.setText(SharedPreferencesUtils.getParam(mContext,"boleName","伯乐名称").toString());
            int selectId = 0;
            switch (SharedPreferencesUtils.getParam(mContext,"reliability","同事").toString()){
                case "同事":
                    selectId=0;
                    break;
                case "上司":
                    selectId=1;
                    break;
                case "下属":
                    selectId=2;
                    break;
            }
            sp_relation.setSelection(selectId);
            et_phone.setText(SharedPreferencesUtils.getParam(mContext,"phoneNumber","伯乐电话").toString());
        }

        super.onStart();
    }

    @AfterViews
    public void initReationList() {
        list.add("同事");
        list.add("上司");
        list.add("下属");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_relation.setAdapter(adapter);

        sp_relation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //SharedPreferencesUtils.setParam(mContext,"resumeWorkLength",adapter.getItem(position).toString());
                //Log.d("test",adapter.getItem(position).toString());
                str_relation = adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Click(R.id.ac_bole_img_back)
    public void back() {
        finish();
    }

    @Click(R.id.ac_bole_btn_submit)
    public void submit() {
        str_boleName = et_boleName.getText().toString();
        str_phone = et_phone.getText().toString();
        userId = SharedPreferencesUtils.getParam(ActivityAddBole.this, "userId", "") + "";
        projectId = SharedPreferencesUtils.getParam(ActivityAddBole.this, "projectId", "") + "";
        if (TextUtils.equals(SharedPreferencesUtils.getParam(mContext,
                "bole_AddOrEditTag","0").toString(), "0")){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    okHttpClient = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder()
                            .add("projectId", projectId)
                            .add("id", userId)
                            .add("boleName", str_boleName)
                            .add("phoneNumber", str_phone)
                            .add("reliability", str_relation)
                            .build();
                    Request request = new Request.Builder()
                            .url(url_add)
                            .post(formBody)
                            .build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String s = response.body().string();
                            Log.i("boleInfo---", s);
                            try {
                                jsonObject = new JSONObject(s);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }).start();

        }else {

            boleId=SharedPreferencesUtils.getParam(mContext,"boleId","")+"";
            new Thread(new Runnable() {
                @Override
                public void run() {
                    okHttpClient2 = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder()
                            .add("boleId", boleId)
                            .add("boleName", str_boleName)
                            .add("phoneNumber", str_phone)
                            .add("reliability", str_relation)
                            .build();
                    Request request = new Request.Builder()
                            .url(url_edit)
                            .post(formBody)
                            .build();
                    okHttpClient2.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String s = response.body().string();
                            Log.i("boleInfo---", s);
                            try {
                                jsonObject = new JSONObject(s);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }).start();
        }


        finish();
    }
}
