package br.org.fundatec.SuperCevaJa.service;

import br.org.fundatec.SuperCevaJa.model.UserModel;
import br.org.fundatec.SuperCevaJa.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;



    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserModel createUser(UserModel userModel) {
        UserModel createdUserModel = userRepository.save(userModel);
        return createdUserModel;
    }

    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    public UserModel findById(Long id) {
        Optional<UserModel> userModel = userRepository.findById(id);
        if (userModel.isPresent()){
            return userModel.get();
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + id);
        }
    }
    public void deleteUser(String login){
        UserModel user = this.userRepository.findByLogin(login);
        this.userRepository.delete(user);
    }

    public UserModel updateUser(UserModel userModel) {
        UserModel updatedUserModel = this.userRepository.save(userModel);
        return updatedUserModel;
    }
}
