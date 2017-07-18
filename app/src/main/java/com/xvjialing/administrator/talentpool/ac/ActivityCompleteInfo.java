package com.xvjialing.administrator.talentpool.ac;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ActivityCompleteInfo extends AppCompatActivity {

    OptionsPickerView pvOptions;

    private static final String url = AppHttp.Url_IP + "opensns/index.php?s=/Home/rencai/jiben";
    @BindView(R.id.ac_completeInfo_titletext)
    TextView acCompleteInfoTitletext;
    @BindView(R.id.ac_completeInfo_titlebar)
    RelativeLayout acCompleteInfoTitlebar;
    @BindView(R.id.ac_completeInfo_tv_name)
    TextView acCompleteInfoTvName;
    @BindView(R.id.ac_completeInfo_et_name)
    EditText acCompleteInfoEtName;
    @BindView(R.id.ac_completeInfo_tv_sex)
    TextView acCompleteInfoTvSex;
    @BindView(R.id.ac_completeInfo_rb_male)
    RadioButton acCompleteInfoRbMale;
    @BindView(R.id.ac_completeInfo_rb_female)
    RadioButton acCompleteInfoRbFemale;
    @BindView(R.id.ac_completeInfo_rg_sex)
    RadioGroup acCompleteInfoRgSex;
    @BindView(R.id.ac_completeInfo_tv_phoneNumber)
    TextView acCompleteInfoTvPhoneNumber;
    @BindView(R.id.ac_completeInfo_et_phoneNumber)
    EditText acCompleteInfoEtPhoneNumber;
    @BindView(R.id.ac_completeInfo_tv_location)
    TextView acCompleteInfoTvLocation;
    @BindView(R.id.ac_completeInfo_tv_location1)
    TextView acCompleteInfoTvLocation1;
    @BindView(R.id.ac_completeInfo_tv_workLength)
    TextView acCompleteInfoTvWorkLength;
    @BindView(R.id.ac_completeInfo_sp_workLength)
    Spinner acCompleteInfoSpWorkLength;
    @BindView(R.id.ac_resume_basicInfo_tv_rencentStatus)
    TextView acResumeBasicInfoTvRencentStatus;
    @BindView(R.id.ac_completeInfo_sp_rencentStatus)
    Spinner acCompleteInfoSpRencentStatus;
    @BindView(R.id.ac_completeInfo_btn_submit)
    Button acCompleteInfoBtnSubmit;
    private OkHttpClient okHttpClient;
    private String userId;
    private String json;

    private ArrayList<ProvinceBean> options1Items = new ArrayList<ProvinceBean>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();
    private Context mContext = ActivityCompleteInfo.this;

    private List<String> list = new ArrayList<String>();
    private List<String> list2 = new ArrayList<String>();

    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter2;

    private String str_name;
    private String str_sex;
    private String str_phoneNumber;
    private String str_location = "请选择地点";
    private String str_workLength;
    private String str_recentStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_complete_info);
        ButterKnife.bind(this);

        radioGroupSet();

        initLocation();

        initWorkList();

        initWorkStatusList();
    }

    //    性别设置
    public void radioGroupSet() {
        acCompleteInfoRgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == acCompleteInfoRbMale.getId()) {
                    str_sex = "男";
                } else {
                    str_sex = "女";
                }
            }
        });
    }

    public void initLocation() {
        initData();

        //选项选择器
        pvOptions = new OptionsPickerView(this);

        //三级联动效果
        pvOptions.setPicker(options1Items, options2Items, options3Items, true);

        //设置选择的三级单位
//        pwOptions.setLabels("省", "市", "区");
        pvOptions.setTitle("选择城市");
        pvOptions.setCyclic(false, true, true);
        //设置默认选中的三级项目
        //监听确定选择按钮
        pvOptions.setSelectOptions(1, 1, 1);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                if (TextUtils.equals(options1Items.get(options1).getPickerViewText(), "上海")) {
                    str_location = options1Items.get(options1).getPickerViewText() + "市"
                            + options2Items.get(options1).get(option2)
                            + options3Items.get(options1).get(option2).get(options3);
                } else {
                    str_location = options1Items.get(options1).getPickerViewText() + "省"
                            + options2Items.get(options1).get(option2) + "市"
                            + options3Items.get(options1).get(option2).get(options3);
                }

                acCompleteInfoTvLocation1.setText(str_location);
            }
        });

    }

    //提交
    public void clickSubmit() {
        if (TextUtils.equals("", acCompleteInfoEtName.getText().toString().trim())) {
            Toast.makeText(mContext, "姓名不能为空", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.equals("", acCompleteInfoEtPhoneNumber.getText().toString())) {
            Toast.makeText(mContext, "联系电话不能为空", Toast.LENGTH_SHORT).show();
        } else if (acCompleteInfoEtPhoneNumber.getText().toString().length() != 11) {
            Toast.makeText(mContext, "联系电话无效", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.equals("", str_location)) {
            Toast.makeText(mContext, "地址不能为空", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.equals("", str_workLength)) {
            Toast.makeText(mContext, "工作年限不能为空", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.equals("", str_recentStatus)) {
            Toast.makeText(mContext, "当前状态不能为空", Toast.LENGTH_SHORT).show();
        } else {
            setSharePreferenceData();
            userId = SharedPreferencesUtils.getParam(mContext, "userId", "") + "";
            Log.i("workLength11111", str_workLength);
            Log.i("fwerwqeqweqweqw", userId);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    okHttpClient = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder()
                            .add("id", userId)
                            .add("realname", str_name)
                            .add("sex", str_sex)
                            .add("phone", str_phoneNumber)
                            .add("address", str_location)
                            .add("workLength", str_workLength)
                            .add("workStatus", str_recentStatus)
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
                            Log.i("addBasicInfo", json);
                        }
                    });
                }
            }).start();
            startActivity(new Intent(mContext, ActivityMain.class));
            finish();
        }

    }

    //保存数据
    private void setSharePreferenceData() {
        str_name = acCompleteInfoEtName.getText().toString();
        str_phoneNumber = acCompleteInfoEtPhoneNumber.getText().toString();

        SharedPreferencesUtils.setParam(mContext, "resume_name", str_name);
        SharedPreferencesUtils.setParam(mContext, "resume_sex", str_sex);
        SharedPreferencesUtils.setParam(mContext, "resume_phoneNumber", str_phoneNumber);
        SharedPreferencesUtils.setParam(mContext, "resume_location", str_location);
        SharedPreferencesUtils.setParam(mContext, "resume_workLength", str_workLength);
        SharedPreferencesUtils.setParam(mContext, "resume_recentStatus", str_recentStatus);

        SharedPreferencesUtils.setParam(mContext, "basicInfoTag", "1");
    }

    public void clickLoCation() {
        //隐藏键盘
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);

        pvOptions.show();
        //tv_location.setText(SharedPreferencesUtils.getParam(mContext,"basicInfoLocatn","浙江省杭州市余杭区").toString());
    }

    //解析json数据
    private void initData() {
        try {
            //解析json数据
            InputStream is = getResources().getAssets().open("city.json");

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

    public void initWorkList() {
        list.add("2-3年");
        list.add("3-4年");
        list.add("4-5年");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        acCompleteInfoSpWorkLength.setAdapter(adapter);

        acCompleteInfoSpWorkLength.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //SharedPreferencesUtils.setParam(mContext,"resumeWorkLength",adapter.getItem(position).toString());
                //Log.d("test",adapter.getItem(position).toString());
                str_workLength = adapter.getItem(position);
                Log.i("--str_workLength", str_workLength);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void initWorkStatusList() {
        list2.add("在职，看看新机会");
        list2.add("离职，急需新工作");
        list2.add("暂时不需要新工作");
        list2.add("在职，无意向");

        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list2);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        acCompleteInfoSpRencentStatus.setAdapter(adapter2);

        acCompleteInfoSpRencentStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //SharedPreferencesUtils.setParam(mContext,"resumeWorkStatus",adapter2.getItem(position).toString());
                str_recentStatus = adapter2.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onStart() {
        acCompleteInfoEtName.setText(SharedPreferencesUtils.getParam(mContext, "resume_name", "").toString());

        str_location = SharedPreferencesUtils.getParam(mContext, "resume_location", "请选择地址").toString();

        if (TextUtils.equals(SharedPreferencesUtils.getParam(mContext, "resume_sex", "男").toString(), "男")) {
            acCompleteInfoRgSex.check(acCompleteInfoRbMale.getId());
            str_sex = "男";
        } else {
            acCompleteInfoRgSex.check(acCompleteInfoRbFemale.getId());
            str_sex = "女";
        }

        acCompleteInfoEtPhoneNumber.setText(SharedPreferencesUtils.getParam(mContext, "resume_phoneNumber", "").toString());

        acCompleteInfoTvLocation1.setText(SharedPreferencesUtils.getParam(mContext, "resume_location", "请选择地点").toString());

        super.onStart();
    }

    @OnClick({R.id.ac_completeInfo_tv_location1, R.id.ac_completeInfo_btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ac_completeInfo_tv_location1:
                clickLoCation();
                break;
            case R.id.ac_completeInfo_btn_submit:
                clickSubmit();
                break;
        }
    }
}
