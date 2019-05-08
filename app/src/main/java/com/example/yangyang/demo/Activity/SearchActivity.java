package com.example.yangyang.demo.Activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yangyang.demo.R;
import com.example.yangyang.demo.TestData.response.main.Student;
import com.example.yangyang.demo.db.table.SearchHistory;
import com.example.yangyang.demo.widget.ContacterAdapter;
import com.example.yangyang.demo.widget.SearchAdapter;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener {

    private LinearLayout historyLayout;

    private TextView cancel ,first,second,third,fourth;

    private EditText serch;

    private RecyclerView recyclerView;

    private ProgressBar progressBar;

    private SearchAdapter adapter ;
    private List<Student> result = new ArrayList<>();

    private List<Student> list;

    private List<SearchHistory> searchHistoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initWidget();
        list = (List<Student>) getIntent().getExtras().getSerializable("list");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter =  new SearchAdapter(result);
        recyclerView.setAdapter(adapter);

         searchHistoryList= SQLite.select().from(SearchHistory.class)
                .queryList();
        if (searchHistoryList.size() == 0){

        }
        else {
            Log.d("adasda", String.valueOf(searchHistoryList.size()));

        }

        int num = searchHistoryList.size();
        showHistory(num,searchHistoryList);

        adapter.setHistories(searchHistoryList);













    }

    private void initWidget(){

        recyclerView = (RecyclerView)findViewById(R.id.search_recyclerview);

        cancel = (TextView) findViewById(R.id.txt_cancel);

        serch = (EditText) findViewById(R.id.edit_search);

        cancel.setOnClickListener(this);

        serch.addTextChangedListener(this);

        first = (TextView) findViewById(R.id.txt_first);

        second = (TextView) findViewById(R.id.txt_second);

        third = (TextView) findViewById(R.id.txt_third);

        fourth = (TextView) findViewById(R.id.txt_fourth);

        first.setOnClickListener(this);
        second.setOnClickListener(this);
        third.setOnClickListener(this);
        fourth.setOnClickListener(this);







    }
    private void judgeHistory(){

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.d("SearchActivity","beforeTextChanged");

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.d("edittextsss",String.valueOf(start));
        Log.d("edittextsss",String.valueOf(before));
        Log.d("edittextsss",String.valueOf(count));
        String name = s + "";
        if (result.size()!=0){
            result.clear();
        }
        if ( name.equals("")){
            result.clear();
        }

        else if(name.charAt(0) >=48 && name.charAt(0) <=57){

            Pattern pattern = Pattern.compile(name,Pattern.CASE_INSENSITIVE);
            for(int i=0; i < list.size(); i++){

                String idstr = String.valueOf((list.get(i)).getId());
                Matcher matcher = pattern.matcher(idstr);
                if(matcher.find()){
                    result.add(list.get(i));

                }
            }
        }
        else {

            Pattern pattern = Pattern.compile(name,Pattern.CASE_INSENSITIVE);
            for(int i=0; i < list.size(); i++){
                Matcher matcher = pattern.matcher((list.get(i)).getName());
                Matcher matcher1 = pattern.matcher((list.get(i)).getGroup());
                if(matcher.find() || matcher1.find()){
                    result.add(list.get(i));

                }

            }

        }



        adapter.setMlist(result);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void afterTextChanged(Editable s) {
        Log.d("SearchActivity","afterTextChanged");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_first:findbyHistory(3);
            break;
            case R.id.txt_second:findbyHistory(2);
                break;
            case R.id.txt_third:findbyHistory(1);
                break;
            case R.id.txt_fourth:findbyHistory(0);
                break;
            case R.id.txt_cancel:     finish();
                break;

        }



    }

    public void showHistory(int num , List<SearchHistory> searchHistoryList){
        switch (num){
            case 0: first.setVisibility(View.GONE);
                    second.setVisibility(View.GONE);
                    third.setVisibility(View.GONE);
                    fourth.setVisibility(View.GONE);
                    break;
            case 1: first.setVisibility(View.GONE);
                second.setVisibility(View.GONE);
                third.setVisibility(View.GONE);
                fourth.setText(searchHistoryList.get(0).getName());
                break;
            case 2: fourth.setText(searchHistoryList.get(0).getName());
                third.setText(searchHistoryList.get(1).getName());
                second.setVisibility(View.GONE);
                first.setVisibility(View.GONE);
                break;
            case 3: fourth.setText(searchHistoryList.get(0).getName());
                third.setText(searchHistoryList.get(1).getName());
                second.setText(searchHistoryList.get(2).getName());
                first.setVisibility(View.GONE);
                break;
            case 4: fourth.setText(searchHistoryList.get(0).getName());
                third.setText(searchHistoryList.get(1).getName());
                second.setText(searchHistoryList.get(2).getName());
                first.setText(searchHistoryList.get(3).getName());
                break;

        }
    }
    public void findbyHistory(int num){
        result.clear();
        Student student = searchHistoryList.get(num).build();
        result.add(student);
        adapter.setMlist(result);
        adapter.notifyDataSetChanged();

    }


}
