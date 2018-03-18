package au.com.domain.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import au.com.domain.demo.component.IssueTrackerDAO;
import au.com.domain.demo.component.ResponseBO;
import au.com.domain.demo.domain.IssueTrackerBO;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping("/issuetrackerapp")
public class IssueTrackerController {
	private static final Logger log = Logger.getLogger("IssueTrackerController");
	@Autowired
	IssueTrackerDAO issueTrackerDAO;
	
    @RequestMapping(value = "/issue" , method = {RequestMethod.POST } , consumes="application/json",produces="application/json") 
    public ResponseEntity<String> createIssue(@RequestBody IssueTrackerBO issue) throws Exception {
    	log.info(":: IssueTrackerApplication :: createIssue "+issue);
    	ResponseBO responseBO = issueTrackerDAO.createIssueTracker(issue);
    	
    		return new ResponseEntity<String>(mapToJson(responseBO),HttpStatus.OK);
    	       
    }
    @RequestMapping(value = "/issue/{issue_id}" , method = {RequestMethod.GET } , produces="application/json")
    @ResponseBody
    public ResponseEntity<String> fetchIssue(@PathVariable("issue_id") BigInteger id) throws Exception{
    	log.info(":: IssueTrackerApplication :: fetchIssue "+id);
    	ResponseBO responseBO = issueTrackerDAO.fetchIssue(id);
    	
    	//return new ResponseEntity<List<IssueTrackerBO>>(responseBO.getIssueTracker() , HttpStatus.valueOf(Integer.parseInt(responseBO.getCode())));
    	return new ResponseEntity<String>(mapToJson(responseBO.getIssueTracker()) , HttpStatus.valueOf(Integer.parseInt(responseBO.getCode())));
    	
        
    }
    @RequestMapping(value = "/issue/{issue_id}" , method = {RequestMethod.PUT } , consumes="application/json",produces="application/json")
    @ResponseBody
    public ResponseEntity<String> updateIssue(@RequestBody IssueTrackerBO issue , @PathVariable("issue_id") BigInteger id) throws Exception {
    	log.info(":: IssueTrackerApplication :: updateIssue "+id);
    	log.info(":: IssueTrackerApplication :: updateIssue "+issue);
    	issue.setIssue_id(id);
    	ResponseBO responseBO = issueTrackerDAO.updateIssue(issue);
    	
		return new ResponseEntity<String>(mapToJson(responseBO), HttpStatus.valueOf(Integer.parseInt(responseBO.getCode())));
        
    }
    @RequestMapping(value = "/issue/{issue_id}" , method = {RequestMethod.DELETE },produces="application/json") 
    @ResponseBody
    public ResponseEntity<String> deleteIssue(@PathVariable("issue_id") BigInteger  id) throws Exception {
    	log.info(":: IssueTrackerApplication :: deleteIssue "+id);
          ResponseBO responseBO = issueTrackerDAO.deleteIssue(id);
    	
    	return new ResponseEntity<String>(mapToJson(responseBO), HttpStatus.valueOf(Integer.parseInt(responseBO.getCode())));
        
    }
    
    @RequestMapping(value = {"/filter/{filter}","/filter/{filter}/sort/{sort}/pagination/{page}" ,"/sort/{sort}", "/sort/{sort}/pagination/{page}",
    		"/filter/{filter}/sort/{sort}","/pagination/{page}"} , method = {RequestMethod.GET },produces="application/json") 
    public ResponseEntity<String> getFilters(@MatrixVariable(pathVar="filter") Map<String , String> filterVars ,
    		@MatrixVariable(pathVar="sort") Map<String , String> sortVars , @MatrixVariable(pathVar="page") Map<String , String> pageVars) throws Exception {
    	log.info(":: IssueTrackerApplication :: getFilters "+filterVars);
    	log.info(":: IssueTrackerApplication :: getFilters "+sortVars);
    	log.info(":: IssueTrackerApplication :: getFilters "+pageVars);
    	
    	ResponseBO responseBO = issueTrackerDAO.fetchBasedOnQuery(filterVars , sortVars , pageVars);
    	return new ResponseEntity<String>(mapToJson(responseBO.getIssueTracker()) , HttpStatus.valueOf(Integer.parseInt(responseBO.getCode())));
    }
    
    private String mapToJson(Object object) throws Exception{
    	ObjectMapper mapper =  new ObjectMapper();
    	return mapper.writeValueAsString(object);
    	
    }

}