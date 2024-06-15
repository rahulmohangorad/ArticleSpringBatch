package com.kkh_chth.articles.batch.SpringBatch.component;

import com.kkh_chth.articles.batch.SpringBatch.bean.Article;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ArticleMapper implements RowMapper<Article> {
    @Override
    public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
        Article article = new Article();
        article.setId(rs.getLong("id"));
        article.setAuthor(rs.getString("author"));
        article.setTitle(rs.getString("title"));
        article.setCategory(rs.getString("category"));
        return article;
    }
}
