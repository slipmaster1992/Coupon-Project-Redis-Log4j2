package com.example.demo.Facade;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.common.ClientType;
import com.example.common.CouponType;
import com.example.demo.DBDAO.CompanyDBDAO;
import com.example.demo.DBDAO.CouponDBDAO;
import com.example.demo.Exception.CompanyNotExistException;
import com.example.demo.Exception.CouponExistException;
import com.example.demo.Exception.CouponNotExistException;
import com.example.demo.Exception.CouponTitleExistException;
import com.example.demo.Exception.CouponsNotExistException;
import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;


@Component
public class CompanyFacade implements CouponClientFacade {


	//-------Empty CTR----//
	public CompanyFacade() {

	}



	// Object's members
	private Company loginCompany;


	// DBDAO
	@Autowired
	private CompanyDBDAO compdb;

	@Autowired
	private CouponDBDAO coupdb;



	/***
	 * Login Method for Company Facade
	 */
	@Override
	public CompanyFacade login(String name, String password, ClientType clientType) {
		// Checking type
		if(!clientType.equals(ClientType.COMPANY))
		{
			return null;
		}

		// Checking name & pass
		if(!compdb.login(name, password))
		{
			return null;
		}

		loginCompany = compdb.getCompanyByNameAndPassword(name, password);
		return this;
	}



	/**
	 * Creating New Coupon
	 * @param c
	 * @param compId
	 * @throws CouponTitleExistException
	 * @throws CompanyNotExistException
	 * @throws CouponExistException
	 */
	public void createCoupon(Coupon c) throws CouponTitleExistException, CompanyNotExistException, CouponExistException {

		Coupon coupCheck = coupdb.getCouponByTitle(c.getTitle());

		//    Checking if Coupon Already Exist By Title
		if(coupCheck != null) {

			throw new CouponTitleExistException("This Coupon Is Already Exist");
		}else {

			c.setCompany(loginCompany);
			coupdb.createCoupon(c, loginCompany.getId());

		}	
	}


	/**
	 * Removing Coupon By Coupon ID 
	 * @param coupId
	 * @param compId
	 * @throws CouponNotExistException
	 * @throws CompanyNotExistException
	 */
	public void removeCoupon(Coupon c) throws CouponNotExistException, CompanyNotExistException {

		Coupon coup = coupdb.getCoupon(c.getId());
		
		// Checking If Coupon Are Excisting By ID 

		if(coup == null) {

			throw new CouponNotExistException("Coupon With The ID " + c.getId() + " Is Not Exist ");

	     } else {

			coupdb.removeCoupon(c.getId(), loginCompany.getId());


		}

	}

	/**
	 * Updating Company Coupon 
	 * By Coupon ID
	 * Setting Only New End Date And Price
	 * @param endDate
	 * @param price
	 * @param coupId
	 * @param compId
	 * @throws CouponNotExistException
	 * @throws CompanyNotExistException
	 */
	public void updateCoupon(Coupon c) throws CouponNotExistException, CompanyNotExistException {

		Coupon coup = coupdb.getCoupon(c.getId());
		

		

		 if (coup == null) {

			throw new CouponNotExistException("Coupon With The ID "+ c.getId() + " Is Not Exist");

		}else {

			coupdb.updateCoupon(c.getPrice(), c.getId(), loginCompany.getId());

		}
	}



	/**
	 * Getting Coupon By ID
	 * @param coupId
	 * @return
	 * @throws CouponNotExistException
	 */
	public Coupon getCoupon (long coupId) throws CouponNotExistException {

		// Checking If Coupon Is Exist
		Coupon coup = coupdb.getCoupon(coupId);

		if(coup == null) {

			throw new CouponNotExistException("Coupon With The  ID " + coupId + " Is Not Exist"); 

		}else {

			return coupdb.getCoupon(coupId);

		}

	}

	/**
	 * Getting List Of All Coupons
	 * @return coupList
	 * @throws CouponsNotExistException 
	 */
	public ArrayList<Coupon> getAllCoupons() throws CouponsNotExistException {

		ArrayList<Coupon>coupList = coupdb.getAllCoupons();

		// Checking If Coupons Are Exist  

		if(coupList == null) {

			throw new CouponsNotExistException("There Are No Coupons !");

		}
		return coupList;

	}


	/**
	 * Getting List Of Coupons By Type
	 * @param couponType
	 * @return
	 * @throws CouponNotExistException
	 */
	public ArrayList<Coupon> getCouponByType(CouponType couponType) throws CouponNotExistException {


		return coupdb.getCouponByType(couponType);

	}

    
	/**
	 * Getting List Of All Coupons Between Dates
	 * @param endDate
	 * @return
	 * @throws CouponNotExistException
	 */
	public ArrayList<Coupon> getCouponByDates(Date startDate, Date endDate) throws CouponNotExistException {


		return coupdb.getCouponByDates(startDate, endDate);

	}

    /**
     * Getting List Of ALL Coupons By Prices
     * @param price
     * @return
     * @throws CouponNotExistException
     */
	public ArrayList<Coupon> getCouponByPrice(double minPrice, double maxPrice) throws CouponNotExistException {


		return coupdb.getCouponByPrices(minPrice, maxPrice);

	}
 
	
     
	/**
	 * Getting List Of Coupons By Type And Company ID
	 * @param couponType
	 * @return
	 */
	public ArrayList<Coupon> getByTypeAndCompId(CouponType couponType){
		
		
		return coupdb.getCouponByTypeAndCompId(loginCompany.getId(), couponType);
		
		
	}
	
	
	
	/**
	 * Getting List Of Coupons By Price And Company ID
	 * @param price
	 * @return
	 */
    public ArrayList<Coupon> getByPriceAndCompId(double minPrice, double maxPrice){
		
		
		return coupdb.getCouponByPriceAndCompId(loginCompany.getId(), minPrice, maxPrice);
		
		
	}
	
	
    
    /**
     * Getting List Of Coupons By Date And Company ID
     * @param endDate
     * @return
     */
      public ArrayList<Coupon> getByDateAndCompId(Date startDate,Date endDate){
		
		
		return coupdb.getCouponByDateAndCompId(loginCompany.getId(), startDate, endDate);
		
		
	}
	/**
	 * Getting List Of Coupon By Logged Company ID
	 * @return
	 */
	public ArrayList<Coupon> getCompanyCoupponsById(){
		
		return (ArrayList<Coupon>) coupdb.getCompanyCoupons(loginCompany.getId());
		
		
	}
	
	
	
	
	
	
	
	

	/***
	 * Getter login Company
	 * @return
	 */
	public Company getLoginCompany() {
		return loginCompany;
	}

	@Override
	public String toString() {
		return "CompanyFacade [loginCompany=" + loginCompany + "]";
	}






}
