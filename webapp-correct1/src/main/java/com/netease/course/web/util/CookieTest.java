package com.netease.course.web.util;

import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ui.ModelMap;
import com.netease.course.web.dao.JdbcDao;
import com.netease.course.web.meta.Person;

public class CookieTest {
	
	public boolean cookieTest(HttpServletRequest req,HttpServletResponse rsp,ModelMap map){
	String userName=null;
	String password=null;	
	int logBuf=0;
	
	Cookie[] cookies=req.getCookies();
	if(cookies!=null){
		for(Cookie cook:cookies){
			if(cook.getName().equals("userName")){
				userName=cook.getValue();
			}
			if(cook.getName().equals("pwd")){
				password=cook.getValue();
			}
		}	
	}
	
	ApplicationContext context=new ClassPathXmlApplicationContext("application-context.xml");
	JdbcDao dao=context.getBean("jdbcDao",JdbcDao.class);
	if(userName!=null){				
		List<Person> persons=dao.getPersonList();
		for(Person per:persons){
			if(userName.equals(per.getUsername())&&password.equals(per.getPassword())){
				logBuf=1;
			}
		}
	}	
	
	if(logBuf==1){	
		Person user=dao.getPersonByName(userName);
		map.addAttribute("user",user);
		((ConfigurableApplicationContext)context).close();
		return true;
	}else{		
		((ConfigurableApplicationContext)context).close();
		return false;
	}
	
	}
}
