package techBuildRunExample.agregadorInvestimento.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import techBuildRunExample.agregadorInvestimento.entity.User;
import techBuildRunExample.agregadorInvestimento.service.UserService;

import java.net.URI;
import java.util.List;

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
        var user = userService.getUserById(userID);
        if (user.isPresent()){
            return ResponseEntity.ok(user.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers(){
        var users = userService.ListUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userID}")
    public ResponseEntity<Void> updateUserById(@PathVariable("userID") String userId, @RequestBody UpdateUserDto updateUserDto){
        userService.updateById(userId, updateUserDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userID}")
    public ResponseEntity<Void> deleteById(@PathVariable("userID") String userID) {
        userService.deleteByID(userID);
        return ResponseEntity.noContent().build();
    }

}
