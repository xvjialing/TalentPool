package com.xvjialing.administrator.talentpool.ac;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.xvjialing.administrator.talentpool.R;
import com.xvjialing.administrator.talentpool.adapter.FragmentAdapter;
import com.xvjialing.administrator.talentpool.fg.FragmentCompanyDeatiJob_;
import com.xvjialing.administrator.talentpool.fg.FragmentCompanyDetailDescription_;
import com.xvjialing.administrator.talentpool.fg.FragmentCompanyDetailEvaluate_;
import com.xvjialing.administrator.talentpool.fg.FragmentCompayDetailStatistics_;
import com.xvjialing.administrator.talentpool.json.AppHttp;
import com.xvjialing.administrator.talentpool.utils.SharedPreferencesUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity
public class ActivityCompanyDetail extends AppCompatActivity {

    @ViewById(R.id.ac_companyDetail_viewpager_content)
    ViewPager mPageVp;
    @ViewById(R.id.id_description_tv)
    TextView textView_description;
    @ViewById(R.id.id_job_tv)
    TextView textView_job;
    @ViewById(R.id.id_evaluate_tv)
    TextView textView_evaluate;
    @ViewById(R.id.id_statistics_tv)
    TextView textView_statistics;
    @ViewById(R.id.id_tab_line_iv)
    ImageView mTabLineIv;
    @ViewById(R.id.ac_companyDetai_img_logo)
    ImageView img_companyLogo;
    @ViewById(R.id.ac_companyDetail_tv_name)
    TextView tv_companyName;
    @ViewById(R.id.ac_companyDetai_tv_location)
    TextView tv_Companylocation;
    @ViewById(R.id.id_tab_description_ll)
    LinearLayout linearLayout_tab_description;
    @ViewById(R.id.id_tab_job_ll)
    LinearLayout linearLayout_tab_job;
    @ViewById(R.id.id_tab_evaluate_ll)
    LinearLayout linearLayout__tab_evaluate;
    @ViewById(R.id.id_tab_statistics_ll)
    LinearLayout linearLayout_tab_statistics;
    @ViewById(R.id.ac_companyDetail_img_back)
    ImageView img_back;
    @ViewById(R.id.ac_companyDetail_img_blackList)
    ImageView img_blackList;

    private Context mComntext=ActivityCompanyDetail.this;
    private String img_logo_url;
    private FragmentCompanyDetailDescription_ fgDescription;
    private FragmentCompanyDeatiJob_ fgJob;
    private FragmentCompanyDetailEvaluate_ fgEvaluate;
    private FragmentCompayDetailStatistics_ fgStatistics;

    //ViewPager的当前选中页
    private int currentIndex;

    //屏幕的宽度
    private int screenWidth;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_company_detail);
    }

    @Click(R.id.ac_companyDetail_img_back)
    public void back() {
        finish();
    }

    //拉入黑名单
    @Click(R.id.ac_companyDetail_img_blackList)
    public void blackList() {
        img_blackList.setImageResource(R.drawable.black_list_on);
        Toast.makeText(ActivityCompanyDetail.this, "成功加入黑名单", Toast.LENGTH_SHORT).show();
    }

    @AfterViews
    public void initViews(){
        String i=SharedPreferencesUtils.getParam(mComntext,"jobTag",1).toString();
        img_logo_url= SharedPreferencesUtils.getParam(mComntext,"big_logo","").toString();

        tv_companyName.setText(SharedPreferencesUtils.getParam(mComntext,"companyName","").toString());
        tv_Companylocation.setText(SharedPreferencesUtils.getParam(mComntext,"address","").toString());
        Picasso.with(mComntext).load(img_logo_url).into(img_companyLogo);

    }

    @AfterViews
    public void init(){
        fgDescription=new FragmentCompanyDetailDescription_();
        fgJob=new FragmentCompanyDeatiJob_();
        fgEvaluate=new FragmentCompanyDetailEvaluate_();
        fgStatistics=new FragmentCompayDetailStatistics_();

        mFragmentList.add(fgDescription);
        mFragmentList.add(fgJob);
        mFragmentList.add(fgEvaluate);
        mFragmentList.add(fgStatistics);

        mFragmentAdapter=new FragmentAdapter(getSupportFragmentManager(),mFragmentList);
        mPageVp.setAdapter(mFragmentAdapter);
        mPageVp.setCurrentItem(0);

        mPageVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float offset, int positionOffsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
                        .getLayoutParams();
                if (currentIndex == 0 && position == 0)// 0->1
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 4) + currentIndex
                            * (screenWidth / 4));
                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 4) + currentIndex
                            * (screenWidth / 4));
                } else if (currentIndex == 1 && position == 1) // 1->2
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 4) + currentIndex
                            * (screenWidth / 4));
                } else if (currentIndex == 2 && position == 1) // 2->1
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 4) + currentIndex
                            * (screenWidth / 4));
                }else if (currentIndex == 2 && position == 2){
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 4) + currentIndex
                            * (screenWidth / 4));
                }else if(currentIndex == 3 && position == 2){
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 4) + currentIndex
                            * (screenWidth / 4));
                }
                mTabLineIv.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();

                switch (position) {
                    case 0:
                        textView_description.setTextColor(getResources().getColor(R.color.obj_on));
                        break;
                    case 1:
                        textView_job.setTextColor(getResources().getColor(R.color.obj_on));
                        break;
                    case 2:
                        textView_evaluate.setTextColor(getResources().getColor(R.color.obj_on));
                        break;
                    case 3:
                        textView_statistics.setTextColor(getResources().getColor(R.color.obj_on));
                }
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 设置滑动条的宽度为屏幕的1/3(根据Tab的个数而定)
     */
    @AfterViews
    public void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
                .getLayoutParams();
        lp.width = screenWidth / 4;
        mTabLineIv.setLayoutParams(lp);
    }

    private void resetTextView() {
        textView_description.setTextColor(getResources().getColor(R.color.obj));
        textView_job.setTextColor(getResources().getColor(R.color.obj));
        textView_evaluate.setTextColor(getResources().getColor(R.color.obj));
        textView_statistics.setTextColor(getResources().getColor(R.color.obj));
    }

    @Click(R.id.id_tab_description_ll)
    public void clickDescption(){
        mPageVp.setCurrentItem(0);
    }

    @Click(R.id.id_tab_job_ll)
    public void clickJob(){
        mPageVp.setCurrentItem(1);
    }

    @Click(R.id.id_tab_evaluate_ll)
    public void clickEvaluate(){
        mPageVp.setCurrentItem(2);
    }

    @Click(R.id.id_tab_statistics_ll)
    public void clickStatitics(){
        mPageVp.setCurrentItem(3);
    }

}
