package telran.forum.service;

import telran.forum.configuration.AccountUserCredential;
import telran.forum.dto.UserProfileDTO;
import telran.forum.dto.UserRegisterDTO;

public interface AccountService {
	
	UserProfileDTO addUser(UserRegisterDTO userRegDto, String auth);
	
	void deleteUser(String auth);
	
	UserProfileDTO updateUser(UserProfileDTO userProfDTO, String auth);
	
	UserProfileDTO changePasswordUser(String Password, String auth);

	AccountUserCredential getExistingUserCreditialsOrThrow(String auth);
	
}
