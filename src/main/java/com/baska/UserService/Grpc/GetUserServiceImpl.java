package com.baska.UserService.Grpc;

import com.baska.UserService.Payloads.GetUserResponsePayload;
import com.baska.UserService.Repository.*;
import com.baska.UserService.models.Status;
import com.baska.UserService.models.User;
import com.google.gson.Gson;
import com.id.grpc.GetUserServiceGrpc;
import com.id.grpc.UserServiceProto;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class GetUserServiceImpl extends GetUserServiceGrpc.GetUserServiceImplBase {


    @Autowired
    UserRepository userRepository;

    @Autowired
    StatusRepository statusRepository;

    @Override
    public void getUser(UserServiceProto.GetUserRequest request,
                        StreamObserver<UserServiceProto.GetUserResponse> responseObserver){
        Long userId = request.getUserId();
        User user = userRepository.findById(userId).orElse(new User());
        GetUserResponsePayload getUserResponsePayload = new GetUserResponsePayload();

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String lastVisit = "";
        if (user.getLastVisit()!=null) {
            Date myLastVisit = Date.from(user.getLastVisit());
            lastVisit = formatter.format(myLastVisit);
        }
        String blockTime = "";
        if (user.getBlockedTime()!=null){
            Date myBlockTime = Date.from(user.getBlockedTime());
            blockTime = formatter.format(myBlockTime);
        }
        getUserResponsePayload.setBlockedTime(blockTime);
        getUserResponsePayload.setLastVisit(lastVisit);
        getUserResponsePayload.setCountWrongPassword(user.getCountWrongPassword());
        getUserResponsePayload.setEmail(user.getEmail());
        getUserResponsePayload.setStatus(statusRepository.findById(user.getStatusId()).orElse(new Status()).getName().toString());
        getUserResponsePayload.setUserId(user.getId());
        getUserResponsePayload.setUserName(user.getUsername());
        Gson gson = new Gson();
        String json = gson.toJson(getUserResponsePayload);
        UserServiceProto.GetUserResponse response = UserServiceProto.GetUserResponse
                .newBuilder()
                .setJson(json)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
