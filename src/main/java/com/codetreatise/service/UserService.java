package com.codetreatise.service;

		import com.codetreatise.bean.User;
		import com.codetreatise.generic.GenericService;

public interface UserService extends GenericService<User> {

	boolean authenticate(String username, String password);

	User findByUserName(String username);

	User findByName(String name);

}
