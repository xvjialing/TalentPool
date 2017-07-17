package com.xvjialing.administrator.talentpool.ac;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xvjialing.administrator.talentpool.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.InputStream;
import java.util.ArrayList;

@EActivity
public class ActivityMailList extends AppCompatActivity {

    /** 获取库Phon表字段 **/
    private static final String[] PHONES_PROJECTION = new String[] {
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Photo.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_ID };
    /** 联系人显示名称 **/
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;
    /** 电话号码 **/
    private static final int PHONES_NUMBER_INDEX = 1;
    /** 头像ID **/
    private static final int PHONES_PHOTO_ID_INDEX = 2;
    /** 联系人的ID **/
    private static final int PHONES_CONTACT_ID_INDEX = 3;
    @ViewById(R.id.ac_mailList_mail_list)
    ListView listView_mailList;
    @ViewById(R.id.ac_mailList_img_back)
    ImageView img_back;
    private Context mContext = ActivityMailList.this;
    /** 联系人名称 **/
    private ArrayList<String> mContactsName = new ArrayList<String>();
    /** 联系人电话 **/
    private ArrayList<String> mContactsNumber = new ArrayList<String>();
    /** 联系人头像 **/
    private ArrayList<Bitmap> mContactsPhonto = new ArrayList<Bitmap>();
    private MyListAdapter myListAdapter=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_mail_list);
    }

    @Click(R.id.ac_mailList_img_back)
    public void back(){
        finish();
    }

    @AfterViews
    public void initView(){
        getPhoneContacts();

        myListAdapter=new MyListAdapter();
        listView_mailList.setAdapter(myListAdapter);
        listView_mailList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                调用系统方法发短信
//                Intent intent = new Intent(Intent.ACTION_CALL, Uri
//                        .parse("tel:" + mContactsNumber.get(position)));

//                Uri uri=Uri.parse("smsto:"+mContactsNumber.get(position));
//                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
//                intent.putExtra("sms_body", "android...");
//                startActivity(intent);
            }
        });
    }

    /** 得到手机通讯录联系人信息 **/
    private void getPhoneContacts() {

        ContentResolver resolver = mContext.getContentResolver();

        // 获取手机联系人
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                PHONES_PROJECTION, null, null, null);

        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {

                // 得到手机号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                // 当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;

                // 得到联系人名称
                String contactName = phoneCursor
                        .getString(PHONES_DISPLAY_NAME_INDEX);

                // 得到联系人ID
                Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);

                // 得到联系人头像ID
                Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);

                // 得到联系人头像Bitamp
                Bitmap contactPhoto = null;

                // photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
                if (photoid > 0) {
                    Uri uri = ContentUris.withAppendedId(
                            ContactsContract.Contacts.CONTENT_URI, contactid);
                    InputStream input = ContactsContract.Contacts
                            .openContactPhotoInputStream(resolver, uri);
                    contactPhoto = BitmapFactory.decodeStream(input);
                } else {
                    contactPhoto = BitmapFactory.decodeResource(getResources(),
                            R.drawable.message_visit);
                }

                mContactsName.add(contactName);
                mContactsNumber.add(phoneNumber);
                mContactsPhonto.add(contactPhoto);
            }

            phoneCursor.close();
        }
    }

    private class MyListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mContactsName.size();
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ImageView iamge = null;
            TextView title = null;
            TextView text = null;
            Button button = null;
            if (convertView == null || position < mContactsNumber.size()) {
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.mail_list_item, null);
                iamge = (ImageView) convertView.findViewById(R.id.color_image);
                title = (TextView) convertView.findViewById(R.id.color_title);
                text = (TextView) convertView.findViewById(R.id.color_text);
                button = (Button) convertView.findViewById(R.id.item_btn_invite);
            }
            // 绘制联系人名称
            title.setText(mContactsName.get(position));
            // 绘制联系人号码
            text.setText(mContactsNumber.get(position));
            // 绘制联系人头像(========注意获取sim卡中的联系人的时候，没有该条目，应为sim卡中没存储头像信息===============)
            iamge.setImageBitmap(mContactsPhonto.get(position));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //调用系统方法发短信
                    Uri uri = Uri.parse("smsto:" + mContactsNumber.get(position));
                    Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                    intent.putExtra("sms_body", "邀请您使用企业人才共享池");
                    startActivity(intent);
                }
            });
            return convertView;
        }


    }


}
