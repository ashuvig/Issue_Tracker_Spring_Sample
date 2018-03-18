package au.com.domain.demo.component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import au.com.domain.demo.domain.IssueTrackerBO;
/***
 * This class perform the DB operation using JDBC Template.
 * This class is being called from IssueTrackerController
 * @author anvi-pc
 *
 */
@Component
@Service
public class IssueTrackerDAO {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	private static final Logger log = Logger.getLogger("IssueTrackerDAO");
	
	// Creating issue in the issue tracker
    public ResponseBO createIssueTracker(IssueTrackerBO issue){
    	String transactionId = "TransactionID_"+(int )(Math.random() * 50 + 1)+"_"+System.currentTimeMillis();
		ResponseBO responseBO = new ResponseBO();
		responseBO.setCode(HttpStatus.OK.toString());
		responseBO.setTransactionId(transactionId);
		responseBO.setMessage("Success");
		responseBO.setIssueTracker(new ArrayList<IssueTrackerBO>());
		
		String sql = "INSERT INTO ISSUE_TRACKER.ISSUE " +
			"(title, description, status, reporter, created) VALUES (?,?,?,?,?);";
		
		try {				
	     jdbcTemplate.update(sql,issue.getIssue_title(),issue.getIssue_description() +"<"+ transactionId+">",issue.getIssue_status(),
				                        issue.getIssue_reporter(),issue.getIssue_created());
		} catch(Exception e){
			log.info("Exception occured while inserting data" + e.getMessage());
			responseBO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			responseBO.setMessage(e.getMessage());
		}
		
		return 	responseBO;
	}
    
 // Fetching issue from IssueTracker based on Id only
    public ResponseBO fetchIssue(BigInteger id){
    	String transactionId = "TransactionID_"+(int )(Math.random() * 50 + 1)+"_"+System.currentTimeMillis();
    	List<IssueTrackerBO> issues = new ArrayList<IssueTrackerBO>();
		ResponseBO responseBO = new ResponseBO();
		responseBO.setCode(HttpStatus.OK.toString());
		responseBO.setTransactionId(transactionId);
		responseBO.setMessage("Success");
		
		String sql = "SELECT ID , title , description , status , reporter , assignee , created , completed   FROM ISSUE_TRACKER.ISSUE WHERE ID = ?";
		
		try {	
			List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql , id);
			
			for (Map row : rows) {
				IssueTrackerBO issue = new IssueTrackerBO();
				issue.setIssue_id(BigInteger.valueOf((long)row.get("ID")));
				issue.setIssue_title((String)(row.get("title")));
				issue.setIssue_description((String)(row.get("description")));
				issue.setIssue_status((String)(row.get("status")));
				if (row.get("reporter") != null) {
				 issue.setIssue_reporter(row.get("reporter").toString());
				}
				if (row.get("assignee") != null) {
					 issue.setIssue_assignee(row.get("assignee").toString());
				}
				if (row.get("created") != null) {
					 issue.setIssue_created(row.get("created").toString());
				}
				if (row.get("completed") != null) {
					 issue.setIssue_completed(row.get("completed").toString());
				}
								
				issues.add(issue);
			}
			
			if (issues.isEmpty()){
				responseBO.setCode(HttpStatus.NO_CONTENT.toString());
				responseBO.setMessage("ID "+id+" doesn't exist");
			}
			responseBO.setIssueTracker(issues);
			
		} catch(Exception e){
			e.printStackTrace();
			log.info("Exception occured while inserting data" + e.getMessage());
			responseBO.setCode(HttpStatus.NO_CONTENT.toString());
			responseBO.setMessage(e.getMessage());
			responseBO.setIssueTracker(issues);
		}
		
