package com.unlimit.task.pms.repository;

import java.util.List;

import com.unlimit.task.pms.model.PersonGeneralInformation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<PersonGeneralInformation, Integer> {
    List<PersonGeneralInformation> findByDetailsCountry(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM persons p WHERE (:scannedBy is null or p.scanned_by = :scannedBy) and (:country is null"
            + " or p.country = :country)")
    List<PersonGeneralInformation> findByDetailsScannedByAndDetailsCountry(@Param("scannedBy") String scannedBy,
            @Param("country") String country);

    @Query(nativeQuery = true, value = "select p.scanned_by, count(*) as count from persons p where (:country is null or p.country = :country) group by scanned_by order by count desc")
    List<Object[]> findDistinctScannedByAndCountry(String country);
}