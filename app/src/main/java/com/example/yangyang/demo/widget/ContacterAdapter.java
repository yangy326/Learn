package com.example.yangyang.demo.widget;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yangyang.demo.Activity.FollowActivity;
import com.example.yangyang.demo.Activity.LogAcitivity;

import com.example.yangyang.demo.R;
import com.example.yangyang.demo.TestData.response.main.Student;
import com.example.yangyang.demo.service.MyService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContacterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Student> mlist;
    private final int ITEM = 0;//加载的为一般item

    private final int FOOT = 1;

    private int loadstate = 2;//

    public final int LOADING = 1;//正在加载

    public final int FINISH = 2;// 加载完成

    public final int END = 3;

    public void set(List<Student> list) {
        mlist = list;
    }

    public void setLoadstate(int loadstate) {//用于动态设置加载状态

        this.loadstate = loadstate;

        notifyDataSetChanged();

    }


    public ContacterAdapter(List<Student> mlist) {
        this.mlist = mlist;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final RecyclerView.ViewHolder holder;
        if (getItemViewType(i) == ITEM) {
            View view = LayoutInflater
                    .from(viewGroup.getContext())
                    .inflate(R.layout.recycle_item, viewGroup, false);
            holder = new ItemViewHolder(view);
            return holder;

        } else if (getItemViewType(i) == FOOT) {
            View view = LayoutInflater
                    .from(viewGroup.getContext())
                    .inflate(R.layout.footview, viewGroup, false);
            holder = new FootViewHolder(view);
            return holder;
        }
        return null;


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if (viewHolder instanceof ItemViewHolder) {
            final Student student = mlist.get(i);
            ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;

            itemViewHolder.StudentName.setText(student.getName());
            itemViewHolder.ClassID.setText(student.getGroup());
            itemViewHolder.StudentID.setText(String.valueOf(student.getId()));
            if (student.getRecentlyConnect() == 0) {
                itemViewHolder.ContactTime.setText("未联系过");
            } else {
                int days = transformDays(student.getRecentlyConnect());
                if (days == 0) {
                    itemViewHolder.ContactTime.setText("最近联系时间 今天");
                } else {
                    itemViewHolder.ContactTime.setText("最近联系时间 " + String.valueOf(days) + "天前");
                }

            }
            itemViewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent3 = new Intent(v.getContext(), MyService.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("userId", student.getId());
                    bundle.putString("group", student.getGroup());
                    bundle.putString("studentName", student.getName());
                    bundle.putString("userPhoneNumber", student.getPhoneNumber());
                    bundle.putLong("teacherGroup", student.getTeacherGroup());

                    intent3.putExtras(bundle);
                    v.getContext().startService(intent3);


                    Intent intent = new Intent();

                    intent.setAction(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + student.getPhoneNumber()));
                    v.getContext().startActivity(intent);

                }
            });
            itemViewHolder.Interpretation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), LogAcitivity.class);
                    Bundle bundle = new Bundle();

                    bundle.putInt("studentId", student.getId());
                    bundle.putString("studentName", student.getName());
                    bundle.putString("studentGroup", student.getGroup());
                    bundle.putString("phone", student.getPhoneNumber());
                    bundle.putLong("teacherGroup", student.getTeacherGroup());
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);


                }
            });
        } else if (viewHolder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) viewHolder;
            switch (loadstate) {
                case LOADING:
                    footViewHolder.noData.setVisibility(View.GONE);
                    break;
                case END:
                    footViewHolder.noData.setVisibility(View.GONE);
                    footViewHolder.loading.setVisibility(View.GONE);
                    break;
                case FINISH:
                    footViewHolder.noData.setVisibility(View.GONE);
                    footViewHolder.loading.setVisibility(View.GONE);
                    break;

            }


        }


    }

    @Override
    public int getItemCount() {
        if (mlist == null) {
            return 0;
        } else {
            return mlist.size() + 1;
        }


    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {

            return FOOT;

        } else {

            return ITEM;

        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView StudentName, ClassID, StudentID, ContactTime;
        ImageView Interpretation;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            Interpretation = (ImageView) itemView.findViewById(R.id.img_shuoming);

            StudentName = (TextView) itemView.findViewById(R.id.txt_studentname);
            ClassID = (TextView) itemView.findViewById(R.id.txt_classid);
            StudentID = (TextView) itemView.findViewById(R.id.txt_studentid);
            ContactTime = (TextView) itemView.findViewById(R.id.txt_contacttime);

        }
    }

    int transformDays(Long time) {
        long currentTime = new Date().getTime();
        int days = (int) ((currentTime - time) / 86400000);
        return days;
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {
        TextView loading, noData;

        public FootViewHolder(@NonNull View itemView) {
            super(itemView);
            loading = (TextView) itemView.findViewById(R.id.loading);
            noData = (TextView) itemView.findViewById(R.id.nocontent);
        }

    /*
    *




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,  int i) {

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Log.d("onBindViewHolder","onBindViewHolder");



       if (viewHolder instanceof ViewHolder){
           viewHolder.StudentName.setText(student.getName());
           viewHolder.ClassID.setText(student.getGroup());
           viewHolder.StudentID.setText(String.valueOf(student.getId()));

           if (student.getRecentlyConnect() == 0){
               viewHolder.ContactTime.setText("未联系过");
           }
           else {
               int days = transformDays(student.getRecentlyConnect()) ;
               if (days == 0){
                   viewHolder.ContactTime.setText("最近联系时间 今天");
               }
               else {
                   viewHolder.ContactTime.setText("最近联系时间 " + String.valueOf(days) + "天前");
               }

           }
           viewHolder.view.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent3 = new Intent(v.getContext(), MyService.class);
                   Bundle bundle = new Bundle();
                   bundle.putInt("userId",student.getId());
                   bundle.putString("group",student.getGroup());
                   bundle.putString("studentName",student.getName());
                   bundle.putString("userPhoneNumber",student.getPhoneNumber());
                   bundle.putLong("teacherGroup",student.getTeacherGroup());

                   intent3.putExtras(bundle);
                   v.getContext().startService(intent3);











                   Intent intent = new Intent();

                   intent.setAction(Intent.ACTION_CALL);
                   intent.setData(Uri.parse("tel:" + student.getPhoneNumber()));
                   v.getContext().startActivity(intent);

               }
           });
           viewHolder.Interpretation.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(v.getContext(), LogAcitivity.class);
                   Bundle bundle = new Bundle();

                   bundle.putInt("studentId",student.getId());
                   bundle.putString("studentName",student.getName());
                   bundle.putString("studentGroup",student.getGroup());
                   bundle.putString("phone",student.getPhoneNumber());
                   bundle.putLong("teacherGroup",student.getTeacherGroup());
                   intent.putExtras(bundle);
                   v.getContext().startActivity(intent);


               }
           });
       }
       else if (viewHolder instanceof FootViewHolder){

       }






    }

    @Override
    public int getItemViewType(int position) {

    }

    @Override
    public int getItemCount() {
        if (mlist == null){
            return 0;
        }
        else {
            return mlist.size();
        }

    }

    static class ItemViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView StudentName,ClassID,StudentID,ContactTime;
        ImageView Interpretation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            Interpretation = (ImageView) itemView.findViewById(R.id.img_shuoming);

            StudentName = (TextView)itemView.findViewById(R.id.txt_studentname);
            ClassID = (TextView)itemView.findViewById(R.id.txt_classid);
            StudentID = (TextView)itemView.findViewById(R.id.txt_studentid);
            ContactTime = (TextView)itemView.findViewById(R.id.txt_contacttime);

        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder{
        TextView loading , noData;

        public FootViewHolder(@NonNull View itemView) {
            super(itemView);
            loading = (TextView)itemView.findViewById(R.id.loading);
            noData = (TextView)itemView.findViewById(R.id.nocontent);
        }
    }*/
    }
}
