package com.xvjialing.administrator.talentpool.ac;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xvjialing.administrator.talentpool.R;
import com.xvjialing.administrator.talentpool.bean.EduInfo2;
import com.xvjialing.administrator.talentpool.bean.ProjectInfo2;
import com.xvjialing.administrator.talentpool.bean.WorkInfo2;
import com.xvjialing.administrator.talentpool.json.AppHttp;
import com.xvjialing.administrator.talentpool.json.JSONParser;
import com.xvjialing.administrator.talentpool.utils.HeadImageUtils;
import com.xvjialing.administrator.talentpool.utils.MyDialog;
import com.xvjialing.administrator.talentpool.utils.SharedPreferencesUtils;
import com.xvjialing.administrator.talentpool.utils.UnicodeDecode;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@EActivity
public class ActivityResume extends AppCompatActivity {

    private static final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");
    private static final String url_uploadAvater= AppHttp.Url_IP + "opensns/index.php?s=/Home/rencai/image";
    private static final String url_show= AppHttp.Url_IP + "opensns/index.php?s=/Home/rencai/allshow";

    private ProgressDialog pDialog;

    String img_path,userId;
    String avaterUrl,realName,sex,phone,address,work_length,workStatus,wishJob,wishLocation,wishsalary;

    private OkHttpClient client;
    private static final int REQUEST_PERMISSION_CAMERA_CODE = 1;
    @ViewById(R.id.ac_resume_img_avater)
    CircleImageView imageView_avater;
    @ViewById(R.id.ac_resume_tv_userName)
    TextView tv_userName;
    @ViewById(R.id.ac_resume_img_sex)
    ImageView img_sex;
    @ViewById(R.id.ac_resume_tv_phoneNumber)
    TextView tv_phoneNumber;
    @ViewById(R.id.ac_resume_tv_location)
    TextView basic_tv_location;
    @ViewById(R.id.ac_resume_tv_workLength)
    TextView basic_tv_workLength;
    @ViewById(R.id.ac_resume_tv_rencentStatus)
    TextView basic_tv_rencentStatus;
    @ViewById(R.id.ac_resume_tv_wishJobName)
    TextView tv_wishJobName;
    @ViewById(R.id.ac_resume_tv_wishLocation)
    TextView tv_wishLocation;
    @ViewById(R.id.ac_resume_tv_wishSalary)
    TextView tv_wishSalary;
    @ViewById(R.id.ac_resume_img_editBasicInfo)
    ImageView img_editBasicInfo;
    @ViewById(R.id.ac_resume_ll_editBasicInfo)
    LinearLayout ll_editBasicInfo;
    @ViewById(R.id.ac_resume_img_editwishJob)
    ImageView img_editwishJob;
    @ViewById(R.id.ac_resume_ll_editwishJob)
    LinearLayout ll_editwishJob;
    @ViewById(R.id.ac_resume_img_editEducate)
    ImageView img_editEducate;
    @ViewById(R.id.ac_resume_ll_editEducate)
    LinearLayout ll_editEducate;
    @ViewById(R.id.ac_resume_lv_EducateExperience)
    ListView lv_EducateExperience;
    @ViewById(R.id.ac_resume_img_editworkExperience)
    ImageView img_editworkExperience;
    @ViewById(R.id.ac_resume_ll_editworkExperience)
    LinearLayout ll_editworkExperience;
    @ViewById(R.id.ac_resume_lv_workExperience)
    ListView lv_workExperience;
    @ViewById(R.id.ac_resume_img_editProjectExpeience)
    ImageView img_editProjectExpeience;
    @ViewById(R.id.ac_resume_ll_editProjectExpeience)
    LinearLayout ll_editProjectExpeience;
    @ViewById(R.id.ac_resume_lv_ProjectExpeience)
    ListView lv_ProjectExpeience;
    @ViewById(R.id.ac_resume_tv_completeBasicInfo)
    TextView tv_completeBasicInfo;
    @ViewById(R.id.ac_resume_ll_basicInfo_content)
    LinearLayout ll_basicInfoConent;
    @ViewById(R.id.ac_resume_tv_completeWishJob)
    TextView tv_completeWishJob;
    @ViewById(R.id.ac_resume_ll_wishJob_content)
    LinearLayout ll_wishJob_content;
    @ViewById(R.id.ac_resume_tv_completeEducateExperience)
    TextView tv_completeEducateExperience;
    @ViewById(R.id.ac_resume_tv_completeWorkExpeience)
    TextView tv_completeWorkExpeience;
    @ViewById(R.id.ac_resume_tv_completeProjectExpeience)
    TextView tv_completeProjectExpeience;
    @ViewById(R.id.ac_resume_img_back)
    ImageView img_back;
    private Context mContext = ActivityResume.this;

