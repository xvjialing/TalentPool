package com.xvjialing.administrator.talentpool.ac;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.xvjialing.administrator.talentpool.R;
import com.xvjialing.administrator.talentpool.adapter.FragmentAdapter;
import com.xvjialing.administrator.talentpool.fg.FragmentCompanyDeatiJob;
import com.xvjialing.administrator.talentpool.fg.FragmentCompanyDetailDescription;
import com.xvjialing.administrator.talentpool.fg.FragmentCompanyDetailEvaluate;
import com.xvjialing.administrator.talentpool.fg.FragmentCompayDetailStatistics;
import com.xvjialing.administrator.talentpool.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityCompanyDetail extends AppCompatActivity {

    @BindView(R.id.ac_companyDetail_img_back)
    ImageView acCompanyDetailImgBack;
    @BindView(R.id.ac_companyDetail_tv_titlebar)
    TextView acCompanyDetailTvTitlebar;
    @BindView(R.id.ac_companyDetail_img_blackList)
    ImageView acCompanyDetailImgBlackList;
    @BindView(R.id.ac_companyDetail_rl_titlebar)
    RelativeLayout acCompanyDetailRlTitlebar;
    @BindView(R.id.ac_companyDetai_img_logo)
    ImageView acCompanyDetaiImgLogo;
    @BindView(R.id.ac_companyDetail_tv_name)
    TextView acCompanyDetailTvName;
    @BindView(R.id.ac_companyDetai_tv_industry)
    TextView acCompanyDetaiTvIndustry;
    @BindView(R.id.ac_companyDetai_tv_location)
    TextView acCompanyDetaiTvLocation;
    @BindView(R.id.id_description_tv)
    TextView idDescriptionTv;
    @BindView(R.id.id_tab_description_ll)
    LinearLayout idTabDescriptionLl;
    @BindView(R.id.id_job_tv)
    TextView idJobTv;
    @BindView(R.id.id_tab_job_ll)
    LinearLayout idTabJobLl;
    @BindView(R.id.id_evaluate_tv)
    TextView idEvaluateTv;
    @BindView(R.id.id_tab_evaluate_ll)
    LinearLayout idTabEvaluateLl;
    @BindView(R.id.id_statistics_tv)
    TextView idStatisticsTv;
    @BindView(R.id.id_tab_statistics_ll)
    LinearLayout idTabStatisticsLl;
    @BindView(R.id.ac_companyDetail_ll_viewpagerTab)
    LinearLayout acCompanyDetailLlViewpagerTab;
    @BindView(R.id.id_tab_line_iv)
    ImageView idTabLineIv;
    @BindView(R.id.ac_companyDetail_viewpager_content)
    ViewPager acCompanyDetailViewpagerContent;

    private Context mComntext = ActivityCompanyDetail.this;
    private String img_logo_url;
    private FragmentCompanyDetailDescription fgDescription;
    private FragmentCompanyDeatiJob fgJob;
    private FragmentCompanyDetailEvaluate fgEvaluate;
    private FragmentCompayDetailStatistics fgStatistics;

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
        ButterKnife.bind(this);

        initViews();

        init();

        initTabLineWidth();
    }


    //拉入黑名单
    public void blackList() {
        acCompanyDetailImgBlackList.setImageResource(R.drawable.black_list_on);
        Toast.makeText(ActivityCompanyDetail.this, "成功加入黑名单", Toast.LENGTH_SHORT).show();
    }


    public void initViews() {
        String i = SharedPreferencesUtils.getParam(mComntext, "jobTag", 1).toString();
        img_logo_url = SharedPreferencesUtils.getParam(mComntext, "big_logo", "").toString();

        acCompanyDetailTvName.setText(SharedPreferencesUtils.getParam(mComntext, "companyName", "").toString());
        acCompanyDetaiTvLocation.setText(SharedPreferencesUtils.getParam(mComntext, "address", "").toString());
        Picasso.with(mComntext).load(img_logo_url).into(acCompanyDetaiImgLogo);

    }


    public void init() {
        fgDescription = new FragmentCompanyDetailDescription();
        fgJob = new FragmentCompanyDeatiJob();
        fgEvaluate = new FragmentCompanyDetailEvaluate();
        fgStatistics = new FragmentCompayDetailStatistics();

        mFragmentList.add(fgDescription);
        mFragmentList.add(fgJob);
        mFragmentList.add(fgEvaluate);
        mFragmentList.add(fgStatistics);

        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragmentList);
        acCompanyDetailViewpagerContent.setAdapter(mFragmentAdapter);
        acCompanyDetailViewpagerContent.setCurrentItem(0);

        acCompanyDetailViewpagerContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float offset, int positionOffsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) idTabLineIv
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
                } else if (currentIndex == 2 && position == 2) {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 4) + currentIndex
                            * (screenWidth / 4));
                } else if (currentIndex == 3 && position == 2) {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 4) + currentIndex
                            * (screenWidth / 4));
                }
                idTabLineIv.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();

                switch (position) {
                    case 0:
                        idDescriptionTv.setTextColor(getResources().getColor(R.color.obj_on));
                        break;
                    case 1:
                        idJobTv.setTextColor(getResources().getColor(R.color.obj_on));
                        break;
                    case 2:
                        idEvaluateTv.setTextColor(getResources().getColor(R.color.obj_on));
                        break;
                    case 3:
                        idStatisticsTv.setTextColor(getResources().getColor(R.color.obj_on));
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
    public void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) idTabLineIv
                .getLayoutParams();
        lp.width = screenWidth / 4;
        idTabLineIv.setLayoutParams(lp);
    }

    private void resetTextView() {
        idDescriptionTv.setTextColor(getResources().getColor(R.color.obj));
        idJobTv.setTextColor(getResources().getColor(R.color.obj));
        idEvaluateTv.setTextColor(getResources().getColor(R.color.obj));
        idStatisticsTv.setTextColor(getResources().getColor(R.color.obj));
    }


    @OnClick({R.id.ac_companyDetail_img_back, R.id.ac_companyDetail_img_blackList, R.id.id_tab_description_ll, R.id.id_tab_job_ll, R.id.id_tab_evaluate_ll, R.id.id_tab_statistics_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ac_companyDetail_img_back:
                finish();
                break;
            case R.id.ac_companyDetail_img_blackList:
                blackList();
                break;
            case R.id.id_tab_description_ll:
                acCompanyDetailViewpagerContent.setCurrentItem(0);
                break;
            case R.id.id_tab_job_ll:
                acCompanyDetailViewpagerContent.setCurrentItem(1);
                break;
            case R.id.id_tab_evaluate_ll:
                acCompanyDetailViewpagerContent.setCurrentItem(2);
                break;
            case R.id.id_tab_statistics_ll:
                acCompanyDetailViewpagerContent.setCurrentItem(3);
                break;
        }
    }
}
