package com.tvz.service.dao;

import com.tvz.model.Dep;
import com.tvz.model.Login;
import com.tvz.model.Register;
import com.tvz.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserDAO {

    ResponseEntity<String> registerUser(Register register);
    ResponseEntity<User> loginUser(Login login);
    List<User> getUsers();
    String deleteUser(Long id);
    String changeAuthorities(Long id);
    User getUserInfo(Long id);
    String changePhoto(String photo, Long id);
    String updateUser(User user);
    String insertDep(Dep dep);
    String deleteDep(Long id);
    List<Dep> getDepartments();
    String changePassword(Login login);
}
