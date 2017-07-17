package com.xvjialing.administrator.talentpool.ac;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.xvjialing.administrator.talentpool.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class ActivityStartPage extends AppCompatActivity {

    @ViewById(R.id.ac_startPage_img_startPic)
    ImageView mg_startPic;

    private Animation anim;
    private Drawable mDraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_start_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @AfterViews
    public void initView(){
        init();

        initListener();
    }

    public void init() {
        initPic();
        initAnim();

        mg_startPic.setImageDrawable(mDraw);
        mg_startPic.setAnimation(anim);
    }

    public void initAnim() {
        anim = AnimationUtils.loadAnimation(this, R.anim.guide_welcome_fade);

    }

    public void initPic() {
        mDraw = getResources().getDrawable(R.drawable.start);
    }


    public void initListener() {
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(ActivityStartPage.this,ActivityLogin_.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
