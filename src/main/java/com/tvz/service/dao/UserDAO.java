package com.tvz.service.dao;

import com.tvz.model.Login;
import com.tvz.model.Register;
import org.springframework.http.ResponseEntity;

public interface UserDAO {

    ResponseEntity<String> registerUser(Register register);
    ResponseEntity<String> loginUser(Login login);
}
