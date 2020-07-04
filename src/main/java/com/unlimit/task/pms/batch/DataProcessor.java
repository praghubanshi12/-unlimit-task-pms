package com.unlimit.task.pms.batch;

import com.unlimit.task.pms.model.PersonGeneralInformation;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class DataProcessor implements ItemProcessor<PersonGeneralInformation, PersonGeneralInformation> {

    public DataProcessor() {

    }

    @Override
    public PersonGeneralInformation process(PersonGeneralInformation person) throws Exception {
        // removing null datas where even the first name is null
        if (person.getFirstName().isEmpty()) {
            return null;
        }

        if (person.getEmail2().isEmpty()) {
            person.setEmail2("-");
        }

        if (person.getDetails().getAddress1().isEmpty()) {
            person.getDetails().setAddress1("-");
        }

        if (person.getDetails().getAddress2().isEmpty()) {
            person.getDetails().setAddress2("-");
        }

        if (person.getDetails().getAddress3().isEmpty()) {
            person.getDetails().setAddress3("-");
        }

        if (person.getDetails().getCity().isEmpty()) {
            person.getDetails().setCity("-");
        }

        if (person.getDetails().getStateId().isEmpty()) {
            person.getDetails().setStateId("-");
        }

        if (person.getDetails().getZip().isEmpty()) {
            person.getDetails().setZip("-");
        }

        if (person.getDetails().getPhoneNumber2().isEmpty()) {
            person.getDetails().setPhoneNumber2("-");
        }

        if (person.getDetails().getFaxNumber().isEmpty()) {
            person.getDetails().setFaxNumber("-");
        }

        if (person.getDetails().getResponse().isEmpty()) {
            person.getDetails().setResponse("-");
        }

        if (person.getDetails().getNote().isEmpty()) {
            person.getDetails().setNote("-");
        }

        if (person.getDetails().getCollateral().isEmpty()) {
            person.getDetails().setCollateral("-");
        }
        return person;
    }
}