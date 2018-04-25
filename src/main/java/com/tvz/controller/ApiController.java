package com.tvz.controller;

import com.tvz.model.Login;
import com.tvz.model.News;
import com.tvz.model.Register;
import com.tvz.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api")
public class ApiController {

    @Autowired
    ApiService apiService;

    @RequestMapping(value = "portal/register", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    private ResponseEntity<String> registerUser(@RequestBody Register register){
        return apiService.registerUser(register);
    }

    @RequestMapping(value = "portal/login", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    private ResponseEntity<String> loginUser(@RequestBody Login login){
        return apiService.loginUser(login);
    }

    @RequestMapping(value = "portal/news", method = RequestMethod.GET, produces = "application/json")
    private ResponseEntity<List<News>> getAllNews(){
        return apiService.getNews();
    }

    @RequestMapping(value = "portal/news", method = RequestMethod.POST)
    @ResponseBody
    private ResponseEntity<String> newPost(@RequestBody News news){
        return apiService.newPost(news);
    }

    @RequestMapping(value = "portal/news/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    private ResponseEntity<String> deletePost(@PathVariable Long id){
        return apiService.deletePost(id);
    }

}
