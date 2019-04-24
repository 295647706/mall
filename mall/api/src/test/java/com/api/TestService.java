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
 * 测试逻辑层
 * 
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(value="dev")
public class TestService {
	
//	@Autowired
//	private UserService userService;
	
	@Test
	public void test()throws Exception{
//		userService.getIdById(1038850135150080105L);
	}

}