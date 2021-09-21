package com.baska.UserService.Repository;

import com.baska.UserService.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query("select usr from User as usr where usr.username=:username")
    User findByUsername(String username);

}
