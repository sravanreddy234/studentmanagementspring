package com.sms.validations;

import org.springframework.jdbc.core.JdbcTemplate;

public class Validations {
	
	JdbcTemplate template;  
	  
	public void setTemplate(JdbcTemplate template) {  
	    this.template = template;  
	} 
	
	public boolean duplicateCheck(int studentId){
		
		String sql = "SELECT count(*) FROM STUDENT WHERE studentId = ?";
	    int count = template.queryForObject(sql, new Object[] { studentId }, Integer.class);
	    return count > 0;
	}
}
