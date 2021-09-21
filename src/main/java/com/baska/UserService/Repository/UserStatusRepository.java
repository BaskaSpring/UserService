package com.baska.UserService.Repository;


import com.baska.UserService.models.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;


@Repository
public interface UserStatusRepository extends JpaRepository<UserStatus,Long> {

    @Query("SELECT us FROM UserStatus AS us WHERE us.dateBegin>:endtime and us.dateBegin<=:date")
    List<UserStatus> getStatus(@Param("endtime") Instant endtime,@Param("date") Instant date);
}
