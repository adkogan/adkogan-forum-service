package telran.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.forum.dto.UserProfileDTO;
import telran.forum.dto.UserRegisterDTO;
import telran.forum.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountManagmentController {
	
	@Autowired
	AccountService accountService;
	
	@PostMapping("/register")
	public UserProfileDTO register(@RequestBody UserRegisterDTO userRegisterDto,
			@RequestHeader(value = "Authorization") String auth) {
		
		return accountService.addUser(userRegisterDto, auth);
	}
	
	@DeleteMapping
	public void deleteUser(@RequestHeader(value = "Authorization") String auth) {
		accountService.deleteUser(auth);
	}
	
	@PutMapping
	public UserProfileDTO updateUser(@RequestBody UserProfileDTO userProfDTO,
			@RequestHeader(value = "Authorization") String auth) {
		return accountService.updateUser(userProfDTO, auth);
	}
	
	@PutMapping("/change_password")
	public UserProfileDTO changePasswordUser(@RequestBody String password, 
			@RequestHeader(value = "Authorization") String auth) {
		return accountService.changePasswordUser(password, auth);
	}
	
	
	
	
	
	
	
	
	

}
