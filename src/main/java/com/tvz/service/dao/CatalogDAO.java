package com.tvz.service.dao;

import com.tvz.model.*;

import java.util.List;

public interface CatalogDAO {
    //processes
    List<Proces> getAllProcessesForDep(Long id);
    String newProcess(Proces process);

    List<Role> getRolesOfProces(Long id);
    String newRole(Role role);

    List<PersonalData> getPersonalDataTable();
    String newPersonalData(PersonalData personalData);

    List<Application> getApplicationTable();
    String newApplication(Application application);

    List<Gdpr> getPersonalDataForEntry(Long fkpr, Long fkrole);
    List<Gdpr> getAllPersonalData();
    List<Application> getApplicationsForPD(Long id);
    String newGdprEntry(Gdpr gdpr);


    //adminpart TODO: definirati funkcije za admina
}
