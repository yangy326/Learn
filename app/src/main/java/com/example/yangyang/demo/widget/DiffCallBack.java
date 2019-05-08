package com.example.yangyang.demo.widget;

import android.support.v7.util.DiffUtil;

import com.example.yangyang.demo.TestData.response.main.Student;

import java.util.List;

public class DiffCallBack extends DiffUtil.Callback {

    private List<Student> mOldDatas, mNewDatas;//看名字

    public DiffCallBack(List<Student> mOldDatas, List<Student> mNewDatas) {
        this.mOldDatas = mOldDatas;
        this.mNewDatas = mNewDatas;
        int i = 0 ;

    }
    @Override
    public int getOldListSize() {
        return mOldDatas != null ? mOldDatas.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return mNewDatas != null ? mNewDatas.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int i, int i1) {
        return mOldDatas.get(i).getName().equals(mNewDatas.get(i1).getName());
    }

    @Override
    public boolean areContentsTheSame(int i, int i1) {
        Student beanOld = mOldDatas.get(i);
        Student beanNew = mNewDatas.get(i1);
        if (!beanOld.getRecentlyConnect().equals(beanNew.getRecentlyConnect())) {
            return false;//如果有内容不同，就返回false
        }

        return true;
    }
}
