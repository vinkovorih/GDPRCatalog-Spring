package com.tvz.service;

import com.tvz.model.*;
import com.tvz.service.dao.PortalDAO;
import com.tvz.service.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class PortalService {

    @Autowired
    JavaMailSender jms;

    @Autowired
    PortalDAO portalDAO;

    public List<News> getNews(){
        return portalDAO.getNews();
    }
    public String newPost(News news){
        news.setDate(Timestamp.from(Instant.now()));
        if(news.getImage() != null){
            if(news.getImage() != null) {
                news.setImage(news.getImage().substring(news.getImage().lastIndexOf(",") + 1));
            }

        }
        return portalDAO.newPost(news);
    }
    public List<News> getLikesForUser(Long iduser){
        return portalDAO.getLikesForUser(iduser);
    }
    public String deletePost(Long id){
        return portalDAO.deletePost(id);
    }
    public String likeDislikePost(Long idnews,Long iduser){
        return portalDAO.likeDislikePost(iduser,idnews);
    }
    public String reportBug(News news){
        try {
            MimeMessage message = jms.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
                helper.setTo("vvorih@tvz.hr");
            helper.setFrom("vvorih@tvz.hr",news.getAuthor());
            helper.setSubject("Prijava greške - GDPR - ["+news.getAuthor()+"]");
            helper.setText(news.getTitle() + "\n\n" +
                    news.getContent());
            jms.send(message);
            portalDAO.insertBugs(news.getAuthor(),news.getTitle(),news.getContent());
            return "Provjera e-mail adrese uspješno poslana!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Došlo je do pogreške prilikom slanja e-maila!";
        }
    }

    @Autowired
    UserDAO userDAO;

    public List<Dep> getDepartments(){return userDAO.getDepartments();}
    public List<User> getUsers(){
        return userDAO.getUsers();
    }
    public String deleteDep(Long id){
        return userDAO.deleteDep(id);
    }
    public String insertDep(Dep dep){
        return userDAO.insertDep(dep);
    }
    public User getUserInfo(Long id){
        return userDAO.getUserInfo(id);
    }
    public String changePhoto(String photo,Long id){
        photo = photo.substring(photo.lastIndexOf(",")+1);
        return userDAO.changePhoto(photo,id);
    }
    public ResponseEntity<String> registerUser(Register register){
        return userDAO.registerUser(register);
    }
    public ResponseEntity<User> loginUser(Login login){
        return userDAO.loginUser(login);
    }
    public String deleteUser(Long id){
        return userDAO.deleteUser(id);
    }
    public String changeAuthorities(Long id){
        return userDAO.changeAuthorities(id);
    }
    public String updateUser(User user){
        return userDAO.updateUser(user);
    }
    public String changePassword(Login login){
        return userDAO.changePassword(login);
    }
}

