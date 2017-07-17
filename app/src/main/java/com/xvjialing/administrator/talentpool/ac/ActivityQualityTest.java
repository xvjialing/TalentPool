package com.xvjialing.administrator.talentpool.ac;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.xvjialing.administrator.talentpool.R;
import com.xvjialing.administrator.talentpool.utils.SharedPreferencesUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity
public class ActivityQualityTest extends AppCompatActivity {

    @ViewById(R.id.ac_qualityTest_img_back)
    ImageView img_back;

    @ViewById(R.id.ac_qualityTest_sp_skillTest)
    Spinner sp_skillTest;

    @ViewById(R.id.ac_qualityTest_sp_comprehensiveSkillsTest)
    Spinner sp_comprehensiveSkillsTest;

    @ViewById(R.id.ac_qualityTest_btn_skillTest)
    Button btn_skillTest;

    @ViewById(R.id.ac_qualityTest_btn_comprehensiveSkillsTest)
    Button btn_comprehensiveSkillsTest;

    private List<String> list = new ArrayList<String>();
    private List<String> list2 = new ArrayList<String>();

    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter2;

    private String str_skill="PHP";
    private String str_comprehensiveSkills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_quality_test);
    }

    @AfterViews
    public void initSkillList() {
        list.add("PHP");
        list.add("Java");
        list.add("Android");
        list.add("C++");
        list.add("C");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_skillTest.setAdapter(adapter);

        sp_skillTest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //SharedPreferencesUtils.setParam(mContext,"resumeWorkLength",adapter.getItem(position).toString());
                //Log.d("test",adapter.getItem(position).toString());
                str_skill = adapter.getItem(position);
                SharedPreferencesUtils.setParam(ActivityQualityTest.this,"skillTag",str_skill);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @AfterViews
    public void initcomprehensiveSkillsList() {
        list2.add("性格测试");
        list2.add("职场身价");
        list2.add("职业兴趣");
        list2.add("团队合作");

        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list2);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_comprehensiveSkillsTest.setAdapter(adapter2);

        sp_comprehensiveSkillsTest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //SharedPreferencesUtils.setParam(mContext,"resumeWorkStatus",adapter2.getItem(position).toString());
                str_comprehensiveSkills = adapter2.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Click(R.id.ac_qualityTest_img_back)
    public void back() {
        finish();
    }

    @Click(R.id.ac_qualityTest_btn_skillTest)
    public void clickSkillTest() {

        startActivity(new Intent(ActivityQualityTest.this, ActivitySkillTest_.class));
    }

    @Click(R.id.ac_qualityTest_btn_comprehensiveSkillsTest)
    public void ClickComprehensiveSkills() {

        startActivity(new Intent(ActivityQualityTest.this, ActivityComprehensiveSkillsTest_.class));
    }
}
