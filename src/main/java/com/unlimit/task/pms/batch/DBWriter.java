package com.unlimit.task.pms.batch;

import java.util.List;

import com.unlimit.task.pms.model.PersonGeneralInformation;
import com.unlimit.task.pms.repository.PersonRepository;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DBWriter implements ItemWriter<PersonGeneralInformation> {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public void write(List<? extends PersonGeneralInformation> persons) throws Exception {
        personRepository.saveAll(persons);
    }
}
