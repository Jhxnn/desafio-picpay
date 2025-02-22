package com.picpay_desafio.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.picpay_desafio.dtos.AuthDto;
import com.picpay_desafio.dtos.UserRequestDto;
import com.picpay_desafio.dtos.UserResponseDto;
import com.picpay_desafio.infra.security.TokenService;
import com.picpay_desafio.models.User;
import com.picpay_desafio.repositories.UserRepository;

@Service
public class UserService {

	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	TokenService tokenService;
	
	
	public UserResponseDto findById(UUID id) {
		var user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("Cannot be found"));
		return new UserResponseDto(user.getUserId(), user.getName(),user.getEmail());
	}
	public List<UserResponseDto> findAll(){
		var users = userRepository.findAll();
		return users.stream()
                .map(user -> new UserResponseDto(user.getUserId(), user.getName(), user.getEmail()))
                .toList();
	}
	public UserResponseDto createUser(UserRequestDto userRequestDto) {
		if(userRepository.findByEmail(userRequestDto.email()) != null) return null;
		String encryptedPass = new BCryptPasswordEncoder().encode(userRequestDto.password());
		var user = new User();
		BeanUtils.copyProperties(userRequestDto, user);
		user.setPassword(encryptedPass);
		userRepository.save(user);
		return new UserResponseDto(user.getUserId(), user.getName(), user.getEmail());
	}
	public String returnToken(AuthDto authDto) {
		var usernamePassord = new UsernamePasswordAuthenticationToken(authDto.email(), authDto.password());
		var auth = this.authenticationManager.authenticate(usernamePassord);
		var token = tokenService.generateToken((User) auth.getPrincipal());
		return token;
	}
	
	public UserResponseDto updateUser(UUID id, UserRequestDto userRequestDto) {
		var user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("Cannot be found"));
		BeanUtils.copyProperties(userRequestDto, user);
		userRepository.save(user);
		return new UserResponseDto(user.getUserId(), user.getName(), user.getEmail());
		
	}
	public void deleteUser(UUID id) {
		var user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("Cannot be found"));
		userRepository.delete(user);
	}
}
