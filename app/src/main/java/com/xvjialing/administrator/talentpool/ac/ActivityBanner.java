package com.xvjialing.administrator.talentpool.ac;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
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
public class ActivityBanner extends AppCompatActivity {

    @ViewById(R.id.ac_banner_img_back)
    ImageView img_back;

    @ViewById(R.id.ac_banner_webView)
    WebView banner_webView;

    private String url;

//    @ViewById(R.id.ac_banner_img_top)
//    ImageView img_top;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_banner);
    }

    @Click(R.id.ac_banner_img_back)
    public void back() {
        finish();
    }

//    @AfterViews
//    public void initView() {
//        switch (SharedPreferencesUtils.getParam(ActivityBanner.this, "bannerTag", "0").toString()) {
//            case "0":
//                img_top.setImageResource(R.drawable.banner1);
//                break;
//            case "1":
//                img_top.setImageResource(R.drawable.banner2);
//                break;
//            case "2":
//                img_top.setImageResource(R.drawable.banner3);
//                break;
//        }
//
//    }
//
    @AfterViews
    public void special(){
        switch (SharedPreferencesUtils.getParam(ActivityBanner.this,"bannerTag","0").toString()){
            case "0":
                url=AppHttp.Url_IP+"banner/banner1.html";
                break;
            case "1":
                url=AppHttp.Url_IP+"banner/banner2.html";
                break;
            case "2":
                url=AppHttp.Url_IP+"banner/banner3.html";
                break;
        }

        banner_webView.loadUrl(url);
        banner_webView.getSettings().setJavaScriptEnabled(true);
        banner_webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        banner_webView.setWebChromeClient(new WebChromeClient(){

        });
    }
}
