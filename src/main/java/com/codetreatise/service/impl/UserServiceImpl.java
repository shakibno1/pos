package com.codetreatise.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codetreatise.bean.Shop;
import com.codetreatise.bean.User;
import com.codetreatise.controller.LoginController;
import com.codetreatise.repository.UserRepository;
import com.codetreatise.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User save(User entity) {
		return userRepository.save(entity);
	}

	@Override
	public User update(User entity) {
		return userRepository.save(entity);
	}

	@Override
	public void delete(User entity) {
		userRepository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public boolean authenticate(String username, String password){
		User user = this.findByUserName(username);
		if(user == null){
			return false;
		}else{
			if(password.equals(user.getPassword())){
				LoginController.setUser(user);
				return true;
			} 
			else return false;
		}
	}

	@Override
	public User findByUserName(String username) {
		return userRepository.findByUserName(username);
	}

	@Override
	public void deleteInBatch(List<User> users) {
		userRepository.deleteInBatch(users);
	}

	@Override
	public User findByName(String name) {
		return userRepository.findByName(name);
	}

	@Override
	public User find(Long id) {
		Optional<User> userOptional = userRepository.findById(id);
		if (userOptional.isPresent()){
			User user = userOptional.get();
			
			return user;
		}
		else{
		   return null;
		}
	}
	
}
