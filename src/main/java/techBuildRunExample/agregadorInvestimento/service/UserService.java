package techBuildRunExample.agregadorInvestimento.service;

import org.aspectj.weaver.ast.Var;
import org.springframework.stereotype.Service;
import techBuildRunExample.agregadorInvestimento.controller.CreateUserDto;
import techBuildRunExample.agregadorInvestimento.entity.User;
import techBuildRunExample.agregadorInvestimento.repository.UserRepository;

import java.time.Instant;
import java.util.UUID;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(CreateUserDto createUserDto){
        var entity = new User(UUID.randomUUID(), createUserDto.username(), createUserDto.email(), createUserDto.password(), Instant.now(), null);
        var userSaved =  userRepository.save(entity);
        return userSaved.getUserID();
    }
}
