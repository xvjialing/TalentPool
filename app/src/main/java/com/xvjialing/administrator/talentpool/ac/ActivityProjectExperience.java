package com.xvjialing.administrator.talentpool.ac;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.OptionsPickerView;
import com.xvjialing.administrator.talentpool.R;
import com.xvjialing.administrator.talentpool.bean.China;
import com.xvjialing.administrator.talentpool.bean.ProvinceBean;
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
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@EActivity
public class ActivityProjectExperience extends AppCompatActivity {

    @ViewById(R.id.ac_resume_projectExperience_et_projectName)
    EditText et_projectName;
    @ViewById(R.id.ac_resume_projectExperience_et_companyName)
    EditText et_companyName;
    @ViewById(R.id.ac_resume_projectExperience_et_projectRole)
    EditText et_projectRole;
    @ViewById(R.id.ac_resume_projectExperience_rl_editBole)
    RelativeLayout rl_editBole;
    @ViewById(R.id.ac_resume_projectExperience_et_Skill)
    EditText et_skill;
    @ViewById(R.id.ac_resume_projectExperience_et_projectDescription)
    EditText et_projectDescription;
    @ViewById(R.id.ac_resume_projectExperience_tv_startTime)
    TextView tv_startTime;
    @ViewById(R.id.ac_resume_projectExperience_tv_endTime)
    TextView tv_endTime;
    @ViewById(R.id.ac_resume_projectExperience_btn_submit)
    Button btn_submit;
    @ViewById(R.id.ac_projectExperience_img_back)
    ImageView img_back;
    private static final String url_creat = AppHttp.Url_IP + "opensns/index.php?s=/Home/rencai/projectcreate";
    private static final String url_submit = AppHttp.Url_IP + "opensns/index.php?s=/Home/rencai/project";
    OptionsPickerView pvOptions, pvOptions2;
    private String str_projectName;
    private String str_companyName;
    private String str_projectRole;
    private String str_Skill;
    private String str_projectDescription;
    private String str_startTime = "2010年10月";
    private String str_endTime = "2012年11月";
    private OkHttpClient okHttpClient, okHttpClient2;
    private String userId, projectId;
    private String json,json2;
    private JSONObject jsonObject, jsonObject2;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<ProvinceBean>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();

