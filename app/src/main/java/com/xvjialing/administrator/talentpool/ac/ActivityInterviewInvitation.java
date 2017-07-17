package com.xvjialing.administrator.talentpool.ac;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xvjialing.administrator.talentpool.R;
import com.xvjialing.administrator.talentpool.utils.SharedPreferencesUtils;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class ActivityInterviewInvitation extends AppCompatActivity {

    @ViewById(R.id.ac_interviewInvitation_img_back)
    ImageView img_back;

//    @ViewById(R.id.ac_interviewInvitation_rl_companyInvitation)
//    RelativeLayout rl_companyInvitation;

    @ViewById(R.id.ac_interviewInvitation_ll_noData)
    LinearLayout ll_noData;

//    @ViewById(R.id.ac_interviewInvitation_img_company)
//    ImageView img_company;
//
//    @ViewById(R.id.ac_interviewInvitation_tv_jobname)
//    TextView tv_jobname;
//
//    @ViewById(R.id.ac_interviewInvitation_tv_companyName)
//    TextView tv_companyName;

    private Context mContext = ActivityInterviewInvitation.this;

//    @ViewById(R.id.ac_interiewInvitation_lv_interview)
//    ListView lv_interview;
//
//    private List<Map<String, Object>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_interview_invitation);
    }

//
//    @AfterViews
//    public void init(){
//        //获取将要绑定的数据设置到data中
//        data = getData();
//        MyAdapter adapter = new MyAdapter(this);
//        lv_interview.setAdapter(adapter);
//    }
//
//    /**
//     *listView的item点击事件
//     **/
//    @AfterViews
//    public void setItemClick(){
//        lv_interview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                startActivity(new Intent(ActivityInterviewInvitation.this,ActivityJobDetail_.class));
//            }
//        });
//    }

    @Click(R.id.ac_interviewInvitation_img_back)
    public void back(){
        finish();
    }
//
//    private List<Map<String, Object>> getData()
//    {
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        Map<String, Object> map;
//        for(int i=0;i<1;i++)
//        {
//            map = new HashMap<String, Object>();
//            map.put("img", R.drawable.company);
//            map.put("jobname", "java工程师");
//            map.put("companyname", "有很多有限公司");
//            list.add(map);
//        }
//        return list;
//    }
//
//    //ViewHolder静态类
//    static class ViewHolder
//    {
//        public ImageView img;
//        public TextView jobname;
//        public TextView companyname;
//    }
//
//    public class MyAdapter extends BaseAdapter {
//        private LayoutInflater mInflater = null;
//        private MyAdapter(Context context)
//        {
//            //根据context上下文加载布局，这里的是Demo17Activity本身，即this
//            this.mInflater = LayoutInflater.from(context);
//        }
//
//        @Override
//        public int getCount() {
//            //How many items are in the data set represented by this Adapter.
//            //在此适配器中所代表的数据集中的条目数
//            return data.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            // Get the data item associated with the specified position in the data set.
//            //获取数据集中与指定索引对应的数据项
//            return position;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            //Get the row id associated with the specified position in the list.
//            //获取在列表中与指定索引对应的行id
//            return position;
//        }
//
//        //Get a View that displays the data at the specified position in the data set.
//        //获取一个在数据集中指定索引的视图来显示数据
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder holder = null;
//            //如果缓存convertView为空，则需要创建View
//            if(convertView == null)
//            {
//                holder = new ViewHolder();
//                //根据自定义的Item布局加载布局
//                convertView = mInflater.inflate(R.layout.list_item_interview, null);
//                holder.img = (ImageView)convertView.findViewById(R.id.company_image);
//                holder.jobname = (TextView)convertView.findViewById(R.id.jobname);
//                holder.companyname = (TextView)convertView.findViewById(R.id.companyName);
//                //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
//                convertView.setTag(holder);
//            }else
//            {
//                holder = (ViewHolder)convertView.getTag();
//            }
//            holder.img.setBackgroundResource((Integer)data.get(position).get("img"));
//            holder.jobname.setText((String)data.get(position).get("jobname"));
//            holder.companyname.setText((String)data.get(position).get("companyname"));
//
//            return convertView;
//        }
//
//    }

//    @Click(R.id.ac_interviewInvitation_rl_companyInvitation)
//    public void invitation() {
//        startActivity(new Intent(mContext, ActivityJobDetail_.class));
//    }


    @Override
    protected void onStart() {
//        tv_jobname.setText(SharedPreferencesUtils.getParam(mContext, "jobName" + 1, "软件工程师").toString());
//
//        tv_companyName.setText(SharedPreferencesUtils.getParam(mContext, "companyName" + 1, "恒宝股份有限公司").toString());
//
//        Picasso.with(mContext).load(AppHttp.Url_IP + "opensns/Uploads/" + SharedPreferencesUtils.getParam(mContext, "big_logo" + 1, "///"))
//                .into(img_company);

        if (TextUtils.equals(SharedPreferencesUtils.getParam(ActivityInterviewInvitation.this, "ac_interviewInvitation_noDataTag", "0").toString(), "1")) {
            ll_noData.setVisibility(View.GONE);
        } else {
            ll_noData.setVisibility(View.VISIBLE);
        }
        super.onStart();
    }
}
