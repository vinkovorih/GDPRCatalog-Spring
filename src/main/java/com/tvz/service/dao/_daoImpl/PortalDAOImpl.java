package com.tvz.service.dao._daoImpl;

import com.tvz.model.News;
import com.tvz.service.dao.PortalDAO;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PortalDAOImpl implements PortalDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<News> getNews() {
        try {
            List<News> news = jdbc.query("SELECT * FROM PORTAL.NEWS ORDER BY CONTENT ASC", (rs, rowNum) ->{
               News n = new News();
               n.setId(rs.getLong(1));
               n.setTitle(rs.getString(2));
               n.setContent(rs.getString(3));
               n.setImage(rs.getString(4));
               n.setDate(rs.getTimestamp(5));
               n.setLarge(rs.getString(6));
               n.setAuthor(rs.getString(7));
               return n;
            });
            return news;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public String newPost(News news) {
        try {
            jdbc.update("INSERT INTO PORTAL.NEWS (TITLE,CONTENT,IMAGE,DATE,LARGE,AUTHOR) VALUES(?,?,?,?,?,?)",new Object[]{ news.getTitle(), news.getContent(), news.getImage(), news.getDate(), news.getLarge(),news.getAuthor()});
            return "Novi post uspješno dodan!";
        } catch (DataAccessException e) {
            e.printStackTrace();
            return "Došlo je do pogreške prilikom dodavanja novog posta";
        }
    }

    @Override
    public String deletePost(Long id) {
        try {
            jdbc.update("DELETE FROM PORTAL.NEWS WHERE ID = ?", id);
            jdbc.update("DELETE FROM PORTAL.LIKES WHERE FKNEWS = ?",id);
            return "Uspješno brisanje posta";
        } catch (DataAccessException e) {
            e.printStackTrace();
            return "Došlo je do pogreške prilikom brisanja posta! Molimo pokušajte kasnije";
        }
    }

    @Override
    public String likeDislikePost(Long iduser, Long idPost) {
            if(jdbc.queryForObject("SELECT COUNT(*) FROM PORTAL.LIKES WHERE FKNEWS = ? AND FKUSER = ?", new Object[]{idPost,iduser},Long.class)>0){
                jdbc.update("DELETE FROM PORTAL.LIKES WHERE FKNEWS = ? AND FKUSER = ?", new Object[]{idPost,iduser});
                return "Uklonili ste post sa liste postova koji vam se sviđaju!";
            }
            else{
                jdbc.update("INSERT INTO PORTAL.LIKES (FKNEWS,FKUSER) VALUES (?,?)", new Object[]{idPost,iduser});
                return "Post je dodan na listu obožavanih postova!";
            }
    }

    @Override
    public List<News> getLikesForUser(Long id) {
        try{
            List<News> likes = jdbc.query("SELECT n.id,n.title,n.author,n.content FROM PORTAL.NEWS N JOIN PORTAL.LIKES L ON L.FKNEWS = N.ID WHERE L.FKUSER = ?",new Object[]{id},(rs, rowNum) -> {
                News news = new News();
                news.setId(rs.getLong(1));
                news.setTitle(rs.getString(2));
                news.setAuthor(rs.getString(3));
                news.setContent(rs.getString(4));
                return news;
            });
            return likes;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void insertBugs(String author, String title, String content) {
        jdbc.update("INSERT INTO PORTAL.BUGS (AUTHOR,TITLE,CONTENT) VALUES (?,?,?)", new Object[]{author,title,content});

    }
}
