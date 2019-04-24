package com.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 
 * @Author Ruan
 * 
 * 测试数据层
 * 
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(value="dev")
public class TestDao {

//	@Autowired
//	private UserRepository userRepository;

	@Test
	public void testUserRepository() {
//		System.out.println("id="+userRepository.getIdById(1038850135150080105L));
//		System.out.println("user="+userRepository.getById(1038850135150080105L));
	}
	
}