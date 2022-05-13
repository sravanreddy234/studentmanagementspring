package com.sms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sms.beans.Student;

public class StudentDao {

	JdbcTemplate template;  
	  
	public void setTemplate(JdbcTemplate template) {  
	    this.template = template;  
	}  
	public int save(Student student){  
	    String sql="INSERT INTO STUDENT (studentId, name, email, country, contactno) values('"+student.getStudentId()+"' ,'"+student.getName()+"','"+student.getEmail()+"','"+student.getCountry()+"','"+student.getContactno()+"')"; 
			return template.update(sql);
	}  
	public int update(Student student){  
	    String sql="update STUDENT set name='"+student.getName()+"', email='"+student.getEmail()+"', country='"+student.getCountry()+"', contactno='"+student.getContactno()+"' where studentid="+student.getStudentId()+"";  
	    return template.update(sql);  
	}  
	public int delete(int id){  
	    String sql="delete from STUDENT where STUDENTID="+id+"";  
	    return template.update(sql);  
	}  
	public Student getStudentById(int id){  
	    String sql="select * from STUDENT where STUDENTID=?";  
	    return template.queryForObject(sql, new Object[]{id},new BeanPropertyRowMapper<Student>(Student.class));  
	}  
	public List<Student> getStudents(){  
	    return template.query("select * from STUDENT",new RowMapper<Student>(){  
	        public Student mapRow(ResultSet rs, int row) throws SQLException {  
	            Student student=new Student();
	            student.setStudentId(rs.getInt(1));
	            student.setName(rs.getString(2));
	            student.setEmail(rs.getString(3));
	            student.setCountry(rs.getString(4));
	            student.setContactno(rs.getString(5)); 
	            return student;  
	        }  
	    });  
	} 
}
