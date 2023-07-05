package br.org.fundatec.SuperCevaJa.service;

import br.org.fundatec.SuperCevaJa.dto.UserDto;
import br.org.fundatec.SuperCevaJa.model.UserModel;
import br.org.fundatec.SuperCevaJa.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserModel createUser(UserModel userModel) {
        Optional<UserModel> existingUser = Optional.ofNullable(userRepository.findByLogin(userModel.getLogin()));

        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User already exists with login: " + userModel.getLogin());
        }

        UserModel createdUserModel = userRepository.save(userModel);
        return createdUserModel;
    }

    public List<UserDto> findAll() {
        List <UserModel> userModel = userRepository.findAll();

        List<UserDto> activeUser = userModel.stream()
                .filter(user -> user.getDeletedAt() == null).map(this::convertToDto)
                .collect(Collectors.toList());

        return activeUser;
    }

    public UserModel updateUser(Long id, String name, String surname){
        Optional<UserModel> foundUser = Optional.ofNullable(findByIdModel(id));
        UserModel updatedUser = foundUser.get();

        updatedUser.setName(name);
        updatedUser.setSurname(surname);

        this.userRepository.save(updatedUser);
        return updatedUser;

    }

    public void deleteUser(String login){
        Optional<UserModel> existingUser = Optional.ofNullable(userRepository.findByLogin(login));
        if (!existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found with login: " + login);
        }
            UserModel deletedUser = existingUser.get();
            deletedUser.setDeletedAt(LocalDateTime.now());
            this.userRepository.save(deletedUser);

    }

    public UserDto findByIdDto(Long id) {
        Optional<UserModel> userModel = userRepository.findById(id);

        if (userModel.isPresent() && userModel.get().getDeletedAt() == null){
            UserDto userDto = convertToDto(userModel.get());
            return userDto;
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + id);
        }
    }

    public UserModel findByIdModel(Long id) {
        Optional<UserModel> userModel = userRepository.findById(id);

        if (userModel.isPresent() && userModel.get().getDeletedAt() == null){
            return userModel.get();
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + id);
        }
    }

    private UserDto convertToDto(UserModel userModel) {
        UserDto userDto = new UserDto();
        userDto.setId(userModel.getId());
        userDto.setName(userModel.getName());
        userDto.setSurname(userModel.getSurname());
        userDto.setCpf(userModel.getCpf());
        userDto.setLogin(userModel.getLogin());
        return userDto;
    }
}
