package techBuildRunExample.agregadorInvestimento.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import techBuildRunExample.agregadorInvestimento.entity.User;
import techBuildRunExample.agregadorInvestimento.service.UserService;

import java.net.URI;

//porta de entrada
@RestController
@RequestMapping("/v1/users")
public class Usercontroller {

    private UserService userService;

    public Usercontroller(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto){
        var userID = userService.createUser(createUserDto);
        return ResponseEntity.created(URI.create("/v1/users/" + userID.toString())).build();
    }

    @GetMapping("/{userID}")
    public ResponseEntity<User> getUserById(@PathVariable("userID") String userID){
        return null;
    }


}
