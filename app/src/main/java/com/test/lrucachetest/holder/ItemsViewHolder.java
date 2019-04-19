package com.test.lrucachetest.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.test.lrucachetest.R;
import com.test.lrucachetest.model.Article;
import com.test.lrucachetest.repo.ImageDownLoaderRepo;

public class ItemsViewHolder extends RecyclerView.ViewHolder {

    private ImageView ivNewsBanner;
    private TextView tvSourceName, tvNewsTitle, tvNewsDescription;

    public ItemsViewHolder(@NonNull View itemView) {
        super(itemView);
        ivNewsBanner = itemView.findViewById(R.id.iv_news_banner);
        tvSourceName = itemView.findViewById(R.id.text_source_name);
        tvNewsTitle = itemView.findViewById(R.id.text_news_title);
        tvNewsDescription = itemView.findViewById(R.id.text_news_description);
    }

    public void bind(Article article) {
        tvSourceName.setText("Source : " + article.getSource().getName()+"\n"+article.getPublishedAt());
        tvNewsTitle.setText(article.getTitle());
        tvNewsDescription.setText(article.getDescription());
        ImageDownLoaderRepo imageDownLoaderRepo=new ImageDownLoaderRepo(ivNewsBanner);
        imageDownLoaderRepo.execute(article.getUrlToImage());
    }
}
