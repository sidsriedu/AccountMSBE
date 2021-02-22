package com.cg;

import java.util.List;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cg.entities.WalletUser;
import com.cg.services.AccountServiceImpl;
import com.cg.services.IAccountService;
import com.cg.services.IUserService;
import com.cg.services.UserServiceImpl;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Import({UserServiceImpl.class,AccountServiceImpl.class})
class UserServiceTests {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	EntityManager entityManager;
	
	@Test
	public void testAddUser() {
		WalletUser user = new WalletUser();
		user.setUserName("Siddhant");
		user.setPassword("sidsri");
		user.setPhoneNumber("12345544321"); 
		WalletUser result = userService.addUser(user);
		List<WalletUser> fetched = entityManager.createQuery("from WalletUser").getResultList();
        WalletUser expected = fetched.get(0);
        Assertions.assertEquals(expected, result);
	}
	
	@Test
	public void testGetUser() {
		WalletUser user = new WalletUser();
		user.setUserName("Siddhant");
		user.setPassword("sidsri");
		user.setPhoneNumber("12345544321");
		user = entityManager.merge(user);
		int id = user.getUserId();
		WalletUser result = userService.getUser(id);
		Assertions.assertEquals(user, result);
	}
	
	@Test
	public void testGetAllUser() {
		List<WalletUser> expected = entityManager.createQuery("from WalletUser").getResultList();
        List<WalletUser> result = userService.getAllUsers();
        Assertions.assertEquals(expected, result);
	}
	
	@Test
	public void testChangePassword() {
		WalletUser user = new WalletUser();
		user.setUserName("Siddhant");
		user.setPassword("sidsri");
		user.setPhoneNumber("12345544321"); 
		user = entityManager.merge(user);
		int id = user.getUserId();
		WalletUser expected = new WalletUser();
		expected.setUserId(id);
		expected.setUserName("Siddhant");
		expected.setPassword("abcdef");
		expected.setPhoneNumber("12345544321");
		WalletUser result = userService.changePassword(id,expected);
        Assertions.assertEquals(expected, result);
	}

}
