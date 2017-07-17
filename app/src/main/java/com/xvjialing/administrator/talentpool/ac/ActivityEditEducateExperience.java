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
import com.xvjialing.administrator.talentpool.bean.JobInfo;
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
public class ActivityEditEducateExperience extends AppCompatActivity {

    @ViewById(R.id.ac_editEducateExperience_img_back)
    ImageView img_back;
    @ViewById(R.id.ac_editEducateExperience_lv_list)
    ListView lv_list;
    @ViewById(R.id.ac_editEducateExperience_btn_add)
    Button btn_add;

    private static final String url_show = AppHttp.Url_IP + "opensns/index.php?s=/Home/rencai/edushow";
    private static final String url_delete = AppHttp.Url_IP + "opensns/index.php?s=/Home/rencai/deleteedu";

    private String userId,educateId;

    private String list_educationId,list_id,list_schoolName,list_startDate,list_endDate,list_major;
    private String list_description,list_educationLevel;

    private ArrayList<EduInfo> eduInfoList;
    private EduInfoListAdapter eduInfoListAdapter;

    private ProgressDialog pDialog;
    private Context mContext=ActivityEditEducateExperience.this;

    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_edit_educate_experience);
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

    @Click(R.id.ac_editEducateExperience_img_back)
    public void back() {
        finish();
    }

    @Click(R.id.ac_editEducateExperience_btn_add)
    public void add() {
        SharedPreferencesUtils.setParam(mContext,"edu_AddOrEditTag","0");
        startActivity(new Intent(ActivityEditEducateExperience.this, ActivityEducateExperience_.class));
    }

    private class EduInfoListAdapter extends BaseAdapter {

        private ArrayList<EduInfo> eduInfoList;

        public EduInfoListAdapter(ArrayList<EduInfo> list) {
            this.eduInfoList = list;
        }

        @Override
        public int getCount() {
            return eduInfoList.size();
        }

        @Override
        public Object getItem(int position) {
            return eduInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListViewItemHolder item=null;
            if (convertView==null){
                convertView= LayoutInflater.from(ActivityEditEducateExperience.this)
                        .inflate(R.layout.list_item_educate,null);
                item=new ListViewItemHolder();
                item.tv_schoolName= (TextView) convertView.findViewById(R.id.item_tv_schoolName);
                item.tv_majorName= (TextView) convertView.findViewById(R.id.item_tv_majorName);
                item.tv_educateTime= (TextView) convertView.findViewById(R.id.item_tv_educateTime);
                item.img_delete= (ImageView) convertView.findViewById(R.id.item_img_deleteEducate);
                item.ll_edit= (LinearLayout) convertView.findViewById(R.id.item_ll_editEducate);

                convertView.setTag(item);
            }else {
                item= (ListViewItemHolder) convertView.getTag();
            }

            final EduInfo eduInfo=eduInfoList.get(position);

            item.tv_schoolName.setText(eduInfo.getSchoolName());
            item.tv_majorName.setText(eduInfo.getMajor());
            item.tv_educateTime.setText(eduInfo.getStartDate()+"~"+eduInfo.getEndDate());
            item.img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    educateId=eduInfo.getEducationId();
                    Log.i("educateId",educateId);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            okHttpClient=new OkHttpClient();
                            FormBody formBody=new FormBody.Builder()
                                    .add("educationId",educateId)
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
                                    Log.i("deleteTag",response.body().string());
                                }
                            });

                        }
                    }).start();

                    new ListData().execute();

                    Log.i("educateId",educateId);
                }
            });
            item.ll_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String educateId=eduInfo.getEducationId();
                    String schoolName=eduInfo.getSchoolName();
                    String majorName=eduInfo.getMajor();
                    String educationLevel=eduInfo.getEducationLevel();
                    String startTime=eduInfo.getStartDate();
                    String endTime=eduInfo.getEndDate();
                    SharedPreferencesUtils.setParam(mContext,"educateId",educateId);
                    SharedPreferencesUtils.setParam(mContext,"schoolName",schoolName);
                    SharedPreferencesUtils.setParam(mContext,"majorName",majorName);
                    SharedPreferencesUtils.setParam(mContext,"educationLevel",educationLevel);
                    SharedPreferencesUtils.setParam(mContext,"startTime",startTime);
                    SharedPreferencesUtils.setParam(mContext,"endTime",endTime);
                    SharedPreferencesUtils.setParam(mContext,"edu_AddOrEditTag","1");
                    startActivity(new Intent(ActivityEditEducateExperience.this,ActivityEducateExperience_.class));
                }
            });
            return convertView;
        }
    }

    private class ListViewItemHolder {
        TextView tv_schoolName;
        TextView tv_majorName;
        TextView tv_educateTime;
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
            eduInfoList=new ArrayList<EduInfo>();

            JSONParser jsonParser=new JSONParser();
            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("id",userId));
            JSONObject jsonObject=jsonParser.makeHttpRequest(url_show,"POST",params1);
            Log.i("---------++++++++++", String.valueOf(jsonObject));

            try {
                int lp=jsonObject.getInt("lp");

                s=lp+"";

                JSONArray jsonArray;
                jsonArray = jsonObject.getJSONArray("data");

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject2 =jsonArray.getJSONObject(i);
                    list_educationId=jsonObject2.getString("educationId");
                    list_id=jsonObject2.getString("id");
                    list_schoolName=jsonObject2.getString("schoolName");
                    list_startDate=jsonObject2.getString("startDate");
                    list_endDate=jsonObject2.getString("endDate");
                    list_major=jsonObject2.getString("major");
                    list_description=jsonObject2.getString("description");
                    list_educationLevel=jsonObject2.getString("educationLevel");

                    eduInfoList.add(new EduInfo(list_educationId,list_id,list_schoolName,list_startDate,
                            list_endDate,list_major,list_description,list_educationLevel));

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return s;
        }

        @Override
        protected void onPostExecute(String s) {

            eduInfoListAdapter=new EduInfoListAdapter(eduInfoList);
            lv_list.setAdapter(eduInfoListAdapter);

            pDialog.dismiss();
            super.onPostExecute(s);
        }
    }
}
