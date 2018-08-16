package com.example.demo.webServices;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.common.ClientType;
import com.example.common.CouponType;
import com.example.demo.Entry.LoginSystem;
import com.example.demo.Exception.CompanyNotExistException;
import com.example.demo.Exception.CouponExistException;
import com.example.demo.Exception.CouponNotExistException;
import com.example.demo.Exception.CouponTitleExistException;
import com.example.demo.Exception.CouponsNotExistException;
import com.example.demo.Facade.AdminFacade;
import com.example.demo.Facade.CompanyFacade;
import com.example.demo.entities.Coupon;

@RestController
@CrossOrigin("*")
public class CompanyWS {


    // Member Object
	@Autowired
	private LoginSystem lS;

	



	
	
	
    /**
     * Create New Coupon
     * @param c
     * @throws CouponTitleExistException
     * @throws CompanyNotExistException
     * @throws CouponExistException
     */
	@RequestMapping(value = "/companyws/createcoupon" , method = RequestMethod.POST , consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)


    public void createCoupon(HttpServletRequest request ,@RequestBody Coupon c) throws CouponTitleExistException, CompanyNotExistException, CouponExistException {
		
		

		
		CompanyFacade cf = (CompanyFacade) request.getSession().getAttribute("companyf");
		
		cf.createCoupon(c);
		
	}

    /**
     * Removing Coupon By ID
     * @param id
     * @param compId
     * @throws CouponNotExistException
     * @throws CompanyNotExistException
     */
	@RequestMapping(value = "/companyws/deletecoupon" , method = RequestMethod.PATCH , consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)

	public void removecoupon(HttpServletRequest request ,@RequestBody Coupon c) throws CouponNotExistException, CompanyNotExistException {
		

		
		CompanyFacade cf = (CompanyFacade) request.getSession().getAttribute("companyf");

        cf.removeCoupon(c);
		
		
		
	}
	
	/**
	 * Updating Coupon By Coupon
	 * @param c
	 * @throws CompanyNotExistException 
	 * @throws CouponNotExistException 
	 */
	@RequestMapping(value = "/companyws/updatecoupon" , method = RequestMethod.PUT , consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)

