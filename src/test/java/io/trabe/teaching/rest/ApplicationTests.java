package io.trabe.teaching.rest;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import io.trabe.teaching.rest.controller.rest.external.common.AccountApiController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTests {

	@Autowired
	private AccountApiController accountApiController;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void contextLoads() {
		assertThat(accountApiController).isNotNull();
	}

	@Test
	public void getAccount() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(Calendar.getInstance().getTime());
		this.mockMvc.perform(get("/api/public/1/users/marcos/accounts/1").header("Accept-Language","es"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().json("{\"id\":1,\"code\":\"ES43INGB2596749386\",\"description\":\"Cuenta corriente\",\"balance\":122.5,\"creationDate\":\"" + date +  "\",\"kind\":\"CHECKING\"}"));
	}

}