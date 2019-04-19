package com.test.lrucachetest.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.lrucachetest.R;
import com.test.lrucachetest.holder.ItemsViewHolder;
import com.test.lrucachetest.model.Article;

import java.util.List;
import java.util.zip.Inflater;

public class ItemsViewAdapter extends RecyclerView.Adapter<ItemsViewHolder> {

    private List<Article> articleList;

    public ItemsViewAdapter(List<Article> articleList) {
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news_view, viewGroup, false);
        ItemsViewHolder viewHolder = new ItemsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder itemsViewHolder, int position) {
        if (articleList != null && !articleList.isEmpty()) {
            Article article = articleList.get(position);
            if (article != null) {
                itemsViewHolder.bind(article);
            }
        }

    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (articleList != null && !articleList.isEmpty()) {
            count = articleList.size();
        }
        return count;
    }

    public void updateDataSet(List<Article> list) {
        this.articleList = list;
        notifyDataSetChanged();
    }
}
