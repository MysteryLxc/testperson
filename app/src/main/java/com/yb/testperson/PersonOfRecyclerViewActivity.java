package com.yb.testperson;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yb.testperson.adapter.RecyclerViewAdapter;
import com.yb.testperson.bean.Bean;
import com.yb.testperson.bean.BeanFirst;

import java.util.List;

import static android.R.id.list;

/**
 * Created by 12475 on 2018/5/7.
 */

public class PersonOfRecyclerViewActivity extends AppCompatActivity implements RecyclerViewAdapter.OnItemClickListener{
    private RecyclerView mRecyclerView;
    private BeanFirst mBeanFirst;
    private int i;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewPerson);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        String url = "http://livedemo.yunbaozhibo.com/api/public/?service=User.getBaseInfo&uid=11711&token=86d24b8506c569703842e93be85c7cce";
        reqData(url);
    }
    //网络请求
    private void reqData(String url){
        OkGo.<String>get(url).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                JSONObject obj = JSON.parseObject(response.body());
                JSONObject info0=obj.getJSONObject("data").getJSONArray("info").getJSONObject(0);
                BeanFirst beanFirst = JSON.toJavaObject(info0,BeanFirst.class);
                List<Bean> list = JSON.parseArray(info0.getString("list"),Bean.class);
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(list,beanFirst,PersonOfRecyclerViewActivity.this);
                adapter.setOnItemClickListener(PersonOfRecyclerViewActivity.this);
                mRecyclerView.setAdapter(adapter);

            }
        });
    }
    @Override
    public void OnItemClick(Bean bean, int position) {
        Toast.makeText(this,bean.getName()+"被点击啦", Toast.LENGTH_SHORT).show();
    }
}
