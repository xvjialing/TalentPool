package com.xvjialing.administrator.talentpool.ac;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
public class ActivityComprehensiveSkillsTest extends AppCompatActivity {

    @ViewById(R.id.ac_comprehensiveSkills_img_back)
    ImageView img_back;

    @ViewById(R.id.ac_comprehensiveSkills_wv_comprehensiveSkillsTest)
    WebView wv_comprehensiveSkillsTest;

    private static final String URL=AppHttp.Url_IP + "comprehensiveTest/comprehensiveTest.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_comprehensive_skills_test);
    }

    @AfterViews
    public void comprehensiveSkills() {
        wv_comprehensiveSkillsTest.loadUrl(URL);
        wv_comprehensiveSkillsTest.getSettings().setJavaScriptEnabled(true);
        wv_comprehensiveSkillsTest.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wv_comprehensiveSkillsTest.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }


        });

        wv_comprehensiveSkillsTest.setWebChromeClient(new WebChromeClient() {
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

    @Click(R.id.ac_comprehensiveSkills_img_back)
    public void back() {
        finish();
    }

}
