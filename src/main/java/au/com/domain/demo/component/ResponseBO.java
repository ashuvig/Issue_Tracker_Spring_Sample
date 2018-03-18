package au.com.domain.demo.component;

import java.io.Serializable;
import java.util.List;

import au.com.domain.demo.domain.IssueTrackerBO;

public class ResponseBO implements Serializable {
  private String code;
  private String transactionId;
  private String message;
  private List<IssueTrackerBO> issueTracker;
  
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}
public String getTransactionId() {
	return transactionId;
}
public void setTransactionId(String transactionId) {
	this.transactionId = transactionId;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public List<IssueTrackerBO> getIssueTracker() {
	return issueTracker;
}
public void setIssueTracker(List<IssueTrackerBO> issueTracker) {
	this.issueTracker = issueTracker;
}
  

}
