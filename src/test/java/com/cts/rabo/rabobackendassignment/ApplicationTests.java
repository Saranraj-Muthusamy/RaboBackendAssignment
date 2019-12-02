package com.cts.rabo.rabobackendassignment;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cts.rabo.rabobackendassignment.controller.AppController;

@RunWith(SpringRunner.class)
@WebMvcTest
class ApplicationTests {

	private String xmlTestData = "<records>\n" + "  <record reference=\"130498\">\n"
			+ "    <accountNumber>NL69ABNA0433647324</accountNumber>\n"
			+ "    <description>Tickets for Peter Theuß</description>\n" + "    <startBalance>26.9</startBalance>\n"
			+ "    <mutation>-18.78</mutation>\n" + "    <endBalance>8.12</endBalance>\n" + "  </record>\n"
			+ "  <record reference=\"167875\">\n" + "    <accountNumber>NL93ABNA0585619023</accountNumber>\n"
			+ "    <description>Tickets from Erik de Vries</description>\n" + "    <startBalance>5429</startBalance>\n"
			+ "    <mutation>-939</mutation>\n" + "    <endBalance>6368</endBalance>\n" + "  </record>\n"
			+ "  </records>";

	@Autowired
	private AppController controller;

	@Autowired
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

	@Test
	public void testMisleadingTransaction() throws Exception {
		
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "transaction.xml", "text/xml",
				xmlTestData.getBytes());
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/upload").file(mockMultipartFile);
		this.mockMvc.perform(builder).andExpect(ok).andDo(MockMvcResultHandlers.print());
	}	
}
