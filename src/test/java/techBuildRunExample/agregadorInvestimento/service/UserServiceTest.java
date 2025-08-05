package techBuildRunExample.agregadorInvestimento.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import techBuildRunExample.agregadorInvestimento.controller.CreateUserDto;
import techBuildRunExample.agregadorInvestimento.entity.User;
import techBuildRunExample.agregadorInvestimento.repository.UserRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

// Arrange -> arrumar
// Act -> chamar o trecho do teste
// Assert -> todas as verificações

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Nested
    class createUser {

        @Test
        @DisplayName("Should create a user with sucess")
        void shouldCreateAUserWithSuccess() {

            //Arrange
            var user = new User(UUID.randomUUID(), "username", "email@gmail.com", "password", Instant.now(), null);
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
            var input = new CreateUserDto(
                    "username",
                    "email@gmail.com",
                    "1234");
            //Act
            var output = userService.createUser(input);

            //Assert
            assertNotNull(output);
            var userCaptured = userArgumentCaptor.getValue();
            assertEquals(input.username(), userCaptured.getUsername());
            assertEquals(input.email(), userCaptured.getEmail());
            assertEquals(input.password(), userCaptured.getPassword());

        }

        @Test
        @DisplayName("Should throw exception when error occurs")
        public void shouldThrowExceptionWhenErrorOccurs() {

            //Arrange
            doThrow(new RuntimeException()).when(userRepository).save(any());
            var input = new CreateUserDto(
                    "username",
                    "email@gmail.com",
                    "1234");

            //Act & Assert
            assertThrows(RuntimeException.class, () -> userService.createUser(input));


        }
    }


    @Nested
    class getUserById{

        @Test
        @DisplayName("should Get User By Id With Sucess when opitional is present")
        void shouldGetUserByIdWithSuccessWhenOptionalIsPresent() {

            //Arrange
            var user = new User(UUID.randomUUID(), "username", "email@gmail.com", "password", Instant.now(), null);
            doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());

            //Act
            var output = userService.getUserById(user.getUserID().toString());

            //Assert
            assertTrue(output.isPresent());
            assertEquals(user.getUserID(),uuidArgumentCaptor.getValue());


        }

        @Test
        @DisplayName("should Get User By Id With Sucess when opitional is Empty ")
        void shouldGetUserByIdWithSuccessWhenOpitionalIsEmpty() {

            //Arrange
            var userID = UUID.randomUUID();
            doReturn(Optional.empty()).when(userRepository).findById(uuidArgumentCaptor.capture());

            //Act
            var output = userService.getUserById(userID.toString());

            //Assert
            assertTrue(output.isEmpty());
            assertEquals(userID,uuidArgumentCaptor.getValue());


        }
    }

}