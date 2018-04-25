package com.tvz.service.dao._daoImpl;

import com.tvz.model.News;
import com.tvz.service.dao.PortalDAO;
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
    public ResponseEntity<List<News>> getNews() {
        try {
            List<News> news = jdbc.query("SELECT * FROM NEWS", (rs, rowNum) ->{
               News n = new News();
               n.setId(rs.getLong(1));
               n.setTitle(rs.getString(2));
               n.setContent(rs.getString(3));
               n.setImage(new String(rs.getBytes(4), StandardCharsets.UTF_8));
               n.setDate(rs.getTimestamp(5));
               n.setPriority(rs.getLong(6));
               return n;
            });
            return ResponseEntity.ok().body(news);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ArrayList<>());
        }
    }

    @Override
    public ResponseEntity<String> newPost(News news) {
        news.setDate(Timestamp.from(Instant.now()));
        byte[] bytes = news.getImage().getBytes(StandardCharsets.UTF_8);
        try {
            jdbc.update("INSERT INTO NEWS (TITLE,CONTENT,IMAGE,DATE,PRIORITY) VALUES(?,?,?,?,?)",new Object[]{ news.getTitle(), news.getContent(), bytes, news.getDate(), news.getPriority()});
            return ResponseEntity.status(200).body("Novi post uspješno dodan!");
        } catch (DataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Došlo je do pogreške prilikom dodavanja novog posta");
        }
    }

    @Override
    public ResponseEntity<String> deletePost(Long id) {
        try {
            jdbc.update("DELETE FROM NEWS WHERE ID = ?", id);
            return ResponseEntity.status(200).body("Uspješno brisanje posta");
        } catch (DataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Došlo je do pogreške prilikom brisanja posta! Molimo pokušajte kasnije");
        }
    }


}
