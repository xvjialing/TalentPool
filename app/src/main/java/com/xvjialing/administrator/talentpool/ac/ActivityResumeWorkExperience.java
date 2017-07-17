package com.xvjialing.administrator.talentpool.ac;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
public class ActivityResumeWorkExperience extends AppCompatActivity {

    @ViewById(R.id.ac_resume_workExperience_img_back)
    ImageView img_back;
    @ViewById(R.id.ac_resume_WorkExperience_et_companyName)
    EditText et_companyName;
    @ViewById(R.id.ac_resume_WorkExperience_et_jobName)
    EditText et_jobName;
    @ViewById(R.id.ac_resume_WorkExperience_et_industry)
    EditText et_industry;
    @ViewById(R.id.ac_resume_WorkExperience_et_address)
    EditText et_address;
    @ViewById(R.id.ac_resume_WorkExperience_tv_startTime)
    TextView tv_startTime;
    @ViewById(R.id.ac_resume_WorkExperience_tv_endTime)
    TextView tv_endTime;
    @ViewById(R.id.ac_resume_WorkExperience_et_offJobReason)
    EditText et_offJobReason;
    @ViewById(R.id.ac_resume_WorkExperience_et_jobDescription)
    EditText et_jobDescription;
    @ViewById(R.id.ac_resume_WorkExperience_btn_submit)
    Button btn_submit;

    OptionsPickerView pvOptions, pvOptions2;
    private static final String url_add = AppHttp.Url_IP + "opensns/index.php?s=/Home/rencai/workexperience";
    private static final String url_edit = AppHttp.Url_IP + "opensns/index.php?s=/Home/rencai/updateworkexperience";
    private ArrayList<ProvinceBean> options1Items = new ArrayList<ProvinceBean>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();
    private Context mContext = ActivityResumeWorkExperience.this;
    private String str_companyName;
    private String str_jobName;
    private String str_industry;
    private String str_address;
    private String str_startTime = "2010年10月";
    private String str_endTime = "2012年11月";
    private String str_offJobReason;
    private String str_jobDescription;
    private OkHttpClient okHttpClient,okHttpClient2;
    private String userId,workExperienceId;
    private String json;
    private JSONArray jsonArray;
    private JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_resume_work_experience);
    }

    @Override
    protected void onStart() {
        if (TextUtils.equals(SharedPreferencesUtils.getParam(mContext,
                "workExp_AddOrEditTag","0").toString(), "0")){

        }else {
            et_companyName.setText(SharedPreferencesUtils.getParam(mContext,"companyName","公司名称").toString());
            et_jobName.setText(SharedPreferencesUtils.getParam(mContext,"position","职位名称").toString());
            et_industry.setText(SharedPreferencesUtils.getParam(mContext,"industry","行业").toString());
            et_address.setText(SharedPreferencesUtils.getParam(mContext,"address","公司地址").toString());
            tv_startTime.setText(SharedPreferencesUtils.getParam(mContext,"startTime","入职时间").toString());
            tv_endTime.setText(SharedPreferencesUtils.getParam(mContext,"endTime","离职时间").toString());
            et_offJobReason.setText(SharedPreferencesUtils.getParam(mContext,"reason","离职原因").toString());
            et_jobDescription.setText(SharedPreferencesUtils.getParam(mContext,"description","职业描述").toString());
        }
        super.onStart();
    }

    @Click(R.id.ac_resume_workExperience_img_back)
    public void back() {
        finish();
    }

    @Click(R.id.ac_resume_WorkExperience_btn_submit)
    public void submit() {
        setSharePreferenceData();
        userId = SharedPreferencesUtils.getParam(mContext, "userId", "") + "";

        if (TextUtils.equals(SharedPreferencesUtils.getParam(mContext,
                "workExp_AddOrEditTag","0").toString(), "0")){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    okHttpClient = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder()
                            .add("id", userId)
                            .add("companyName", str_companyName)
                            .add("position", str_jobName)
                            .add("industry", str_industry)
                            .add("address",str_address)
                            .add("startTime", str_startTime)
                            .add("endTime", str_endTime)
                            .add("reason", str_offJobReason)
                            .add("description", str_jobDescription)
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
                            Log.i("---------", response.body().string());
                        }
                    });
                }
            }).start();
        }else {
            workExperienceId=SharedPreferencesUtils.getParam(mContext,"workExperienceId","")+"";
            new Thread(new Runnable() {
                @Override
                public void run() {
                    okHttpClient2 = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder()
                            .add("ExperienceId", workExperienceId)
                            .add("companyName", str_companyName)
                            .add("position", str_jobName)
                            .add("industry", str_industry)
                            .add("address",str_address)
                            .add("startTime", str_startTime)
                            .add("endTime", str_endTime)
                            .add("reason", str_offJobReason)
                            .add("description", str_jobDescription)
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
                            Log.i("---------", response.body().string());
                        }
                    });
                }
            }).start();
        }

        finish();
    }

    @Click(R.id.ac_resume_WorkExperience_tv_startTime)
    public void startTime() {
        //隐藏键盘
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);

        pvOptions.show();
    }

    @Click(R.id.ac_resume_WorkExperience_tv_endTime)
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
        pvOptions.setTitle("入职时间");
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
        pvOptions2.setTitle("离职时间");
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

    //存储信息
    private void setSharePreferenceData() {
        str_companyName = et_companyName.getText().toString();
        str_jobName = et_jobName.getText().toString();
        str_industry = et_industry.getText().toString();
        str_address=et_address.getText().toString();
        str_offJobReason = et_offJobReason.getText().toString();
        str_jobDescription = et_jobDescription.getText().toString();

        SharedPreferencesUtils.setParam(mContext, "workExperience_companyName", str_companyName);
        SharedPreferencesUtils.setParam(mContext, "workExperience_jobName", str_jobName);
        SharedPreferencesUtils.setParam(mContext, "workExperience_industry", str_industry);
        SharedPreferencesUtils.setParam(mContext, "workExperience_offJobReason", str_offJobReason);
        SharedPreferencesUtils.setParam(mContext, "workExperience_jobDescription", str_jobDescription);

        SharedPreferencesUtils.setParam(mContext, "workExpeienceTag","1");
    }
}
