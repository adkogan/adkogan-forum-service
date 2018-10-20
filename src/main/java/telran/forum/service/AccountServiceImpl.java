package telran.forum.service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.function.Supplier;

import javax.swing.text.html.parser.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import telran.forum.configuration.AccountConfiguration;

import telran.forum.configuration.AccountUserCredential;
import telran.forum.dao.UserAccountRepository;
import telran.forum.domain.UserAccount;
import telran.forum.dto.UserProfileDTO;
import telran.forum.dto.UserRegisterDTO;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	UserAccountRepository userRepository;

	@Autowired
	AccountConfiguration accountConfiguration;

	@Override
	public UserProfileDTO addUser(UserRegisterDTO userRegDto, String auth) {

		AccountUserCredential credentials = accountConfiguration.tokenDecode(auth);// ???

		if (userRepository.existsById(credentials.getLogin())) {
			throw new UserExistException();
		}

		UserAccount userAccount = UserAccount.builder().id(credentials.getLogin()).password(credentials.getPassword())
				.firstName(userRegDto.getFirstName()).lastName(userRegDto.getLastName()).role("User")
				.expdate(LocalDateTime.now().plusDays(accountConfiguration.getExpPeriod())).build();

		userRepository.save(userAccount);

		return new UserProfileDTO(credentials.getLogin(), userRegDto.getFirstName(), userRegDto.getLastName());
	}

	@Override
	public void deleteUser(String auth) {
		AccountUserCredential credentials = getExistingUserCreditialsOrThrow(auth);

		userRepository.deleteById(credentials.getLogin());
	}

	@Override
	public AccountUserCredential getExistingUserCreditialsOrThrow(String auth) {
		AccountUserCredential credentials = accountConfiguration.tokenDecode(auth);// ???

		return userRepository.findById(credentials.getLogin())
				.filter(u -> u.getPassword().equals(credentials.getPassword())).map(o -> credentials)
				.orElseThrow(() -> new ForbidenException());

	}

	@Override
	public UserProfileDTO updateUser(UserProfileDTO userProfDTO, String auth) {
		AccountUserCredential credentials = getExistingUserCreditialsOrThrow(auth);

		UserAccount userAccount = UserAccount.builder().id(credentials.getLogin()).password(credentials.getPassword())
				.firstName(userProfDTO.getFirstName()).lastName(userProfDTO.getLastName()).role("User")
				.expdate(LocalDateTime.now().plusDays(accountConfiguration.getExpPeriod())).build();

		userRepository.save(userAccount);

		return userProfDTO;

	}

	@Override
	public UserProfileDTO changePasswordUser(String password, String auth) {
		AccountUserCredential credentials = getExistingUserCreditialsOrThrow(auth);
		
		Optional<UserAccount> account = userRepository.findById(credentials.getLogin());
		return account
			.map(a-> a.withPassword(password))
			.map(a -> {
				userRepository.save(a);
				return new UserProfileDTO(
						credentials.getLogin(),
						a.getFirstName(),
						a.getLastName()
				);
			})
			.orElseThrow(() -> new NotFoundException());
	}
}