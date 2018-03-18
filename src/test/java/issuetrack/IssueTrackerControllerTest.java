package issuetrack;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import au.com.domain.demo.component.IssueTrackerDAO;
import au.com.domain.demo.component.ResponseBO;
import au.com.domain.demo.controller.IssueTrackerController;
import au.com.domain.demo.domain.IssueTrackerBO;
import ch.qos.logback.core.status.Status;

@RunWith(SpringJUnit4ClassRunner.class)
//@WebMvcTest(value = IssueTrackerController.class, secure = false)
@SpringBootTest(classes={IssueTrackerController.class})
@AutoConfigureMockMvc 
@EnableWebMvc
public class IssueTrackerControllerTest {

	 @Autowired
	    private MockMvc mockMvc;

	    @MockBean
	    private IssueTrackerDAO issueTrackerDAO;
	    
	   
	    

	    @Test
	    public void fetchIssue() throws Exception {
	        

	       Mockito.when(issueTrackerDAO.fetchIssue(BigInteger.valueOf(5))).thenReturn(getResponse());


	        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/issuetrackerapp/issue/{id}" , 5).accept("application/json");
	        	    	
	        
	          
	      
	        mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());


	        

	    }

	    @Test
	    public void updateIssue() throws Exception {
	    	Mockito.when(issueTrackerDAO.updateIssue(Mockito.any(IssueTrackerBO.class))).thenReturn(getResponse());
	    	RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/issuetrackerapp/issue/{id}",5 ).content(mapToJson(getIssueResponse())).contentType(MediaType.APPLICATION_JSON);
	        	    	
	        mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());
	    }

	    @Test
	    public void deleteIssue() throws Exception {
	    	Mockito.when(issueTrackerDAO.deleteIssue(BigInteger.valueOf(5))).thenReturn(getResponse());


	        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/issuetrackerapp/issue/{id}" , 5).accept("application/json");
	        	    	
	        
	          
	      
	        mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());
	    }
	    @Test
	    public void createIssue() throws Exception {
	    	
	    	

	    	Mockito.when(issueTrackerDAO.createIssueTracker(Mockito.any(IssueTrackerBO.class))).thenReturn(getResponse());
	    	RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/issuetrackerapp/issue" ).content(mapToJson(getIssueResponse())).contentType(MediaType.APPLICATION_JSON);
	        	    	
	        mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());
	        	       
	        
	        
	    	
	    }

	    private ResponseBO getResponse(){
	    	
	        ResponseBO responseBO = new ResponseBO();
	        IssueTrackerBO issueTrackerBO =  new IssueTrackerBO();
	        issueTrackerBO.setIssue_assignee("2");
	        issueTrackerBO.setIssue_completed("2018-03-03");
	        issueTrackerBO.setIssue_created("2018-03-03");
	        issueTrackerBO.setIssue_description("Test Sample");
	        issueTrackerBO.setIssue_id(BigInteger.valueOf(100l));
	        issueTrackerBO.setIssue_reporter(String.valueOf("2"));
	        issueTrackerBO.setIssue_status("Done");
	        issueTrackerBO.setIssue_title("Sample Title");

	        responseBO.setCode(HttpStatus.OK.toString());
	        responseBO.setMessage("Successfully Done");
	        responseBO.setTransactionId("Transaction_"+Math.random());
	        responseBO.setIssueTracker(Arrays.asList(issueTrackerBO));

	        
	        return responseBO;
	        
	    }
	    
private IssueTrackerBO getIssueResponse(){
	    	
	        
	        IssueTrackerBO issueTrackerBO =  new IssueTrackerBO();
	        issueTrackerBO.setIssue_assignee("2");
	        issueTrackerBO.setIssue_completed("2018-03-03");
	        issueTrackerBO.setIssue_created("2018-03-03");
	        issueTrackerBO.setIssue_description("Test Sample");
	        //issueTrackerBO.setIssue_id(BigInteger.valueOf(100l));
	        issueTrackerBO.setIssue_reporter(String.valueOf("2"));
	        issueTrackerBO.setIssue_status("Done");
	        issueTrackerBO.setIssue_title("Sample Title");

	        
	        
	        return issueTrackerBO;
}
	    
	    private String mapToJson(Object object) throws Exception{
	    	ObjectMapper mapper =  new ObjectMapper();
	    	
	    	return mapper.writeValueAsString(object);
	    	
	    }
	    private byte[] mapToBytes(Object object) throws Exception{
	    	ObjectMapper mapper =  new ObjectMapper();
	    	
	    	return mapper.writeValueAsBytes(object);
	    	
	    }
	}


