package com.kkh_chth.articles.batch.SpringBatch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JobCompletionNotificationImpl implements JobExecutionListener {


    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Job Started");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus()== BatchStatus.COMPLETED){
            log.info("Job Completed");
        }
    }
}