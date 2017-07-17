package com.xvjialing.administrator.talentpool.ac;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.xvjialing.administrator.talentpool.R;
import com.xvjialing.administrator.talentpool.json.AppHttp;
import com.xvjialing.administrator.talentpool.utils.SharedPreferencesUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity
public class ActivityJobDetail extends AppCompatActivity {

    @ViewById(R.id.ac_jobDetail_rl_companyDetail)
    RelativeLayout relativeLayout_companyDetail;
    @ViewById(R.id.ac_jobDetail_tv_jobName)
    TextView tv_jobName;
    @ViewById(R.id.ac_jobDetail_tv_salary)
    TextView tv_salary;
    @ViewById(R.id.ac_jobDetail_tv_locaion)
    TextView tv_location;
    @ViewById(R.id.ac_jobDetail_tv_workLength)
    TextView tv_workLength;
    @ViewById(R.id.ac_jobDetail_tv_degree)
    TextView tv_degree;
    @ViewById(R.id.ac_jobDetail_welfare)
    TextView tv_welfare;
    @ViewById(R.id.ac_jobDetail_img_companyLogo)
    ImageView img_logo;
    @ViewById(R.id.ac_jobDetail_tv_companyName)
    TextView tv_companyName;
    @ViewById(R.id.ac_jobDetail_tv_descrip)
    TextView tv_descrip;
    @ViewById(R.id.ac_jobDetail_ll_collection)
    LinearLayout ll_collection;
    @ViewById(R.id.ac_jobDetail_img_collection)
    ImageView img_collection;
    @ViewById(R.id.ac_jobDetail_tv_collection)
    TextView tv_collection;
    @ViewById(R.id.ac_jobDetail_img_back)
    ImageView img_back;
    @ViewById(R.id.ac_jobDetail_floaltingButton_remark)
    FloatingActionButton floaltingButton_remark;
    @ViewById(R.id.ac_jobDetail_ll_deliverResume)
    LinearLayout ll_deliverResume;
    @ViewById(R.id.ac_jobDetail_tv_deliverResume)
    TextView tv_deliverResume;
    @ViewById(R.id.ac_jobDetail_tv_ContactsPhone)
    TextView tv_ContactsPhone;
    @ViewById(R.id.ac_jobDetail_tv_webSite)
    TextView tv_webSite;
    @ViewById(R.id.ac_jobDetail_tv_workLocation)
    TextView tv_workLocation;

    private String companyId,i;
    private Context mContext=ActivityJobDetail.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_job_detail);

        i=SharedPreferencesUtils.getParam(mContext,"jobTag",1).toString();
    }

    @Click(R.id.ac_jobDetail_img_back)
    public void back() {
        finish();
    }

    @Click(R.id.ac_jobDetail_floaltingButton_remark)
    public void remarks() {
        startActivity(new Intent(ActivityJobDetail.this, ActivityRemarks_.class));
    }

    @Override
    protected void onStart() {
        if (TextUtils.equals(SharedPreferencesUtils.getParam(mContext,"focus"+i,"0").toString(),"0")){
            img_collection.setImageResource(R.drawable.icon_star);
            tv_collection.setText("添加收藏");
        }else {
            img_collection.setImageResource(R.drawable.icon_star_on);
            tv_collection.setText("已收藏");
        }

        if (TextUtils.equals(SharedPreferencesUtils.getParam(mContext,"deliver"+i,"0").toString(),"0")){
            tv_deliverResume.setText("投递简历");
        }else {
            tv_deliverResume.setText("已投递");
        }
        super.onStart();
    }

    @AfterViews
    public void CompeleteInfo(){

        companyId=SharedPreferencesUtils.getParam(mContext,"companyId"+i,"")+"";
        Log.i("companyId",companyId);
        changeUI();
    }

    @UiThread
    public void changeUI() {
        tv_jobName.setText(SharedPreferencesUtils.getParam(mContext,"jobName","软件工程师").toString());
        tv_salary.setText(SharedPreferencesUtils.getParam(mContext,"salary","5k-10k").toString());
        tv_location.setText(SharedPreferencesUtils.getParam(mContext,"address","杭州").toString());
        tv_workLength.setText(SharedPreferencesUtils.getParam(mContext,"workLength","2年").toString());
        tv_degree.setText(SharedPreferencesUtils.getParam(mContext,"degree","本科").toString());
        tv_welfare.setText(SharedPreferencesUtils.getParam(mContext,"welfare","双休日").toString());
        tv_companyName.setText(SharedPreferencesUtils.getParam(mContext,"companyName","恒宝股份有限公司").toString());
        tv_descrip.setText(SharedPreferencesUtils.getParam(mContext,"descrip","java软件工程师").toString());
        tv_ContactsPhone.setText(SharedPreferencesUtils.getParam(mContext,"phoneNumber","电话号码").toString());
        tv_webSite.setText(SharedPreferencesUtils.getParam(mContext,"website","公司网站").toString());
        tv_workLocation.setText(SharedPreferencesUtils.getParam(mContext,"companyAddress","公司地址").toString());
        Picasso.with(mContext).load(SharedPreferencesUtils.getParam(mContext,"big_logo","///").toString())
                .into(img_logo);
    }

    @Click(R.id.ac_jobDetail_rl_companyDetail)
    public void IntentCompanyDetail(){
        Intent intent=new Intent(ActivityJobDetail.this,ActivityCompanyDetail_.class);
        startActivity(intent);
    }

    @Click(R.id.ac_jobDetail_ll_collection)
    public void clickCollection() {
        if (TextUtils.equals(SharedPreferencesUtils.getParam(mContext,"focus"+i,"0").toString(),"0")){
            img_collection.setImageResource(R.drawable.icon_star_on);
            tv_collection.setText("已收藏");
            Toast.makeText(mContext, "收藏成功", Toast.LENGTH_SHORT).show();
            SharedPreferencesUtils.setParam(mContext,"focus"+i,"1");
        }else {
            img_collection.setImageResource(R.drawable.icon_star);
            tv_collection.setText("添加收藏");
            Toast.makeText(mContext, "取消收藏", Toast.LENGTH_SHORT).show();
            SharedPreferencesUtils.setParam(mContext,"focus"+i,"0");
        }


    }

    @Click(R.id.ac_jobDetail_ll_deliverResume)
    public void deliverResume() {
        if (TextUtils.equals(SharedPreferencesUtils.getParam(mContext,"deliver"+i,"0").toString(),"0")){
            tv_deliverResume.setText("已投递");
            Toast.makeText(ActivityJobDetail.this, "投递成功", Toast.LENGTH_SHORT).show();
            SharedPreferencesUtils.setParam(mContext,"deliver"+i,"1");
        }else {
            tv_deliverResume.setText("投递简历");
            Toast.makeText(ActivityJobDetail.this, "取消投递成功", Toast.LENGTH_SHORT).show();
            SharedPreferencesUtils.setParam(mContext,"deliver"+i,"0");
        }
    }

}
