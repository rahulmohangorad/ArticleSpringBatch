package com.kkh_chth.articles.batch.SpringBatch.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Article {
    Long id;
    String title;
    String author;
    String category;
}
