package com.example.demo.webServices;



import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.common.ClientType;
import com.example.common.CouponType;
import com.example.demo.Entry.LoginSystem;
import com.example.demo.Exception.CouponAmountIsZeroException;
import com.example.demo.Exception.CouponNotExistException;
import com.example.demo.Exception.CouponsNotExistException;
import com.example.demo.Exception.CustomerAlreadyHaveCouponException;
import com.example.demo.Exception.CustomerNotExistException;
import com.example.demo.Exception.NoCouponsAtAllException;
import com.example.demo.Facade.CompanyFacade;
import com.example.demo.Facade.CustomerFacade;
import com.example.demo.entities.Coupon;

@RestController
@CrossOrigin("*")
public class CustomerWS {

	
	
	
	
	// Member Object
	@Autowired
	private LoginSystem lS;

	
	
	
	

	
	
	/**
	 * Customer Purchase Coupon
	 * @param id
	 * @throws CustomerNotExistException
	 * @throws CouponNotExistException
	 * @throws CustomerAlreadyHaveCouponException
	 * @throws CouponAmountIsZeroException
	 */
	@RequestMapping(value = "/customerws/purchasecoupon/{id}" , method = RequestMethod.PUT , consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)

	public void purchaseCoupon(HttpServletRequest request,@PathVariable("id") long id) throws CustomerNotExistException, CouponNotExistException, CustomerAlreadyHaveCouponException, CouponAmountIsZeroException {
		
		
		CustomerFacade cuf = (CustomerFacade) request.getSession().getAttribute("customerf");
        cuf.purchesCoupon(id);
		
		
	}
	
	/**
	 * Customer Getting Coupon By ID
	 * @param id
	 * @return
	 * @throws NoCouponsAtAllException
	 * @throws CustomerNotExistException
	 */
	@RequestMapping(value = "/customerws/getcustomercoupon/{id}" , method = RequestMethod.GET , produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)

	
	public Coupon getCustomerCoupon(HttpServletRequest request,@PathVariable("id") long id) throws NoCouponsAtAllException, CustomerNotExistException {
		
		CustomerFacade cuf = (CustomerFacade) request.getSession().getAttribute("customerf");
		return cuf.getCustomerCoupon(id);
		
	}
	
	
	/**
	 * Customer Getting List Of Purchase Coupons
	 * @return
	 * @throws NoCouponsAtAllException
	 * @throws CustomerNotExistException
	 */
	@RequestMapping(value = "/customerws/getcustomercoupons" , method = RequestMethod.GET , produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)

	
	public List<Coupon> getCustomerCoupons(HttpServletRequest request) throws NoCouponsAtAllException, CustomerNotExistException{
		
		CustomerFacade cuf = (CustomerFacade) request.getSession().getAttribute("customerf");
		return cuf.getCustomerCoupons();
         
		
		
	}
	
	
	/**
	 * Customer Getting LIst Of Purchase Coupons By Type
	 * @param couponType
	 * @return
	 * @throws CustomerNotExistException
	 * @throws NoCouponsAtAllException
	 */
	@RequestMapping(value = "/customerws/getcustomercouponsbytype/{couponType}" , method = RequestMethod.GET , produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)

	
	public List<Coupon> getCustomerCouponsByType(HttpServletRequest request,@PathVariable("couponType") CouponType couponType) throws CustomerNotExistException, NoCouponsAtAllException {
		
		
		
		CustomerFacade cuf = (CustomerFacade) request.getSession().getAttribute("customerf");

		return cuf.getCustomerCouponsByType(couponType);
		
		
		
	}
	
	
	
