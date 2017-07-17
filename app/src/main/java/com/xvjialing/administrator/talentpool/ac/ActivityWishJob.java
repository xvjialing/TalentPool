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
import android.widget.Toast;

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
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@EActivity
public class ActivityWishJob extends AppCompatActivity {

    @ViewById(R.id.ac_wishJob_et_wishJob)
    EditText et_wishJob;
    @ViewById(R.id.ac_wishJob_tv_wishLocation1)
    TextView tv_wishLocation;
    @ViewById(R.id.ac_wishJob_sp_salary)
    Spinner sp_salary;
    @ViewById(R.id.ac_wishJob_btn_submit)
    Button btn_submit;
    @ViewById(R.id.ac_wishJob_img_back)
    ImageView img_back;
    private static final String url_add = AppHttp.Url_IP + "opensns/index.php?s=/Home/rencai/qiwang";
    private static final String url_edit = AppHttp.Url_IP + "opensns/index.php?s=/Home/rencai/updateqiwang";
    OptionsPickerView pvOptions;
    private OkHttpClient okHttpClient,okHttpClient2;
    private String userId,jobId;
    private JSONObject jsonObject;
    private String str_wishJob;
    private String str_wishLocation = "请选择地点";
    private String str_salary;
    private ArrayList<ProvinceBean> options1Items = new ArrayList<ProvinceBean>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();

    private Context mContext =ActivityWishJob.this;

