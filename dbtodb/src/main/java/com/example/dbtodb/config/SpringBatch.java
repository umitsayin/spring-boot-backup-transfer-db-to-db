package com.example.dbtodb.config;

import com.example.dbtodb.config.steps.processor.UserProcessor;
import com.example.dbtodb.config.steps.reader.UserReader;
import com.example.dbtodb.config.steps.writer.UserWriter;
import com.example.dbtodb.model.User;
import com.example.dbtodb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;



@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class SpringBatch {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final UserRepository userRepository;
    private final UserReader userReader;
    private final UserProcessor userProcessor;
    @Bean
    public Step userStep(){
        return stepBuilderFactory.get("userStep").<User,User>chunk(20)
                .reader(userReader)
                .processor(userProcessor)
                .writer(new UserWriter())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Job job(){
        return jobBuilderFactory.get("userJob")
                .flow(userStep()).end().build();
    }

    public TaskExecutor taskExecutor(){
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
        simpleAsyncTaskExecutor.setConcurrencyLimit(10);
        return simpleAsyncTaskExecutor;
    }

}
