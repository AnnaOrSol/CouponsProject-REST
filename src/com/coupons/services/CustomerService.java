package com.coupons.services;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.coupon.beans.Coupon;
import com.coupon.beans.CouponType;
import com.coupon.couponSystem.CouponSystemSingleton;
import com.coupon.exception.MyException;
import com.coupon.facade.CustomerFacade;
import com.coupon.facade.UserType;
import com.coupons.annotations.LoginFilterAnnotation;
import com.coupons.classesPOJO.ApplicationMessage;
import com.coupons.classesPOJO.LoginInfo;
import com.coupons.classesPOJO.ResponseCodes;

@Path("CustomerService")
public class CustomerService {
	
	@Context
	HttpServletRequest request;
	
	@Path("login")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@LoginFilterAnnotation
	public Object login (LoginInfo loginInfo) {
		try {
		CustomerFacade customer =(CustomerFacade) CouponSystemSingleton.getInstance().login(loginInfo.getUserName(),
				loginInfo.getPassword(), UserType.CUSTOMER);
		if (customer == null)
			return new ApplicationMessage(ResponseCodes.OTHER_ERROR, "the information you have provided is not good.");
		
		HttpSession session = request.getSession();
		session.setAttribute("facade" , customer);
		return new ApplicationMessage(ResponseCodes.SUCCESS, "Success");
		} catch (MyException e) {
			return new ApplicationMessage(ResponseCodes.SYSTEM_EXCEPTION, e.getMessage());
		}
	}

	public CustomerService() {	
	}
	
	
	
	public void purchaseCoupon (Coupon coupon) {
		
	}
	
	
	public Collection<Coupon> getAllPurchasedCoupons () {
		
		return null;
	}
	
	
    public Collection<Coupon> getAllPurchasedCouponsByType (CouponType couponType) {
		
		return null;
	}

    public Collection<Coupon> getAllPurchasedCouponsByPrice (double price) {
	
	return null;
    }
	
	
    
    
    
	
}
