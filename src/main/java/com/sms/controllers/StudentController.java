package com.sms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sms.beans.Student;
import com.sms.dao.StudentDao;
import com.sms.validations.Validations;

@Controller
public class StudentController {
	
	@Autowired  
    StudentDao studentDao; 
	
	@Autowired
	Validations validations;
     
    @RequestMapping("/studentform")  
    public String showform(Model m){  
    	m.addAttribute("command", new Student());
    	return "studentform"; 
    } 
    
    @RequestMapping(value="/save",method = RequestMethod.POST)  
    public String save(@ModelAttribute("Student") Student student, RedirectAttributes redirectAtt){
    	boolean isExistingUser = validations.duplicateCheck(student.getStudentId());
    	if(isExistingUser == true) {
    		redirectAtt.addFlashAttribute("error", "Entered Student ID already exists in our Records. Please Enter a different Student ID");
    		redirectAtt.addFlashAttribute("student", student);
    		return "redirect:/studentform";
    	}else if(!(student.getEmail().contains("@")) || !(student.getEmail().toLowerCase().contains(".com"))) {
    		redirectAtt.addFlashAttribute("error", "Please Enter a valid Email ID");
    		redirectAtt.addFlashAttribute("student", student);
    		return "redirect:/studentform";
    	}else {
        studentDao.save(student);  
        return "redirect:/viewstudent";
    	}
    }  
       
    @RequestMapping("/viewstudent")  
    public String viewemp(Model m){  
        List<Student> list=studentDao.getStudents();  
        m.addAttribute("list",list);
        return "viewstudent";  
    } 
    
    @RequestMapping(value="/editstudent/{id}")  
    public String edit(@PathVariable int id, Model m){  
        Student student=studentDao.getStudentById(id);  
        m.addAttribute("command",student);
        return "studenteditform";  
    } 
    
    @RequestMapping(value="/editsave",method = RequestMethod.POST)  
    public String editsave(@ModelAttribute("Student") Student student, RedirectAttributes redirectAtt){ 
    	if(!(student.getEmail().contains("@")) || !(student.getEmail().contains(".com"))) {
    		redirectAtt.addFlashAttribute("error", "Please Enter a valid Email ID");
    		String url = "/editstudent/"+student.getStudentId();
    		return "redirect:"+url+".do";
    	}else {
    		studentDao.update(student);  
            return "redirect:/viewstudent";
    	}
    } 
    
    @RequestMapping(value="/deletestudent/{id}",method = RequestMethod.GET)  
    public String delete(@PathVariable int id){  
        studentDao.delete(id);  
        return "redirect:/viewstudent";  
    } 

}
