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

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class ActivityFirstInterviewTest extends AppCompatActivity {

    @ViewById(R.id.ac_firstInterview_img_back)
    ImageView img_back;

    @ViewById(R.id.ac_firstInterViewTest_wv_interViewTest)
    WebView wv_interViewTest;
    private static final String URL=AppHttp.Url_IP + "firstTest/firstTest1.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_first_interview_test);
    }

    @AfterViews
    public void firstInterview() {
        wv_interViewTest.loadUrl(URL);
        wv_interViewTest.getSettings().setJavaScriptEnabled(true);
        wv_interViewTest.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wv_interViewTest.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }


        });

        wv_interViewTest.setWebChromeClient(new WebChromeClient() {
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


    @Click(R.id.ac_firstInterview_img_back)
    public void back() {
        finish();
    }

}
