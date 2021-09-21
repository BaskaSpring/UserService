package com.baska.UserService.Grpc;

import com.baska.UserService.Repository.StatusRepository;
import com.baska.UserService.Repository.UserRepository;
import com.baska.UserService.Services.SRandomService;
import com.baska.UserService.Services.UserServiceImpl;
import com.baska.UserService.models.EStatus;
import com.baska.UserService.models.User;
import com.id.grpc.SignUpServiceGrpc;
import com.id.grpc.UserServiceProto;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignUpServiceImpl extends SignUpServiceGrpc.SignUpServiceImplBase {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    SRandomService sRandomService;

    @Override
    public void signUp(UserServiceProto.SignUpRequest request,
                       StreamObserver<UserServiceProto.SignUpResponse> responseObserver) {
        String username = request.getUsername();
        String email = request.getEmail();
        String password = request.getPassword();
        Boolean dontreg = false;
        String answer = "User registered successfully!";
        if (userRepository.existsByEmail(email)) {
            answer = "Error: Email is already in use!";
            dontreg = true;
        }
        if (userRepository.existsByUsername(username)) {
            answer = "Error: User name is already in use!";
            dontreg = true;
        }
        if (!dontreg) {
            String salt = sRandomService.getSalt(16,30);
            byte [] saltByte = sRandomService.strToByte(salt);
            String hashPassword = userService.getSecurePassword(password, saltByte);
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setSalt(salt);
            user.setPassword(hashPassword);
            Long statusId = statusRepository.findByName(EStatus.ACTIVE).getId();
            user.setStatusId(statusId);
            userRepository.save(user);
        }
        UserServiceProto.SignUpResponse response = UserServiceProto.SignUpResponse
                .newBuilder().setMessage(answer).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
