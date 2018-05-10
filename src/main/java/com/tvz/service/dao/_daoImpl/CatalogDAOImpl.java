package com.tvz.service.dao._daoImpl;

import com.tvz.model.*;
import com.tvz.service.dao.CatalogDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CatalogDAOImpl implements CatalogDAO{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<Proces> getAllProcessesForDep(Long id) {
        List<Proces> procesiOdjela = jdbc.query("select * from catalog.processes where fkdep = ?",new Object[]{id}, (rs, rowNum) -> {
            Proces process = new Proces();
           process.setId(rs.getLong(1));
           process.setName(rs.getString(2));
           process.setDesc(rs.getString(3));
           process.setFkdep(rs.getLong(4));
           return process;
        });
        return procesiOdjela;
    }

    @Override
    public String newProcess(Proces process) {
        try {
            jdbc.update("insert into catalog.processes (process_name,process_desc,fkdep) values (?,?,?)", new Object[]{
                    process.getName(),process.getDesc(),process.getFkdep()
            });
            return "Uspješno dodavanje!";
        } catch (DataAccessException e) {
            e.printStackTrace();
            return "Došlo je do pogreške!";
        }
    }

    @Override
    public List<Role> getRolesOfProces(Long id) {
        List<Role> ulogeProcesa = jdbc.query("select * from catalog.roles where fkpr = ?",new Object[]{id}, (rs, rowNum) -> {
            Role role = new Role();
                role.setId(rs.getLong(1));
                role.setName(rs.getString(2));
                role.setDesc(rs.getString(3));
                role.setFkpr(rs.getLong(4));
            return role;
        });
        return ulogeProcesa;
    }

    @Override
    public String newRole(Role role) {
        try {
            jdbc.update("insert into catalog.roles (role_name,role_desc,fkpr) values (?,?,?)", new Object[]{
                    role.getName(),role.getDesc(),role.getFkpr()
            });
            return "Uspješno dodavanje!";
        } catch (DataAccessException e) {
            e.printStackTrace();
            return "Došlo je do pogreške!";
        }
    }

    @Override
    public List<PersonalData> getPersonalDataTable() {
        List<PersonalData> pd = jdbc.query("select * from catalog.personal_data order by personal_data_name asc", (rs, rowNum) -> {
            PersonalData personalData = new PersonalData();
            personalData.setId(rs.getLong(1));
            personalData.setName(rs.getString(2));
            personalData.setDesc(rs.getString(3));
            return personalData;
        });
        return pd;
    }

    @Override
    public String newPersonalData(PersonalData personalData) {
        try {
            jdbc.update("insert into catalog.personal_data (personal_data_name,personal_data_desc) values (?,?)", new Object[]{
                    personalData.getName(),personalData.getDesc()
            });
            return "Uspješno dodavanje!";
        } catch (DataAccessException e) {
            e.printStackTrace();
            return "Došlo je do pogreške!";
        }
    }

    @Override
    public List<Application> getApplicationTable() {
        List<Application> applications = jdbc.query("select * from catalog.applications order by app_name asc", (rs, rowNum) -> {
           Application app = new Application();
           app.setId(rs.getLong(1));
           app.setName(rs.getString(2));
           app.setDesc(rs.getString(3));
           app.setOwner(rs.getString(4));
           return app;
        });
        return applications;
    }

    @Override
    public String newApplication(Application application) {
        try {
            jdbc.update("insert into catalog.applications (app_name,app_desc,app_owner) values (?,?,?)", new Object[]{
                    application.getName(),application.getDesc(),application.getOwner()
            });
            return "Uspješno dodavanje!";
        } catch (DataAccessException e) {
            e.printStackTrace();
            return "Došlo je do pogreške!";
        }
    }

    @Override
    public List<Gdpr> getPersonalDataForEntry(Long fkpr, Long fkrole) {
        List<Gdpr> gdpr = jdbc.query("select * from catalog.gdpr_pd gdpr join catalog.personal_data " +
                "pd on pd.id = gdpr.fkpd where fkpr = ? and fkrole = ? ", new Object[]{fkpr,fkrole}, (rs, rowNum) -> {
           Gdpr gdprs = new Gdpr();
           gdprs.setId(rs.getLong(1));
           gdprs.setFkpd(rs.getLong(2));
           gdprs.setFkpr(rs.getLong(3));
           gdprs.setFkrole(rs.getLong(4));
           gdprs.setNeeded(rs.getBoolean(5));
           gdprs.setThird(rs.getBoolean(6));
           gdprs.setPdName(rs.getString(8));
           gdprs.setPdDesc(rs.getString(9));
           return gdprs;
        });
        return gdpr;
    }

    @Override
    public List<Gdpr> getAllPersonalData() {
        List<Gdpr> gdpr = jdbc.query("select * from catalog.gdpr_pd gdpr join catalog.personal_data pd on pd.id = gdpr.fkpd", new Object[]{}, (rs, rowNum) -> {
            Gdpr gdprs = new Gdpr();
            gdprs.setId(rs.getLong(1));
            gdprs.setFkpd(rs.getLong(2));
            gdprs.setFkpr(rs.getLong(3));
            gdprs.setFkrole(rs.getLong(4));
            gdprs.setNeeded(rs.getBoolean(5));
            gdprs.setThird(rs.getBoolean(6));
            gdprs.setPdName(rs.getString(7));
            gdprs.setPdDesc(rs.getString(8));
            return gdprs;
        });
        return gdpr;
    }

    @Override
    public String newGdprEntry(Gdpr gdpr) {
        try {
            jdbc.update("insert into catalog.gdpr_pd (fkpd,fkpr,fkrole,needed,third) values(?,?,?,?,?)",new               Object[]{
                    gdpr.getFkpd(),gdpr.getFkpr(),gdpr.getFkrole(),gdpr.isNeeded(),gdpr.isThird()
            });
            Long id = jdbc.queryForObject("select max(id) from catalog.gdpr_pd",Long.class);
            List<Object[]> params = new ArrayList<>();
            for(Long idapp: gdpr.getApplications()){
                params.add(new Object[]{
                        idapp,id
                });
            }
            jdbc.batchUpdate("insert into catalog.applications_conn (fkapp,fkgdpr) values (?,?)",params);
            return "Uspješno dodavanje novog osobnog podatka";
        } catch (DataAccessException e) {
            e.printStackTrace();
            return "Došlo je do pogeške prilikom batch inserta";
        }
    }

    @Override
    public List<Application> getApplicationsForPD(Long id) {
        List<Application> applications = jdbc.query("select * from catalog.applications app JOIN catalog.applications_conn conn ON conn.fkapp = app.id WHERE conn.fkgdpr = ?",new Object[]{id}, (rs, rowNum) -> {
            Application app = new Application();
            app.setId(rs.getLong(1));
            app.setName(rs.getString(2));
            app.setDesc(rs.getString(3));
            app.setOwner(rs.getString(4));
            return app;
        });
        return applications;
    }
}
