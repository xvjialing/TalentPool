package com.xvjialing.administrator.talentpool.ac;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@EActivity
public class ActivityEducateExperience extends AppCompatActivity {

    @ViewById(R.id.ac_educateExperience_et_schoolName)
    EditText et_schoolName;
    @ViewById(R.id.ac_educateExperience_et_MajorName)
    EditText et_majorName;
    @ViewById(R.id.ac_educateExperience_sp_degree)
    Spinner sp_degree;
    @ViewById(R.id.ac_educateExperience_tv_educateStartTime)
    TextView tv_educateStartTime;
    @ViewById(R.id.ac_educateExperience_tv_educateEndTime)
    TextView tv_educateEndTime;
    @ViewById(R.id.ac_educateExperience_img_back)
    ImageView img_back;
    @ViewById(R.id.ac_educateExperience_btn_submit)
    Button btn_submit;
    OptionsPickerView pvOptions, pvOptions2;
    private static final String url_add = AppHttp.Url_IP + "opensns/index.php?s=/Home/rencai/education";
    private static final String url_edit = AppHttp.Url_IP + "opensns/index.php?s=/Home/rencai/updateedu";
    private Context mContext=ActivityEducateExperience.this;
    private List<String> list = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private String str_shoolName;
    private String str_majorName;
    private String str_degree;
    private String str_educateStartTime="";
    private String str_educateEndTime="";
    private ArrayList<ProvinceBean> options1Items = new ArrayList<ProvinceBean>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();
    private OkHttpClient okHttpClient,okHttpClient2;
    private String userId,educationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_educate_experience);
    }


    @Override
    protected void onStart() {
        if (TextUtils.equals(SharedPreferencesUtils.getParam(mContext,
                "edu_AddOrEditTag","0").toString(), "0")){

        }else {
            et_schoolName.setText(SharedPreferencesUtils.getParam(mContext,"schoolName","学校名").toString());
            et_majorName.setText(SharedPreferencesUtils.getParam(mContext,"majorName","专业名称").toString());
            int selectId = 0;
            switch (SharedPreferencesUtils.getParam(mContext,"educationLevel","专科").toString()){
                case "专科":
                    selectId=0;
                    break;
                case "本科":
                    selectId=1;
                    break;
                case "研究生":
                    selectId=2;
                    break;
                case "硕士":
                    selectId=3;
                    break;
                case "博士":
                    selectId=4;
                    break;

            }
            sp_degree.setSelection(selectId);
            tv_educateStartTime.setText(SharedPreferencesUtils.getParam(mContext,"startTime","开始时间").toString());
            tv_educateEndTime.setText(SharedPreferencesUtils.getParam(mContext,"endTime","结束时间").toString());
        }
        super.onStart();
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
        pvOptions.setTitle("入学时间");
        pvOptions.setCyclic(false, true, true);
        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions.setSelectOptions(1, 1, 1);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                str_educateStartTime = options1Items.get(options1).getPickerViewText()+"-"
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3);

                tv_educateStartTime.setText(str_educateStartTime);
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
        pvOptions2.setTitle("毕业时间");
        pvOptions2.setCyclic(false, true, true);
        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions2.setSelectOptions(1, 1, 1);
        pvOptions2.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                str_educateEndTime = options1Items.get(options1).getPickerViewText()+"-"
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3);
                tv_educateEndTime.setText(str_educateEndTime);
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

    @AfterViews
    public void initDegreeList(){
        list.add("专科");
        list.add("本科");
        list.add("研究生");
        list.add("硕士");
        list.add("博士");

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_degree.setAdapter(adapter);
        sp_degree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //SharedPreferencesUtils.setParam(mContext,"resumeDegree",adapter3.getItem(position));
                str_degree = adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Click(R.id.ac_educateExperience_img_back)
    public void back() {
        finish();
    }

    //提交
    @Click(R.id.ac_educateExperience_btn_submit)
    public void clickSubmit(){
        setSharePreferenceData();
        userId = SharedPreferencesUtils.getParam(mContext, "userId", "") + "";

        if (TextUtils.equals(SharedPreferencesUtils.getParam(mContext, "edu_AddOrEditTag","0").toString(), "0")){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    okHttpClient = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder()
                            .add("id", userId)
                            .add("schoolName", str_shoolName)
                            .add("major", str_majorName)
                            .add("educationLevel", str_degree)
                            .add("startDate", str_educateStartTime)
                            .add("endDate", str_educateEndTime)
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
                            Log.i("----------------", response.body().string());
                        }
                    });
                }
            }).start();
        }else {
            educationId=SharedPreferencesUtils.getParam(mContext,"educateId","").toString();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    okHttpClient2 = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder()
                            .add("educationId", educationId)
                            .add("schoolName", str_shoolName)
                            .add("major", str_majorName)
                            .add("educationLevel", str_degree)
                            .add("startDate", str_educateStartTime)
                            .add("endDate", str_educateEndTime)
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
                            Log.i("----------------", response.body().string());
                        }
                    });
                }
            }).start();
        }


        finish();
    }

    @Click(R.id.ac_educateExperience_tv_educateStartTime)
    public void startTime(){
        //隐藏键盘
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);

        pvOptions.show();
    }

    @Click(R.id.ac_educateExperience_tv_educateEndTime)
    public void endTime(){


        //隐藏键盘
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);

        pvOptions2.show();

    }

    private void setSharePreferenceData() {
        str_majorName=et_majorName.getText().toString();
        str_shoolName=et_schoolName.getText().toString();
        str_educateStartTime=tv_educateStartTime.getText().toString();
        str_educateEndTime=tv_educateEndTime.getText().toString();

        SharedPreferencesUtils.setParam(mContext,"resume_shoolName",str_shoolName);
        SharedPreferencesUtils.setParam(mContext,"resume_majorName",str_majorName);
        SharedPreferencesUtils.setParam(mContext,"resume_degree",str_degree);
        SharedPreferencesUtils.setParam(mContext,"resume_educateStartTime",str_educateStartTime);
        SharedPreferencesUtils.setParam(mContext,"resume_educateEndTime",str_educateEndTime);

        SharedPreferencesUtils.setParam(mContext, "educateExpeienceTag","1");
    }
}
