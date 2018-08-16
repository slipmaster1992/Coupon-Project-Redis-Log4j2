package com.example.demo.webServices;

import java.io.IOError;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.common.ClientType;
import com.example.demo.Entry.LoginSystem;
import com.example.demo.Facade.AdminFacade;
import com.example.demo.Facade.CompanyFacade;
import com.example.demo.Facade.CouponClientFacade;
import com.example.demo.Facade.CustomerFacade;


@Controller
   public class LoginServlet {

	@Autowired
	private LoginSystem couponSystem;
	
	
	@RequestMapping(value = "/loginservlet", method = RequestMethod.POST  )
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		
		System.out.println("ggggg");
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		String userType = request.getParameter("usertype");
		
	
	    try {
	    	ClientType clientType = ClientType.valueOf(userType);
	    
//	    	CouponClientFacade facade = (CouponClientFacade) couponSystem.login(userName, password,  ClientType.valueOf(userType));
	    	
	    	
	    	switch (userType) {
	    	
	    	case "ADMIN" :
	    		AdminFacade adminFacade = (AdminFacade) couponSystem.login(userName, password, clientType);
	    		request.getSession().setAttribute("adminf", adminFacade);
	    	    response.sendRedirect("http://localhost:8080/admin_page/index.html");
	    	    break;
	    	    
            case "COMPANY" :
            	CompanyFacade companyFacade = (CompanyFacade) couponSystem.login(userName, password, clientType);
	    		request.getSession().setAttribute("companyf", companyFacade);
	    	    response.sendRedirect("http://localhost:8080/company_page/index.html");
	    	    break;    
	    	
	    	case "CUSTOMER" :
	    		CustomerFacade customerFacade = (CustomerFacade) couponSystem.login(userName, password, clientType);
	    		request.getSession().setAttribute("customerf", customerFacade);
	    	    response.sendRedirect("http://localhost:8080/customer_page/index.html");
	    	    break;
	    	
	         }
	    	
	    	
	      } catch (IOException e ) {
	    	  
	    	  try {
	    		  response.sendRedirect("http://localhost:8080/login.html");
	    		  
	    	  } catch (IOException e1 ) {
	    		  
	    	      e1.printStackTrace();	  
	    	  }
	    	
	      }
	    		  
	  }
	
  
  }
