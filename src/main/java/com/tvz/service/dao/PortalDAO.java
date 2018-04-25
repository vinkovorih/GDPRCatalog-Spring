package com.tvz.service.dao;

import com.tvz.model.News;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PortalDAO {

    ResponseEntity<List<News>> getNews();
    ResponseEntity<String> newPost(News news);
    ResponseEntity<String> deletePost(Long id);

}
