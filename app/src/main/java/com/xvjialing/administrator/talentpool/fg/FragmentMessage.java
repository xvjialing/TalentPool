package com.xvjialing.administrator.talentpool.fg;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xvjialing.administrator.talentpool.R;
import com.xvjialing.administrator.talentpool.ac.ActivityDeliverFeedbak_;
import com.xvjialing.administrator.talentpool.ac.ActivityInterestMe_;
import com.xvjialing.administrator.talentpool.ac.ActivityInterviewInvitation_;
import com.xvjialing.administrator.talentpool.ac.ActivityRecentVisitor_;
import com.xvjialing.administrator.talentpool.ac.ActivitySystemMessage_;
import com.xvjialing.administrator.talentpool.utils.SharedPreferencesUtils;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment
public class FragmentMessage extends Fragment {

    @ViewById(R.id.ll_message_interview)
    LinearLayout linearLayout_interview;

    @ViewById(R.id.ll_message_visit)
    LinearLayout ll_recentVisitor;

    @ViewById(R.id.ll_message_interst)
    LinearLayout ll_interest;

    @ViewById(R.id.ll_message_feedback)
    LinearLayout ll_feedback;

    @ViewById(R.id.ll_message_systemMessage)
    LinearLayout ll_systemMessage;

    @ViewById(R.id.fg_message_img_systemMessage_redPoint)
    ImageView img_systemMessage_redPoint;

    public FragmentMessage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fg_message, container, false);
    }

    @Click(R.id.ll_message_systemMessage)
    public void clickSystemMessage() {
        SharedPreferencesUtils.setParam(getContext(), "systemMessageRedPointTag", "1");
        startActivity(new Intent(getContext(), ActivitySystemMessage_.class));
    }

    @Click(R.id.ll_message_interview)
    public void interview(){
        Intent intent=new Intent(getContext(), ActivityInterviewInvitation_.class);
        startActivity(intent);
    }

    @Click(R.id.ll_message_visit)
    public void recentVitor(){
        Intent intent=new Intent(getContext(), ActivityRecentVisitor_.class);
        startActivity(intent);
    }

    @Click(R.id.ll_message_interst)
    public void interest(){
        Intent intent=new Intent(getContext(), ActivityInterestMe_.class);
        startActivity(intent);
    }

    @Click(R.id.ll_message_feedback)
    public void feedback(){
        Intent intent=new Intent(getContext(), ActivityDeliverFeedbak_.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        if (TextUtils.equals(SharedPreferencesUtils.getParam(getContext(), "systemMessageRedPointTag", "0").toString(), "1")) {
            img_systemMessage_redPoint.setVisibility(View.GONE);
        } else {
            img_systemMessage_redPoint.setVisibility(View.VISIBLE);
        }
        super.onStart();
    }
}
