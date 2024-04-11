package com.example.project.Controller;

import com.example.project.Model.SaveRequest.SaveUserRequest;
import com.example.project.Model.User;
import com.example.project.Response.EntityResponse;
import com.example.project.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping( "/user/api")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("firstapii")
    private String firstapii(){
        return "Working";
    }

    //@RequestMapping(name = "/saveOrUpdate",method = RequestMethod.POST)
    @PostMapping ("/save")
    public ResponseEntity<?>saveOrUpdate(@RequestBody SaveUserRequest saveUserRequest){
        return new ResponseEntity<>(new EntityResponse(userService.saveOrUpdate(saveUserRequest),0), HttpStatus.OK);
    }

    @GetMapping("/deleteById")
    public ResponseEntity<?>deleteById(@RequestParam Long userId) {
        try {
            return new ResponseEntity<>(new EntityResponse(userService.deleteById(userId), 0), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }
    @GetMapping("/getById")
    public ResponseEntity<?>getById(@RequestParam Long userId){
        return new ResponseEntity<>(new EntityResponse(userService.getById(userId),0),HttpStatus.OK);
    }
    @PutMapping("changeStatus")
    public ResponseEntity<?>changeStatus(@RequestParam Long userId){
        return new ResponseEntity<>(new EntityResponse(userService.changeStatus(userId),0),HttpStatus.OK);
    }
    @RequestMapping(value = "/getAllUser",method = RequestMethod.GET)
    private ResponseEntity<?>getAllUser(@RequestParam (defaultValue = "10",required = false)Integer pageSize,
                                        @RequestParam(defaultValue = "0",required = false)Integer pageNo,
                                        @RequestParam(required = false)String userName,
                                        @RequestParam(required = false)String userMobileNo,
                                        @RequestParam(required = false)String userEmail){
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        return new ResponseEntity<>(new EntityResponse( userService.getAllUser11(userName,userMobileNo,userEmail,pageable),0),HttpStatus.OK);
    }

    @DeleteMapping("/deleteMultipleUser")
    public ResponseEntity<?>deleteMultipleUser(@RequestParam String userId){
        return new ResponseEntity<>(new EntityResponse(userService.deleteMultipleUser(userId),0),HttpStatus.NOT_FOUND);
    }

    @GetMapping("/checkUserEmailExits")
    public ResponseEntity<?>checkUserEmailExits(@RequestParam String userEmail,
                                                @RequestParam(defaultValue = "0",required = false) Long userId) {
        try {
            return new ResponseEntity<>(new EntityResponse(userService.checkUserEmailExits(userEmail, userId), 0), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    @GetMapping("/checkMobileNoExits")
    public ResponseEntity<?>checkMobileNoExits(@RequestParam String userMobileNo,
                                               @RequestParam(defaultValue = "0",required = false)Long userId){
        try {
            return new ResponseEntity<>(new EntityResponse(userService.checkMobileNoExits(userMobileNo,userId),0),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),-1),HttpStatus.OK);
        }
    }

    @GetMapping("/getAll")
    private ResponseEntity<?>user(){
        try {
            return new ResponseEntity<>(new EntityResponse(userService.getAll(), 0), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),-1),HttpStatus.OK);
        }
    }

}
