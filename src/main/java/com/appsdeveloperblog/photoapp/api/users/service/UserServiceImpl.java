package com.appsdeveloperblog.photoapp.api.users.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.appsdeveloperblog.photoapp.api.users.data.AlbumsServiceClient;
import com.appsdeveloperblog.photoapp.api.users.data.UserEntity;
import com.appsdeveloperblog.photoapp.api.users.model.AlbumResponseModel;
import com.appsdeveloperblog.photoapp.api.users.repository.UsersRepository;
import com.appsdeveloperblog.photoapp.api.users.shared.UserDto;

@Service
public class UserServiceImpl implements UserService {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UsersRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder passEncoder;
	
	@Autowired
	private AlbumsServiceClient albumServiceClient;
//	private RestTemplate restTemplate;
	
	
//	@Autowired
//	private Environment env;

	@Override
	public UserDto createUser(UserDto userDetails) {
		
		userDetails.setEncryptedPassword(passEncoder.encode(userDetails.getPassword()));
		
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = mapper.map(userDetails, UserEntity.class);
		
		userRepo.save(userEntity);
		
		UserDto responseModel = mapper.map(userEntity, UserDto.class);
		
		return responseModel;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepo.findByEmail(username);
		
		if(userEntity == null) 
			throw new UsernameNotFoundException(String.format(
					"User with email [%s] not found.", username));
		
		return new User(userEntity.getEmail(), 
				userEntity.getEncryptedPassword(), 
					true, true, true, true,
						new ArrayList<>());
	}

	@Override
	public UserDto getUserDetailByEmail(String email) {
		UserEntity userEntity = userRepo.findByEmail(email);
		
		if(userEntity == null) 
			throw new UsernameNotFoundException(String.format(
					"User with email [%s] not found.", email));
		
		return new ModelMapper().map(userEntity, UserDto.class);
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity userEntity = userRepo.findByUserId(userId);
		
		if(userEntity == null)
			throw new UsernameNotFoundException("User not found!");
			
		UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
		
//		String albumsUrl = String.format(env.getProperty("albums.url"), userId);
		
//		ResponseEntity<List<AlbumResponseModel>> albumListResponse 
//			= restTemplate.exchange(albumsUrl, HttpMethod.GET, null,
//					new ParameterizedTypeReference<List<AlbumResponseModel>>() {});		
		
//		List<AlbumResponseModel> albumsList = null;
//		try {
		
		logger.info("Before calling albums microservie.");
		List<AlbumResponseModel> albumsList = albumServiceClient.getAlbums(userId);
		logger.info("After calling albums microservie.");
//		} catch (FeignException e) {
//			logger.error(e.getMessage());
//		}
		
		userDto.setAlbums(albumsList);
		
		return userDto;
	}

}