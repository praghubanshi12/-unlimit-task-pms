package com.unlimit.task.pms.config;

import com.unlimit.task.pms.model.PersonGeneralInformation;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
            ItemReader<PersonGeneralInformation> itemReader,
            ItemProcessor<PersonGeneralInformation, PersonGeneralInformation>
            itemProcessor,
            ItemWriter<PersonGeneralInformation> itemWriter) {

        Step step = stepBuilderFactory.get("ETL-file-load")
                .<PersonGeneralInformation, PersonGeneralInformation>chunk(100).reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter).build();

        return jobBuilderFactory.get("ETL-Load").incrementer(new RunIdIncrementer()).start(step).build();
    }

    @Bean
    public FlatFileItemReader<PersonGeneralInformation> itemReader() {
        //reading the csv file
        FlatFileItemReader<PersonGeneralInformation> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/persons.csv"));
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }

    @Bean
    public LineMapper<PersonGeneralInformation> lineMapper() {
        //mapping the csv fields value with our entity model PersonGeneralInformation
        DefaultLineMapper<PersonGeneralInformation> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(true);
        lineTokenizer.setNames(new String[] { "details.eventName", "details.scannedDate", "details.scannedTime", "firstName",
                "middleInitial", "lastName", "email", "email2", "details.companyName", "details.jobTitle", "details.address1", "details.address2",
                "details.address3", "details.city", "details.stateId", "details.zip", "details.country", "details.phoneNumber", "details.phoneNumber2", "details.faxNumber", 
                "details.question", "details.response", "details.note", "details.collateral", "details.qualified", "details.scannedBy"});

        BeanWrapperFieldSetMapper<PersonGeneralInformation> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(PersonGeneralInformation.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}