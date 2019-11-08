package com.mywallet.repository;

import com.mywallet.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author linapex
 */
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users findByUsernameAndPassword(String username, String password);
}

