package com.developer.santa.api.apibatch;


import com.developer.santa.api.domain.batchdata.BatchData;
import com.developer.santa.api.domain.course.CourseRepository;
import com.developer.santa.api.domain.mountain.Mountain;
import com.developer.santa.api.domain.mountain.MountainRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class MountainApiJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final MountainRepository mountainRepository;
    private final CourseRepository courseRepository;

    private static final int chunkSize = 5;


    @Bean
    public MountainApiReader reader() {
        return new MountainApiReader();
    }

    @Bean
    public MountainApiProcessor processor(){
        return new MountainApiProcessor(mountainRepository, courseRepository);
    }

    @Bean
    public MountainApiWriter<String> writer() {
        JpaItemWriter<String> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return new MountainApiWriter<>(writer);
    }

    @Bean
    public Job MountainApiJob() {
        return jobBuilderFactory.get("MountainApiJob")
                .start(MountainApiStep1())
                .next(MountainApiStep2())
                .build();
    }

    @Bean
    public Step MountainApiStep1() {
        return stepBuilderFactory.get("MountainApiStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>>>> mountainApiStep1");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step MountainApiStep2(){
        return stepBuilderFactory.get("MountainApiStep2")
                .<BatchData, List<Mountain>>chunk(chunkSize)
                .reader(jpaJob_dbItemReader())
                .processor(processor())
                .writer(mountainWriter())
                .build();

    }

    private ItemProcessor<BatchData, Mountain> jpaJob_processor(){
        return BatchData -> {
            return new Mountain("1");
        };
    }

    @Bean
    public JpaPagingItemReader<BatchData> jpaJob_dbItemReader() {
        return new JpaPagingItemReaderBuilder<BatchData>()
                .name("jpaJob_dbItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("SELECT d FROM BatchData d order by id asc")
                .build();
    }

    @Bean
    public JpaItemWriter<Mountain> jpaJob_printItemWriter(){
        JpaItemWriter<Mountain> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

    @Bean
    public ItemWriter<? super List<Mountain>> mountainWriter(){
        JpaItemWriter<List<Mountain>> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

}
