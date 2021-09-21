package com.baska.UserService;

import com.baska.UserService.Grpc.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


import java.io.IOException;


@EnableAutoConfiguration
@ComponentScan
@EnableScheduling
public class UserServiceApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(UserServiceApplication.class, args);
		Server server = ServerBuilder.forPort(8079)
				.addService(ApplicationContextHolder.getContext().getBean(SignUpServiceImpl.class))
				.addService(ApplicationContextHolder.getContext().getBean(SignInServiceImpl.class))
				.addService(ApplicationContextHolder.getContext().getBean(CheckJwtServiceImpl.class))
				.addService(ApplicationContextHolder.getContext().getBean(GetAllUserServiceImpl.class))
				.addService(ApplicationContextHolder.getContext().getBean(GetUserServiceImpl.class))
				.addService(ApplicationContextHolder.getContext().getBean(RenewJwtServiceImpl.class))
				.build();
		server.start();
		server.awaitTermination();
	}

}
