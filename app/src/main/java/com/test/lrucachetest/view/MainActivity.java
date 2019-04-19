package com.test.lrucachetest.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.test.lrucachetest.R;
import com.test.lrucachetest.adapter.ItemsViewAdapter;
import com.test.lrucachetest.model.Article;
import com.test.lrucachetest.model.DataModel;
import com.test.lrucachetest.repo.DataRespository;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DataRespository.ResultCallback {

    String url = "http://www.mocky.io/v2/5cb9b1983000001f24bfa60a";
    private List<Article> mArticleList;
    private DataRespository mDataRespository;
    private ItemsViewAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv_view);
        mArticleList = new ArrayList<>();
        adapter = new ItemsViewAdapter(mArticleList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        mDataRespository = new DataRespository(this);
        mDataRespository.execute(url);
    }

    @Override
    public void onResponseSuccess(DataModel dataModel) {
        if (dataModel != null) {
            mArticleList.clear();
            mArticleList = dataModel.getArticles();
            adapter.updateDataSet(mArticleList);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDataRespository.cancel(true);
    }
}
