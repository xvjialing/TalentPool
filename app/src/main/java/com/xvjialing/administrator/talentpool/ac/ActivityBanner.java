package com.xvjialing.administrator.talentpool.ac;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xvjialing.administrator.talentpool.R;
import com.xvjialing.administrator.talentpool.json.AppHttp;
import com.xvjialing.administrator.talentpool.utils.SharedPreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ActivityBanner extends AppCompatActivity {

    @BindView(R.id.ac_banner_img_back)
    ImageView acBannerImgBack;
    @BindView(R.id.ac_banner_tv_titlebar)
    TextView acBannerTvTitlebar;
    @BindView(R.id.ac_banner_rl_titlebar)
    RelativeLayout acBannerRlTitlebar;
    @BindView(R.id.ac_banner_webView)
    WebView acBannerWebView;

    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_banner);
        ButterKnife.bind(this);

        special();
    }

    public void special() {
        switch (SharedPreferencesUtils.getParam(ActivityBanner.this, "bannerTag", "0").toString()) {
            case "0":
                url = AppHttp.Url_IP + "banner/banner1.html";
                break;
            case "1":
                url = AppHttp.Url_IP + "banner/banner2.html";
                break;
            case "2":
                url = AppHttp.Url_IP + "banner/banner3.html";
                break;
        }

        acBannerWebView.loadUrl(url);
        acBannerWebView.getSettings().setJavaScriptEnabled(true);
        acBannerWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        acBannerWebView.setWebChromeClient(new WebChromeClient() {

        });
    }

    @OnClick(R.id.ac_banner_img_back)
    public void onViewClicked() {
        finish();
    }
}
