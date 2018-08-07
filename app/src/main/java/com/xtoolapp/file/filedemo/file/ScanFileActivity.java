package com.xtoolapp.file.filedemo.file;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xtoolapp.file.filedemo.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by WangYu on 2018/5/31.
 */
public class ScanFileActivity extends AppCompatActivity {

    private RecyclerView mRv;
    private File mCurrentPath;
    private TextView mTvPath;
    private long mCount,startTime;
    private ExecutorService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanfile);
        mRv = findViewById(R.id.recycler_view);
        mTvPath = findViewById(R.id.tv_path);
        mCurrentPath = Environment.getExternalStorageDirectory();
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(new ScanFileActivity.MyAdapter());
        mTvPath.setText(mCurrentPath.getPath());
        service = Executors.newFixedThreadPool(100);
        new Thread(new Runnable() {
            @Override
            public void run() {
                startTime=System.currentTimeMillis();
                try {
                    mCount = getTotalSizeOfFilesInDir(mCurrentPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("wangyu", mCount+"  "+(System.currentTimeMillis()-startTime)/1000.0);
            }
        }).start();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvName;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvName = itemView.findViewById(R.id.tv_name);
        }
    }

    class MyAdapter extends RecyclerView.Adapter<ScanFileActivity.MyViewHolder> {

        @NonNull
        @Override
        public ScanFileActivity.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false);
            return new ScanFileActivity.MyViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(@NonNull ScanFileActivity.MyViewHolder holder, final int position) {
            final File[] mFiles = mCurrentPath.listFiles();
            StringBuilder sb = new StringBuilder();
            sb.append(mFiles[position].getName()).append("   ");
            if (mFiles[position].isDirectory() && mFiles[position].list() != null) {
                sb.append(mFiles[position].list().length).append("项");
            } else {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
                sb.append(simpleDateFormat.format(new Date(mFiles[position].lastModified())));
            }
            holder.mTvName.setText(sb.toString());
            holder.mTvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mFiles[position].isDirectory()) {
                        mCurrentPath = mFiles[position];
                        mTvPath.setText(mCurrentPath.getPath());
                        notifyDataSetChanged();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mCurrentPath.listFiles().length;
        }
    }

    private class SubDirectoriesAndSize {

        final public long size;
        public long count;
        final public List<File> subDirectories;

        public SubDirectoriesAndSize(final long totalSize,
                                     final List<File> theSubDirs,long count) {
            size = totalSize;
            subDirectories = Collections.unmodifiableList(theSubDirs);
            this.count=count;
        }

    }

    private ScanFileActivity.SubDirectoriesAndSize getTotalAndSubDirs(final File file) {
        long total = 0;
        long count=0;
        final List<File> subDirectories = new ArrayList<File>();
        if (file.isDirectory()) {
            final File[] children = file.listFiles();
            if (children != null)
                for (final File child : children) {
                    if (child.isFile()) {
                        count++;
                        total += child.length();
                    } else {
                        subDirectories.add(child);
                    }
                }
        }
        return new ScanFileActivity.SubDirectoriesAndSize(total, subDirectories,count);
    }

    private long getTotalSizeOfFilesInDir(final File file)
            throws InterruptedException, ExecutionException, TimeoutException {
        final ExecutorService service = Executors.newFixedThreadPool(100);
        try {
            long total = 0;
            long count=0;
            final List<File> directories = new ArrayList<>();
            directories.add(file);
            while (!directories.isEmpty()) {
                final List<Future<SubDirectoriesAndSize>> partialResults = new ArrayList<>();
                for (final File directory : directories) {
                    partialResults.add(service.submit(new Callable<SubDirectoriesAndSize>() {
                        public ScanFileActivity.SubDirectoriesAndSize call() {
                            return getTotalAndSubDirs(directory);
                        }
                    }));
                }
                directories.clear();
                for (final Future<ScanFileActivity.SubDirectoriesAndSize> partialResultFuture : partialResults) {
                    final ScanFileActivity.SubDirectoriesAndSize subDirectoriesAndSize = partialResultFuture.get(100, TimeUnit.SECONDS);
                    directories.addAll(subDirectoriesAndSize.subDirectories);
                    total += subDirectoriesAndSize.size;
                    count+=subDirectoriesAndSize.count;
                }
            }
            return count;
        } finally {
            service.shutdown();
        }
    }

}
