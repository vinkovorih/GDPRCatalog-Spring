package com.tvz.service;

import com.tvz.model.Login;
import com.tvz.model.News;
import com.tvz.model.Register;
import com.tvz.service.dao.PortalDAO;
import com.tvz.service.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    PortalDAO portalDAO;

    public ResponseEntity<String> registerUser(Register register){
        return userDAO.registerUser(register);
    }
    public ResponseEntity<String> loginUser(Login login){
        return userDAO.loginUser(login);
    }
    public ResponseEntity<List<News>> getNews(){
        return portalDAO.getNews();
    }
    public ResponseEntity<String> newPost(News news){
        return portalDAO.newPost(news);
    }
    public ResponseEntity<String> deletePost(Long id){
        return portalDAO.deletePost(id);
    }

}