	public void updateCoupon(HttpServletRequest request ,@RequestBody Coupon c) throws CouponNotExistException, CompanyNotExistException {
		
		

		
		CompanyFacade cf = (CompanyFacade) request.getSession().getAttribute("companyf");
         cf.updateCoupon(c);
		
		
		
	}
	
	
	
	
	
	
	
	
	 /**
	  * Getting Coupon By ID
	  * @param id
	  * @return
	  * @throws CouponNotExistException
	  */
	@RequestMapping(value = "/companyws/getcoupon/{id}" , method = RequestMethod.GET , produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
   

     public Coupon getCoupon(HttpServletRequest request ,@PathVariable("id") long id) throws CouponNotExistException {
		
//		
		CompanyFacade cf = (CompanyFacade) request.getSession().getAttribute("companyf");
		return cf.getCoupon(id);
		
	}



     /**
      * Getting List Of Coupons
      * @return
      * @throws CouponsNotExistException
      */
	@RequestMapping(value = "/companyws/getallcoupons" , method = RequestMethod.GET , produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)

     public List<Coupon> getAllCoupons(HttpServletRequest request) throws CouponsNotExistException{
		
		CompanyFacade cf = (CompanyFacade) request.getSession().getAttribute("companyf");
		return cf.getAllCoupons();
		
		
		
	}

   
	/**
	 * Getting List Of Coupons By Type
	 * @param couponType
	 * @return
	 * @throws CouponNotExistException
	 */
	@RequestMapping(value = "/companyws/getcouponstype/{couponType}" , method = RequestMethod.GET , produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)

    public List<Coupon> getAllCouponsByType(HttpServletRequest request ,@PathVariable("couponType") CouponType couponType) throws CouponNotExistException{
		
		CompanyFacade cf = (CompanyFacade) request.getSession().getAttribute("companyf");

		
		return cf.getCouponByType(couponType);
		
		
	}
     
	/**
	 * Getting List Of All Coupons By Dates
	 * @param endDate
	 * @return
	 * @throws CouponNotExistException
	 */
	@RequestMapping(value = "/companyws/getcouponsbydate/{startDate},{endDate}" , method = RequestMethod.GET , produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)

    public List<Coupon> getAllCouponsByDate(HttpServletRequest request ,@PathVariable("startDate")  @DateTimeFormat(pattern = "yyyy-MM-dd")  Date startDate, @PathVariable("endDate")  @DateTimeFormat(pattern = "yyyy-MM-dd")  Date endDate) throws CouponNotExistException{
		
		CompanyFacade cf = (CompanyFacade) request.getSession().getAttribute("companyf");

		
		return cf.getCouponByDates(startDate, endDate);
	
	
	}
	
	/**
	 *  Getting List Of All Coupons By Price
	 * @param price
	 * @return
	 * @throws CouponNotExistException
	 */
	@RequestMapping(value = "/companyws/getcouponsbyprice/{minPrice},{maxPrice}" , method = RequestMethod.GET , produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)

    public List<Coupon> getAllCouponsByPrice(HttpServletRequest request , @PathVariable("minPrice") double minPrice, @PathVariable("maxPrice") double maxPrice) throws CouponNotExistException{
		
		CompanyFacade cf = (CompanyFacade) request.getSession().getAttribute("companyf");
		
		return cf.getCouponByPrice(minPrice, maxPrice);
	}
	
	
	
	
	/**
      * Getting List Of All Company Purchase Coupons
      * @return
      */
	@RequestMapping(value = "/companyws/getallcompanycoupons" , method = RequestMethod.GET , produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public List<Coupon> getCompanyCoupons(HttpServletRequest request ){
		
		CompanyFacade cf = (CompanyFacade) request.getSession().getAttribute("companyf");
		return cf.getCompanyCoupponsById() ;
		
		
	}

    /**
     * Getting List Of All Company Coupons By Type
     * @param couponType
     * @return
     * @throws CouponNotExistException
     */
	@RequestMapping(value = "/companyws/getcompanycouponstype/{couponType}" , method = RequestMethod.GET , produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)

    public List<Coupon> getAllCompanyCouponsByType(HttpServletRequest request ,@PathVariable("couponType") CouponType couponType) throws CouponNotExistException{
		
		CompanyFacade cf = (CompanyFacade) request.getSession().getAttribute("companyf");

		
		return cf.getByTypeAndCompId(couponType);
		
		}


    /**
     * Getting List Of All Company Coupons Between Minimum Price And Maximum Price
     * @param couponType
     * @return
     * @throws CouponNotExistException
     */
	@RequestMapping(value = "/companyws/getcompanycouponsprice/{minPrice},{maxPrice}" , method = RequestMethod.GET , produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)

    public List<Coupon> getAllCompanyCouponsByPrice(HttpServletRequest request ,@PathVariable("minPrice") double minPrice, @PathVariable("maxPrice") double maxPrice ) throws CouponNotExistException{
		
		CompanyFacade cf = (CompanyFacade) request.getSession().getAttribute("companyf");
		
		return cf.getByPriceAndCompId(minPrice, maxPrice);
    
	    }


   /**
    *  Getting List Of All Company Coupons By Date
    * @param date
    * @return
    * @throws CouponNotExistException
    */
	@RequestMapping(value = "/companyws/getcompanycouponsdate/{startDate},{endDate}" , method = RequestMethod.GET , produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)

    public List<Coupon> getAllCompanyCouponsByDate(HttpServletRequest request ,@PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) throws CouponNotExistException{
		
		CompanyFacade cf = (CompanyFacade) request.getSession().getAttribute("companyf");

		
		return cf.getByDateAndCompId(startDate, endDate);
    
	    }









}