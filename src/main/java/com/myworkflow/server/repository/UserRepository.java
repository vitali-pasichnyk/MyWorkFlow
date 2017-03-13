package com.myworkflow.server.repository;

import com.myworkflow.server.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by User
 * creation date: 12-Mar-17
 * email: code.crosser@gmail.com
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByLogin(String login);

    UserEntity findByLoginAndPassword(String login, String password);
}