	/**
	 * Customer Getting LIst Of Purchase Coupons By price
     * @param price
	 * @return
	 * @throws CustomerNotExistException
	 * @throws NoCouponsAtAllException
	 */
    @RequestMapping(value = "/customerws/getcustomercouponsbyprice/{minprice},{maxprice}" , method = RequestMethod.GET , produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)

	
	public List<Coupon> getCustomerCouponsByPrice(HttpServletRequest request,@PathVariable("minprice") double minPrice, @PathVariable("maxprice") double maxPrice) throws CustomerNotExistException, NoCouponsAtAllException {
		
		
		
    	CustomerFacade cuf = (CustomerFacade) request.getSession().getAttribute("customerf");

		return cuf.getCustomerCouponsByPrice(minPrice, maxPrice);
		
		
		
	}
	
	
	/**
	 * Customer Getting List Of Purchase Coupons Between Dates
	 * @param date
	 * @return
	 * @throws CustomerNotExistException
	 * @throws NoCouponsAtAllException
	 */
    @RequestMapping(value = "/customerws/getcustomercouponsbydate/{startdate},{enddate}" , method = RequestMethod.GET , produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)

	
	public List<Coupon> getCustomerCouponsByDate(HttpServletRequest request,@PathVariable("startdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @PathVariable("enddate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) throws CustomerNotExistException, NoCouponsAtAllException {
		
		
		
    	CustomerFacade cuf = (CustomerFacade) request.getSession().getAttribute("customerf");

		return cuf.getCustomerCouponsByDate(startDate, endDate);
		
		
       }
	
	 
	/**
	 * Getting List Of All Existing Coupons
	 * @return
	 * @throws CouponsNotExistException
	 */
    @RequestMapping(value = "/customerws/getallcoupons" , method = RequestMethod.GET , produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)

	
	 public List<Coupon> getAllCoupons(HttpServletRequest request) throws CouponsNotExistException {
		
		
		
    	CustomerFacade cuf = (CustomerFacade) request.getSession().getAttribute("customerf");

		 return cuf.getAllCoupons();
		
       }
	
    
    
     /**
      * Getting List Of All Coupons By Type
      * @param couponType
      * @return
      * @throws CustomerNotExistException
      * @throws NoCouponsAtAllException
      * @throws CouponNotExistException
      */
     @RequestMapping(value = "/customerws/getcouponsbytype/{couponType}" , method = RequestMethod.GET , produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)

	
	public List<Coupon> getCouponsByType(HttpServletRequest request,@PathVariable("couponType") CouponType couponType) throws CustomerNotExistException, NoCouponsAtAllException, CouponNotExistException {
		
		
		
    	 CustomerFacade cuf = (CustomerFacade) request.getSession().getAttribute("customerf");

		return cuf.getAllCouponsByType(couponType);
		
		
	    }
    
    
    /**
     * Getting List Of All Coupons By Date
     * @param date
     * @return
     * @throws CustomerNotExistException
     * @throws NoCouponsAtAllException
     * @throws CouponNotExistException
     */
     @RequestMapping(value = "/customerws/getcouponsbydate/{startDate},{endDate}" , method = RequestMethod.GET , produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)

 	
 	public List<Coupon> getCouponsByDate(HttpServletRequest request,@PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) throws CustomerNotExistException, NoCouponsAtAllException, CouponNotExistException {
 		
 		
 		
    	 CustomerFacade cuf = (CustomerFacade) request.getSession().getAttribute("customerf");
 		return cuf.getAllCouponsByDates(startDate , endDate);
 		
 		
 		
 	}
     
    
    
    
    /**
     * Getting List Of All Coupons By Price
     * @param price
     * @return
     * @throws CustomerNotExistException
     * @throws NoCouponsAtAllException
     * @throws CouponNotExistException
     */
     @RequestMapping(value = "/customerws/getcouponsbyprice/{minPrice},{maxPrice}" , method = RequestMethod.GET , produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)

  	
  	public List<Coupon> getCouponsByPrice(HttpServletRequest request, @PathVariable("minPrice") double minPrice, @PathVariable("maxPrice") double maxPrice) throws CustomerNotExistException, NoCouponsAtAllException, CouponNotExistException {
  		
  		
  		
    	 CustomerFacade cuf = (CustomerFacade) request.getSession().getAttribute("customerf");

  		return cuf.getAllCouponsByPrices(minPrice, maxPrice);
  		
  		
  		
  	}
      
     
    
    
    
    
    
}
