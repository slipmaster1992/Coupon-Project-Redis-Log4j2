package com.example.demo.webServices;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.common.ClientType;
import com.example.demo.Entry.LoginSystem;
import com.example.demo.Exception.CompaniesNotExistException;
import com.example.demo.Exception.CompanyExistException;
import com.example.demo.Exception.CompanyNotExistException;
import com.example.demo.Exception.CustomerExistException;
import com.example.demo.Exception.CustomerNotExistException;
import com.example.demo.Exception.CustomersNotExistException;
import com.example.demo.Facade.AdminFacade;
import com.example.demo.entities.Company;
import com.example.demo.entities.Customer;

@RestController
@CrossOrigin("*")
public class AdminWS {

	@Autowired
	private LoginSystem lS;


	private Logger logger = LogManager.getLogger(AdminWS.class);
	
	
	/**
	 * Creating Company
	 * @param c
	 * @throws CompanyExistException
	 */
	@RequestMapping(value = "/adminws/createcompany" , method = RequestMethod.POST , consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public void createCompany(HttpServletRequest request ,@RequestBody Company c) throws CompanyExistException {
    	
    	
//    	AdminFacade af = this.getFacade();
    	
		AdminFacade af = (AdminFacade) request.getSession().getAttribute("adminf");
		
		af.createCompany(c);
    	
		logger.info("createCompany() executed ");
    }
	
	/**
	 * Removing Company By ID
	 * @param id
	 * @throws CompanyNotExistException
	 */
	@RequestMapping(value = "/adminws/removecompany" , method = RequestMethod.PATCH , consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	
	
	public void removeCompany(HttpServletRequest request ,@RequestBody Company c) throws CompanyNotExistException {
		
//		AdminFacade af = getFacade();
		AdminFacade af = (AdminFacade) request.getSession().getAttribute("adminf");
		af.removeCompany(c);
		logger.info("removeCompany() executed ");
		
	}
	
	
     
	
	/**
	 * Updating Company
	 * @param password
	 * @param email
	 * @param id
	 * @throws CompanyExistException
	 * @throws CompanyNotExistException
	 */
	@RequestMapping(value = "/adminws/updatecompany" , method = RequestMethod.PUT , consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public void updateCompany(HttpServletRequest request ,@RequestBody Company c ) throws CompanyExistException, CompanyNotExistException {
    	
    	
//    	AdminFacade af = this.getFacade();
		AdminFacade af = (AdminFacade) request.getSession().getAttribute("adminf");
    	af.updateCompany(c);
    	logger.info("updateCompany() executed ");
    	
    }
	
	
	/**
	 * Getting Company By ID
	 * @param id
	 * @return
	 * @throws CompanyNotExistException
	 */
	@RequestMapping(value = "/adminws/getcompany/{id}" , method = RequestMethod.GET , produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	
	public Company getCompony(HttpServletRequest request ,@PathVariable("id") long id) throws CompanyNotExistException {
		
//		AdminFacade af = this.getFacade();
		
		AdminFacade af = (AdminFacade) request.getSession().getAttribute("adminf");
		logger.info("getCompany() executed ");
		return af.getCompany(id);		
	}
	
	
	
	
	
	
	
	/**
	 * Getting List Of All Companies
	 * @return
	 * @throws CompaniesNotExistException
	 */
	@RequestMapping(value = "/adminws/getcomponies", method = RequestMethod.GET, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public List<Company> getAllComponies(HttpServletRequest request ) throws CompaniesNotExistException {

//		AdminFacade af = this.getFacade();
		AdminFacade af = (AdminFacade) request.getSession().getAttribute("adminf");
		logger.info("getAllComponies() executed ");
		return af.getAllCompanies();

	}

	
	/**
	 * Creating New Customer 
	 * @param c
	 * @throws CustomerExistException
	 */
	@RequestMapping(value = "/adminws/createcustomer" , method = RequestMethod.POST , consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	
	
	public void createCustomer(HttpServletRequest request , @RequestBody Customer c) throws CustomerExistException {
		
//		AdminFacade af = this.getFacade();
		AdminFacade af = (AdminFacade) request.getSession().getAttribute("adminf");
		logger.info("createCustomer() executed ");
		af.createCustomer(c);
		
	}
	
	
   /**
    * Removing Customer By ID	
    * @param id
    * @throws CustomerNotExistException
   */
   @RequestMapping(value = "/adminws/removecustomer" , method = RequestMethod.PATCH , consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	
	
	public void removeCustomer(HttpServletRequest request ,@RequestBody Customer c ) throws CustomerNotExistException   {
		
//		AdminFacade af = getFacade();
	   AdminFacade af = (AdminFacade) request.getSession().getAttribute("adminf");
	   logger.info("removeCustomer() executed ");
		af.removeCustomer(c);
	}
	
	
   
   
   
   /**
    * Updating Customer Password By ID
    * @param password
    * @param id
    * @throws CustomerNotExistException
    */
   @RequestMapping(value = "/adminws/updatecustomer" , method = RequestMethod.PUT , consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
   public void updateCustomer(HttpServletRequest request ,@RequestBody  Customer c ) throws CustomerNotExistException  {
   	
   	
//   	AdminFacade af = this.getFacade();
	   AdminFacade af = (AdminFacade) request.getSession().getAttribute("adminf");
   	af.updateCustomer(c);
   	logger.info("updateCustomer() executed ");
   	
   }
	
   /**
    * Getting Customer By ID
    * @param id
    * @return
    * @throws CustomerNotExistException
    */
   @RequestMapping(value = "/adminws/getcustomer/{id}" , method = RequestMethod.GET , produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	
	public Customer getCustomer(HttpServletRequest request ,@PathVariable("id") long id) throws CustomerNotExistException  {
		
//		AdminFacade af = this.getFacade();
	   AdminFacade af = (AdminFacade) request.getSession().getAttribute("adminf");
	   logger.info("getCustomer() executed ");
	   return af.getCustomer(id);		
	}
	
	
   /**
    * Getting List Of All Customers
    * @return
    * @throws CustomersNotExistException
    */
   @RequestMapping(value = "/adminws/getallcustomers", method = RequestMethod.GET, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public List<Customer> getAllCustomers(HttpServletRequest request ) throws CustomersNotExistException  {

//		AdminFacade af = this.getFacade();
	   AdminFacade af = (AdminFacade) request.getSession().getAttribute("adminf");
	   logger.info("getAllCustomers() executed ");
		return af.getAllCustomers();

	}
   
   
   
   
   
	
	
	
}