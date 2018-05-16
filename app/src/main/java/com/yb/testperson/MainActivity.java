package com.yb.testperson;


import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yb.testperson.adapter.ListViewAdapter;
import com.yb.testperson.bean.Bean;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private ImageView mImageView;
    private TextView  mUserName;
    private TextView  mId;
    private TextView  mLivers;
    private TextView  mFollow;
    private TextView  mFans;
    private Handler   mHandler;
    private View      mHeadView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listView);
        mHeadView = View.inflate(this,R.layout.item_list_view_head,null);

        mUserName = (TextView) mHeadView.findViewById(R.id.userName);
        mId = (TextView) mHeadView.findViewById(R.id.id);
        mImageView = (ImageView) mHeadView.findViewById(R.id.head);
        mLivers = (TextView) mHeadView.findViewById(R.id.liver);
        mFollow = (TextView) mHeadView.findViewById(R.id.follow);
        mFans = (TextView) mHeadView.findViewById(R.id.fans);

        mListView.addHeaderView(mHeadView);

        String requrl = "http://livedemo.yunbaozhibo.com/api/public/?service=User.getBaseInfo&uid=11711&token=86d24b8506c569703842e93be85c7cce";
        reqData(requrl);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                JSONObject info = (JSONObject) msg.obj;
                mUserName.setText(info.getString("user_nicename"));
                mId.setText("ID:"+info.getString("id"));
                Glide.with(MainActivity.this).load(info.getString("avatar")).into(mImageView);
                mLivers.setText(info.getString("lives"));
                mFollow.setText(info.getString("follows"));
                mFans.setText(info.getString("fans"));

            }
        };


    }
        private void reqData(String url){
            OkGo.<String>get(url).execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    JSONObject obj = JSON.parseObject(response.body());
                    JSONObject info = obj.getJSONObject("data").getJSONArray("info").getJSONObject(0);
                    List<Bean> list = JSON.parseArray(obj.getJSONObject("data").getJSONArray("info").getJSONObject(0).getString("list"),Bean.class);
                    ListAdapter adapter = new ListViewAdapter(list,MainActivity.this);

                    mListView.setAdapter(adapter);

                    Message msg = Message.obtain();
                    msg.obj = info;
                    mHandler.sendMessage(msg);


                }
            });
        }
}
