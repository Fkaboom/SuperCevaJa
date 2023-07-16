package br.org.fundatec.SuperCevaJa.service;

import br.org.fundatec.SuperCevaJa.dto.user.UserDTO;
import br.org.fundatec.SuperCevaJa.dto.user.UserRequestCreateDTO;
import br.org.fundatec.SuperCevaJa.dto.user.UserRequestUpdateDTO;
import br.org.fundatec.SuperCevaJa.model.UserModel;
import br.org.fundatec.SuperCevaJa.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(UserRequestCreateDTO userRequestCreateDTO) {
        UserModel userModel = convertToModel(userRequestCreateDTO);
        Optional<UserModel> existingUser = Optional.ofNullable(userRepository.findByLogin(userModel.getLogin()));

        boolean isPresent = existingUser.isPresent();

        if (isPresent) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User already exists with login " + userModel.getLogin());
        }

        userRepository.save(userModel);
    }

    public List<UserDTO> findAll() {
        List<UserModel> userModel = userRepository.findAll();

        List<UserDTO> activeUser = userModel.stream()
                .filter(user -> user.getDeletedAt() == null).map(this::convertToDTO)
                .collect(Collectors.toList());

        return activeUser;
    }

    public void updateUser(UserRequestUpdateDTO userRequestUpdateDTO) {
        Long userId = userRequestUpdateDTO.getId();


        Optional<UserModel> foundUser = Optional.ofNullable(findByIdModel(userId));
        UserModel updatedUser = foundUser.get();

        updatedUser.setName(userRequestUpdateDTO.getName());
        updatedUser.setSurname(userRequestUpdateDTO.getSurname());

        this.userRepository.save(updatedUser);

    }

    public void deleteUser(String login) {

        Optional<UserModel> existingUser = Optional.ofNullable(getUserByLogin(login));

        boolean isEmpty = existingUser.isEmpty();

        if (isEmpty) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found with login " + login);
        }

        UserModel deletedUserModel = existingUser.get();
        deletedUserModel.setDeletedAt(LocalDateTime.now());
        this.userRepository.save(deletedUserModel);

    }

    public UserModel getUserByLogin(String login) {
        Optional<UserModel> existingUser = Optional.ofNullable(userRepository.findByLogin(login));

        boolean isEmpty = existingUser.isEmpty();

        if (isEmpty) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found with login " + login);
        }

        return existingUser.get();
    }

    public UserDTO findByIdDto(Long id) {
        Optional<UserModel> userModel = userRepository.findById(id);

        boolean isEmpty = userModel.isEmpty();
        boolean isDeleted = userModel.get().getDeletedAt() != null;

        if (isDeleted || isEmpty) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + id);
        }

        UserDTO userDto = convertToDTO(userModel.get());
        return userDto;

    }

    public UserModel findByIdModel(Long id) {
        Optional<UserModel> userModel = userRepository.findById(id);

        boolean isEmpty = userModel.isEmpty();
        boolean isDeleted = userModel.get().getDeletedAt() != null;


        if (isDeleted || isEmpty) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID " + id);
        }

        return userModel.get();

    }

    private UserModel convertToModel(UserRequestCreateDTO userRequestCreateDTO) {


        UserModel userModel = new UserModel();
        userModel.setName(userRequestCreateDTO.getName());
        userModel.setSurname(userRequestCreateDTO.getSurname());
        userModel.setBirthDate(userRequestCreateDTO.getBirthDate());
        userModel.setCpf(userRequestCreateDTO.getCpf());
        userModel.setLogin(userRequestCreateDTO.getLogin());

        String plainPassword = userRequestCreateDTO.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(plainPassword);
        userModel.setPassword(encryptedPassword);

        return userModel;
    }

    private UserDTO convertToDTO(UserModel userModel) {
        UserDTO userDto = new UserDTO();
        userDto.setName(userModel.getName());
        userDto.setSurname(userModel.getSurname());
        userDto.setCpf(userModel.getCpf());
        userDto.setLogin(userModel.getLogin());

        return userDto;
    }
}
