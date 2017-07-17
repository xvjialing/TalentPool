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
import com.xvjialing.administrator.talentpool.bean.BoleInfo;
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
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@EActivity
public class ActivityEditBole extends AppCompatActivity {

    @ViewById(R.id.ac_editBole_img_back)
    ImageView mg_back;
    @ViewById(R.id.ac_editBole_lv_list)
    ListView lv_list;
    @ViewById(R.id.ac_editBole_btn_add)
    Button btn_add;

    private static final String url_show = AppHttp.Url_IP + "opensns/index.php?s=/Home/rencai/boleshow";
    private static final String url_delete = AppHttp.Url_IP + "opensns/index.php?s=/Home/rencai/deletebole";


    private String userId,projectId,boleId;
    private OkHttpClient okHttpClient;

    private String list_boleId,list_projectId,list_id,list_boleName,list_phoneNumber,list_reliability;

    private ArrayList<BoleInfo> boleInfoList;
    private BoleInfoListAdapter boleInfoListAdapter;

    private ProgressDialog pDialog;
    private Context mContext=ActivityEditBole.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_edit_bole);
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

    @Click(R.id.ac_editBole_img_back)
    public void back() {
        finish();
    }

    @Click(R.id.ac_editBole_btn_add)
    public void add() {
        SharedPreferencesUtils.setParam(mContext,"bole_AddOrEditTag","0");
        startActivity(new Intent(ActivityEditBole.this, ActivityAddBole_.class));
    }

    private class BoleInfoListAdapter extends BaseAdapter {

        private ArrayList<BoleInfo> boleInfoList;

        public BoleInfoListAdapter(ArrayList<BoleInfo> list) {
            this.boleInfoList = list;
        }

        @Override
        public int getCount() {
            return boleInfoList.size();
        }

        @Override
        public Object getItem(int position) {
            return boleInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ListViewItemHolder item=null;
            if (convertView==null){
                convertView= LayoutInflater.from(mContext)
                        .inflate(R.layout.list_item_bole,null);
                item=new ListViewItemHolder();
                item.tv_boleName= (TextView) convertView.findViewById(R.id.item_tv_boleName);
                item.tv_reliability= (TextView) convertView.findViewById(R.id.item_tv_reliability);
                item.tv_phoneNumber= (TextView) convertView.findViewById(R.id.item_tv_phoneNumber);
                item.img_delete= (ImageView) convertView.findViewById(R.id.item_img_deleteBole);
                item.ll_edit= (LinearLayout) convertView.findViewById(R.id.item_ll_editBole);

                convertView.setTag(item);
            }else {
                item= (ListViewItemHolder) convertView.getTag();
            }

            final BoleInfo boleInfo=boleInfoList.get(position);

            item.tv_boleName.setText(boleInfo.getBoleName());
            item.tv_reliability.setText(boleInfo.getReliability());
            item.tv_phoneNumber.setText(boleInfo.getPhoneNumber());
            item.img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boleId=boleInfo.getBoleId();
                    Log.i("boleId",boleId);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            okHttpClient=new OkHttpClient();
                            FormBody formBody=new FormBody.Builder()
                                    .add("boleId",boleId)
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

                                    Log.i("deleteBole",response.body().string());
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
                    String boleId=boleInfo.getBoleId();
                    String boleName=boleInfo.getBoleName();
                    String phoneNumber=boleInfo.getPhoneNumber();
                    String reliability=boleInfo.getReliability();

                    SharedPreferencesUtils.setParam(mContext,"boleId",boleId);
                    SharedPreferencesUtils.setParam(mContext,"boleName",boleName);
                    SharedPreferencesUtils.setParam(mContext,"phoneNumber",phoneNumber);
                    SharedPreferencesUtils.setParam(mContext,"reliability",reliability);
                    SharedPreferencesUtils.setParam(mContext,"bole_AddOrEditTag","1");
                    startActivity(new Intent(mContext,ActivityAddBole_.class));
                }
            });
            return convertView;
        }
    }

    private class ListViewItemHolder {
        TextView tv_boleName;
        TextView tv_reliability;
        TextView tv_phoneNumber;
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
            projectId= SharedPreferencesUtils.getParam(mContext,"projectId","projectId")+"";
            userId = SharedPreferencesUtils.getParam(mContext, "userId", "") + "";
            boleInfoList=new ArrayList<BoleInfo>();

            JSONParser jsonParser=new JSONParser();
            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("id",userId));
            params1.add(new BasicNameValuePair("projectId",projectId));
            JSONObject jsonObject=jsonParser.makeHttpRequest(url_show,"POST",params1);

            int lp= 0;
            try {
                lp = jsonObject.getInt("lp");

                s=lp+"";

                JSONArray jsonArray;
                jsonArray = jsonObject.getJSONArray("data");

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject2 =jsonArray.getJSONObject(i);
                    list_boleId=jsonObject2.getString("boleId");
                    list_projectId=jsonObject2.getString("projectId");
                    list_id=jsonObject2.getString("id");
                    list_boleName=jsonObject2.getString("boleName");
                    list_phoneNumber=jsonObject2.getString("phoneNumber");
                    list_reliability=jsonObject2.getString("reliability");

                    boleInfoList.add(new BoleInfo(list_boleId,list_projectId,list_id,list_boleName,
                            list_phoneNumber,list_reliability));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return s;
        }

        @Override
        protected void onPostExecute(String s) {

            pDialog.dismiss();

            boleInfoListAdapter=new BoleInfoListAdapter(boleInfoList);
            lv_list.setAdapter(boleInfoListAdapter);
            super.onPostExecute(s);
        }
    }

}

