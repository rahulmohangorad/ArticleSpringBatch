package com.kkh_chth.articles.batch.SpringBatch.component;

import com.kkh_chth.articles.batch.SpringBatch.bean.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class CustomItemProcessor implements ItemProcessor<Article, Article> {

    @Override
    public Article process(Article article) throws Exception {
        return article;
    }
}
