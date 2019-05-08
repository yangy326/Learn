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

import java.util.Date;
import java.util.List;

public class ContacterAdapter extends RecyclerView.Adapter<ContacterAdapter.ViewHolder> {

    private List<Student> mlist ;


    public void set(List<Student> list){
        mlist = list;
    }



    public ContacterAdapter(List<Student> mlist) {
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,  int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.recycle_item,viewGroup,false);
       final ViewHolder holder = new ViewHolder(view);





        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Log.d("onBindViewHolder","onBindViewHolder");


       final Student student = mlist.get(i);
        Log.d("asfddfdaf", String.valueOf(student.getName().length()));
        Log.d("asfddfdaf", String.valueOf(student.getGroup().length()));

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


    @Override
    public int getItemCount() {
        return mlist.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
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
    int transformDays(Long time){
        long currentTime = new Date().getTime();
        int days = (int) ((currentTime - time) / 86400000);
        return days;
    }
}
