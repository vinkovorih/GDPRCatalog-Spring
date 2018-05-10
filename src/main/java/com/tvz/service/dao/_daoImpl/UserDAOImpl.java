package com.tvz.service.dao._daoImpl;

import com.tvz.model.Dep;
import com.tvz.model.Login;
import com.tvz.model.Register;
import com.tvz.model.User;
import com.tvz.service.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public ResponseEntity<String> registerUser(Register register) {
        if(jdbc.queryForObject("select count(*) from PORTAL.users where username = ?",new Object[]{register.getUsername()},Long.class) > 0){
            return ResponseEntity.unprocessableEntity().body("Korisnički račun već postoji");
        }
        try {
            register.setRole("user");
            jdbc.update("insert into PORTAL.users (password,email,firstname,lastname,role,username,dep) values(?,?,?,?,?,?,?)",new Object[]{register.getPassword(), register.getEmail(), register.getFirstname(), register.getLastname(), register.getRole(), register.getUsername(),register.getDep()});
            return ResponseEntity.ok().body("Uspješna registracija");
        } catch (DataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Došlo je do pogreške prilikom registracije!");
        }
    }

    @Override
    public ResponseEntity<User> loginUser(Login login) {
        try {
            if(jdbc.queryForObject("SELECT count(*) from PORTAL.users where username = ? and password = ?", new Object[]{login.getUsername(), login.getPassword()},Long.class) == 1){
                User u =  jdbc.queryForObject("SELECT * FROM PORTAL.users where username = ? and password = ?", new Object[]{login.getUsername(), login.getPassword()},
                        (rs, rowNum) -> {
                            User user = new User();
                            user.setId(rs.getLong(1));
                            user.setUsername(rs.getString(7));
                            user.setFirstName(rs.getString(4));
                            user.setLastName(rs.getString(5));
                            user.setRole(rs.getString(6));
                            user.setDep(rs.getLong(8));
                            return user;
                        });
                return ResponseEntity.status(200).body(u);
            }
            else return ResponseEntity.status(401).body(new User());
        } catch (DataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new User());
        }
    }

    @Override
    public List<Dep> getDepartments() {
        List<Dep> dep =  jdbc.query("select * from PORTAL.dep", (rs, rowNum)-> {
           Dep d = new Dep();
           d.setId(rs.getLong(1));
           d.setName(rs.getString(2));
           d.setShortname(rs.getString(3));
           return d;
        });
        return dep;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = jdbc.query("select * from PORTAL.users", (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong(1));
            user.setUsername(rs.getString(7));
            user.setFirstName(rs.getString(4));
            user.setLastName(rs.getString(5));
            user.setRole(rs.getString(6));
            user.setDep(rs.getLong(8));
            return user;
        });
        return users;
    }

    @Override
    public String deleteUser(Long id) {
        try {
            jdbc.update("DELETE FROM PORTAL.USERS WHERE ID = ?", id);
            return "Uspješno brisanje korisnika!";
        } catch (DataAccessException e) {
            e.printStackTrace();
            return "Došlo je do pogreške prilikom brisanja korisnika";
        }
    }

    @Override
    public String deleteDep(Long id) {
        try {
            jdbc.update("DELETE FROM PORTAL.DEP WHERE ID = ?",id);
            return "Uspješno brisanje odjela!";
        } catch (DataAccessException e) {
            e.printStackTrace();
            return "Došlo je do pogreške prilikom brisanja odjela!";
        }
    }

    @Override
    public String changeAuthorities(Long id) {
        try {
            if (jdbc.queryForObject("SELECT ROLE FROM PORTAL.USERS WHERE ID = ?", new Object[]{id}, String.class).equals("admin")) {
                jdbc.update("UPDATE PORTAL.USERS SET ROLE='user' WHERE ID = ?",id);
                return "Uspješno uklanjanje administratorskih ovlasti!";
            }
            else{
                jdbc.update("UPDATE PORTAL.USERS SET ROLE = 'admin' WHERE ID = ?", id);
                return "Korisnik od sada ima administratorske ovlasti!";
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            return "Došlo je do pogreške prilikom ažuriranja prava korisnika!";
        }
    }

    @Override
    public String insertDep(Dep dep) {
        try {
            jdbc.update("INSERT INTO PORTAL.DEP (NAME,SHORT) VALUES(?,?)",new Object[]{dep.getName(),dep.getShortname().toUpperCase()});
            return "Uspješno dodavanje odjela!";
        } catch (DataAccessException e) {
            e.printStackTrace();
            return "Došlo je do pogreške prilikom dodavanja odjela!";
        }
    }

    @Override
    public User getUserInfo(Long id) {
        return jdbc.queryForObject("SELECT * FROM PORTAL.USERS WHERE ID = ?", new Object[]{id}, (rs, rowNum) -> {
            User u = new User();
            u.setId(rs.getLong(1));
            u.setEmail(rs.getString(3));
            u.setFirstName(rs.getString(4));
            u.setLastName(rs.getString(5));
            u.setRole(rs.getString(6));
            u.setUsername(rs.getString(7));
            u.setImg(rs.getString(9));
            return u;
        });
    }

    @Override
    public String changePhoto(String photo, Long id) {
        try {
            jdbc.update("UPDATE USERS SET IMG = ? WHERE ID = ?",new Object[]{photo,id});
            return "Uspješno ažuriranje fotografije!";
        } catch (DataAccessException e) {
            e.printStackTrace();
            return "Došlo je do pogreške prilikom ažuriranja!";
        }
    }

    @Override
    public String updateUser(User user) {
        try{
            jdbc.update("UPDATE USERS SET EMAIL = ?, FIRSTNAME = ?, LASTNAME = ? WHERE ID = ?",
                    new Object[]{user.getEmail(), user.getFirstName(), user.getLastName(), user.getId()});
            return "Uspješno ažuriranje!";
        }catch (Exception ex){
            ex.printStackTrace();
            return "Došlo je do pogreške prilikom ažuriranja korisnika";
        }
    }

    @Override
    public String changePassword(Login login) {
        try {
            jdbc.update("UPDATE USERS SET PASSWORD = ? WHERE ID = ?",new Object[]{
                    login.getPassword(), Long.parseLong(login.getUsername())
            });
            return "Uspješno ažuriranje lozinke!";
        } catch (DataAccessException e) {
            e.printStackTrace();
            return "Došlo je do pogreške!";
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "Došlo je do pogreške!";
        }
    }
}