    private String list_schoolName,list_startDateEdu,list_endDateEdu,list_major;
    private String list_companyName,list_startTimeCompany,list_endTimeCompany,list_position;
    private String list_projectName,list_startDateProject,list_endDateProject,list_projectRole;

    private ArrayList<EduInfo2> eduInfo2List;
    private EduInfo2ListAdapter eduInfo2ListAdapter;

    private ArrayList<WorkInfo2> workInfo2List;
    private WorkInfo2ListAdapter workInfo2ListAdapter;

    private ArrayList<ProjectInfo2> projectInfo2List;
    private ProjectInfo2ListAdapter projectInfo2ListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_resume);

        userId = SharedPreferencesUtils.getParam(mContext,"userId","")+"";
    }

    /*
    * 加载页面时调用
    * */
    @Override
    protected void onStart() {

        new getData().execute();
        if (pDialog.isShowing()){
            pDialog.dismiss();
        }

        //初始化界面
        initView();

        //初始化基本信息
        initBasicInfo();

        //初始化期望工作信息
        initWishJob();

        //初始化教育经历信息
        initEducateExperience();

        super.onStart();
    }



    @Override
    protected void onResume() {
        new getData().execute();
        //初始化界面
        initView();
        //初始化基本信息
        initBasicInfo();
        //初始化期望工作信息
        initWishJob();
        //初始化教育经历信息
        initEducateExperience();

        super.onResume();
    }

    private class getData extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(mContext);
            pDialog.setMessage("正在加载..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            eduInfo2List=new ArrayList<EduInfo2>();

            workInfo2List=new ArrayList<WorkInfo2>();

            projectInfo2List=new ArrayList<ProjectInfo2>();

            JSONParser jsonParser=new JSONParser();
            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("id",userId));
            JSONObject jsonObject=jsonParser.makeHttpRequest(url_show,"POST",params1);
            Log.i("-----allshow-----",jsonObject.toString());
            try {
                if (!jsonObject.isNull("data2")){
                    JSONArray jsonArray1=jsonObject.getJSONArray("data2");
                    Log.i("jsonArray1", String.valueOf(jsonArray1));
                    JSONObject jsonObject2=jsonArray1.getJSONObject(0);
                    avaterUrl=AppHttp.Url_IP+"opensns/Uploads/"+jsonObject2.getString("touxiang");
                    realName=jsonObject2.getString("realname");
                    sex=jsonObject2.getString("sex");
                    phone=jsonObject2.getString("phone");
                    address=jsonObject2.getString("address");
                    work_length=jsonObject2.getString("work_Length");
                    workStatus=jsonObject2.getString("workStatus");
                    SharedPreferencesUtils.setParam(mContext,"avaterUrl",avaterUrl);
                    SharedPreferencesUtils.setParam(mContext,"resume_name",realName);
                    SharedPreferencesUtils.setParam(mContext,"resume_sex",sex);
                    SharedPreferencesUtils.setParam(mContext,"resume_phoneNumber",phone);
                    SharedPreferencesUtils.setParam(mContext,"resume_location",address);
                    SharedPreferencesUtils.setParam(mContext,"resume_workLength",work_length);
                    SharedPreferencesUtils.setParam(mContext,"resume_recentStatus",workStatus);

                    SharedPreferencesUtils.setParam(mContext, "basicInfoTag","1");
                }

                if (!jsonObject.isNull("data1")){
                    JSONArray jsonArray2=jsonObject.getJSONArray("data1");
                    JSONObject jsonObject3=jsonArray2.getJSONObject(0);
                    Log.i("jsonArray2", String.valueOf(jsonArray2));
                    Log.i("position", String.valueOf(jsonObject3.isNull("position")));
                    if (!jsonObject3.isNull("position")){
                        wishJob=jsonObject3.getString("position");
                        wishLocation=jsonObject3.getString("address");
                        wishsalary=jsonObject3.getString("salary");

                        SharedPreferencesUtils.setParam(mContext,"resume_wishJob",wishJob);
                        SharedPreferencesUtils.setParam(mContext,"resume_wishLocation",wishLocation);
                        SharedPreferencesUtils.setParam(mContext,"resume_salary",wishsalary);

                        SharedPreferencesUtils.setParam(mContext, "wishJobTag", "1");
                    }else {
                        SharedPreferencesUtils.setParam(mContext, "wishJobTag", "0");
                    }


                }

                if (!jsonObject.isNull("data2")){
                    JSONArray jsonArray3=jsonObject.getJSONArray("data2");
                    Log.i("jsonArray3", String.valueOf(jsonArray3));
                    for (int i=0;i<jsonArray3.length();i++){
                        JSONObject jsonObject4=jsonArray3.getJSONObject(i);
                        list_schoolName=jsonObject4.getString("schoolName");
                        list_major=jsonObject4.getString("major");
                        list_startDateEdu=jsonObject4.getString("startDate");
                        list_endDateEdu=jsonObject4.getString("endDate");

                        eduInfo2List.add(new EduInfo2(list_schoolName,
                                list_startDateEdu,list_endDateEdu,list_major));

                    }
                    SharedPreferencesUtils.setParam(mContext, "educateExpeienceTag","1");
                }else {
                    SharedPreferencesUtils.setParam(mContext, "educateExpeienceTag","0");
                }


                if (!jsonObject.isNull("data3")){
                    JSONArray jsonArray4=jsonObject.getJSONArray("data3");
                    Log.i("jsonArray4", String.valueOf(jsonArray4));
                    for (int i=0;i<jsonArray4.length();i++){
                        JSONObject jsonObject5=jsonArray4.getJSONObject(i);
                        list_companyName=jsonObject5.getString("companyName");
                        list_startTimeCompany=jsonObject5.getString("startTime");
                        list_endTimeCompany=jsonObject5.getString("endTime");
                        list_position=jsonObject5.getString("position");

                        workInfo2List.add(new WorkInfo2(list_companyName,list_startTimeCompany,
                                list_endTimeCompany,list_position));
                    }
                    SharedPreferencesUtils.setParam(mContext, "workExpeienceTag","1");
                }else {
                    SharedPreferencesUtils.setParam(mContext, "workExpeienceTag","0");
                }

                if (!jsonObject.isNull("data4")){
                    JSONArray jsonArray5=jsonObject.getJSONArray("data4");
                    Log.i("jsonArray5", String.valueOf(jsonArray5));
                    for (int i=0;i<jsonArray5.length();i++){
                        JSONObject jsonObject6=jsonArray5.getJSONObject(i);
                        list_projectName=jsonObject6.getString("projectName");
                        list_projectRole=jsonObject6.getString("projectRole");
                        list_startDateProject=jsonObject6.getString("startDate");
                        list_endDateProject=jsonObject6.getString("endDate");

                        projectInfo2List.add(new ProjectInfo2(list_projectName,list_startDateProject,
                                list_endDateProject,list_projectRole));
                    }
                    SharedPreferencesUtils.setParam(mContext, "projectExpeienceTag","1");
                }else {
                    SharedPreferencesUtils.setParam(mContext, "projectExpeienceTag","0");
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            eduInfo2ListAdapter=new EduInfo2ListAdapter(eduInfo2List);
            lv_EducateExperience.setAdapter(eduInfo2ListAdapter);
            setListViewHeightBasedOnChildren(lv_EducateExperience);

            workInfo2ListAdapter=new WorkInfo2ListAdapter(workInfo2List);
            lv_workExperience.setAdapter(workInfo2ListAdapter);
            setListViewHeightBasedOnChildren2(lv_workExperience);

            projectInfo2ListAdapter=new ProjectInfo2ListAdapter(projectInfo2List);
            lv_ProjectExpeience.setAdapter(projectInfo2ListAdapter);
            setListViewHeightBasedOnChildren3(lv_ProjectExpeience);

            pDialog.dismiss();
            super.onPostExecute(s);
        }
    }

    private class EduInfo2ListAdapter extends BaseAdapter {
        private ArrayList<EduInfo2> eduInfo2List;

        public EduInfo2ListAdapter(ArrayList<EduInfo2> list) {
            this.eduInfo2List = list;
        }

        @Override
        public int getCount() {
            return eduInfo2List.size();
        }

        @Override
        public Object getItem(int position) {
            return eduInfo2List.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListViewItemHolder_edu item=null;
            if (convertView==null){
                convertView= LayoutInflater.from(mContext)
                        .inflate(R.layout.list_item_educate2,null);
                item=new ListViewItemHolder_edu();
                item.tv_schoolName= (TextView) convertView.findViewById(R.id.item_tv_schoolName);
                item.tv_majorName= (TextView) convertView.findViewById(R.id.item_tv_majorName);
                item.tv_educateTime= (TextView) convertView.findViewById(R.id.item_tv_educateTime);
                convertView.setTag(item);
            }else {
                item= (ListViewItemHolder_edu) convertView.getTag();
            }

            EduInfo2 eduInfo2=eduInfo2List.get(position);

            item.tv_schoolName.setText(eduInfo2.getSchoolName());
            item.tv_majorName.setText(eduInfo2.getMajor());
            item.tv_educateTime.setText(eduInfo2.getStartDate()+"~"+eduInfo2.getEndDate());

            return convertView;
        }
    }

    private class WorkInfo2ListAdapter extends BaseAdapter {
        private ArrayList<WorkInfo2> workInfo2List;

        public WorkInfo2ListAdapter(ArrayList<WorkInfo2> list) {
            this.workInfo2List = list;
        }

        @Override
        public int getCount() {
            return workInfo2List.size();
        }

        @Override
        public Object getItem(int position) {
            return workInfo2List.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListViewItemHolder_work item=null;
            if (convertView==null){
                convertView= LayoutInflater.from(mContext)
                        .inflate(R.layout.list_item_work_ex2,null);
                item=new ListViewItemHolder_work();
                item.tv_companyName= (TextView) convertView.findViewById(R.id.item_tv_companyName);
                item.tv_position= (TextView) convertView.findViewById(R.id.item_tv_jobName);
                item.tv_onJobTime= (TextView) convertView.findViewById(R.id.item_tv_onJobTime);
                convertView.setTag(item);
            }else {
                item= (ListViewItemHolder_work) convertView.getTag();
            }

            WorkInfo2 workInfo2=workInfo2List.get(position);

            item.tv_companyName.setText(workInfo2.getCompanyName());
            item.tv_position.setText(workInfo2.getPosition());
            item.tv_onJobTime.setText(workInfo2.getStartTime()+"~"+workInfo2.getEndTime());

            return convertView;
        }
    }

    private class ProjectInfo2ListAdapter extends BaseAdapter {
        private ArrayList<ProjectInfo2> projectInfo2List;

        public ProjectInfo2ListAdapter(ArrayList<ProjectInfo2> list) {
            this.projectInfo2List = list;
        }

        @Override
        public int getCount() {
            return projectInfo2List.size();
        }

        @Override
        public Object getItem(int position) {
            return projectInfo2List.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListViewItemHolder_project item=null;
            if (convertView==null){
                convertView= LayoutInflater.from(mContext)
                        .inflate(R.layout.list_item_project2,null);
                item=new ListViewItemHolder_project();
                item.tv_projectName= (TextView) convertView.findViewById(R.id.ac_resume_tv_projectName);
                item.tv_projectRole= (TextView) convertView.findViewById(R.id.ac_resume_tv_projectRole);
                item.tv_time= (TextView) convertView.findViewById(R.id.ac_resume_tv_time);
                convertView.setTag(item);

            }else {
                item= (ListViewItemHolder_project) convertView.getTag();
            }
            ProjectInfo2 projectInfo2=projectInfo2List.get(position);

            item.tv_projectName.setText(projectInfo2.getProjectName());
            item.tv_projectRole.setText(projectInfo2.getProjectRole());
            item.tv_time.setText(projectInfo2.getStartDate()+"~"+projectInfo2.getEndDate());


            return convertView;
        }
    }

    private class ListViewItemHolder_edu {
        TextView tv_schoolName;
        TextView tv_majorName;
        TextView tv_educateTime;
    }

    private class ListViewItemHolder_work {
        TextView tv_companyName;
        TextView tv_position;
        TextView tv_onJobTime;
    }

    private class ListViewItemHolder_project {
        TextView tv_projectName;
        TextView tv_projectRole;
        TextView tv_time;
    }

    //初始化教育经历信息
    private void initEducateExperience() {
        if (TextUtils.equals(SharedPreferencesUtils.getParam(mContext, "educateExpeienceTag","0").toString(), "1")) {
            String educateTime = SharedPreferencesUtils.getParam(mContext, "resume_educateStartTime", "开始时间").toString()
                    + "~" + SharedPreferencesUtils.getParam(mContext, "resume_educateEndTime", "结束时间").toString();
        }
    }

    //初始化期望工作信息
    private void initWishJob() {
        if (TextUtils.equals(SharedPreferencesUtils.getParam(mContext, "wishJobTag","0").toString(), "1")) {
            tv_wishJobName.setText(SharedPreferencesUtils.getParam(mContext, "resume_wishJob", "期望工作名").toString());
            tv_wishLocation.setText(SharedPreferencesUtils.getParam(mContext, "resume_wishLocation", "期望工作地址").toString());
            tv_wishSalary.setText(SharedPreferencesUtils.getParam(mContext, "resume_salary", "期望工作薪水").toString());
        }
    }

    //初始化界面
    private void initView() {
//        if (TextUtils.equals(SharedPreferencesUtils.getParam(mContext, "basicInfoTag","0").toString(), "0")) {
//            tv_completeBasicInfo.setVisibility(View.VISIBLE);
//            ll_basicInfoConent.setVisibility(View.GONE);
//        } else {
            tv_completeBasicInfo.setVisibility(View.GONE);
            ll_basicInfoConent.setVisibility(View.VISIBLE);
//        }

        if (TextUtils.equals(SharedPreferencesUtils.getParam(mContext, "wishJobTag","0").toString(), "0")) {
            tv_completeWishJob.setVisibility(View.VISIBLE);
            ll_editwishJob.setVisibility(View.GONE);
            ll_wishJob_content.setVisibility(View.GONE);
        } else {
            tv_completeWishJob.setVisibility(View.GONE);
            ll_editwishJob.setVisibility(View.VISIBLE);
            ll_wishJob_content.setVisibility(View.VISIBLE);
        }

        if (TextUtils.equals(SharedPreferencesUtils.getParam(mContext, "educateExpeienceTag","0").toString(), "0")) {
            tv_completeEducateExperience.setVisibility(View.VISIBLE);
            ll_editEducate.setVisibility(View.GONE);
            lv_EducateExperience.setVisibility(View.GONE);
        } else {
            tv_completeEducateExperience.setVisibility(View.GONE);
            ll_editEducate.setVisibility(View.VISIBLE);
            lv_EducateExperience.setVisibility(View.VISIBLE);
        }

        if (TextUtils.equals(SharedPreferencesUtils.getParam(mContext, "workExpeienceTag","0").toString(), "0")) {
            tv_completeWorkExpeience.setVisibility(View.VISIBLE);
            ll_editworkExperience.setVisibility(View.GONE);
            lv_workExperience.setVisibility(View.VISIBLE);
        } else {
            tv_completeWorkExpeience.setVisibility(View.GONE);
            ll_editworkExperience.setVisibility(View.VISIBLE);
            lv_workExperience.setVisibility(View.VISIBLE);
        }

        if (TextUtils.equals(SharedPreferencesUtils.getParam(mContext, "projectExpeienceTag","0").toString(), "0")) {
            tv_completeProjectExpeience.setVisibility(View.VISIBLE);
            ll_editProjectExpeience.setVisibility(View.GONE);
            lv_ProjectExpeience.setVisibility(View.GONE);
        } else {
            tv_completeProjectExpeience.setVisibility(View.GONE);
            ll_editProjectExpeience.setVisibility(View.VISIBLE);
            lv_ProjectExpeience.setVisibility(View.VISIBLE);
        }
    }

    //初始化基本信息
    private void initBasicInfo() {

        //更改图像
        String avaterImgTag=SharedPreferencesUtils.getParam(mContext,"avaterImgTag","0")+"";
        if (avaterImgTag=="1"){
            String avaterUri = SharedPreferencesUtils.getParam(mContext, "avaterUri", "fefwefw").toString();
            if (avaterUri != null) {
                imageView_avater.setImageURI(null);
                imageView_avater.setImageURI(Uri.parse(avaterUri));
            }
        }

        tv_userName.setText(SharedPreferencesUtils.getParam(mContext,"resume_name","用户名").toString());
        String sex=SharedPreferencesUtils.getParam(mContext,"resume_sex","男").toString();
        if (TextUtils.equals(sex,"男")){
            img_sex.setImageResource(R.drawable.icon_male);
        }else {
            img_sex.setImageResource(R.drawable.icon_female);
        }
        tv_phoneNumber.setText(SharedPreferencesUtils.getParam(mContext,"resume_phoneNumber","1886893753").toString());
        basic_tv_location.setText(SharedPreferencesUtils.getParam(mContext, "resume_location", "居住地").toString());
        basic_tv_workLength.setText(SharedPreferencesUtils.getParam(mContext, "resume_workLength", "工作年限").toString());
        basic_tv_rencentStatus.setText(SharedPreferencesUtils.getParam(mContext, "resume_recentStatus", "工作状态").toString());
    }

    /*
    * 编辑基本信息
    * */
    @Click(R.id.ac_resume_ll_editBasicInfo)
    public void editBasicInfo(){
        Intent intent=new Intent(mContext,ActivityResumeBasicInfo_.class);
        startActivity(intent);
    }


    //编辑期望职位
    @Click(R.id.ac_resume_tv_completeWishJob)
    public void WishJob() {
        Intent intent=new Intent(mContext,ActivityWishJob_.class);
        startActivity(intent);
    }

    //编辑期望职位
    @Click(R.id.ac_resume_ll_editwishJob)
    public void editWishJob() {
        Intent intent=new Intent(mContext,ActivityWishJob_.class);
        startActivity(intent);
    }


    //添加教育经历
    @Click(R.id.ac_resume_tv_completeEducateExperience)
    public void addEducate() {
        SharedPreferencesUtils.setParam(mContext,"edu_AddOrEditTag","0");
        Intent intent=new Intent(mContext,ActivityEducateExperience_.class);
        startActivity(intent);
    }

    //编辑教育经历
    @Click(R.id.ac_resume_ll_editEducate)
    public void editEducate() {
        Intent intent = new Intent(mContext, ActivityEditEducateExperience_.class);
        startActivity(intent);
    }

    //添加工作经历

    @Click(R.id.ac_resume_tv_completeWorkExpeience)
    public void addworkExperience(){
        SharedPreferencesUtils.setParam(mContext,"workExp_AddOrEditTag","0");
        Intent intent=new Intent(mContext,ActivityResumeWorkExperience_.class);
        startActivity(intent);
    }

    //编辑工作经历
    @Click(R.id.ac_resume_ll_editworkExperience)
    public void editworkExperience() {
        Intent intent = new Intent(mContext, ActivityEditWorkExperience_.class);
        startActivity(intent);
    }

    //添加项目经历

    @Click(R.id.ac_resume_tv_completeProjectExpeience)
    public void addProjectExpeience(){
        SharedPreferencesUtils.setParam(mContext,"projectExp_AddOrEditTag","0");
        Intent intent=new Intent(mContext,ActivityProjectExperience_.class);
        startActivity(intent);
    }

    //编辑项目经历

    @Click(R.id.ac_resume_ll_editProjectExpeience)
    public void ProjectExpeience() {
        Intent intent = new Intent(mContext, ActivityEditProjectExperience_.class);
        startActivity(intent);
    }

    //更换头像
    @Click(R.id.ac_resume_img_avater)
    public void changeAvater(){
        MyDialog.showPhotoDialog(ActivityResume.this, "更改头像",new int[]{R.drawable.conversation_options_secretfile,
                        R.drawable.conversation_options_camera},
                new String[]{"相册","照相"},new MyDialog.MyDialogItemOnClick()
                {

                    @Override
                    public void itemSelect(String itemSelect) {
                        if(itemSelect.equals("相册"))
                        {
                            HeadImageUtils.getFromLocation(ActivityResume.this);

                        }
                        if(itemSelect.equals("照相"))
                        {
                            if (ContextCompat.checkSelfPermission(ActivityResume.this,
                                    Manifest.permission.CAMERA)
                                    != PackageManager.PERMISSION_GRANTED) {

                                if (ActivityCompat.shouldShowRequestPermissionRationale(ActivityResume.this,
                                        Manifest.permission.CAMERA)) {

                                } else {
                                    ActivityCompat.requestPermissions(ActivityResume.this,
                                            new String[]{Manifest.permission.CAMERA},
                                            REQUEST_PERMISSION_CAMERA_CODE);
                                }
                            } else {
                                HeadImageUtils.getFromCamara(ActivityResume.this);
                            }

                        }

                    }

                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CAMERA_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    HeadImageUtils.getFromCamara(ActivityResume.this);
                } else {
                    Toast.makeText(ActivityResume.this, "没有相机权限", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }


     //裁剪图像后返回

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case HeadImageUtils.FROM_CRAMA:
                if(HeadImageUtils.photoCamare!=null)
                {
                    HeadImageUtils.cutCorePhoto(this, HeadImageUtils.photoCamare);
                }
                break;
            case HeadImageUtils.FROM_LOCAL:
                if(data!=null&&data.getData()!=null)
                {
                    HeadImageUtils.cutCorePhoto(this, data.getData());
                }
                break;
            case HeadImageUtils.FROM_CUT:
                if(HeadImageUtils.cutPhoto != null)
                {
                    imageView_avater.setImageURI(HeadImageUtils.cutPhoto);

                    String ImgUri=null;
                    ImgUri= HeadImageUtils.cutPhoto.toString();
                    SharedPreferencesUtils.setParam(ActivityResume.this,"avaterUri",ImgUri);
                    SharedPreferencesUtils.setParam(ActivityResume.this,"avaterImgTag","0");
                    Uri uri=Uri.parse(ImgUri);
                    Log.i("ImgUri",uri.getPath());
                    String[] proj = { MediaStore.Images.Media.DATA };
                    Cursor cursor = managedQuery(uri,proj,null,null,null);
                    int actual_image_column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    img_path = cursor.getString(actual_image_column_index);
                    Log.i("imgpath",img_path);
                    //图片上传
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            client=new OkHttpClient();
                                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                                File f=new File(img_path);
                                if (f!=null){
                                    builder.addFormDataPart("id",userId);
                                    builder.addFormDataPart("img",f.getName(), RequestBody.create(MEDIA_TYPE_JPG,f));
                                }
                                MultipartBody requestBody = builder.build();
                                Request request=new Request.Builder()
                                        .url(url_uploadAvater)
                                        .post(requestBody)
                                        .build();
                                client.newCall(request).enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {

                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        UnicodeDecode unicodeDecode=new UnicodeDecode();
                                        String code=unicodeDecode.decode(response.body().string());
                                        Log.i("dsdsdsdsdsdsds",code);
                                        Log.d("upload","上传成功");
                                    }
                                });

                        }
                    }).start();
                }
                break;

        }
    }

    @Click(R.id.ac_resume_img_back)
    public void back() {
        finish();
    }

    /**
     * 解决scrolView与ListView冲突
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        EduInfo2ListAdapter listAdapter = (EduInfo2ListAdapter) listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    public void setListViewHeightBasedOnChildren2(ListView listView) {
        WorkInfo2ListAdapter listAdapter = (WorkInfo2ListAdapter) listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    public void setListViewHeightBasedOnChildren3(ListView listView) {
        ProjectInfo2ListAdapter listAdapter = (ProjectInfo2ListAdapter) listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

}
