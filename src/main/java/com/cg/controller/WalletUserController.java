package com.cg.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.dto.TransactionDto;
import com.cg.dto.WalletAccountDto;
import com.cg.dto.WalletUserDto;
import com.cg.entities.WalletAccount;
import com.cg.entities.WalletUser;
import com.cg.exceptions.AccountDoesNotExistsException;
import com.cg.exceptions.InsufficientFundsException;
import com.cg.exceptions.InvalidCredentialsException;
import com.cg.exceptions.UserAlreadyExistsException;
import com.cg.exceptions.UserDoesNotExistsException;
import com.cg.services.AccountServiceImpl;
import com.cg.services.IAccountService;
import com.cg.services.IUserService;
import com.cg.services.UserServiceImpl;

//@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/users")
public class WalletUserController {

	private static final Logger log = LoggerFactory.getLogger(WalletUserController.class); 
	
	@Autowired
	private IUserService userService;
	@Autowired
	private IAccountService accountService;
	
	@PostMapping("/add")
	ResponseEntity<WalletUserDto> addUser(@RequestBody WalletUserDto userDto){
		WalletUser user = new WalletUser();
		user.setUserName(userDto.getUserName());
		user.setPassword(userDto.getPassword());
		user.setPhoneNumber(userDto.getPhoneNumber());
		WalletUser user1 = userService.addUser(user);
		WalletUserDto userDto1 = new WalletUserDto();
		userDto1.setUserId(user1.getUserId());
		userDto1.setUserName(user1.getUserName());
		userDto1.setPassword(user1.getPassword());
		userDto1.setPhoneNumber(user1.getPhoneNumber());
		userDto1.setAccountId(user1.getWalletAccount().getAccountId());
		userDto1.setAccountBalance(user1.getWalletAccount().getAccountBalance());
		userDto1.setStatus(user1.getWalletAccount().getStatus());
		ResponseEntity<WalletUserDto> response = new ResponseEntity<WalletUserDto>(userDto1,HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/{userId}")
	ResponseEntity<WalletUserDto> getUser(@PathVariable("userId") int userId){
		WalletUser user1 = userService.getUser(userId);
		WalletUserDto userDto = new WalletUserDto();
		userDto.setUserId(user1.getUserId());
		userDto.setUserName(user1.getUserName());
		userDto.setPassword(user1.getPassword());
		userDto.setPhoneNumber(user1.getPhoneNumber());
		ResponseEntity<WalletUserDto> response = new ResponseEntity<WalletUserDto>(userDto,HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/{userId}/account")
	ResponseEntity<WalletAccountDto> getAccount(@PathVariable("userId") int userId){
		WalletAccount account = accountService.getAccount(userId);
		WalletAccountDto walletDto = new WalletAccountDto();
		walletDto.setAccountId(account.getAccountId());
		walletDto.setAccountBalance(account.getAccountBalance());
		walletDto.setStatus(account.getStatus());
		ResponseEntity<WalletAccountDto> response = new ResponseEntity<WalletAccountDto>(walletDto,HttpStatus.OK);
		return response;
	}
	
	@GetMapping("")
	ResponseEntity<List<WalletUserDto>> getAllUsers(){
		List<WalletUser> users = userService.getAllUsers();
		List<WalletUserDto> userDtos = new ArrayList<WalletUserDto>();
		for(WalletUser walletUser : users) {
			WalletUserDto walletDto = new WalletUserDto();
			walletDto.setUserId(walletUser.getUserId());
			walletDto.setUserName(walletUser.getUserName());
			walletDto.setPhoneNumber(walletUser.getPhoneNumber());
			walletDto.setAccountId(walletUser.getWalletAccount().getAccountId());
			walletDto.setAccountBalance(walletUser.getWalletAccount().getAccountBalance());
			walletDto.setStatus(walletUser.getWalletAccount().getStatus());
			userDtos.add(walletDto);
		}
		ResponseEntity<List<WalletUserDto>> response = new ResponseEntity<List<WalletUserDto>>(userDtos,HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/getalluserids")
	ResponseEntity<List<Integer>> getAllUserIds(){
		List<WalletUser> users = userService.getAllUsers();
		List<Integer> userIds = new ArrayList<Integer>();
		for(WalletUser walletUser : users) {
			userIds.add(walletUser.getUserId());
		}
		ResponseEntity<List<Integer>> response = new ResponseEntity<List<Integer>>(userIds,HttpStatus.OK);
		return response;
	}
	
	@PutMapping("/{userId}/changepassword")
	ResponseEntity<Void> changePassword(@PathVariable("userId") int userId,@RequestBody WalletUserDto userDto){
		WalletUser user = new WalletUser();
		user.setPhoneNumber(userDto.getPhoneNumber());
		user.setPassword(userDto.getPassword());
		user.setUserName(userDto.getUserName());
		userService.changePassword(userId, user);
		ResponseEntity<Void> response = new ResponseEntity<Void>(HttpStatus.OK);
		return response;
	}
	
	@PutMapping("/{accountId}/account/addmoney")
	ResponseEntity<WalletAccountDto> addMoney(@PathVariable("accountId") int accountId,@RequestBody TransactionDto transactionDto){
		WalletAccount account = accountService.addMoney(accountId, transactionDto);
		WalletAccountDto walletDto = new WalletAccountDto();
		walletDto.setAccountId(account.getAccountId());
		walletDto.setAccountBalance(account.getAccountBalance());
		walletDto.setStatus(account.getStatus());
		ResponseEntity<WalletAccountDto> response = new ResponseEntity<WalletAccountDto>(walletDto,HttpStatus.OK);
		return response;
	}
	
	@PutMapping("/{accountId}/account/sendmoney")
	ResponseEntity<WalletAccountDto> sendMoney(@PathVariable("accountId") int accountId,@RequestBody TransactionDto transactionDto){
		WalletAccount account = accountService.sendMoney(accountId, transactionDto);
		WalletAccountDto walletDto = new WalletAccountDto();
		walletDto.setAccountId(account.getAccountId());
		walletDto.setAccountBalance(account.getAccountBalance());
		walletDto.setStatus(account.getStatus());
		ResponseEntity<WalletAccountDto> response = new ResponseEntity<WalletAccountDto>(walletDto,HttpStatus.OK);
		return response;
	}
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<String> handleExceptionUserAlreadyExists(UserAlreadyExistsException exception){
		 log.error("User Already Exists Exception",exception);
		 String msg = exception.getMessage();
	     ResponseEntity<String> response = new ResponseEntity<>(msg, HttpStatus.NOT_ACCEPTABLE);
	     return response;
	}
	
	@ExceptionHandler(AccountDoesNotExistsException.class)
	public ResponseEntity<String> handleExceptionAccountDoesNotExists(AccountDoesNotExistsException exception){
		 log.error("Account Does Not Exists Exception",exception);
		 String msg = exception.getMessage();
	     ResponseEntity<String> response = new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
	     return response;
	}
	
	@ExceptionHandler(InsufficientFundsException.class)
	public ResponseEntity<String> handleExceptionInsufficientFunds(InsufficientFundsException exception){
		 log.error("Insufficient Funds Exception",exception);
		 String msg = exception.getMessage();
	     ResponseEntity<String> response = new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
	     return response;
	}
	
	@ExceptionHandler(UserDoesNotExistsException.class)
	public ResponseEntity<String> handleExceptionUserDoesNotExists(UserDoesNotExistsException exception){
		 log.error("User Does Not Exists Exception",exception);
		 String msg = exception.getMessage();
	     ResponseEntity<String> response = new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
	     return response;
	}
	
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<String> handleExceptionInvalidCredentials(InvalidCredentialsException exception){
		 log.error("Invalid Credentials Exception",exception);
		 String msg = exception.getMessage();
	     ResponseEntity<String> response = new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
	     return response;
	}
	
	@ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleAll(Throwable ex) {
        log.error("exception caught", ex);
        String msg = ex.getMessage();
        ResponseEntity<String> response = new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
        return response;
    }
}



//@Value("${transactionServiceUrl}")
//private String transactionUrl;

//ResponseEntity<TransactionDto[]> getTransactions() {
//	String url = transactionUrl + "/get";
//	ResponseEntity<TransactionDto[]> response = restTemplate.getForEntity(url, TransactionDto[].class);
//	return response;
//}
//
//ResponseEntity<TransactionDto> getTransaction() {
//	String url = transactionUrl + "/get";
//	TransactionDto transactions = restTemplate.getForObject(url, TransactionDto.class);
//	ResponseEntity<TransactionDto> response = new ResponseEntity<TransactionDto>(transactions,HttpStatus.OK);
//	return response;
//}
//
//@Autowired
//private RestTemplate restTemplate;
