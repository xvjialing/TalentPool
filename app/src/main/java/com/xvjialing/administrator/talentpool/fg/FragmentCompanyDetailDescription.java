package com.xvjialing.administrator.talentpool.fg;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xvjialing.administrator.talentpool.R;
import com.xvjialing.administrator.talentpool.json.AppHttp;
import com.xvjialing.administrator.talentpool.json.JSONParser;
import com.xvjialing.administrator.talentpool.utils.SharedPreferencesUtils;
import com.xvjialing.administrator.talentpool.utils.UnicodeDecode;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
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

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment
public class FragmentCompanyDetailDescription extends Fragment {

    @ViewById(R.id.fg_company_tv_description)
    TextView textView_description;

    private ProgressDialog pDialog;

    private String companyId,i,summary;
    private static String url_company = AppHttp.Url + "companyshow";


    public FragmentCompanyDetailDescription() {
        // Required empty public constructor
    }

    @AfterViews
    public void init(){
        new ListData().execute();
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
            i=SharedPreferencesUtils.getParam(getContext(),"jobTag",1)+"";
            companyId=SharedPreferencesUtils.getParam(getContext(),"companyId","")+"";
            String s=null;
            JSONParser jsonParser=new JSONParser();
            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("companyId",companyId));
            JSONObject jsonObject=jsonParser.makeHttpRequest(url_company,"POST",params1);
            Log.i("jsonObject", String.valueOf(jsonObject));
            JSONArray jsonArray= null;
            try {
                jsonArray = jsonObject.getJSONArray("data1");
                JSONObject jsonObject2=jsonArray.getJSONObject(0);
                summary=jsonObject2.getString("summary");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            textView_description.setText(summary);
            pDialog.dismiss();
            super.onPostExecute(s);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fg_company_detail_description, container, false);
    }

}
