package com.myworkflow.server.service;

import com.myworkflow.server.entity.UserEntity;

import java.util.List;

/**
 * Created by User
 * creation date: 12-Mar-17
 * email: code.crosser@gmail.com
 */
public interface UserService {

    List<UserEntity> getAll();

    UserEntity getByLogin();

    UserEntity findByLoginAndPassword(String login, String password);

    UserEntity getById(Long userId);

    UserEntity findByLogin(String login);

    UserEntity createAndSave(String userName, String password);

    UserEntity createAndSave(UserEntity userEntity);

    void delete(UserEntity userEntity);

    void delete(Long userId);


}
