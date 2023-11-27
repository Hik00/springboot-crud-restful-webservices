package com.demo.repository;

import com.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRespository extends JpaRepository<User, Long> {
    @Query("SELECT MAX(u.id) FROM User u")
    Long findMaxUserId();
}
