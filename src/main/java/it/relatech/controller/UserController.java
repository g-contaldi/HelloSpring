package it.relatech.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.relatech.model.User;
import it.relatech.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	private final Logger logger = Logger.getLogger(UserController.class.getName());

	@GetMapping("/getModel")
	public User getUserModel() {
		return new User();
	}

	@PostMapping("/save")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		try {
			User savedUser = userService.saveUser(user);
			logger.info("User saved: " + savedUser);
			return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
		} catch (Exception e) {
			logger.severe("Error: " + e);
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
		try {
			User user = userService.getUserById(id);
			logger.info("Get User by id: " + user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (Exception e) {
			logger.severe("Error: " + e);
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getList")
	public ResponseEntity<List<User>> getListUsers() {
		try {
			List<User> listUsers = userService.getListUsers();
			return new ResponseEntity<List<User>>(listUsers, HttpStatus.OK);
		} catch (Exception e) {
			logger.severe("Error: " + e);
			return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getByUsernameAndPassword")
	public ResponseEntity<User> getUserByUsernameAndPassword(@RequestHeader("username") String username,
			@RequestHeader("password") String password) {
		try {
			User user = userService.getUserByUsernameAndPassword(username, password);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (Exception e) {
			logger.severe("Error: " + e);
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
