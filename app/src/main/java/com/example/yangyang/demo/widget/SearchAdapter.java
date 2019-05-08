package com.example.yangyang.demo.widget;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yangyang.demo.Activity.LogAcitivity;
import com.example.yangyang.demo.R;
import com.example.yangyang.demo.TestData.response.main.Student;
import com.example.yangyang.demo.db.table.SearchHistory;
import com.example.yangyang.demo.db.table.SearchHistory_Table;
import com.example.yangyang.demo.service.MyService;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Date;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private boolean isdelete = false;
    private List<Student> mlist ;

    private List<SearchHistory> histories;

    public SearchAdapter() {
    }

    public void setHistories(List<SearchHistory> histories) {
        this.histories = histories;
    }



    public SearchAdapter(List<Student> mlist) {
        this.mlist = mlist;
    }

    public void setMlist(List<Student> mlist) {
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.search_item,viewGroup,false);
        final SearchAdapter.ViewHolder holder = new SearchAdapter.ViewHolder(view);





        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Student student = mlist.get(i);
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
                SearchHistory searchHistory = new SearchHistory();
                searchHistory.setGroup(student.getGroup());
                searchHistory.setId(student.getId());
                searchHistory.setName(student.getName());
                searchHistory.setPhoneNumber(student.getPhoneNumber());
                searchHistory.setRecentlyConnect(student.getRecentlyConnect());
                searchHistory.setTeacherGroup(student.getTeacherGroup());
                searchHistory.save();


               if (histories.size() < 4){
                   for (int i = 0 ; i < histories.size(); i ++){
                       if (student.getId() == histories.get(i).getId()){
                           SQLite.delete(SearchHistory.class)

                                   .where(SearchHistory_Table.contentId.eq(histories.get(i).getContentId()))
                                   .execute();
                           searchHistory.save();
                           isdelete = true;
                           break;

                       }
                   }
                   if (!isdelete){

                       searchHistory.save();

                   }
                   else {
                       isdelete = false;
                   }
                   searchHistory.save();

               }
               else {
                   for (int i = 0 ; i < histories.size(); i ++){
                       if (student.getId() == histories.get(i).getId()){
                           SQLite.delete(SearchHistory.class)

                                   .where(SearchHistory_Table.contentId.eq(histories.get(i).getContentId()))
                                   .execute();
                           searchHistory.save();
                           isdelete = true;
                           break;

                       }
                   }
                   if (!isdelete){
                       SQLite.delete(SearchHistory.class)

                               .where(SearchHistory_Table.contentId.eq(histories.get(0).getContentId()))
                               .execute();
                       searchHistory.save();

                   }
                   else {
                       isdelete = false;
                   }

               }





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
                ((Activity)v.getContext()).finish();
            }
        });
        viewHolder.Interpretation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LogAcitivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("deviceId","123");
                bundle.putInt("studentId",student.getId());
                bundle.putString("studentName",student.getName());
                bundle.putString("studentGroup",student.getGroup());
                bundle.putString("phone",student.getPhoneNumber());
                bundle.putLong("teacherGroup",student.getTeacherGroup());
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
                ((Activity)v.getContext()).finish();


            }
        });

    }


    @Override
    public int getItemCount() {
        return  mlist.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView StudentName,ClassID,StudentID,ContactTime;
        ImageView Interpretation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            Interpretation = (ImageView) itemView.findViewById(R.id.img_search_shuoming);

            StudentName = (TextView)itemView.findViewById(R.id.txt_search_studentname);
            ClassID = (TextView)itemView.findViewById(R.id.txt_search_classid);
            StudentID = (TextView)itemView.findViewById(R.id.txt_search_studentid);
            ContactTime = (TextView)itemView.findViewById(R.id.txt_search_contacttime);

        }
    }
    int transformDays(Long time){
        long currentTime = new Date().getTime();
        int days = (int) ((currentTime - time) / 86400000);
        return days;
    }
}
