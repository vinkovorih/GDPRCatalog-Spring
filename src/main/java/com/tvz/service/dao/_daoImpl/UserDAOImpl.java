package com.tvz.service.dao._daoImpl;

import com.tvz.model.Login;
import com.tvz.model.Register;
import com.tvz.service.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public ResponseEntity<String> registerUser(Register register) {
        if(jdbc.queryForObject("select count(*) from users where username = ?",new Object[]{register.getUsername()},Long.class) > 0){
            return ResponseEntity.unprocessableEntity().body("Korisnički račun već postoji");
        }
        try {
            jdbc.update("insert into users (password,email,firstname,lastname,role,username) values(?,?,?,?,?,?)",new Object[]{register.getPassword(), register.getEmail(), register.getFirstname(), register.getLastname(), register.getRole(), register.getUsername()});
            return ResponseEntity.ok().body("Uspješna prijava");
        } catch (DataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Došlo je do pogreške prilikom registracije!");
        }
    }

    @Override
    public ResponseEntity<String> loginUser(Login login) {
        try {
            if(jdbc.queryForObject("SELECT count(*) from users where username = ? and password = ?", new Object[]{login.getUsername(), login.getPassword()},Long.class) == 1){
                if(jdbc.queryForObject("select role from users where username = ? and password = ?", new Object[]{login.getUsername(), login.getPassword()},String.class).equals("ADMIN")){
                    return ResponseEntity.status(200).body("Uspješna prijava administratora!");
                }
                else return ResponseEntity.status(201).body("Uspješna prijava!");
            }
            else return ResponseEntity.status(404).body("Neispravno korisničko ime ili lozinka!");
        } catch (DataAccessException e) {
            return ResponseEntity.badRequest().body("Došlo je do pogreške prilikom prijave. Molimo vas pokušajte kasnije!");
        }
    }
}
