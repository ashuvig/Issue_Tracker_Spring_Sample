package au.com.domain.demo.domain;

import java.io.Serializable;
import java.math.BigInteger;

public class IssueTrackerBO implements Serializable{
	private BigInteger issue_id;
	private String issue_title;
	private String issue_description;
	private String issue_status;
	private String issue_reporter;
	private String issue_assignee;
	private String issue_created;
	private String issue_completed;
	public BigInteger getIssue_id() {
		return issue_id;
	}
	public void setIssue_id(BigInteger issue_id) {
		this.issue_id = issue_id;
	}
	public String getIssue_title() {
		return issue_title;
	}
	public void setIssue_title(String issue_title) {
		this.issue_title = issue_title;
	}
	public String getIssue_description() {
		return issue_description;
	}
	public void setIssue_description(String issue_description) {
		this.issue_description = issue_description;
	}
	public String getIssue_status() {
		return issue_status;
	}
	public void setIssue_status(String issue_status) {
		this.issue_status = issue_status;
	}
	public String getIssue_reporter() {
		return issue_reporter;
	}
	public void setIssue_reporter(String issue_reporter) {
		this.issue_reporter = issue_reporter;
	}
	public String getIssue_assignee() {
		return issue_assignee;
	}
	public void setIssue_assignee(String issue_assignee) {
		this.issue_assignee = issue_assignee;
	}
	public String getIssue_created() {
		return issue_created;
	}
	public void setIssue_created(String issue_created) {
		this.issue_created = issue_created;
	}
	public String getIssue_completed() {
		return issue_completed;
	}
	public void setIssue_completed(String issue_completed) {
		this.issue_completed = issue_completed;
	}
	@Override
	public String toString() {
		return "IssueTrackerBO [issue_id=" + issue_id + ", issue_title=" + issue_title + ", issue_description="
				+ issue_description + ", issue_status=" + issue_status + ", issue_reporter=" + issue_reporter
				+ ", issue_assignee=" + issue_assignee + ", issue_created=" + issue_created + ", issue_completed="
				+ issue_completed + "]";
	}
	
	

}
