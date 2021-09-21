package com.baska.UserService.Grpc;


import com.baska.UserService.Payloads.SignInResponse;
import com.baska.UserService.Repository.UserRepository;
import com.baska.UserService.Services.JWTService;
import com.baska.UserService.models.User;
import com.google.gson.Gson;
import com.id.grpc.RenewJwtServiceGrpc;
import com.id.grpc.UserServiceProto;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RenewJwtServiceImpl extends RenewJwtServiceGrpc.RenewJwtServiceImplBase {

    @Autowired
    JWTService jwtService;

    @Autowired
    UserRepository userRepository;

    @Override
    public void renew(UserServiceProto.RenewJwtRequest request,
                      StreamObserver<UserServiceProto.RenewJwtResponse> responseObserver) {
        String refreshToken = request.getToken();
        Long userId = request.getUserId();
        User user =  userRepository.findById(userId).orElse(new User());
        String answer = "refresh token is not valid";
        if (jwtService.checkRefreshJwtToken(refreshToken,user)){
            SignInResponse signInResponse = new SignInResponse();
            signInResponse.setUserId(user.getId());
            signInResponse.setUserName(user.getUsername());
            Map<Integer,String> response = jwtService.getNewRefreshToken(user);
            signInResponse.setToken("Bearer " + response.get(1));
            signInResponse.setRefreshToken(response.get(2));
            Gson gson = new Gson();
            answer = gson.toJson(signInResponse);
        }

        UserServiceProto.RenewJwtResponse response = UserServiceProto.RenewJwtResponse
                .newBuilder().setToken(answer).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }
}
