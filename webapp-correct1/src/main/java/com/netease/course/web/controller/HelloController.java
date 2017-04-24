package com.netease.course.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.netease.course.web.dao.JdbcDao;
import com.netease.course.web.meta.ListBuy;
import com.netease.course.web.meta.Person;
import com.netease.course.web.meta.Product;
import com.netease.course.web.meta.ShowProduct;
import com.netease.course.web.util.CookieTest;


@Controller
public class HelloController {			
		
		@RequestMapping(value={"/index"},method=RequestMethod.GET)
		public String index(ModelMap map,HttpServletRequest req,HttpServletResponse rsp) {
			CookieTest ct=new CookieTest();
			ct.cookieTest(req,rsp,map);
			ApplicationContext context=new ClassPathXmlApplicationContext("application-context.xml");
			JdbcDao dao=context.getBean("jdbcDao",JdbcDao.class);
			List<Product> products=dao.getProductList();
			for(Product pro :products){			
				System.out.println("isbuy:"+pro.getIsBuy());
				System.out.println("issell"+pro.getIsSell());
			}
			map.addAttribute("productList", products);
			
			((ConfigurableApplicationContext)context).close();
			return"index";		
		}
		
		@RequestMapping(value={"/type"},method=RequestMethod.GET)
		public String indextype(ModelMap map,HttpServletRequest req,HttpServletResponse rsp) {
			CookieTest ct=new CookieTest();
			ct.cookieTest(req,rsp,map);
			ApplicationContext context=new ClassPathXmlApplicationContext("application-context.xml");
			JdbcDao dao=context.getBean("jdbcDao",JdbcDao.class);
			Person per=(Person) map.get("user");
			List<Product> products;
			if(per.getUsername()=="buyer"){
			products=dao.getProductListIsbuy();
			}else{
			products=dao.getProductListIsSell();	
			}
			for(Product pro :products){
				System.out.println("type price: "+pro.getPrice());
				System.out.println("type isbuy: "+pro.getIsBuy());
				System.out.println("type issell: "+pro.getIsSell());			
			}
			map.addAttribute("productList", products);
			((ConfigurableApplicationContext)context).close();
			return"index";		
		}
		
		
		
	@RequestMapping(value={"","/","login"},method=RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value="/api/login",method=RequestMethod.POST)
	public void login(@RequestParam ("userName") String userName,
			@RequestParam("password") String password,HttpServletRequest req,
			HttpServletResponse rsp) throws IOException {		
		
		int logBuf=0;
		String jsonStr;			
		ApplicationContext context=new ClassPathXmlApplicationContext("application-context.xml");
		JdbcDao dao=context.getBean("jdbcDao",JdbcDao.class);
		List<Person> persons=dao.getPersonList();
		
		for(Person per:persons){
			if(userName.equals(per.getUsername())&&password.equals(per.getPassword())){
				logBuf=1;				
			}
		}
		((ConfigurableApplicationContext)context).close();
		
		if(logBuf==1){
			jsonStr="{\"code\":\"200\",\"message\":\"Login success!\",\"result\":\"true\"}";
			Cookie userNameCookie=new Cookie("userName",userName);
			Cookie passwordCookie=new Cookie("pwd",password);
			userNameCookie.setMaxAge(10*60);
			passwordCookie.setMaxAge(10*60);	
			userNameCookie.setPath("/");
			passwordCookie.setPath("/");
			rsp.addCookie(userNameCookie);
			rsp.addCookie(passwordCookie);
		}else{
			jsonStr="{\"code\":\"404\",\"message\":\"Login failed!\",\"result\":\"false\"}";
		}	
			rsp.getWriter().write(jsonStr);
			rsp.flushBuffer();
			rsp.getWriter().close();			
	}
	
	@RequestMapping(value="/api/delete",method=RequestMethod.POST)
	public void deleteProduct(@RequestParam("id")int id,HttpServletResponse rsp) throws IOException{
		int delBuf=0;
		String jsonStr;
		ApplicationContext context=new ClassPathXmlApplicationContext("application-context.xml");
		JdbcDao dao=context.getBean("jdbcDao",JdbcDao.class);
		delBuf=dao.deleteProduct(id);	
		((ConfigurableApplicationContext)context).close();
		if(delBuf==1){
			jsonStr="{\"code\":\"200\",\"message\":\"Delete success!\",\"result\":\"true\"}";
			
		}else{
			jsonStr="{\"code\":\"404\",\"message\":\"Delete failed!\",\"result\":\"false\"}";
		}	
			
			rsp.getWriter().write(jsonStr);
			rsp.flushBuffer();
			rsp.getWriter().close();	
	}
	
