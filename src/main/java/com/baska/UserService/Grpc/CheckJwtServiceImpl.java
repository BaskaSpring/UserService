package com.baska.UserService.Grpc;

import com.baska.UserService.Repository.UserRepository;
import com.baska.UserService.Services.JWTService;
import com.baska.UserService.models.User;
import com.id.grpc.CheckJwtServiceGrpc;
import com.id.grpc.UserServiceProto;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class CheckJwtServiceImpl extends CheckJwtServiceGrpc.CheckJwtServiceImplBase {

    @Autowired
    JWTService jwtService;

    @Autowired
    UserRepository userRepository;

    @Override
    public void checkJwt(UserServiceProto.CheckJwtRequest request,
                         StreamObserver<UserServiceProto.CheckJwtResponse> responseObserver){
        String token  = jwtService.getJwt(request.getBearer());
        Long usedId = request.getUserId();
        boolean permission = false;
        boolean expiredToken = false;
        User user = userRepository.findById(usedId).orElse(new User());
        if (jwtService.validateJwtToken(token,user).getExpired()){
            expiredToken = true;
        } else if (jwtService.checkJwtToken(token,user)){
            permission = true;
        }
        UserServiceProto.CheckJwtResponse response = UserServiceProto.CheckJwtResponse
                .newBuilder()
                .setExpiredToken(expiredToken)
                .setValid(permission)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
