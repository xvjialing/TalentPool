package com.xvjialing.administrator.talentpool.ac;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xvjialing.administrator.talentpool.R;
import com.xvjialing.administrator.talentpool.bean.EduInfo;
import com.xvjialing.administrator.talentpool.bean.ProjectInfo;
import com.xvjialing.administrator.talentpool.json.AppHttp;
import com.xvjialing.administrator.talentpool.json.JSONParser;
import com.xvjialing.administrator.talentpool.utils.SharedPreferencesUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@EActivity
public class ActivityEditProjectExperience extends AppCompatActivity {

    @ViewById(R.id.ac_editProjetExperience_img_back)
    ImageView img_back;

    @ViewById(R.id.ac_editProjetExperience_lv_list)
    ListView lv_list;
    @ViewById(R.id.ac_editProjetExperience_btn_add)
    Button btn_add;

    private static final String url_show = AppHttp.Url_IP + "opensns/index.php?s=/Home/rencai/projectshow";
    private static final String url_delete = AppHttp.Url_IP + "opensns/index.php?s=/Home/rencai/deleteproject";

    private String userId,projectId;
    private OkHttpClient okHttpClient;

    private String list_projectId,list_id,list_projectName,list_startDate,list_endDate,list_projectRole;
    private String list_projectDescription,list_companyName,list_status,list_technique;

    private ArrayList<ProjectInfo> projectInfoList;
    private ProjectInfoListAdapter projectInfoListAdapter;

