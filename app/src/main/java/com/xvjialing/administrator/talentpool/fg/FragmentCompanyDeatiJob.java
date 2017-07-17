package com.xvjialing.administrator.talentpool.fg;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xvjialing.administrator.talentpool.R;
import com.xvjialing.administrator.talentpool.ac.ActivityJobDetail_;
import com.xvjialing.administrator.talentpool.bean.JobInfo;
import com.xvjialing.administrator.talentpool.json.AppHttp;
import com.xvjialing.administrator.talentpool.json.JSONParser;
import com.xvjialing.administrator.talentpool.utils.SharedPreferencesUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@EFragment
public class FragmentCompanyDeatiJob extends Fragment {

    private static final String img_url_base = AppHttp.Url_IP + "opensns/Uploads/";
    private static String url_job = AppHttp.Url + "companyshow";

    private ArrayList<JobInfo> jobList;
    private JobListAdapter jobListAdapter;

    private ProgressDialog pDialog;

    private String list_jobName,list_address,list_salary,list_workLength;
    private String list_publishTime,list_website,list_companyaddress,list_companyId;
    private String list_companyName,list_companySize,list_companyLogo;
    private String list_degree,list_welfare,list_discrip,list_phoneNumber;

    public FragmentCompanyDeatiJob() {}

