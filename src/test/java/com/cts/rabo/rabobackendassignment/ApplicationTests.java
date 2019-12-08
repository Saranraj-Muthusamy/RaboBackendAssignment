package com.cts.rabo.rabobackendassignment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

	private String validXmlSrc = "<records>\r\n" + "<record reference=\"167875\">\r\n"
			+ "  <accountNumber>NL93ABNA0585619023</accountNumber>\r\n"
			+ "  <description>Tickets from Erik de Vries</description>\r\n" + "  <startBalance>5429</startBalance>\r\n"
			+ "  <mutation>-939</mutation>\r\n" + "  <endBalance>4490</endBalance>\r\n" + "  </record>\r\n"
			+ "  </records>";
	private String invalidXmlSrc = "<records>\r\n" + "<record reference=\"130498\">\r\n"
			+ "    <accountNumber>NL69ABNA0433647324</accountNumber>\r\n"
			+ "    <description>Tickets for Peter Theuß</description>\r\n" + "    <startBalance>26.9</startBalance>\r\n"
			+ "    <mutation>-18.78</mutation>\r\n" + "    <endBalance>8.12</endBalance>\r\n" + "  </record>\r\n"
			+ "  <record reference=\"130498\">\r\n" + "    <accountNumber>NL93ABNA0585619023</accountNumber>\r\n"
			+ "    <description>Tickets from Erik de Vries</description>\r\n"
			+ "    <startBalance>5429</startBalance>\r\n" + "    <mutation>-939</mutation>\r\n"
			+ "    <endBalance>6368</endBalance>\r\n" + "  </record>\r\n" + "  </records>";

	private String validCsvSrc = "Reference,AccountNumber,Description,Start Balance,Mutation,End Balance\r\n"
			+ "194261,NL91RABO0315273637,Clothes from Jan Bakker,21.6,-41.83,-20.23";

	private String InvalidCsvSrc = "Reference,AccountNumber,Description,Start Balance,Mutation,End Balance\r\n"
			+ "194261,NL91RABO0315273637,Clothes from Jan Bakker,21.6,-41.83,-20.23\r\n"
			+ "112806,NL27SNSB0917829871,Clothes for Willem Dekker,91.23,-15.57,106.8";
		
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testValidXMLSource() throws Exception {

		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "transaction.xml", "text/xml",
				validXmlSrc.getBytes());
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/upload").file(mockMultipartFile);
		this.mockMvc.perform(builder).andExpect(ok).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testInValidXMLSource() throws Exception {

		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "transaction.xml", "text/xml",
				invalidXmlSrc.getBytes());
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/upload").file(mockMultipartFile);
		this.mockMvc.perform(builder).andExpect(ok).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void testValidCSVSource() throws Exception {

		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "transaction.csv", "text/csv",
				validCsvSrc.getBytes());
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/upload").file(mockMultipartFile);
		this.mockMvc.perform(builder).andExpect(ok).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testInValidCSVSource() throws Exception {

		ResultMatcher ok = MockMvcResultMatchers.status().isOk();
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "transaction.csv", "text/csv",
				InvalidCsvSrc.getBytes());
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/upload").file(mockMultipartFile);
		this.mockMvc.perform(builder).andExpect(ok).andDo(MockMvcResultHandlers.print());
	}
}