    private List<String> list = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_wish_job);
    }

    @Override
    protected void onStart() {
        if (TextUtils.equals(SharedPreferencesUtils.getParam(mContext, "wishJobTag","0").toString(), "1")) {
            et_wishJob.setText(SharedPreferencesUtils.getParam(mContext, "resume_wishJob", "期望工作名").toString());
            tv_wishLocation.setText(SharedPreferencesUtils.getParam(mContext, "resume_wishLocation", "期望工作地址").toString());
            switch (SharedPreferencesUtils.getParam(mContext, "resume_salary", "期望工作薪水").toString()) {
                case "5k以下":
                    sp_salary.setSelection(0);
                    break;
                case "5~10k":
                    sp_salary.setSelection(1);
                    break;
                case "10~15k":
                    sp_salary.setSelection(2);
                    break;
                case "15~20k":
                    sp_salary.setSelection(3);
                    break;
                case "20~25k":
                    sp_salary.setSelection(4);
                    break;
                case "25~30k":
                    sp_salary.setSelection(5);
                    break;
                case "30k以上":
                    sp_salary.setSelection(6);
                    break;

            }
        }
        super.onStart();
    }

    @AfterViews
    public void initLocation(){
        initData();

        pvOptions = new OptionsPickerView(this);
        pvOptions.setPicker(options1Items, options2Items, options3Items, true);
        pvOptions.setTitle("选择城市");
        pvOptions.setCyclic(false, true, true);
        pvOptions.setSelectOptions(1, 1, 1);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                if (TextUtils.equals(options1Items.get(options1).getPickerViewText(), "上海")) {
                    str_wishLocation = options1Items.get(options1).getPickerViewText() + "市"
                            + options2Items.get(options1).get(option2)
                            + options3Items.get(options1).get(option2).get(options3);
                } else {
                    str_wishLocation = options1Items.get(options1).getPickerViewText() + "省"
                            + options2Items.get(options1).get(option2) + "市"
                            + options3Items.get(options1).get(option2).get(options3);
                }
                tv_wishLocation.setText(str_wishLocation);

            }
        });

    }

    //提交按钮
    @Click(R.id.ac_wishJob_btn_submit)
    public void submit(){
        if (TextUtils.equals("", et_wishJob.getText().toString())) {
            Toast.makeText(mContext, "职位不能为空", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.equals("", str_wishLocation)) {
            Toast.makeText(mContext, "地址不能为空", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.equals(str_salary, "")) {
            Toast.makeText(mContext, "地址不能为空", Toast.LENGTH_SHORT).show();
        } else {
            setSharePreferenceData();
            userId = SharedPreferencesUtils.getParam(mContext, "userId", "") + "";

            if (TextUtils.equals(SharedPreferencesUtils.getParam(mContext, "wishJobTag","0").toString(), "0")){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        okHttpClient = new OkHttpClient();

                        FormBody formBody = new FormBody.Builder()
                                .add("id", userId)
                                .add("position", str_wishJob)
                                .add("salary", str_salary)
                                .add("address", str_wishLocation)
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
                                String str=response.body().string();
                                try {
                                    jsonObject=new JSONObject(str);
                                    jobId=jsonObject.getJSONObject("data").getString("jobId");
                                    SharedPreferencesUtils.setParam(mContext,"WisjJobId",jobId);
                                    Log.i("jobId1",jobId);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }
                }).start();

            }else {
                jobId=SharedPreferencesUtils.getParam(mContext,"WisjJobId","").toString();
                Log.i("jobId",jobId);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        okHttpClient2 = new OkHttpClient();

                        FormBody formBody = new FormBody.Builder()
                                .add("jobId", jobId)
                                .add("position", str_wishJob)
                                .add("salary", str_salary)
                                .add("address", str_wishLocation)
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
                                Log.i("editWishjobtttttt", response.body().string());
                            }
                        });
                    }
                }).start();
            }

            SharedPreferencesUtils.setParam(mContext, "wishJobTag","1");

            finish();
        }
    }

    @Click(R.id.ac_wishJob_tv_wishLocation1)
    public void clickWisLocation(){
        //隐藏键盘
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);

        pvOptions.show();
        //tv_wishLocation.setText(SharedPreferencesUtils.getParam(mContext,"basicInfoLocatn","浙江省杭州市余杭区").toString());
    }

    @Click(R.id.ac_wishJob_img_back)
    public void back() {
        finish();
    }

    private void setSharePreferenceData() {
        str_wishJob=et_wishJob.getText().toString();

        SharedPreferencesUtils.setParam(mContext,"resume_wishJob",str_wishJob);
        SharedPreferencesUtils.setParam(mContext,"resume_wishLocation",str_wishLocation);
        SharedPreferencesUtils.setParam(mContext, "resume_salary", str_salary);


    }

    //解析json数据
    private void initData() {
        try {
            //解析json数据
            InputStream is = getResources().getAssets().open("city.json");

            int available = is.available();

            byte [] b=new byte[available];
            int read = is.read(b);
            //注意格式，utf-8 或者gbk  否则解析出来可能会出现乱码
            String json=new String(b,"UTF-8");

            //System.out.println(json);

            China china = JSON.parseObject(json, China.class);
            ArrayList<China.Province> citylist = china.citylist;
            //======省级
            for (China.Province province: citylist
                    ) {
                String provinceName = province.p;

                // System.out.println("provinceName==="+provinceName);
                ArrayList<China.Province.Area> c = province.c;
                //选项1
                options1Items.add(new ProvinceBean(0,provinceName,"",""));
                ArrayList<ArrayList<String>> options3Items_01 = new ArrayList<ArrayList<String>>();
                //区级
                //选项2

                ArrayList<String> options2Items_01=new ArrayList<String>();
                if (c!=null) {
                    for (China.Province.Area area : c
                            ) {
                        //System.out.println("area------" + area.n + "------");

                        options2Items_01.add(area.n);
                        ArrayList<China.Province.Area.Street> a = area.a;
                        ArrayList<String> options3Items_01_01=new ArrayList<String>();

                        //县级
                        if (a!=null) {
                            for (China.Province.Area.Street street : a
                                    ) {
                                // System.out.println("street/////" + street.s);
                                options3Items_01_01.add(street.s);
                            }
                            options3Items_01.add(options3Items_01_01);
                        }else{
                            //县级为空的时候
                            options3Items_01_01.add("");
                            options3Items_01.add(options3Items_01_01);
                        }
                    }
                    options2Items.add(options2Items_01);
                }else{
                    //区级为空的时候  国外
                    options2Items_01.add("");
                }
                options3Items.add(options3Items_01);
                ArrayList<String> options3Items_01_01=new ArrayList<String>();
                options3Items_01_01.add("");
                options3Items_01.add(options3Items_01_01);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterViews
    public void initSalayList() {
        list.add("5k以下");
        list.add("5~10k");
        list.add("10~15k");
        list.add("15~20k");
        list.add("20~25k");
        list.add("25~30k");
        list.add("30k以上");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_salary.setAdapter(adapter);

        sp_salary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //SharedPreferencesUtils.setParam(mContext,"resumeWorkLength",adapter.getItem(position).toString());
                //Log.d("test",adapter.getItem(position).toString());
                str_salary = adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
