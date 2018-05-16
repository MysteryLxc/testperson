package com.yb.testperson.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yb.testperson.R;
import com.yb.testperson.bean.Bean;

import java.util.List;

/**
 * Created by 12475 on 2018/5/7.
 */

public class ListViewAdapter extends BaseAdapter{
    private List<Bean> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ListViewAdapter(List<Bean> list, Context context) {
        mList = list;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }



    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView==null){
            vh = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_list_view,parent,false);
            vh.mImageView = (ImageView) convertView.findViewById(R.id.listImg);
            vh.mTextView = (TextView) convertView.findViewById(R.id.textList);
            convertView.setTag(vh);

        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        Bean bean = mList.get(position);
        Glide.with(mContext).load(bean.getThumb()).into(vh.mImageView);
        vh.mTextView.setText(bean.getName());
        return convertView;
    }

    private class ViewHolder{
        TextView mTextView;
        ImageView mImageView;
    }
}
