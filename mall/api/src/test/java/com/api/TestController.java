package com.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * 
 * @Author Ruan
 * 
 * 测试控制层
 * 
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(value="dev")
@AutoConfigureMockMvc
public class TestController {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testControllerMethods() throws Exception {
		//无参数get请求
//		mockMvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
//				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
		
		//带参数get请求
//		mockMvc.perform(MockMvcRequestBuilders.get("/")
//				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).param("id", "113"))
//				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
		
		//带参数post请求
//		JSONObject paramJson = new JSONObject();
//		paramJson.put("userId", 1038850135150080105L);
		mockMvc.perform(MockMvcRequestBuilders.post("/test/jwttoken").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
//				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(paramJson.toString()))//@RequestBody：接收application/json参数，并封装成对象
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).param("userId", "1038850135150080105"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
	}

}