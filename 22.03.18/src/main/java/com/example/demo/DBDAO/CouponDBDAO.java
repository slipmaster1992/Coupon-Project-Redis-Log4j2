package com.example.demo.DBDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.common.CouponType;
import com.example.demo.Exception.CompanyNotExistException;
import com.example.demo.Exception.CouponExistException;
import com.example.demo.Exception.CouponNotExistException;
import com.example.demo.Exception.CouponsNotExistException;
import com.example.demo.entities.Company;
import com.example.demo.entities.Coupon;

import Connections.Connection;
import Connections.ConnectionPool;

@Service
public class CouponDBDAO implements CouponDAO{

	@Autowired
	private CouponRepo coupRepo;

	@Autowired
	private CompanyRepo compRepo;
  
	
	private ConnectionPool cp = ConnectionPool.getInstance();




	/***
	 * Creating new Company's Coupon
	 */
	@Override
	public void createCoupon(Coupon c, long compId) throws CompanyNotExistException, CouponExistException {
		Connection con = cp.getConnection();
		Company	comp = compRepo.findOne(compId);
		c.setCompany(comp);
		coupRepo.save(c);
		cp.returnConnection(con);
	}

	/***
	 * Removing Company's Coupon
	 */
	@Override
	public void removeCoupon(long coupId, long compId) throws CompanyNotExistException, CouponNotExistException {
		Connection con = cp.getConnection();
		coupRepo.removeCoupon(coupId, compId);
		cp.returnConnection(con);
	}

	/***
	 * Updating Coupon only endDate & price
	 */
	@Override
	public void updateCoupon( double price, long coupId, long compId)
			throws CompanyNotExistException, CouponNotExistException {
		Connection con = cp.getConnection();
		coupRepo.updateCoupon(price, coupId, compId);
		cp.returnConnection(con);
	}


	/**
	 * Updating Coupon Amount By ID
	 * @param coupId
	 */
	public void updateCouponAmount(long coupId) {
		Connection con = cp.getConnection();
		coupRepo.updateCouponAmount(coupId);
		cp.returnConnection(con);
	}




	/***
	 * Getting Coupon by id
	 */
	@Override
	public Coupon getCoupon(long coupId) throws CouponNotExistException {
		
		Connection con = cp.getConnection();
		cp.returnConnection(con);
		return coupRepo.findOne(coupId);
		
	}

	/***
	 * Get All Coupons
	 */
	@Override
	public ArrayList<Coupon> getAllCoupons() throws CouponsNotExistException {
		Connection con = cp.getConnection();
		ArrayList<Coupon> allCoupons = (ArrayList<Coupon>) coupRepo.findAll();
		cp.returnConnection(con);
		return allCoupons;

	}

	/***
	 * Get Coupons by type
	 */
	@Override
	public ArrayList<Coupon> getCouponByType(CouponType couponType) throws CouponNotExistException {
		Connection con = cp.getConnection();
		cp.returnConnection(con);
		return coupRepo.findBytype(couponType);

	}
    
	
	/**
	 * Getting List Of All Coupons By Dates
	 */
	@Override
	public ArrayList<Coupon> getCouponByDates(Date startDate, Date endDate) throws CouponNotExistException {
		Connection con = cp.getConnection();
		cp.returnConnection(con);
		return (ArrayList<Coupon>) coupRepo.findCouponByDates(startDate, endDate);

	}
	
	
	
	/**
	 * Getting LIst Of ALL Coupons By Price
	 */
	@Override
	public ArrayList<Coupon> getCouponByPrices(double minPrice, double maxPrice) throws CouponNotExistException {
		Connection con = cp.getConnection();
		cp.returnConnection(con);
		return (ArrayList<Coupon>) coupRepo.findCouponByPrices(minPrice, maxPrice);

	}
	
	
	/**
	 * Getting Coupon By Title
	 * @param title
	 * @return
	 */
	public Coupon getCouponByTitle(String title) {
		Connection con = cp.getConnection();
		cp.returnConnection(con);
		return coupRepo.findByTitle(title);

	}

	
	
