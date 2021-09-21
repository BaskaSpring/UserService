package com.baska.UserService.Repository;

import com.baska.UserService.models.TokenData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<TokenData,Long> {

    @Query("select x.tokenSecret from TokenData as x where x.userId =:userid")
    List<String> findByUserId (Long userid);


}
