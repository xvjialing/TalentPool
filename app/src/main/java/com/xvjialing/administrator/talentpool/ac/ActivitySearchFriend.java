package com.xvjialing.administrator.talentpool.ac;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xvjialing.administrator.talentpool.R;
import com.xvjialing.administrator.talentpool.bean.FriendInfo;
import com.xvjialing.administrator.talentpool.json.AppHttp;
import com.xvjialing.administrator.talentpool.json.JSONParser;
import com.xvjialing.administrator.talentpool.utils.SharedPreferencesUtils;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@EActivity
public class ActivitySearchFriend extends AppCompatActivity {
    
    @ViewById(R.id.ac_searchFriend_lv_firend)
    ListView lv_friend;

    @ViewById(R.id.ac_searchFriend_img_search)
    ImageView img_search;

    private ArrayList<FriendInfo> friendList;
    private FriendListAdapter friendListAdapter;
    private String userId,friendId;
    private Context mContext=ActivitySearchFriend.this;

    private ProgressDialog pDialog;

    private String list_friendId,list_friendName,list_address,list_skill,list_company,list_position,list_status;

    private static final String url_friend_show = AppHttp.Url + "ren";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_search_friend);
    }

    @Click(R.id.ac_searchFriend_img_search)
    public void search(){
        new ListData().execute();
    }

    private class ListData extends AsyncTask<String,String,String> {

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

            friendList=new ArrayList<FriendInfo>();

            JSONParser jsonParser=new JSONParser();
            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            JSONObject jsonObject=jsonParser.makeHttpRequest(url_friend_show,"POST",params1);

            try {
                int lp = jsonObject.getInt("lp");

                s=lp+"";

                JSONArray jsonArray;
                jsonArray = jsonObject.getJSONArray("data");
                Log.i("test", String.valueOf(jsonArray));

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject2 =jsonArray.getJSONObject(i);
                    list_friendId=jsonObject2.getString("ren_id");
                    list_friendName=jsonObject2.getString("name");
                    list_address=jsonObject2.getString("province");
                    list_skill=jsonObject2.getString("skill");
                    list_company=jsonObject2.getString("companyname");
                    list_position=jsonObject2.getString("position");
                    list_status=jsonObject2.getString("status");
                    if (TextUtils.equals("0",list_status)){
                        friendList.add(new FriendInfo(list_friendId,list_friendName,list_address,list_skill,
                                list_company,list_position,list_status));
                    }


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            return s;
        }

        @Override
        protected void onPostExecute(String s) {

            lv_friend.setVisibility(View.VISIBLE);

            friendListAdapter = new FriendListAdapter(friendList);

            lv_friend.setAdapter(friendListAdapter);

            pDialog.dismiss();
            super.onPostExecute(s);
        }
    }

    private class FriendListAdapter extends BaseAdapter {
        private ArrayList<FriendInfo> friendList;
        public FriendListAdapter(ArrayList<FriendInfo> list) {
            this.friendList = list;
        }

        @Override
        public int getCount() {
            return friendList.size();
        }

        @Override
        public Object getItem(int position) {
            return friendList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListViewItemHolder item=null;
            if (convertView==null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_friend, null);
                item = new ListViewItemHolder();
                item.tv_friendName= (TextView) convertView.findViewById(R.id.friend_name);
                item.tv_company= (TextView) convertView.findViewById(R.id.friend_companyName);
                item.rl_whole= (RelativeLayout) convertView.findViewById(R.id.rl_friend_whole);
                convertView.setTag(item);
            }else {
                item= (ListViewItemHolder) convertView.getTag();
            }

            final FriendInfo friendInfo=friendList.get(position);

            item.tv_friendName.setText(friendInfo.getFriendName());
            item.tv_company.setText(friendInfo.getCompany());
            item.rl_whole.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferencesUtils.setParam(mContext,"ren_id",friendInfo.getFriendId());
                    SharedPreferencesUtils.setParam(mContext,"name",friendInfo.getFriendName());
                    SharedPreferencesUtils.setParam(mContext,"province",friendInfo.getAddress());
                    SharedPreferencesUtils.setParam(mContext,"companyname",friendInfo.getCompany());
                    SharedPreferencesUtils.setParam(mContext,"skill",friendInfo.getSkill());
                    SharedPreferencesUtils.setParam(mContext,"position",friendInfo.getPosition());
                    SharedPreferencesUtils.setParam(mContext,"status",friendInfo.getStatus());
                    startActivity(new Intent(mContext, ActivityFriend_.class));
                }
            });
            return convertView;
        }
    }

    private class ListViewItemHolder {
        TextView tv_friendName;
        TextView tv_company;
        RelativeLayout rl_whole;
    }
}