	/**
	 * Getting List Of Coupons By Type And Company ID
	 * @param couponType
	 * @param compId
	 * @return
	 */
	public ArrayList<Coupon> getCouponByTypeAndCompId(long compId, CouponType couponType){
		
		Connection con = cp.getConnection();
		cp.returnConnection(con);
		return  coupRepo.findByCompanyIdAndType(compId, couponType);
		
		
		
	}
	
	
	/**
	 * Getting List Of Coupons By Price And Company ID
	 * @param compId
	 * @param price
	 * @return
	 */
     public ArrayList<Coupon> getCouponByPriceAndCompId(long compId, double minPrice,double maxPrice){
		
		Connection con = cp.getConnection();
		cp.returnConnection(con);
		return  (ArrayList<Coupon>) coupRepo.findCompanyCouponByPrice(compId, minPrice, maxPrice);
		
      }
	
	
    /**
     *  Getting List Of Coupons By Date And Company ID
     * @param compId
     * @param endDate
     * @return
     */
     public ArrayList<Coupon> getCouponByDateAndCompId(long compId,Date startDate,Date endDate){
 		
 		Connection con = cp.getConnection();
 		cp.returnConnection(con);
 		return  (ArrayList<Coupon>) coupRepo.findCompanyCouponBetweenDates(compId, startDate, endDate);
 		
       }
 	
	
	
	/**
	 * Getting Customer Coupon By Customer ID And Coupon ID
	 * @param custId
	 * @param coupId
	 * @return
	 */
	public Coupon getCustomerCoupon(long custId, long coupId) {
		Connection con = cp.getConnection();
		cp.returnConnection(con);
		return coupRepo.findCustomerCoupon(custId, coupId);

	}


	/**
	 * Getting All Customer Coupons 
	 * By Customer ID 
	 * @param custId
	 * @return
	 */
	public List<Coupon> getCustomerCoupons(long custId){
		Connection con = cp.getConnection();
		List<Coupon>custCoupons = coupRepo.findCustomerCoupons(custId);
		cp.returnConnection(con);
		return custCoupons;

	}

	/**
	 * Getting Customer Coupons By Type
	 * And Customer ID
	 * @param custId
	 * @param type
	 * @return
	 */
	public List<Coupon> getCustomerCouponsByType(long custId , CouponType type){
		Connection con = cp.getConnection();
		List<Coupon> custCouponsByType = coupRepo.findCustomerCouponsByType(custId, type);
		cp.returnConnection(con);
		return custCouponsByType;

	}


	/**
	 * Getting Customer Coupons By Price
	 * And Customer ID
	 * @param custId
	 * @param price
	 * @return
	 */
	public List<Coupon> getCustomerCouponsByPrice(long custId , double minPrice, double maxPrice){
		Connection con = cp.getConnection();
		List<Coupon> custCouponsByPrice = coupRepo.findCustomerCouponByPrices(custId, minPrice, maxPrice);
		cp.returnConnection(con);
		return custCouponsByPrice;

	}

	/**
	 * Getting Customer Coupons BBetween Dates
	 * And Customer ID
	 * @param custId
	 * @param date
	 * @return
	 */
	public List<Coupon> getCustomerCouponsByDate(long custId , Date startDate, Date endDate){
		Connection con = cp.getConnection();
		List<Coupon> custCouponsByDate = coupRepo.findCustomerCouponBetweenDates(custId, startDate, endDate);
		cp.returnConnection(con);
		return custCouponsByDate;

	}

    public List<Coupon> getCompanyCoupons(long compId){
    	
    	Connection con = cp.getConnection();
		List<Coupon> companycoupons = coupRepo.findByCompanyId(compId);
		cp.returnConnection(con);
		return companycoupons;
    	
    	
    }
	
	
	

}
