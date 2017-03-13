package com.myworkflow.server.service;

import com.myworkflow.server.entity.UserEntity;
import com.myworkflow.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by User
 * creation date: 12-Mar-17
 * email: code.crosser@gmail.com
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public UserEntity findByLoginAndPassword(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password);
    }

    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getByLogin() {
        return null;
    }

    public UserEntity getById(Long userId) {
        return userRepository.findOne(userId);
    }

    public UserEntity createAndSave(String userName, String password) {

        UserEntity userEntity = new UserEntity(userName, password);
        return this.createAndSave(userEntity);
    }

    public UserEntity createAndSave(UserEntity userEntity) {
        return userRepository.saveAndFlush(userEntity);
    }

    public void delete(UserEntity userEntity) {
        userRepository.delete(userEntity);
    }

    public void delete(Long userId) {
        userRepository.delete(userId);
    }
}
