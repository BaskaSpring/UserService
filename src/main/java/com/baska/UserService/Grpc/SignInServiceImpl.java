package com.baska.UserService.Grpc;

import com.baska.UserService.Payloads.SignInResponse;
import com.baska.UserService.Repository.StatusRepository;
import com.baska.UserService.Repository.UserRepository;
import com.baska.UserService.Repository.UserStatusRepository;
import com.baska.UserService.Services.JWTService;
import com.baska.UserService.Services.SRandomService;
import com.baska.UserService.Services.UserServiceImpl;
import com.baska.UserService.models.EStatus;
import com.baska.UserService.models.Status;
import com.baska.UserService.models.User;
import com.google.gson.Gson;

import com.id.grpc.SignInServiceGrpc;
import com.id.grpc.UserServiceProto;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Map;


@Service
public class SignInServiceImpl extends SignInServiceGrpc.SignInServiceImplBase {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    JWTService jwtService;

    @Autowired
    UserStatusRepository userStatusRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    SRandomService sRandomService;


    @Override
    public void signIn(UserServiceProto.SignInRequest request,
                       StreamObserver<UserServiceProto.SignInResponse> responseObserver){
        String answer = "";
        String username = request.getUsername();
        String password = request.getPassword();
        if (userRepository.existsByUsername(username)){
            User user = userRepository.findByUsername(username);
            Status status = statusRepository.findById(user.getStatusId()).orElse(new Status());
            if ((user.getBlockedTime()!=null)&&(user.getBlockedTime().isBefore(Instant.now()))){
                answer = "Error: User is blocked!";
            } else
            if (status.getName() != EStatus.ACTIVE) {
                answer = "Error: User is not active!";
            }else {
                String salt = user.getSalt();
                byte[] byteSalt = sRandomService.strToByte(salt);
                String hashPassword = userService.getSecurePassword(password,byteSalt);
                if (hashPassword.equals(user.getPassword())) {
                    user.setLastVisit(Instant.now());
                    user.setCountWrongPassword(0);
                    userRepository.save(user);
                    SignInResponse signInResponse = new SignInResponse();
                    Map<Integer,String> response = jwtService.getNewRefreshToken(user);
                    signInResponse.setToken("Bearer " + response.get(1));
                    signInResponse.setRefreshToken(response.get(2));
                    signInResponse.setUserName(user.getUsername());
                    signInResponse.setUserId(user.getId());
                    Gson gson = new Gson();
                    String json = gson.toJson(signInResponse);
                    answer = json;
                } else {
                    int count = 1;
                    if (user.getCountWrongPassword()!=null){
                        count = user.getCountWrongPassword()+1;
                    };
                    if (count==5){
                        user.setBlockedTime(ZonedDateTime.now().plusMinutes(15).toInstant());
                    }
                    if (count==10){
                        user.setStatusId(statusRepository.findByName(EStatus.DELETED).getId());
                    }
                    user.setCountWrongPassword(count);
                    userRepository.save(user);
                    answer = "Error: Password is wrong!";
                }
            }
        }
        else {
            answer = "Error: User is not found!";
        }
        UserServiceProto.SignInResponse response = UserServiceProto.SignInResponse
                .newBuilder().setAnswer(answer).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
