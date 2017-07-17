package com.xvjialing.administrator.talentpool.ac;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.LoginFilter;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.xvjialing.administrator.talentpool.R;
import com.xvjialing.administrator.talentpool.json.AppHttp;
import com.xvjialing.administrator.talentpool.utils.SharedPreferencesUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class ActivitySkillTest extends AppCompatActivity {

    @ViewById(R.id.ac_skill_img_back)
    ImageView img_back;

    @ViewById(R.id.ac_skill_wv_skillTest)
    WebView wv_skillTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_skill_test);
    }

    @AfterViews
    public void skillTest() {
        Log.i("skillTag2",SharedPreferencesUtils.getParam(ActivitySkillTest.this,"skillTag","0").toString());
        switch (SharedPreferencesUtils.getParam(ActivitySkillTest.this,"skillTag","PHP").toString()){
            case "PHP":
                wv_skillTest.loadUrl(AppHttp.Url_IP + "qualityTest/qualityTest1.html");
                break;
            case "Java":
                wv_skillTest.loadUrl(AppHttp.Url_IP + "qualityTest/qualityTest2.html");
                break;
            default:
                wv_skillTest.loadUrl(AppHttp.Url_IP + "qualityTest/qualityTest1.html");
                break;
        }
        wv_skillTest.getSettings().setJavaScriptEnabled(true);
        wv_skillTest.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wv_skillTest.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }


        });

        wv_skillTest.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
//                if (newProgress==100){
//                    pDialog.dismiss();
//                }else {
//                    pDialog=new ProgressDialog(ActivityQualityTest.this);
//                    pDialog.setMessage("加载中..");
////                    pDialog.setIndeterminate(false);
//                    pDialog.setCancelable(true);
//                    pDialog.show();
//                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    @Click(R.id.ac_skill_img_back)
    public void back() {
        finish();
    }

}
