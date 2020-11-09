package com.appsdeveloperblog.photoapp.api.users.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appsdeveloperblog.photoapp.api.users.model.CreateUserReponseModel;
import com.appsdeveloperblog.photoapp.api.users.model.CreateUserRequestModel;
import com.appsdeveloperblog.photoapp.api.users.model.UserResponseModel;
import com.appsdeveloperblog.photoapp.api.users.service.UserService;
import com.appsdeveloperblog.photoapp.api.users.shared.UserDto;

@RefreshScope
@RestController
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UserService userService;

	@GetMapping("/status/check")
	public String status() {
		return "Service Running on port: " + env.getProperty("local.server.port") 
				+ ", with token: " + env.getProperty("token.secret");
	}
	
	@PostMapping(
			consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
	)
	public ResponseEntity<CreateUserReponseModel> createUser(
											@Valid @RequestBody CreateUserRequestModel userRequest) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserDto userDto = mapper.map(userRequest, UserDto.class);
		userService.createUser(userDto);
		
		CreateUserReponseModel responseModel = mapper.map(userRequest, CreateUserReponseModel.class);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
	}
	
		
    @GetMapping(value="/{userId}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("principal == #userId")
    public ResponseEntity<UserResponseModel> getUser(@PathVariable("userId") String userId) {

    	UserDto userDto = userService.getUserByUserId(userId);
    	UserResponseModel returnValue = new ModelMapper().map(userDto, UserResponseModel.class);
    	
    	return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }	
	
}
