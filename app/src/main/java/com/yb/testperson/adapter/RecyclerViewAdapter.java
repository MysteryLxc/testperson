package com.yb.testperson.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.yb.testperson.R;
import com.yb.testperson.bean.Bean;
import com.yb.testperson.bean.BeanFirst;

import java.util.List;

/**
 * Created by 12475 on 2018/5/7.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter{
    private List<Bean> mList;
    private BeanFirst mBeanFirst;
    private Context    mContext;
    private LayoutInflater mLayoutInflater;
    private static final int HEAD=0;
    private static final int NORMAL=1;
    private OnItemClickListener mOnItemClickListener;

    public RecyclerViewAdapter(List<Bean> list,BeanFirst bean, Context context) {
        mList = list;
        mBeanFirst=bean;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return HEAD;
        }else {
            return NORMAL;
        }
    }

    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==HEAD){
            return new HeadViewHolder(mLayoutInflater.inflate(R.layout.item_list_view_head,parent,false));
        }
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_list_view,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof HeadViewHolder){
               ((HeadViewHolder) holder).setData(mBeanFirst);
            }else {
                ((ViewHolder) holder).setData(mList.get(position-1),position-1);
            }
    }

    @Override
    public int getItemCount() {
        return mList.size()+1;
    }
    class HeadViewHolder extends RecyclerView.ViewHolder{
        TextView mName;
        TextView mId;
        ImageView mHead;
        TextView  mLivers;
        TextView  mFollow;
        TextView  mFans;

        public HeadViewHolder(View itemView) {
            super(itemView);
            mName= (TextView) itemView.findViewById(R.id.userName);
            mId = (TextView) itemView.findViewById(R.id.id);
            mHead = (ImageView) itemView.findViewById(R.id.head);
            mLivers = (TextView) itemView.findViewById(R.id.liver);
            mFans = (TextView) itemView.findViewById(R.id.fans);
            mFollow = (TextView) itemView.findViewById(R.id.follow);
        }
        void setData(BeanFirst beanFirst){
            mName.setText(beanFirst.getUser_nicename());
            mId.setText("ID:"+beanFirst.getId());
            Glide.with(mContext).load(beanFirst.getAvatar_thumb()).into(mHead);
            mLivers.setText(beanFirst.getLives());
            mFollow.setText(beanFirst.getFollows());
            mFans.setText(beanFirst.getFans());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
         TextView  mTextView;
         ImageView mImageView;
         Bean      mBean;
         int       mPosition;


        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.textList);
            mImageView = (ImageView) itemView.findViewById(R.id.listImg);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener!=null){
                        mOnItemClickListener.OnItemClick(mBean,mPosition);
                    }
                }
            });
        }
        void setData(Bean bean,int  position){
            mBean=bean;
            mPosition = position;
            mTextView.setText(bean.getName());
            Glide.with(mContext).load(bean.getThumb()).into(mImageView);
        }
    }
    public interface OnItemClickListener{
        void OnItemClick(Bean bean,int position);
    }
}
