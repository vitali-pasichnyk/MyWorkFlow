package com.myworkflow.server.repository.auth;

import com.myworkflow.server.entity.auth.RealTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by User
 * creation date: 12-Mar-17
 * email: code.crosser@gmail.com
 */
public interface RealTokenRepository extends JpaRepository<RealTokenEntity, Long> {
}
