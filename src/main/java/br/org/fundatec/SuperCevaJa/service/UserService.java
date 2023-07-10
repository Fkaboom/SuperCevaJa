package br.org.fundatec.SuperCevaJa.service;

import br.org.fundatec.SuperCevaJa.dto.user.UserDTO;
import br.org.fundatec.SuperCevaJa.dto.user.UserRequestCreateDTO;
import br.org.fundatec.SuperCevaJa.dto.user.UserRequestUpdateDTO;
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

    public void createUser(UserRequestCreateDTO userRequestCreateDTO) {
        UserModel userModel = convertToModel(userRequestCreateDTO);
        Optional<UserModel> existingUser = Optional.ofNullable(userRepository.findByLogin(userModel.getLogin()));

        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User already exists with login: " + userModel.getLogin());
        }

        userRepository.save(userModel);
    }



    public List<UserDTO> findAll() {
        List <UserModel> userModel = userRepository.findAll();

        List<UserDTO> activeUser = userModel.stream()
                .filter(user -> user.getDeletedAt() == null).map(this::convertToDto)
                .collect(Collectors.toList());

        return activeUser;
    }

    public void updateUser(UserRequestUpdateDTO userRequestUpdateDTO){
        Optional<UserModel> foundUser = Optional.ofNullable(findByIdModel(userRequestUpdateDTO.getId()));
        UserModel updatedUser = foundUser.get();

        updatedUser.setName(userRequestUpdateDTO.getName());
        updatedUser.setSurname(userRequestUpdateDTO.getSurname());

        this.userRepository.save(updatedUser);
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

    public UserDTO findByIdDto(Long id) {
        Optional<UserModel> userModel = userRepository.findById(id);

        if (userModel.isPresent() && userModel.get().getDeletedAt() == null){
            UserDTO userDto = convertToDto(userModel.get());
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

    private UserModel convertToModel(UserRequestCreateDTO userRequestCreateDTO) {
        UserModel userModel = new UserModel();
        userModel.setId(userRequestCreateDTO.getId());
        userModel.setName(userRequestCreateDTO.getName());
        userModel.setSurname(userRequestCreateDTO.getSurname());
        userModel.setBirthDate(userRequestCreateDTO.getBirthDate());
        userModel.setCpf(userRequestCreateDTO.getCpf());
        userModel.setLogin(userRequestCreateDTO.getLogin());
        userModel.setPassword(userRequestCreateDTO.getPassword());
        return userModel;
    }

    private UserDTO convertToDto(UserModel userModel) {
        UserDTO userDto = new UserDTO();
        userDto.setId(userModel.getId());
        userDto.setName(userModel.getName());
        userDto.setSurname(userModel.getSurname());
        userDto.setCpf(userModel.getCpf());
        userDto.setLogin(userModel.getLogin());
        return userDto;
    }
}
