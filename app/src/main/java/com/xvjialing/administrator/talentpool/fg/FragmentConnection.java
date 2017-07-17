package com.xvjialing.administrator.talentpool.fg;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xvjialing.administrator.talentpool.R;
import com.xvjialing.administrator.talentpool.ac.ActivityFriend_;
import com.xvjialing.administrator.talentpool.ac.ActivityMailList_;
import com.xvjialing.administrator.talentpool.ac.ActivityMyFriend_;
import com.xvjialing.administrator.talentpool.ac.ActivitySearchFriend_;
import com.xvjialing.administrator.talentpool.bean.FriendInfo;
import com.xvjialing.administrator.talentpool.json.AppHttp;
import com.xvjialing.administrator.talentpool.json.JSONParser;
import com.xvjialing.administrator.talentpool.utils.SharedPreferencesUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@EFragment
public class FragmentConnection extends Fragment {

    private static final int REQUEST_PERMISSION_READ_CONTACTS_CODE = 1;
    @ViewById(R.id.fg_connection_ll_addFriend)
    LinearLayout ll_addFriend;
    @ViewById(R.id.fg_connection_lv_firend)
    ListView lv_friend;
    @ViewById(R.id.fg_connection_rl_search)
    RelativeLayout rl_search;
    @ViewById(R.id.fg_connection_ll_myFriend)
    LinearLayout ll_myFriend;

    private ArrayList<FriendInfo> friendList;
    private FriendListAdapter friendListAdapter;
    private String userId,friendId;

    private ProgressDialog pDialog;

    private String list_friendId,list_friendName,list_address,list_skill,list_company,list_position,list_status;

    private static String url_friend_show = AppHttp.Url + "ren";


    public FragmentConnection() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fg_connection, container, false);
    }

    @AfterViews
    public void getListData(){
        new ListData().execute();
    }

    @Click(R.id.fg_connection_ll_addFriend)
    public void addFriend(){
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_CONTACTS)) {

            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_CONTACTS},
                        REQUEST_PERMISSION_READ_CONTACTS_CODE);

            }
        } else {
            Intent intent = new Intent(getContext(), ActivityMailList_.class);
            startActivity(intent);
        }

    }

    @Click(R.id.fg_connection_ll_myFriend)
    public void myFriend(){
        startActivity(new Intent(getContext(), ActivityMyFriend_.class));
    }

    //    运行时申请权限返回结果
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_READ_CONTACTS_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(getContext(), ActivityMailList_.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "没有读取联系人权限", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

    @Click(R.id.fg_connection_rl_search)
    public void search() {
        startActivity(new Intent(getContext(), ActivitySearchFriend_.class));
    }

    /**
     * 解决scrolView与ListView冲突
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        FriendListAdapter listAdapter = (FriendListAdapter) listView.getAdapter();
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

            friendListAdapter = new FriendListAdapter(friendList);

            lv_friend.setAdapter(friendListAdapter);
            setListViewHeightBasedOnChildren(lv_friend);

            pDialog.dismiss();
            super.onPostExecute(s);
        }
    }

    private class FriendListAdapter extends BaseAdapter{
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
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_friend, null);
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
                    SharedPreferencesUtils.setParam(getContext(),"ren_id",friendInfo.getFriendId());
                    SharedPreferencesUtils.setParam(getContext(),"name",friendInfo.getFriendName());
                    SharedPreferencesUtils.setParam(getContext(),"province",friendInfo.getAddress());
                    SharedPreferencesUtils.setParam(getContext(),"companyname",friendInfo.getCompany());
                    SharedPreferencesUtils.setParam(getContext(),"skill",friendInfo.getSkill());
                    SharedPreferencesUtils.setParam(getContext(),"position",friendInfo.getPosition());
                    SharedPreferencesUtils.setParam(getContext(),"status",friendInfo.getStatus());
                    startActivity(new Intent(getContext(), ActivityFriend_.class));
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
