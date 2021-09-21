package com.baska.UserService.Shedulers;

import com.baska.UserService.Repository.UserRepository;
import com.baska.UserService.Repository.UserStatusEndRepository;
import com.baska.UserService.Repository.UserStatusRepository;
import com.baska.UserService.models.User;
import com.baska.UserService.models.UserStatus;
import com.baska.UserService.models.UserStatusEnd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;


@Component
public class ScheduledTasks {

    @Autowired
    UserStatusRepository userStatusRepository;

    @Autowired
    UserStatusEndRepository userStatusEndRepository;

    @Autowired
    UserRepository userRepository;

    @Scheduled(cron = "*/10 * * * * *")
    public void changeUserStatus(){
        UserStatusEnd max = userStatusEndRepository.getMaxTime();
        if (max==null) {
            max = new UserStatusEnd(1L, ZonedDateTime.now().minusYears(5).toInstant());
        }
        Instant maxTime = max.getEndTime();
        List<UserStatus> userStatuses;
        userStatuses = userStatusRepository.getStatus(max.getEndTime(), Instant.now());
        if (userStatuses.size() > 0) {
            for (UserStatus el : userStatuses) {
                User user = userRepository.findById(el.getUserId()).orElse(new User());
                user.setStatusId(el.getStatus());
                userRepository.save(user);
                if (maxTime.isBefore(el.getDateBegin())) {
                    maxTime = el.getDateBegin();
                }
            }
            max.setEndTime(maxTime);
            userStatusEndRepository.save(max);
        }
    }
}
