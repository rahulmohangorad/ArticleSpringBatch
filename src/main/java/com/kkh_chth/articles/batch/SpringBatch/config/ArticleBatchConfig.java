package com.kkh_chth.articles.batch.SpringBatch.config;

import com.kkh_chth.articles.batch.SpringBatch.bean.Article;
import com.kkh_chth.articles.batch.SpringBatch.component.ArticleMapper;
import com.kkh_chth.articles.batch.SpringBatch.component.CustomItemProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.support.PostgresPagingQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
//@EnableBatchProcessing
public class ArticleBatchConfig {


  private final DataSource dataSource;

    @Bean
    public Job jobBean(JobRepository jobRepository,
                       JobCompletionNotificationImpl listener,
                       Step steps
    ) {
        return new JobBuilder("job", jobRepository)
                .listener(listener)
                .start(steps)
                .build();
    }

    @Bean
    public Step steps(
            JobRepository jobRepository,
            DataSourceTransactionManager transactionManager,
            ItemReader<Article> reader,
            ItemProcessor<Article, Article> processor,
            ItemWriter<Article> writer
    ) {
        return new StepBuilder("jobStep", jobRepository)
                .<Article, Article>chunk(5, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();

    }


    @Bean
    public JdbcPagingItemReader<Article> dataReader() {
        final JdbcPagingItemReader<Article> reader = new JdbcPagingItemReader<>();
        final ArticleMapper studentMapper = new ArticleMapper();
        reader.setDataSource(dataSource);
        reader.setFetchSize(10);
        reader.setPageSize(10);
        reader.setRowMapper(studentMapper);
        PostgresPagingQueryProvider  postgresPagingQueryProvider = new  PostgresPagingQueryProvider();
        postgresPagingQueryProvider.setSelectClause("id,author,category,title");
        postgresPagingQueryProvider.setFromClause("from articles");
        postgresPagingQueryProvider.setWhereClause("category = 'Category A'");
        Map<String, Order> orderByName = new HashMap<>();
        orderByName.put("author", Order.ASCENDING);
        postgresPagingQueryProvider.setSortKeys(orderByName);

        reader.setQueryProvider(postgresPagingQueryProvider);
        return reader;
    }


    @Bean
    public ItemProcessor<Article, Article> dataProcessor() {
        return new CustomItemProcessor();
    }
    ;

    @Bean
    public ItemWriter<Article> dataWriter() {
        return new JdbcBatchItemWriterBuilder<Article>()
               // .sql("insert into articles(id,author,category,title)values(:Id, :title, :category, :title)")
                .sql("UPDATE articles SET author = 'NA' WHERE category = :category")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }
}