    private ProgressDialog pDialog;
    private Context mContext=ActivityEditProjectExperience.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_edit_project_experience);
    }

    @Override
    protected void onResume() {
        new ListData().execute();
        super.onResume();
    }

    @AfterViews
    public void getListData(){
        new ListData().execute();
        pDialog.dismiss();
    }

    @Click(R.id.ac_editProjetExperience_img_back)
    public void back() {
        finish();
    }

    @Click(R.id.ac_editProjetExperience_btn_add)
    public void add() {
        SharedPreferencesUtils.setParam(mContext,"projectExp_AddOrEditTag","0");
        startActivity(new Intent(ActivityEditProjectExperience.this, ActivityProjectExperience_.class));
    }

    private class ProjectInfoListAdapter extends BaseAdapter {
        private ArrayList<ProjectInfo> projectInfoList;

        public ProjectInfoListAdapter(ArrayList<ProjectInfo> list) {
            this.projectInfoList = list;
        }

        @Override
        public int getCount() {
            return projectInfoList.size();
        }

        @Override
        public Object getItem(int position) {
            return projectInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListViewItemHolder item=null;
            if (convertView==null){
                convertView= LayoutInflater.from(mContext)
                        .inflate(R.layout.list_item_project,null);
                item=new ListViewItemHolder();
                item.tv_projectName= (TextView) convertView.findViewById(R.id.ac_resume_tv_projectName);
                item.tv_projectRole= (TextView) convertView.findViewById(R.id.ac_resume_tv_projectRole);
                item.tv_reference= (TextView) convertView.findViewById(R.id.ac_resume_tv_reference);
                item.tv_time= (TextView) convertView.findViewById(R.id.ac_resume_tv_time);
                item.tv_company= (TextView) convertView.findViewById(R.id.ac_resume_tv_company);
                item.tv_skill= (TextView) convertView.findViewById(R.id.ac_resume_tv_skill);
                item.tv_projectDescription= (TextView) convertView.findViewById(R.id.ac_resume_tv_projectDescription);
                item.img_delete= (ImageView) convertView.findViewById(R.id.item_img_deleteProject);
                item.ll_edit= (LinearLayout) convertView.findViewById(R.id.item_ll_editProjectExpeience);

                convertView.setTag(item);
            }else {
                item= (ListViewItemHolder) convertView.getTag();
            }

            final ProjectInfo projectInfo=projectInfoList.get(position);

            item.tv_projectName.setText(projectInfo.getProjectName());
            item.tv_projectRole.setText(projectInfo.getProjectRole());
            item.tv_reference.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferencesUtils.setParam(mContext,"projectId",projectInfo.getProjectId());
                    startActivity(new Intent(mContext,ActivityEditBole_.class));
                }
            });
            item.tv_time.setText(projectInfo.getStartDate()+"~"+projectInfo.getEndDate());
            item.tv_company.setText(projectInfo.getCompanyName());
            item.tv_skill.setText(projectInfo.getTechnique());
            item.tv_projectDescription.setText(projectInfo.getProjectDescription());
            item.img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    projectId=projectInfo.getProjectId();
                    Log.i("projectId",projectId);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            okHttpClient=new OkHttpClient();
                            FormBody formBody=new FormBody.Builder()
                                    .add("projectId",projectId)
                                    .build();
                            Request request=new Request.Builder()
                                    .url(url_delete)
                                    .post(formBody)
                                    .build();
                            okHttpClient.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {

                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    Log.i("deleteProjectExp",response.body().string());
                                }
                            });
                        }
                    }).start();

                    new ListData().execute();
                }
            });
            item.ll_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String projectId=projectInfo.getProjectId();
                    String projectName=projectInfo.getProjectName();
                    String companyName=projectInfo.getCompanyName();
                    String projectRole=projectInfo.getProjectRole();
                    String technique=projectInfo.getTechnique();
                    String startDate=projectInfo.getStartDate();
                    String endDate=projectInfo.getEndDate();
                    String description=projectInfo.getProjectDescription();

                    SharedPreferencesUtils.setParam(mContext,"projectId",projectId);
                    SharedPreferencesUtils.setParam(mContext,"projectName",projectName);
                    SharedPreferencesUtils.setParam(mContext,"companyName",companyName);
                    SharedPreferencesUtils.setParam(mContext,"projectRole",projectRole);
                    SharedPreferencesUtils.setParam(mContext,"technique",technique);
                    SharedPreferencesUtils.setParam(mContext,"startDate",startDate);
                    SharedPreferencesUtils.setParam(mContext,"endDate",endDate);
                    SharedPreferencesUtils.setParam(mContext,"description",description);
                    SharedPreferencesUtils.setParam(mContext,"projectExp_AddOrEditTag","1");

                    startActivity(new Intent(mContext,ActivityProjectExperience_.class));
                }
            });

            return convertView;
        }
    }

    private class ListViewItemHolder {
        TextView tv_projectName;
        TextView tv_projectRole;
        TextView tv_reference;
        TextView tv_time;
        TextView tv_company;
        TextView tv_skill;
        TextView tv_projectDescription;
        ImageView img_delete;
        LinearLayout ll_edit;
    }

    private class ListData  extends AsyncTask<String,String,String> {
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
            String s=null;
            userId = SharedPreferencesUtils.getParam(mContext, "userId", "") + "";
            projectInfoList=new ArrayList<ProjectInfo>();

            JSONParser jsonParser=new JSONParser();
            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("id",userId));
            JSONObject jsonObject=jsonParser.makeHttpRequest(url_show,"POST",params1);
            Log.i("---------++++++++++", String.valueOf(jsonObject));

            int lp= 0;
            try {
                lp = jsonObject.getInt("lp");

                s=lp+"";

                JSONArray jsonArray;
                jsonArray = jsonObject.getJSONArray("data");

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject2 =jsonArray.getJSONObject(i);
                    list_projectId=jsonObject2.getString("projectId");
                    list_id=jsonObject2.getString("id");
                    list_projectName=jsonObject2.getString("projectName");
                    list_startDate=jsonObject2.getString("startDate");
                    list_endDate=jsonObject2.getString("endDate");
                    list_projectRole=jsonObject2.getString("projectRole");
                    list_projectDescription=jsonObject2.getString("projectDescription");
                    list_companyName=jsonObject2.getString("companyName");
                    list_technique=jsonObject2.getString("technique");

                    projectInfoList.add(new ProjectInfo(list_projectId,list_id,list_projectName,list_startDate,
                            list_endDate,list_projectRole,list_projectDescription,list_companyName,list_status
                    ,list_technique));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return s;
        }

        @Override
        protected void onPostExecute(String s) {

            projectInfoListAdapter=new ProjectInfoListAdapter(projectInfoList);
            lv_list.setAdapter(projectInfoListAdapter);

            pDialog.dismiss();
            super.onPostExecute(s);
        }
    }
}
