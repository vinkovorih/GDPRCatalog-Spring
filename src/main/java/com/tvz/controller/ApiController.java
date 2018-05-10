package com.tvz.controller;

import com.tvz.model.*;
import com.tvz.service.CatalogService;
import com.tvz.service.PortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api")
public class ApiController {

    @Autowired
    PortalService portalService;

    @Autowired
    CatalogService catalogService;

    //portal
    @RequestMapping(value = "portal/news", method = RequestMethod.GET, produces = "application/json")
    private List<News> getAllNews(){
        return portalService.getNews();
    }
    @RequestMapping(value = "portal/news", method = RequestMethod.POST ,produces = "text/plain;charset=UTF-8")
    private String newPost(@RequestBody News news){
        return portalService.newPost(news);
    }
    @RequestMapping(value = "portal/news/{id}", method = RequestMethod.DELETE, produces = "text/plain;charset=UTF-8")
    private String deletePost(@PathVariable Long id){
        return portalService.deletePost(id);
    }
    @RequestMapping(value = "portal/news/{idnews}/{iduser}", method = RequestMethod.PUT,produces = "text/plain;charset=UTF-8")
    private String likeDislikePost(@PathVariable Long idnews, @PathVariable Long iduser){
        return portalService.likeDislikePost(idnews,iduser);
    }
    @RequestMapping(value = "portal/likes/{iduser}", method = RequestMethod.GET)
    private List<News> getLikesForUser(@PathVariable Long iduser){
        return portalService.getLikesForUser(iduser);
    }
    @RequestMapping(value = "report", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    private String reportBug(@RequestBody News news){
        return portalService.reportBug(news);
    }
    @RequestMapping(value="portal/users", method = RequestMethod.GET)
    private List<User> getUsers(){
        return portalService.getUsers();
    }
    @RequestMapping(value="portal/users/{id}", method = RequestMethod.GET)
    private User getUser(@PathVariable Long id){
        return portalService.getUserInfo(id);
    }
    @RequestMapping(value="portal/users/{id}", method = RequestMethod.PUT ,produces = "text/plain;charset=UTF-8")
    private String changeAuthorites(@PathVariable Long id){
        return portalService.changeAuthorities(id);
    }
    @RequestMapping(value="portal/users/{id}",method = RequestMethod.DELETE,produces = "text/plain;charset=UTF-8")
    private String deleteUser(@PathVariable Long id){
        return portalService.deleteUser(id);
    }
    @RequestMapping(value="portal/user/{id}", method = RequestMethod.PUT ,produces = "text/plain;charset=UTF-8")
    private String updatePhoto(@PathVariable Long id, @RequestBody User u){
        return portalService.changePhoto(u.getImg(),id);
    }
    @RequestMapping(value = "portal/user", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    private String updateUser(@RequestBody User user){
        return portalService.updateUser(user);
    }
    @RequestMapping(value = "portal/user", method = RequestMethod.PUT, produces = "text/plain;charset=UTF-8")
    private String updatePassword(@RequestBody Login login){
        return portalService.changePassword(login);
    }
    @RequestMapping(value = "portal/register", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    private ResponseEntity<String> registerUser(@RequestBody Register register){
        return portalService.registerUser(register);
    }
    @RequestMapping(value = "portal/login", method = RequestMethod.POST, produces = "application/json")
    private ResponseEntity<User> loginUser(@RequestBody Login login){ return portalService.loginUser(login);
    }
    @RequestMapping(value="portal/deps/{id}", method = RequestMethod.DELETE,produces = "text/plain;charset=UTF-8")
    private String deleteDepartment(@PathVariable Long id){
        return portalService.deleteDep(id);
    }
    @RequestMapping(value = "portal/deps", method = RequestMethod.GET)
    private List<Dep> getDeps(){
        return portalService.getDepartments();
    }
    @RequestMapping(value = "portal/deps", method = RequestMethod.POST ,produces = "text/plain;charset=UTF-8")
    private String insertDep(@RequestBody Dep dep){
        return portalService.insertDep(dep);
    }

    //catalog
    @RequestMapping(value = "catalog/processes/{id}", method = RequestMethod.GET)
    private List<Proces> getAllProcessesForDep(@PathVariable Long id){
        return catalogService.getAllProcessesForDep(id);
    }
    @RequestMapping(value = "catalog/processes", method = RequestMethod.POST)
    private String newProcess(@RequestBody Proces process){
        return catalogService.newProcess(process);
    }
    @RequestMapping(value = "catalog/roles/{id}", method = RequestMethod.GET)
    List<Role> getRolesOfProces(@PathVariable Long id){
        return catalogService.getRolesOfProces(id);
    }
    @RequestMapping(value = "catalog/roles", method = RequestMethod.POST)
    private String newRole(@RequestBody Role role){
        return catalogService.newRole(role);
    }
    @RequestMapping(value = "catalog/personaldata", method = RequestMethod.GET)
    private List<PersonalData> getPersonalDataTable(){
        return catalogService.getPersonalDataTable();
    }
    @RequestMapping(value = "catalog/personaldata", method = RequestMethod.POST)
    private String newPersonalData(@RequestBody PersonalData personalData){
        System.out.println(personalData.getName() + " " + personalData.getDesc());
        return catalogService.newPersonalData(personalData);
    }
    @RequestMapping(value = "catalog/applications", method = RequestMethod.GET)
    private List<Application> getApplicationTable(){
        return catalogService.getApplicationTable();
    }
    @RequestMapping(value = "catalog/applications", method = RequestMethod.POST)
    private String newApplication(@RequestBody Application application){
        return catalogService.newApplication(application);
    }
    @RequestMapping(value = "catalog/gdpr/{fkpr}/{fkrole}", method = RequestMethod.GET)
    private List<Gdpr> getPersonalDataForEntry(@PathVariable Long fkpr,@PathVariable Long fkrole){
        return catalogService.getPersonalDataForEntry(fkpr,fkrole);
    }
    @RequestMapping(value = "catalog/gdpr", method = RequestMethod.GET)
    private List<Gdpr> getAllPersonalData(){
        return catalogService.getAllPersonalData();
    }
    @RequestMapping(value = "catalog/gdpr/{id}", method = RequestMethod.GET)
    private List<Application> getApplicationsForPD(@PathVariable Long id){
        return catalogService.getApplicationsForPD(id);
    }
    @RequestMapping(value = "catalog/gdpr", method = RequestMethod.POST)
    private String newGdprEntry(@RequestBody Gdpr gdpr){
        return catalogService.newGdprEntry(gdpr);
    }
}
