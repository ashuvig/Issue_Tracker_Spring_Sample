package issuetrack;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Any;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import au.com.domain.demo.component.IssueTrackerDAO;
import au.com.domain.demo.component.ResponseBO;
import au.com.domain.demo.controller.IssueTrackerController;
import au.com.domain.demo.domain.IssueTrackerBO;
import junit.framework.Assert;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes={IssueTrackerDAO.class})

public class IssueTrackerDAOTest {
	@Mock
	 private JdbcTemplate jdbcTemplate;
	@InjectMocks
	private IssueTrackerDAO mockIssueTrackerDAO ;//= new IssueTrackerDAO();
	 
	 private ResponseBO responseBO;
	 private IssueTrackerBO issueTrackerBO;
	 private String sql = "SELECT ID , title , description , status , reporter , assignee , created , completed   FROM ISSUE_TRACKER.ISSUE WHERE ID = ?"; 
	
	 @Before
	    public void setUp() throws Exception {
		 //MockitoAnnotations.initMocks(this);
		 
		 
		 responseBO = new ResponseBO();
		  issueTrackerBO =  new IssueTrackerBO();
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
	                	        
	        }
	 
	 @Test
	    public void fetchIssue() throws Exception {
		 Mockito.when(jdbcTemplate.queryForList(sql , BigInteger.valueOf(100L) )).thenReturn(getdata());
		
		ResponseBO responseBO =  mockIssueTrackerDAO.fetchIssue(BigInteger.valueOf(100L));
		Assert.assertNotNull(responseBO);
		Assert.assertEquals(HttpStatus.OK.toString(), responseBO.getCode());
	 }
	 
	 @Test
	    public void createIssueTracker() throws Exception {
		 String sql = "INSERT INTO ISSUE_TRACKER.ISSUE " +
					"(title, description, status, reporter, created) VALUES (?,?,?,?,?);";
		 Mockito.when(jdbcTemplate.update(sql , issueTrackerBO.getIssue_title(),issueTrackerBO.getIssue_description(),issueTrackerBO.getIssue_status(),
				                           issueTrackerBO.getIssue_reporter(),issueTrackerBO.getIssue_created() ) ).thenReturn(1);
		
		ResponseBO responseBO =  mockIssueTrackerDAO.createIssueTracker(issueTrackerBO);
		Assert.assertNotNull(responseBO);
		Assert.assertEquals(HttpStatus.OK.toString(), responseBO.getCode());
	 }
	 
	 private List<Map<String,Object>> getdata(){
			 
		 Map records = new HashMap<>();
		 records.put("ID", Long.parseLong("100"));
		 records.put("title", "Title Value");
		 records.put("description", "Description Value");
		 records.put("status", "Done");
		 records.put("reporter", "1");
		 records.put("assignee", "1");
		 records.put("created", new Date(System.currentTimeMillis()));
		 records.put("completed", new Date(System.currentTimeMillis()));
		 
		 return Arrays.asList(records);
		 
	 }

}
