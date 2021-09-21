package com.baska.UserService.Grpc;

import com.baska.UserService.Payloads.GetAllUserResponsePayload;
import com.baska.UserService.Repository.StatusRepository;
import com.baska.UserService.Repository.UserRepository;
import com.baska.UserService.models.Status;
import com.baska.UserService.models.User;
import com.google.gson.Gson;
import com.id.grpc.GetAllUserServiceGrpc;
import com.id.grpc.UserServiceProto;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class GetAllUserServiceImpl extends GetAllUserServiceGrpc.GetAllUserServiceImplBase {

    @Autowired
    UserRepository userRepository;

    @Autowired
    StatusRepository statusRepository;

    @Override
    public void getAllUser(UserServiceProto.GetAllUserRequest request,
                           StreamObserver<UserServiceProto.GetAllUserResponse> responseObserver){
        List<User> users = userRepository.findAll();
        List<GetAllUserResponsePayload> userResponsePayloads = new ArrayList<>();
        for (User x:users){
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            String lastVisit = "";
            if (x.getLastVisit()!=null) {
                Date myLastVisit = Date.from(x.getLastVisit());
                lastVisit = formatter.format(myLastVisit);
            }
            String blockTime = "";
            if (x.getBlockedTime()!=null){
                Date myBlockTime = Date.from(x.getBlockedTime());
                blockTime = formatter.format(myBlockTime);
            }
            userResponsePayloads.add(new GetAllUserResponsePayload(
                    x.getId(),
                    x.getUsername(),
                    x.getEmail(),
                    statusRepository.findById(x.getStatusId()).orElse(new Status()).getName().toString(),
                    lastVisit,
                    blockTime,
                    x.getCountWrongPassword()));
        }
        Gson gson = new Gson();
        String json = gson.toJson(userResponsePayloads);
        UserServiceProto.GetAllUserResponse response = UserServiceProto.GetAllUserResponse
                .newBuilder()
                .setJson(json)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
