package com.example.demo.Facade;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.example.common.ClientType;
import com.example.common.CouponType;
import com.example.demo.DBDAO.CompanyDBDAO;
import com.example.demo.DBDAO.CouponDBDAO;
import com.example.demo.DBDAO.CustomerDBDAO;
import com.example.demo.Exception.CouponAmountIsZeroException;
import com.example.demo.Exception.CouponNotExistException;
import com.example.demo.Exception.CouponsNotExistException;
import com.example.demo.Exception.CustomerAlreadyHaveCouponException;
import com.example.demo.Exception.CustomerNotExistException;
import com.example.demo.Exception.NoCouponsAtAllException;

import com.example.demo.entities.Coupon;
import com.example.demo.entities.Customer;

@Component
public class CustomerFacade implements CouponClientFacade {







	//-------Empty CTR-------//

	public CustomerFacade() {

	}


	// Object's members
	private Customer loginCustomer;


	// DBDAO
	
	@Autowired
	private CouponDBDAO coupdb;

	@Autowired
	private CustomerDBDAO custdb;



	@Override
	public CustomerFacade login(String name, String password, ClientType clientType) {
		// Checking type
		if(!clientType.equals(ClientType.CUSTOMER))
		{
			return null;
		}

		// Checking name & pass
		if(!custdb.login(name, password))
		{
			return null;
		}

		loginCustomer = custdb.getCustomerByNameAndPassword(name, password);
		return this;



	}

	/**
	 * Customer Purchase Coupon By Coupon ID
	 * @param custId
	 * @param coupId
	 * @throws CustomerNotExistException
	 * @throws CouponNotExistException
	 * @throws CustomerAlreadyHaveCouponException
	 * @throws CouponAmountIsZeroException 
	 */
	public void purchesCoupon(long coupId ) throws CustomerNotExistException, CouponNotExistException, CustomerAlreadyHaveCouponException, CouponAmountIsZeroException {

		
		// Checking If Coupon Exist
		Coupon coup = coupdb.getCoupon(coupId);

		if(coup == null) {

			throw new CouponNotExistException("Coupon With The ID " + coupId + " Is Not Exist");	

		}

		// Checking If Customer Already Got The Coupon
		Coupon custCoup = coupdb.getCustomerCoupon(loginCustomer.getId(), coupId);

		if(custCoup != null) {

			throw new CustomerAlreadyHaveCouponException();
		}

		


		// Purchase Coupon
		custdb.purchaseCoupon(loginCustomer.getId(), coupId);
		coupdb.updateCouponAmount(coupId);
	}


	/**
	 * Getting Customer Purchase Coupon
	 * By Customer ID And Coupon ID
	 * @param custId
	 * @param coupId
	 * @return
	 * @throws NoCouponsAtAllException 
	 * @throws CustomerNotExistException 
	 */
	public Coupon getCustomerCoupon( long coupId) throws NoCouponsAtAllException, CustomerNotExistException{


		Coupon coup = coupdb.getCustomerCoupon(loginCustomer.getId(), coupId);
		
		// Checking If Coupon Exist
		if(coup == null) {

			throw new NoCouponsAtAllException("You Have No Purchased Coupon By This Coupon ID");

		}

		return coup;

	}


	/**
	 * Getting List Of Customer Purchase Coupons
	 * By Customer ID
	 * @param custId
	 * @return
	 * @throws NoCouponsAtAllException
	 * @throws CustomerNotExistException 
	 */
	public List<Coupon> getCustomerCoupons() throws NoCouponsAtAllException, CustomerNotExistException{


		Customer cust = custdb.getCustomer(loginCustomer.getId());
		List<Coupon>custCoupons = coupdb.getCustomerCoupons(loginCustomer.getId());


		

		if(custCoupons.isEmpty() | custCoupons == null) {

			throw new NoCouponsAtAllException();

		} 

		else {

			return custCoupons;
		}



	}


