package com.example.demo.Facade;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.common.ClientType;
import com.example.demo.DBDAO.CompanyDBDAO;
import com.example.demo.DBDAO.CustomerDBDAO;
import com.example.demo.Exception.CompaniesNotExistException;
import com.example.demo.Exception.CompanyExistException;
import com.example.demo.Exception.CompanyNotExistException;
import com.example.demo.Exception.CustomerExistException;
import com.example.demo.Exception.CustomerNotExistException;
import com.example.demo.Exception.CustomersNotExistException;
import com.example.demo.entities.Company;
import com.example.demo.entities.Customer;

@Component
public class AdminFacade implements CouponClientFacade {

	//    Empty CTR

	public AdminFacade() {

	}



	// DBDAO
	@Autowired
	private CompanyDBDAO compdb;
	@Autowired
	private CustomerDBDAO custdb;

	// Login attributes
	private final String NAME = "admin";
	private final String PASSWORD = "1234";

	/***
	 * Login Method for Admin Facade
	 */
	@Override

	public AdminFacade login(String name , String password , ClientType type)
	{
		if(!type.equals(ClientType.ADMIN))
		{
			System.out.println("You are trying to connect with the wrong type:" + type);
			return null;
		}
		String question = name + password;
		String answer = NAME + PASSWORD;
		
		if(question.equals(answer))
		{
			return this;	
		}
			else
		{
			return null;	
		}
	}
	
	
	
	
	
	//	public AdminFacade login(String adminName, String adminNamev, ClientType clientType) {
//		// Checking Client type before name & pass
//		if (!clientType.equals(ClientType.ADMIN)) {
//			return null;
//		}
//
////		String que = adminName + adminName;
////		String ans = ADMINNAME + PASSWORD;
//
//		// Checking name & pass
////		if (!que.equals(ans)) {
////			return null;
////		}
//
//		// Success
//		return this;
//	}

	/**
	 * Creating Company if its not exist
	 * 
	 * @param company
	 * @throws CompanyExistException
	 */
	public void createCompany(Company company) throws CompanyExistException {
		// Checking if exist
		Company check = compdb.getCompanyByName(company.getCompanyName());
		if (check != null) {
			throw new CompanyExistException("Company " + company.getCompanyName() + " alreadey exist");
		}

		// Creating new Company
		compdb.createCompany(company);
	}

	/***
	 * Remove Company & Company's Coupons if its exist
	 * 
	 * @param companyId
	 * @throws CompanyNotExistException
	 */
	public void removeCompany(Company company) throws CompanyNotExistException {

		// Checking if exist
		Company check = compdb.getCompany(company.getId());
		if (check == null) {
			throw new CompanyNotExistException("Company with the id:" + company.getId() + " Does not exist");
		}

		// Removing..
		compdb.removeAllCompanyCoupons(company.getId());
		compdb.removeCompany(company.getId());

	}

	/***
	 * Updating Company Set only password & email by ID
	 * 
	 * @param password
	 * @param email
	 * @param companyId
	 * @throws CompanyNotExistException
	 */
	public void updateCompany(Company c) throws CompanyNotExistException {

		// Checking if exist
		Company check = compdb.getCompany(c.getId());
		if (check == null) {
			throw new CompanyNotExistException("Company with the id:" + c.getId() + " does not exist");
		}

		// Updating
		compdb.updateCompany(c.getEmail(), c.getPassword(), c.getId());
	}

	/***
	 * Getting Company by ID
	 * 
	 * @param companyId
	 * @return
	 * @throws CompanyNotExistException
	 */
	public Company getCompany(long companyId) throws CompanyNotExistException {

		// Checking if exist
		Company check = compdb.getCompany(companyId);
		if (check == null) {
			throw new CompanyNotExistException("Company with the id:" + companyId + " does not exist");
		}

		return check;
	}

	/***
	 * Get All Companies
	 * 
	 * @return
	 * @throws CompaniesNotExistException
	 */
	public ArrayList<Company> getAllCompanies() throws CompaniesNotExistException {
		ArrayList<Company> allCompanies = compdb.getAllCompanies();
		// Checking for null
		if (allCompanies == null) {
			throw new CompaniesNotExistException("There Are No0 Companies Yet.");
		}

		return allCompanies;
	}

	/**
	 * Creating New Customer
	 * 
	 * @param customer
	 * @throws CustomerExistException
	 */
	public void createCustomer(Customer customer) throws CustomerExistException {
		// Checking If Customer Exist
		Customer custCheck = custdb.getCustomerByName(customer.getCustName());
		if (custCheck != null) {
			throw new CustomerExistException("Customer " + customer.getCustName() + " already exist");
		}

		custdb.createCustomer(customer);
	}

	/**
	 * Removing Customer By ID
	 * 
	 * @param customerId
	 * @throws CustomerNotExistException
	 */
	public void removeCustomer(Customer c) throws CustomerNotExistException {
		// Checking If Customer Exist By ID
		Customer custCheck = custdb.getCustomer(c.getId());
		if (custCheck == null) {
			throw new CustomerNotExistException("Customer With The ID : " + c.getId() + " Does Not Exist");
		}
		custdb.removeCustomer(c.getId());

	}

	/***
	 * Update Customer set only password by id
	 * 
	 * @param password
	 * @param customerId
	 * @throws CustomerNotExistException
	 */
	public void updateCustomer(Customer c) throws CustomerNotExistException {

		// Checking If Customer Exist By ID
		Customer custCheck = custdb.getCustomer(c.getId());
		if (custCheck == null) {
			throw new CustomerNotExistException("Customer With The ID : " + c.getId() + " Does Not Exist");
		}
		// Updating..
		custdb.updateCustomer(c.getPassword(), c.getId());
	}

	/***
	 * Get Customer By ID
	 * @param customerId
	 * @return
	 * @throws CustomerNotExistException
	 */
	public Customer getCustomer(long customerId) throws CustomerNotExistException {

		// Checking If Customer Exist By ID
		Customer custCheck = custdb.getCustomer(customerId);
		if (custCheck == null) {
			throw new CustomerNotExistException("Customer With The ID : " + customerId + " Does Not Exist");
		}

		return custCheck;
	}

	/***
	 * Get all Customers
	 * @return
	 * @throws CustomersNotExistException
	 */
	public ArrayList<Customer> getAllCustomers() throws CustomersNotExistException
	{
		ArrayList<Customer> allCustomers = custdb.getAllCustomers();
		// Checking if not null
		if(allCustomers == null)
		{
			throw new CustomersNotExistException("There are no Customers yet.");
		}

		return allCustomers;

	}

	@Override
	public String toString() {
		return "AdminFacade [hi im the great! admin]";
	}

}
