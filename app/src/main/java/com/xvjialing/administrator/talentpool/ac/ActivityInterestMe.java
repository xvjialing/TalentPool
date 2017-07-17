package com.xvjialing.administrator.talentpool.ac;

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

import java.util.List;
import java.util.Map;

@EActivity
public class ActivityInterestMe extends AppCompatActivity {

    @ViewById(R.id.ac_interestMe_img_back)
    ImageView img_back;

    @ViewById(R.id.ac_interestMe_ll_noData)
    LinearLayout ll_noData;

//    @ViewById(R.id.ac_interestMe_lv_recommend)
//    ListView lv;

    private List<Map<String, Object>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_interest_me);
    }

//    @AfterViews
//    public void init(){
//        //获取将要绑定的数据设置到data中
//        data = getData();
//        MyAdapter adapter = new MyAdapter(this);
//        lv.setAdapter(adapter);
//    }

    @Click(R.id.ac_interestMe_img_back)
    public void back(){
        finish();
    }

//    private List<Map<String, Object>> getData()
//    {
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        Map<String, Object> map;
//        for(int i=0;i<10;i++)
//        {
//            map = new HashMap<String, Object>();
//            map.put("img", R.drawable.message_visit);
//            map.put("title", "xjl");
//            map.put("info", "有很多有限公司");
//            map.put("time", "12.22");
//            list.add(map);
//        }
//        return list;
//    }
//
//    //ViewHolder静态类
//    static class ViewHolder
//    {
//        public ImageView img;
//        public TextView title;
//        public TextView info;
//        public TextView time;
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
//                convertView = mInflater.inflate(R.layout.list_item_interest, null);
//                holder.img = (ImageView)convertView.findViewById(R.id.interest_image);
//                holder.title = (TextView)convertView.findViewById(R.id.interest_title);
//                holder.info = (TextView)convertView.findViewById(R.id.interest_text);
//                holder.time = (TextView)convertView.findViewById(R.id.interest_time);
//                //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
//                convertView.setTag(holder);
//            }else
//            {
//                holder = (ViewHolder)convertView.getTag();
//            }
//            holder.img.setBackgroundResource((Integer)data.get(position).get("img"));
//            holder.title.setText((String)data.get(position).get("title"));
//            holder.info.setText((String)data.get(position).get("info"));
//            holder.time.setText((String)data.get(position).get("time"));
//
//            return convertView;
//        }
//
//    }


    @Override
    protected void onStart() {

        if (TextUtils.equals(SharedPreferencesUtils.getParam(ActivityInterestMe.this, "ac_interestMe_noDataTag", "0").toString(), "1")) {
            ll_noData.setVisibility(View.GONE);
        } else {
            ll_noData.setVisibility(View.VISIBLE);
        }

        super.onStart();
    }
}
