package techBuildRunExample.agregadorInvestimento.service;

import org.aspectj.weaver.ast.Var;
import org.springframework.stereotype.Service;
import techBuildRunExample.agregadorInvestimento.controller.CreateUserDto;
import techBuildRunExample.agregadorInvestimento.controller.UpdateUserDto;
import techBuildRunExample.agregadorInvestimento.entity.User;
import techBuildRunExample.agregadorInvestimento.repository.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
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

    public Optional<User> getUserById(String userId){
         return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> ListUsers(){
        return userRepository.findAll();
    }

    public void updateById(String userId, UpdateUserDto updateUserDto){
        var id = UUID.fromString(userId);
        var userEntity = userRepository.findById(id);

        if (userEntity.isPresent()){
            var user = userEntity.get();

            if(updateUserDto.username() != null){
                user.setUsername(updateUserDto.username());
            }

            if(updateUserDto.password() != null){
                user.setPassword(updateUserDto.password());
            }

            userRepository.save(user);
        }
    }

    public void deleteByID(String userId){
        var id = UUID.fromString(userId);
        var userExists = userRepository.existsById(id);

        if (userExists) {
            userRepository.deleteById(id);
        }

    }


}
