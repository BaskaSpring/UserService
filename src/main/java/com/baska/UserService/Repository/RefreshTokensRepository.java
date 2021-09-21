package com.baska.UserService.Repository;

import com.baska.UserService.models.RefreshTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RefreshTokensRepository extends JpaRepository<RefreshTokens,Long> {

    Boolean existsByUserIdAndRefreshTokenSecret(Long userId,String refreshTokenSecret);


    @Query("select x from RefreshTokens as x where x.userId=:userId")
    List<RefreshTokens> findByUserId(Long userId);
}