    @ViewById(R.id.fg_companyDetailJob_lv_job)
    ListView lv_job;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_company_deati_job, container, false);
    }

    @AfterViews
    public void getListData(){
        new ListData().execute();
    }

    /**
     * 解决scrolView与ListView冲突
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        JobListAdapter listAdapter = (JobListAdapter) listView.getAdapter();
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

    private class ListData extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("正在加载..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String n=SharedPreferencesUtils.getParam(getContext(),"jobTag",1)+"";
            String companyId=SharedPreferencesUtils.getParam(getContext(),"companyId","")+"";
            String s=null;

            jobList=new ArrayList<JobInfo>();

            JSONParser jsonParser=new JSONParser();
            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("companyId",companyId));
            JSONObject jsonObject=jsonParser.makeHttpRequest(url_job,"POST",params1);

            try {
                int lp=jsonObject.getInt("lp");

                s=lp+"";

                JSONArray jsonArray;
                jsonArray = jsonObject.getJSONArray("data1");
                Log.i("test", String.valueOf(jsonArray));

                for(int i=0;i<jsonArray.length();i++){
//                    SharedPreferencesUtils.setParam(getContext(),"arrayLength",jsonArray.length());
                    JSONObject jsonObject2 =jsonArray.getJSONObject(i);

                    list_jobName=jsonObject2.getString("jobName");
                    list_address=jsonObject2.getString("address");
                    list_salary=jsonObject2.getString("salary");
                    list_workLength=jsonObject2.getString("workLength");
                    list_publishTime=jsonObject2.getString("publishingTime");
                    list_companyName=jsonObject2.getString("companyName");
                    list_companySize=jsonObject2.getString("size");
                    list_companyLogo=jsonObject2.getString("big_logo");
                    list_degree=jsonObject2.getString("degree");
                    list_welfare=jsonObject2.getString("welfare");
                    list_discrip=jsonObject2.getString("descrip");
                    list_companyaddress=jsonObject2.getString("company_address");
                    list_phoneNumber=jsonObject2.getString("phone");
                    Log.i("phone",list_phoneNumber);
                    list_website=jsonObject2.getString("website");
                    list_companyId=jsonObject2.getString("companyId");

                    jobList.add(new JobInfo(list_jobName,list_address,list_salary,list_workLength
                            ,list_publishTime,list_companyName,list_companySize,
                            img_url_base+list_companyLogo,list_degree,list_welfare,list_discrip,list_companyaddress,
                            list_phoneNumber,list_website,list_companyId));

                    SharedPreferencesUtils.setParam(getContext(),"jobName"+i,list_jobName);
                    SharedPreferencesUtils.setParam(getContext(),"address"+i,list_address);
                    SharedPreferencesUtils.setParam(getContext(),"salary"+i,list_salary);
                    SharedPreferencesUtils.setParam(getContext(),"workLength"+i,list_workLength);
                    SharedPreferencesUtils.setParam(getContext(),"publishingTime"+i,list_publishTime);
                    SharedPreferencesUtils.setParam(getContext(),"companyName"+i,list_companyName);
                    SharedPreferencesUtils.setParam(getContext(),"size"+i,list_companySize);
                    SharedPreferencesUtils.setParam(getContext(),"big_logo"+i,list_companyLogo);
                    SharedPreferencesUtils.setParam(getContext(),"degree"+i,list_degree);
                    SharedPreferencesUtils.setParam(getContext(),"welfare"+i,list_welfare);
                    SharedPreferencesUtils.setParam(getContext(),"descrip"+i,list_discrip);
                    SharedPreferencesUtils.setParam(getContext(),"companyAddress"+i,list_companyaddress);
                    SharedPreferencesUtils.setParam(getContext(),"phoneNumber"+i,list_phoneNumber);
                    SharedPreferencesUtils.setParam(getContext(),"website"+i,list_website);
                    SharedPreferencesUtils.setParam(getContext(),"companyId"+i,list_companyId);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return s;
        }

        @Override
        protected void onPostExecute(String s) {

            jobListAdapter = new JobListAdapter(jobList);

            lv_job.setAdapter(jobListAdapter);
            setListViewHeightBasedOnChildren(lv_job);

            pDialog.dismiss();

            super.onPostExecute(s);
        }
    }

    private class JobListAdapter extends BaseAdapter {

        private ArrayList<JobInfo> jobList;
        public JobListAdapter(ArrayList<JobInfo> list) {
            this.jobList = list;
        }

        @Override
        public int getCount() {
            return jobList.size();
        }

        @Override
        public Object getItem(int position) {
            return jobList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ListViewItemHolder item=null;
            if (convertView==null){
                convertView=LayoutInflater.from(getContext()).inflate(R.layout.list_item_job,null);
                item=new ListViewItemHolder();
                item.img_companyLogo= (ImageView) convertView.findViewById(R.id.item_job_tv_companyLogo);
                item.tv_jobName= (TextView) convertView.findViewById(R.id.item_job_tv_jobName);
                item.tv_address= (TextView) convertView.findViewById(R.id.item_job_tv_locaion);
                item.tv_salary= (TextView) convertView.findViewById(R.id.item_job_tv_salary);
                item.tv_workLength= (TextView) convertView.findViewById(R.id.item_job_tv_workLength);
                item.tv_publishingTime= (TextView) convertView.findViewById(R.id.item_job_tv_publishingTime);
                item.tv_CompanyName= (TextView) convertView.findViewById(R.id.item_job_tv_companyName);
                item.tv_companySize= (TextView) convertView.findViewById(R.id.item_job_tv_companySize);
                item.tv_degree= (TextView) convertView.findViewById(R.id.item_job_tv_degree);
                item.ll_whole= (LinearLayout) convertView.findViewById(R.id.item_job_ll_whole);

                convertView.setTag(item);
            }else {
                item= (ListViewItemHolder) convertView.getTag();
            }

            JobInfo jobInfo=jobList.get(position);

            //这里就是异步加载网络图片的地方
            Picasso.with(getContext()).load(jobInfo.getCompanyLogo())
                    .into(item.img_companyLogo);

            item.tv_jobName.setText(jobInfo.getJobName());
            item.tv_address.setText(jobInfo.getAddress());
            item.tv_salary.setText(jobInfo.getSalary());
            item.tv_workLength.setText(jobInfo.getWorkLength());
            item.tv_publishingTime.setText(jobInfo.getPublishingTime());
            item.tv_CompanyName.setText(jobInfo.getCompanyName());
            item.tv_companySize.setText("公司规模"+jobInfo.getSize());
            item.tv_degree.setText(jobInfo.getDegree());
            item.ll_whole.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferencesUtils.setParam(getContext(),"jobTag",position);
                    Intent intent=new Intent(getContext(),ActivityJobDetail_.class);
                    startActivity(intent);
                }
            });

            return convertView;
        }
    }

    private class ListViewItemHolder {
        ImageView img_companyLogo;
        TextView tv_jobName;
        TextView tv_address;
        TextView tv_salary;
        TextView tv_workLength;
        TextView tv_publishingTime;
        TextView tv_CompanyName;
        TextView tv_companySize;
        TextView tv_degree;
        LinearLayout ll_whole;
    }
}
