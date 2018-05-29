package com.coupons.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import com.coupon.facade.AdminFacade;
import com.coupon.facade.UserType;

@Path("SessionService")
public class SessionService {

	@Context private HttpServletRequest httpRequest;
	
	@GET
	@Path("logout")
	public void logout() {
		this.httpRequest.getSession().invalidate();
	}
	
	@GET
	@Path("CheckSession")
	public Object checkSession() {
		HttpSession session = this.httpRequest.getSession();
		if(session.getAttribute("facade") == null) 
			return UserType.GUEST;
		if(session.getAttribute("facade") instanceof AdminFacade) 
			return UserType.ADMIN;
		if(session.getAttribute("facade") instanceof AdminFacade) 
			return UserType.COMPANY;
		return UserType.CUSTOMER;
	}
	
	
}
