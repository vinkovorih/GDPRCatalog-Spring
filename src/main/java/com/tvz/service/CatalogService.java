package com.tvz.service;

import com.tvz.model.*;
import com.tvz.service.dao.CatalogDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService {

    @Autowired
    CatalogDAO catalogDAO;

    public List<Proces> getAllProcessesForDep(Long id){
        return catalogDAO.getAllProcessesForDep(id);
    }
    public String newProcess(Proces process){
        return catalogDAO.newProcess(process);
    }

    public List<Role> getRolesOfProces(Long id){
        return catalogDAO.getRolesOfProces(id);
    }
    public String newRole(Role role){
        return catalogDAO.newRole(role);
    }

    public List<PersonalData> getPersonalDataTable(){
        return catalogDAO.getPersonalDataTable();
    }
    public String newPersonalData(PersonalData personalData){
        return catalogDAO.newPersonalData(personalData);
    }

    public List<Application> getApplicationTable(){
        return catalogDAO.getApplicationTable();
    }
    public String newApplication(Application application){
        return catalogDAO.newApplication(application);
    }

    public List<Gdpr> getPersonalDataForEntry(Long fkpr, Long fkrole){
        return catalogDAO.getPersonalDataForEntry(fkpr,fkrole);
    }
    public List<Gdpr> getAllPersonalData(){
        return catalogDAO.getAllPersonalData();
    }
    public List<Application> getApplicationsForPD(Long id){
        return catalogDAO.getApplicationsForPD(id);
    }
    public String newGdprEntry(Gdpr gdpr){
        return catalogDAO.newGdprEntry(gdpr);
    }
}