    private Context mContext = ActivityProjectExperience.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_project_experience);
    }

    @Override
    protected void onStart() {
        if (TextUtils.equals(SharedPreferencesUtils.getParam(mContext,
                "projectExp_AddOrEditTag","0").toString(), "0")){

            userId = SharedPreferencesUtils.getParam(mContext, "userId", "") + "";
            new Thread(new Runnable() {
                @Override
                public void run() {
                    okHttpClient = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder()
                            .add("id", userId)
                            .build();
                    final Request request = new Request.Builder()
                            .url(url_creat)
                            .post(formBody)
                            .build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            json = response.body().string();
                            try {
                                jsonObject = new JSONObject(json);
                                String s = jsonObject.getInt("lp") + "";

                                projectId = jsonObject.getJSONObject("data").getInt("projectId") + "";
                                SharedPreferencesUtils.setParam(mContext,"projectId",projectId);
                                Log.i("projectId1-------", projectId);
                                SharedPreferencesUtils.setParam(mContext, "projectId", projectId);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }).start();

        }else {
            et_projectName.setText(SharedPreferencesUtils.getParam(mContext,"projectName","项目名").toString());
            et_companyName.setText(SharedPreferencesUtils.getParam(mContext,"companyName","公司名").toString());
            et_projectRole.setText(SharedPreferencesUtils.getParam(mContext,"projectRole","项目角色").toString());
            et_skill.setText(SharedPreferencesUtils.getParam(mContext,"technique","项目技术").toString());
            tv_startTime.setText(SharedPreferencesUtils.getParam(mContext,"startDate","开始时间").toString());
            tv_endTime.setText(SharedPreferencesUtils.getParam(mContext,"endDate","结束时间").toString());
            et_projectDescription.setText(SharedPreferencesUtils.getParam(mContext,"description","项目描述").toString());

        }
        super.onStart();
    }

    @Click(R.id.ac_projectExperience_img_back)
    public void back(){
        finish();
    }

    @Click(R.id.ac_resume_projectExperience_rl_editBole)
    public void editBole() {
        startActivity(new Intent(ActivityProjectExperience.this, ActivityEditBole_.class));
    }


    //项目开始时间
    @Click(R.id.ac_resume_projectExperience_tv_startTime)
    public void startTime() {
        //隐藏键盘
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);

        pvOptions.show();
    }

    //项目结束时间
    @Click(R.id.ac_resume_projectExperience_tv_endTime)
    public void endTime() {
        //隐藏键盘
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);

        pvOptions2.show();
    }

    @AfterViews
    public void initLocation() {
        initData();

        //选项选择器
        pvOptions = new OptionsPickerView(this);

        //三级联动效果
        pvOptions.setPicker(options1Items, options2Items, options3Items, true);

        //设置选择的三级单位
//        pwOptions.setLabels("省", "市", "区");
        pvOptions.setTitle("开始时间");
        pvOptions.setCyclic(false, true, true);
        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions.setSelectOptions(1, 1, 1);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                str_startTime = options1Items.get(options1).getPickerViewText()+"-"
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3);

                tv_startTime.setText(str_startTime);
            }
        });

    }

    @AfterViews
    public void initLocation2() {
        initData();

        //选项选择器
        pvOptions2 = new OptionsPickerView(this);

        //三级联动效果
        pvOptions2.setPicker(options1Items, options2Items, options3Items, true);

        //设置选择的三级单位
//        pwOptions.setLabels("省", "市", "区");
        pvOptions2.setTitle("结束时间");
        pvOptions2.setCyclic(false, true, true);
        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions2.setSelectOptions(1, 1, 1);
        pvOptions2.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                str_endTime = options1Items.get(options1).getPickerViewText()+"-"
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3);

                tv_endTime.setText(str_endTime);
            }
        });

    }

    //解析json数据
    private void initData() {
        try {
            //解析json数据
            InputStream is = getResources().getAssets().open("date.json");

            int available = is.available();

            byte[] b = new byte[available];
            int read = is.read(b);
            //注意格式，utf-8 或者gbk  否则解析出来可能会出现乱码
            String json = new String(b, "UTF-8");

            //System.out.println(json);

            China china = JSON.parseObject(json, China.class);
            ArrayList<China.Province> citylist = china.citylist;
            //======省级
            for (China.Province province : citylist
                    ) {
                String provinceName = province.p;

                // System.out.println("provinceName==="+provinceName);
                ArrayList<China.Province.Area> c = province.c;
                //选项1
                options1Items.add(new ProvinceBean(0, provinceName, "", ""));
                ArrayList<ArrayList<String>> options3Items_01 = new ArrayList<ArrayList<String>>();
                //区级
                //选项2

                ArrayList<String> options2Items_01 = new ArrayList<String>();
                if (c != null) {
                    for (China.Province.Area area : c
                            ) {
                        //System.out.println("area------" + area.n + "------");

                        options2Items_01.add(area.n);
                        ArrayList<China.Province.Area.Street> a = area.a;
                        ArrayList<String> options3Items_01_01 = new ArrayList<String>();

                        //县级
                        if (a != null) {
                            for (China.Province.Area.Street street : a
                                    ) {
                                // System.out.println("street/////" + street.s);
                                options3Items_01_01.add(street.s);
                            }
                            options3Items_01.add(options3Items_01_01);
                        } else {
                            //县级为空的时候
                            options3Items_01_01.add("");
                            options3Items_01.add(options3Items_01_01);
                        }
                    }
                    options2Items.add(options2Items_01);
                } else {
                    //区级为空的时候  国外
                    options2Items_01.add("");
                }
                options3Items.add(options3Items_01);
                ArrayList<String> options3Items_01_01 = new ArrayList<String>();
                options3Items_01_01.add("");
                options3Items_01.add(options3Items_01_01);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Click(R.id.ac_resume_projectExperience_btn_submit)
    public void submit() {
        setSharePreferenceData();

        updateProjectExp();

        finish();
    }

    private void updateProjectExp(){
        userId = SharedPreferencesUtils.getParam(mContext, "userId", "") + "";
        projectId=SharedPreferencesUtils.getParam(mContext,"projectId","")+"";
        new Thread(new Runnable() {
            @Override
            public void run() {
                okHttpClient2 = new OkHttpClient();
                FormBody formBody = new FormBody.Builder()
                        .add("projectId", projectId)
                        .add("id", userId)
                        .add("projectName", str_projectName)
                        .add("companyName", str_companyName)
                        .add("projectRole", str_projectRole)
                        .add("technique", str_Skill)
                        .add("startDate", str_startTime)
                        .add("endDate", str_endTime)
                        .add("projectDescription",str_projectDescription)
                        .build();
                Request request = new Request.Builder()
                        .url(url_submit)
                        .post(formBody)
                        .build();
                okHttpClient2.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        json2 = response.body().string();
                        try {
                            jsonObject2 = new JSONObject(json2);
                            String s = jsonObject2.getInt("lp") + "";

                            String projectId2 = jsonObject2.getJSONObject("data").getInt("projectId") + "";

                            Log.i("projectId2-------", projectId2);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }

    private void setSharePreferenceData() {
        str_projectName = et_projectName.getText().toString();
        str_companyName = et_companyName.getText().toString();
        str_projectRole = et_projectRole.getText().toString();
        str_Skill = et_skill.getText().toString();
        str_projectDescription = et_projectDescription.getText().toString();

        SharedPreferencesUtils.setParam(mContext, "projectExpeience_projectName", str_projectName);
        SharedPreferencesUtils.setParam(mContext, "projectExpeience_companyName", str_companyName);
        SharedPreferencesUtils.setParam(mContext, "projectExpeience_projectRole", str_projectRole);
        SharedPreferencesUtils.setParam(mContext, "projectExpeience_Skill", str_Skill);
        SharedPreferencesUtils.setParam(mContext, "projectExpeience_projectDescription", str_projectDescription);

        SharedPreferencesUtils.setParam(mContext, "projectExpeienceTag","1");
    }
}
