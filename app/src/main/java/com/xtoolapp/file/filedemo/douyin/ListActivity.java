package com.xtoolapp.file.filedemo.douyin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xtoolapp.file.filedemo.R;

import java.util.ArrayList;

/**
 * Created by WangYu on 2018/6/5.
 */
public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<MyEntity> myData;

    class MyEntity {
        public String str;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new MyLinearLayoutManager(this));
        initData();
        recyclerView.setAdapter(new MyAdapter());
    }

    //初始化数据
    private void initData() {
        int size = 30;
        myData = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            MyEntity e = new MyEntity();
            e.str = ("str:" + i);
            myData.add(e);
        }
    }


    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_item,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.textView.setText(myData.get(position).str);
        }

        @Override
        public int getItemCount() {
            return myData.size();
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

       public TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
        }
    }


}
