package com.baska.UserService.Repository;

import com.baska.UserService.models.UserStatusEnd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatusEndRepository extends JpaRepository<UserStatusEnd,Long> {

    @Query("Select use from UserStatusEnd as use")
    UserStatusEnd getMaxTime();
}