		return 	responseBO;
	}
    
 // Delete issue from IssueTracker based on Id only
    public ResponseBO deleteIssue(BigInteger id){
    	String transactionId = "TransactionID_"+(int )(Math.random() * 50 + 1)+"_"+System.currentTimeMillis();
    	
		ResponseBO responseBO = new ResponseBO();
		responseBO.setCode(HttpStatus.OK.toString());
		responseBO.setTransactionId(transactionId);
		responseBO.setMessage("Successfully Deleted");
		responseBO.setIssueTracker(new ArrayList<IssueTrackerBO>());
		
		String sql = "DELETE FROM ISSUE_TRACKER.ISSUE WHERE ID = ?";
		
		try {				
			int update = jdbcTemplate.update(sql , id);
			if (! (update > 0) ) {
				responseBO.setCode(HttpStatus.NO_CONTENT.toString());
				responseBO.setMessage("ID "+id+" doesn't exist");
				
			}
		} catch(Exception e){
			log.info("Exception occured while inserting data" + e.getMessage());
			responseBO.setCode(HttpStatus.NO_CONTENT.toString());
			responseBO.setMessage(e.getMessage());
			
		}
		
		return 	responseBO;
	}
    
    
 // Modify issue in the IssueTracker based on Id only
    public ResponseBO updateIssue(IssueTrackerBO issue){
    	String transactionId = "TransactionID_"+(int )(Math.random() * 50 + 1)+"_"+System.currentTimeMillis();
    	
		ResponseBO responseBO = new ResponseBO();
		responseBO.setCode(HttpStatus.OK.toString());
		responseBO.setTransactionId(transactionId);
		responseBO.setMessage("Successfully Modified");
		responseBO.setIssueTracker(new ArrayList<IssueTrackerBO>());
		
		String sql = "UPDATE ISSUE_TRACKER.ISSUE " +
			"SET title=?, description=?, status=?, assignee=?, completed=? WHERE ID = ?";
		
		try {				
			int update = jdbcTemplate.update(sql,issue.getIssue_title(),issue.getIssue_description() +"<"+ transactionId+">",issue.getIssue_status(),
				                        issue.getIssue_assignee(),issue.getIssue_completed(),issue.getIssue_id());
			if (! (update > 0) ) {
				responseBO.setCode(HttpStatus.NO_CONTENT.toString());
				responseBO.setMessage("ID "+issue.getIssue_id()+" doesn't exist");
				
			}
		} catch(Exception e){
			log.info("Exception occured while inserting data" + e.getMessage());
			responseBO.setCode(HttpStatus.NO_CONTENT.toString());
			responseBO.setMessage(e.getMessage());
		}
		
		return 	responseBO;
	}
    
 // Fetching issue from IssueTracker based on Criteria
    public ResponseBO fetchBasedOnQuery(Map <String,String> filtercriteria , Map <String,String> Sortcriteria , Map <String,String> pageCriteria){
    	String transactionId = "TransactionID_"+(int )(Math.random() * 50 + 1)+"_"+System.currentTimeMillis();
    	List<IssueTrackerBO> issues = new ArrayList<IssueTrackerBO>();
		ResponseBO responseBO = new ResponseBO();
		responseBO.setCode(HttpStatus.OK.toString());
		responseBO.setTransactionId(transactionId);
		responseBO.setMessage("Success");
		
		String sql = "SELECT ID , title , description , status , reporter , assignee , created , completed FROM ISSUE_TRACKER.ISSUE";
		
		try {	
			if (filtercriteria != null && filtercriteria.size() > 0){
				sql = sql + " WHERE ";
				int index = 1;
				for (Map.Entry<String, String> entry : filtercriteria.entrySet())
				{
					if (index == filtercriteria.size()){
						sql = sql + entry.getKey() +"=" +entry.getValue();
					} else {
					sql = sql + entry.getKey() +"="+ entry.getValue() +" AND " ;
					}
					index++;
					
				} 
			} 
			if(Sortcriteria != null && Sortcriteria.size() > 0 ){
				sql = sql + " ORDER BY ";
				for (Map.Entry<String, String> entry : Sortcriteria.entrySet())
				{
					sql = sql + entry.getKey() +" "+entry.getValue();
										
				} 
			}
			if(pageCriteria != null && pageCriteria.size() > 0 ){
				
			}
			log.info("Final Dymanic Sql Query ==>" + sql);
			
			List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql);
			for (Map row : rows) {
				IssueTrackerBO issue = new IssueTrackerBO();
				
				issue.setIssue_id(BigInteger.valueOf((long)row.get("ID")));
				issue.setIssue_title((String)(row.get("title")));
				issue.setIssue_description((String)(row.get("description")));
				issue.setIssue_status((String)(row.get("status")));
				if (row.get("reporter") != null) {
				 issue.setIssue_reporter(row.get("reporter").toString());
				}
				if (row.get("assignee") != null) {
					 issue.setIssue_assignee(row.get("assignee").toString());
				}
				if (row.get("created") != null) {
					 issue.setIssue_created(row.get("created").toString());
				}
				if (row.get("completed") != null) {
					 issue.setIssue_completed(row.get("completed").toString());
				}
								
				issues.add(issue);
			}
			
			if (issues.isEmpty()){
				responseBO.setCode(HttpStatus.NO_CONTENT.toString());
				responseBO.setMessage("Data for transction " +transactionId+"doesn't exist");
			}
			responseBO.setIssueTracker(issues);
			
		} catch(Exception e){
			log.info("Exception occured while inserting data" + e.getMessage());
			responseBO.setCode(HttpStatus.NO_CONTENT.toString());
			responseBO.setMessage(e.getMessage());
			responseBO.setIssueTracker(issues);
		}
		
		return 	responseBO;
	}
}
