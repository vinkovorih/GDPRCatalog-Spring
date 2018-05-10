package com.tvz.service.dao;

import com.tvz.model.News;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PortalDAO {

    List<News> getNews();
    List<News> getLikesForUser(Long id);
    String newPost(News news);
    String deletePost(Long id);
    String likeDislikePost(Long iduser, Long idPost);
    void insertBugs(String author, String title, String content);
}