	@RequestMapping(value="/api/buy",method=RequestMethod.POST)
	public void apibuy(@RequestParam("id")int  id,@RequestParam("number")int buyNum,
			HttpServletResponse rsp) throws IOException{
		String buyTime=System.currentTimeMillis()+"";
		int buyLog=0;
		String jsonStr;
		ApplicationContext context=new ClassPathXmlApplicationContext("application-context.xml");
		JdbcDao dao=context.getBean("jdbcDao",JdbcDao.class);
		Product pro=dao.getProById(id);
		dao.updateProductSaleNum(id, buyNum);
		double total=pro.getPrice()*buyNum;
		buyLog=dao.trxById(id, buyNum, buyTime, total);
		if(buyLog==1){
			jsonStr="{\"code\":\"200\",\"message\":\"Buy success!\",\"result\":\"true\"}";
				
		}else{
			jsonStr="{\"code\":\"404\",\"message\":\"Buy failed!\",\"result\":\"false\"}";
		}	
			rsp.getWriter().write(jsonStr);
			rsp.flushBuffer();
			rsp.getWriter().close();	
		((ConfigurableApplicationContext)context).close();
	}
	@RequestMapping(value="/api/upload",method=RequestMethod.POST)
	@ResponseBody
	public ModelMap apiupload(@RequestParam("file")MultipartFile file,HttpServletRequest req,HttpServletResponse rsp,
			ModelMap map) throws IOException{
		//时间作为新文件名
		String Prename= System.currentTimeMillis()+"";	
		//取文件后缀
		String filename = file.getOriginalFilename();		
		String nameSuffix = filename.substring(filename.lastIndexOf(".")+1);
		//拼接成新文件名
		filename=Prename+"."+nameSuffix;
		//文件传输
		String localpath="C:/Users/wgsan/workspaceforjavaeee/webapp-correct1/src/main/webapp/image/";
	   File savefile=new File(localpath+filename);
	   file.transferTo(savefile);
	   
	   String address="./image/"+filename;	  
	   map.addAttribute("code", 200);
	   map.addAttribute("message","Upload success!");
	   map.addAttribute("result", address);
	   System.out.println(address);
	   return map;
	}	
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public void logout(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher=req.getRequestDispatcher("/login");
		dispatcher.forward(req, resp);		
	}
	
	@RequestMapping(value="/show",method=RequestMethod.GET)
	public String show(@RequestParam("id")int id,ModelMap map,HttpServletRequest req,
			HttpServletResponse rsp){
		CookieTest ct=new CookieTest();
		ct.cookieTest(req,rsp, map);
		ApplicationContext context=new ClassPathXmlApplicationContext("application-context.xml");
		JdbcDao dao=context.getBean("jdbcDao",JdbcDao.class);
		ShowProduct pro=dao.getShowProductById(id);
		System.out.println("isBuy : "+pro.getIsBuy());
		System.out.println("isSell : "+pro.getIsSell());
		map.addAttribute("product", pro);
		((ConfigurableApplicationContext)context).close();		
		return "show";
	}
	@RequestMapping(value="/account",method=RequestMethod.GET)
	public String account(HttpServletRequest req,HttpServletResponse rsp,ModelMap map){
		CookieTest ct=new CookieTest();
		ct.cookieTest(req,rsp,map);
		ApplicationContext context=new ClassPathXmlApplicationContext("application-context.xml");
		JdbcDao dao=context.getBean("jdbcDao",JdbcDao.class);
		List<ListBuy> buyList=dao.getBuyList();
		map.addAttribute("buyList", buyList);
		((ConfigurableApplicationContext)context).close();
		return "account";
	}
	@RequestMapping(value="/public",method=RequestMethod.GET)
	public String publi(HttpServletRequest req,HttpServletResponse rsp,ModelMap map){
		CookieTest ct=new CookieTest();
		ct.cookieTest(req,rsp, map);
		return "public";
	}
	@RequestMapping(value="/publicSubmit",method=RequestMethod.POST)
	public String publicSubmit(@RequestParam("title") String title,@RequestParam("image") String image,
			@RequestParam("detail") String detail,@RequestParam("price") double price,
			@RequestParam("summary") String summary,ModelMap map,HttpServletRequest req,
			HttpServletResponse rsp){
		CookieTest ct=new CookieTest();
		ct.cookieTest(req,rsp, map);
		int tag;
		ApplicationContext context=new ClassPathXmlApplicationContext("application-context.xml");
		JdbcDao dao=context.getBean("jdbcDao",JdbcDao.class);
		tag=dao.insertProduct(price, title, image, summary, detail);
		if(tag==1){
		Product pro=dao.getProByImage(image);
		map.addAttribute("product", pro);
		}
		((ConfigurableApplicationContext)context).close();
		return "publicSubmit";
	}
	@RequestMapping(value="/edit",method=RequestMethod.GET)
	public String edit(HttpServletRequest req,HttpServletResponse rsp,ModelMap map,
			@RequestParam("id") int id){
		CookieTest ct=new CookieTest();
		ct.cookieTest(req,rsp, map);
		ApplicationContext context=new ClassPathXmlApplicationContext("application-context.xml");
		JdbcDao dao=context.getBean("jdbcDao",JdbcDao.class);
		Product pro=dao.getProById(id);
		map.addAttribute("product", pro);
		((ConfigurableApplicationContext)context).close();
		return "edit";
	}
	@RequestMapping(value="/editSubmit",method=RequestMethod.POST)
	public String editSubmit(@RequestParam("title") String title,@RequestParam("image") String image,
			@RequestParam("detail") String detail,@RequestParam("price") double price,
			@RequestParam("summary") String summary,@RequestParam("id") int id,ModelMap map,
			HttpServletRequest req,HttpServletResponse rsp){
		int updBuf=0;
		CookieTest ct=new CookieTest();
		ct.cookieTest(req,rsp,map);
		ApplicationContext context=new ClassPathXmlApplicationContext("application-context.xml");
		JdbcDao dao=context.getBean("jdbcDao",JdbcDao.class);
		updBuf=dao.updateProduct(id, price, title, image, summary, detail);	
		if(updBuf==1){
		Product pro=dao.getProById(id);
		map.addAttribute("product", pro);
		}
		((ConfigurableApplicationContext)context).close();
		return "editSubmit";
	}	
	@RequestMapping(value="/settleAccount",method=RequestMethod.GET)
	public String settleAccount(HttpServletRequest req,HttpServletResponse rsp,ModelMap map){
		CookieTest ct=new CookieTest();
		ct.cookieTest(req,rsp, map);
		return "settleAccount";
	}
	
}
