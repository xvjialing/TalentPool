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
import com.xvjialing.administrator.talentpool.bean.WorkInfo;
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
public class ActivityEditWorkExperience extends AppCompatActivity {

    @ViewById(R.id.ac_editWorkExperience_img_back)
    ImageView img_back;
    @ViewById(R.id.ac_editWorkExperience_lv_list)
    ListView lv_list;
    @ViewById(R.id.ac_editWorkExperience_btn_add)
    Button btn_add;

    private static final String url_show = AppHttp.Url_IP + "opensns/index.php?s=/Home/rencai/workshow";
    private static final String url_delete = AppHttp.Url_IP + "opensns/index.php?s=/Home/rencai/deletework";

    private String userId,workExperienceId;
    private OkHttpClient okHttpClient;

    private String list_ExperienceId,list_id,list_companyName,list_startTime,list_endTime,list_address;
    private String list_description,list_position,list_industry,list_reason;

    private ArrayList<WorkInfo> workInfoList;
    private WorkInfoListAdapter workInfoListAdapter;

    private ProgressDialog pDialog;
    private Context mContext=ActivityEditWorkExperience.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_edit_work_experience);
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

    @Click(R.id.ac_editWorkExperience_img_back)
    public void back() {
        finish();
    }

    @Click(R.id.ac_editWorkExperience_btn_add)
    public void add() {
        SharedPreferencesUtils.setParam(mContext,"workExp_AddOrEditTag","0");
        startActivity(new Intent(ActivityEditWorkExperience.this, ActivityResumeWorkExperience_.class));
    }

    private class WorkInfoListAdapter extends BaseAdapter {
        private ArrayList<WorkInfo> workInfoList;

        public WorkInfoListAdapter(ArrayList<WorkInfo> list) {
            this.workInfoList = list;
        }

        @Override
        public int getCount() {
            return workInfoList.size();
        }

        @Override
        public Object getItem(int position) {
            return workInfoList.get(position);
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
                        .inflate(R.layout.list_item_work_ex,null);
                item=new ListViewItemHolder();
                item.tv_companyName= (TextView) convertView.findViewById(R.id.item_tv_companyName);
                item.tv_position= (TextView) convertView.findViewById(R.id.item_tv_jobName);
                item.tv_industry= (TextView) convertView.findViewById(R.id.item_tv_industry);
                item.tv_address= (TextView) convertView.findViewById(R.id.item_tv_address);
                item.tv_onJobTime= (TextView) convertView.findViewById(R.id.item_tv_onJobTime);
                item.tv_offJobReason= (TextView) convertView.findViewById(R.id.item_tv_offJobReason);
                item.tv_description= (TextView) convertView.findViewById(R.id.item_tv_jobDescription);
                item.img_delete= (ImageView) convertView.findViewById(R.id.item_img_deleteWorkExperience);
                item.ll_edit= (LinearLayout) convertView.findViewById(R.id.item_ll_editworkExperience);

                convertView.setTag(item);
            }else {
                item= (ListViewItemHolder) convertView.getTag();
            }

            final WorkInfo workInfo=workInfoList.get(position);

            item.tv_companyName.setText(workInfo.getCompanyName());
            item.tv_position.setText(workInfo.getPosition());
            item.tv_industry.setText(workInfo.getIndustry());
            item.tv_address.setText(workInfo.getAddress());
            item.tv_onJobTime.setText(workInfo.getStartTime()+"~"+workInfo.getEndTime());
            item.tv_offJobReason.setText(workInfo.getReason());
            item.tv_description.setText(workInfo.getDescription());
            item.img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    workExperienceId=workInfo.getExperienceId();
                    Log.i("workexperienceId",workExperienceId);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            okHttpClient=new OkHttpClient();
                            FormBody formBody=new FormBody.Builder()
                                    .add("workexperienceId",workExperienceId)
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
                                    Log.i("deleteWorkExpTag",response.body().string());
                                }
                            });
                        }
                    }).start();

                    new ListData().execute();
                    Log.i("workExperienceId",workExperienceId);
                }
            });
            item.ll_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String workExperienceId=workInfo.getExperienceId();
                    String companyName=workInfo.getCompanyName();
                    String position=workInfo.getPosition();
                    String industry=workInfo.getIndustry();
                    String address=workInfo.getAddress();
                    String startTime=workInfo.getStartTime();
                    String endTime=workInfo.getEndTime();
                    String reason=workInfo.getReason();
                    String description=workInfo.getDescription();

                    SharedPreferencesUtils.setParam(mContext,"workExperienceId",workExperienceId);
                    SharedPreferencesUtils.setParam(mContext,"companyName",companyName);
                    SharedPreferencesUtils.setParam(mContext,"position",position);
                    SharedPreferencesUtils.setParam(mContext,"industry",industry);
                    SharedPreferencesUtils.setParam(mContext,"address",address);
                    SharedPreferencesUtils.setParam(mContext,"startTime",startTime);
                    SharedPreferencesUtils.setParam(mContext,"endTime",endTime);
                    SharedPreferencesUtils.setParam(mContext,"reason",reason);
                    SharedPreferencesUtils.setParam(mContext,"description",description);
                    SharedPreferencesUtils.setParam(mContext,"workExp_AddOrEditTag","1");
                    startActivity(new Intent(mContext,ActivityResumeWorkExperience_.class));
                }
            });

            return convertView;
        }
    }

    private class ListViewItemHolder {
        TextView tv_companyName;
        TextView tv_position;
        TextView tv_industry;
        TextView tv_address;
        TextView tv_onJobTime;
        TextView tv_offJobReason;
        TextView tv_description;
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
            workInfoList=new ArrayList<WorkInfo>();

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
                    list_ExperienceId=jsonObject2.getString("ExperienceId");
                    list_id=jsonObject2.getString("id");
                    list_companyName=jsonObject2.getString("companyName");
                    list_startTime=jsonObject2.getString("startTime");
                    list_endTime=jsonObject2.getString("endTime");
                    list_address=jsonObject2.getString("address");
                    list_description=jsonObject2.getString("description");
                    list_position=jsonObject2.getString("position");
                    list_industry=jsonObject2.getString("industry");
                    list_reason=jsonObject2.getString("reason");

                    workInfoList.add(new WorkInfo(list_ExperienceId,list_id,list_companyName,list_startTime,
                            list_endTime,list_address,list_description,list_position,list_industry,list_reason));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            workInfoListAdapter=new WorkInfoListAdapter(workInfoList);
            lv_list.setAdapter(workInfoListAdapter);

            pDialog.dismiss();
            super.onPostExecute(s);
        }
    }
}
