package com.baska.UserService.Repository;

import com.baska.UserService.models.EStatus;
import com.baska.UserService.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status,Long> {

    Status findByName(EStatus status);

    Optional<Status> findById(Long id);
}