	/**
	 * Getting Customer Coupons By Type
	 * 
	 * @param custId
	 * @param type
	 * @return
	 * @throws CustomerNotExistException
	 * @throws NoCouponsAtAllException
	 */
	public List<Coupon> getCustomerCouponsByType(CouponType type) throws CustomerNotExistException, NoCouponsAtAllException{

		
		List<Coupon>custCoupons = coupdb.getCustomerCouponsByType(loginCustomer.getId(), type);




		if(custCoupons.isEmpty() | custCoupons == null) {

			throw new NoCouponsAtAllException();

		} 

		else {

			return custCoupons;
		}


	}


	/**
	 * Getting Customer Coupons By Price
	 * 
	 * @param custId
	 * @param price
	 * @return
	 * @throws CustomerNotExistException
	 * @throws NoCouponsAtAllException
	 */
	public List<Coupon> getCustomerCouponsByPrice( double minPrice, double maxPrice) throws CustomerNotExistException, NoCouponsAtAllException{

		
		List<Coupon>custCouponsByPrice = coupdb.getCustomerCouponsByPrice(loginCustomer.getId(), minPrice, maxPrice);



		if(custCouponsByPrice.isEmpty() | custCouponsByPrice == null) {

			throw new NoCouponsAtAllException();

		} 

		else {

			return custCouponsByPrice;
		}


	}

	/**
	 * Getting Customer Coupons By Date
	 * And Customer ID 
	 * @param custId
	 * @param date
	 * @return
	 * @throws CustomerNotExistException
	 * @throws NoCouponsAtAllException
	 */
	public List<Coupon> getCustomerCouponsByDate(Date startDate, Date endDate) throws CustomerNotExistException, NoCouponsAtAllException{

		List<Coupon>custCouponsByDate = coupdb.getCustomerCouponsByDate(loginCustomer.getId(), startDate, endDate);


		// Checcking If Coupons Exist

		if(custCouponsByDate.isEmpty() | custCouponsByDate == null) {

			throw new NoCouponsAtAllException();

		} 

		else {

			return custCouponsByDate;
		}


	}

       
       /**
        * Getting List Of All Coupons

        * @return
        * @throws CouponsNotExistException
        */
	@Cacheable("getAllCoupons")
	public List<Coupon> getAllCoupons() throws CouponsNotExistException{
		
    	 List<Coupon> allCoupons = coupdb.getAllCoupons();  
    	   
    	   return allCoupons ;
    	   
    	   }
	
	   
       /**
        * Getting List Of All Coupons By Type
        * @param type
        * @return
        * @throws CouponNotExistException
        */
       @Cacheable("getAllCouponsByType")
       public List<Coupon> getAllCouponsByType(CouponType type) throws  CouponNotExistException{
   		
      	 List<Coupon> allCouponsByType = coupdb.getCouponByType(type);
      	   
      	   return allCouponsByType ;
      	   
      	   }
  	
	   
       /**
        * Getting List Of All Coupons By Date
        * @param endDate
        * @return
        * @throws CouponNotExistException
        */
       public List<Coupon> getAllCouponsByDates(Date startDate, Date endDate) throws  CouponNotExistException{
      	
        	 List<Coupon> allCouponsByDates = coupdb.getCouponByDates(startDate, endDate);
        	   
           return allCouponsByDates ;
        	   
        	   }
	
	
	   
       /**
        * Getting List Of All Coupons By Prices
        * @param price
        * @return
        * @throws CouponNotExistException
        */
       public List<Coupon> getAllCouponsByPrices(double minPrice, double maxPrice) throws  CouponNotExistException{
     		
      	 List<Coupon> allCouponsByPrices = coupdb.getCouponByPrices(minPrice, maxPrice);
      	   
      	   return allCouponsByPrices ;
      	   
      	   }
	
       
       
       
        /***
	     * Getter login Customer
	     * @return
     	 */
	   public Customer getLoginCustomer() {
		return loginCustomer;
	     }

	
	    @Override
	   public String toString() {
		return "CustomerFacade [loginCustomer=" + loginCustomer + "]";
	     }

















}
