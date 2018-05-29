package com.coupons.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.coupon.beans.Coupon;
import com.coupon.beans.CouponType;
import com.coupon.couponSystem.CouponSystemSingleton;
import com.coupon.exception.MyException;
import com.coupon.facade.CompanyFacade;
import com.coupons.annotations.LoginFilterAnnotation;
import com.coupons.annotations.SessionFilterAnnotation;
import com.coupons.classesPOJO.ApplicationMessage;
import com.coupons.classesPOJO.LoginInfo;
import com.coupons.classesPOJO.ResponseCodes;

@Path("CompanyService")
public class CompanyService {

	@Context
	HttpServletRequest request;

	@Path("login")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@LoginFilterAnnotation
	public Object login(LoginInfo loginInfo) {
		try {
			CompanyFacade company = (CompanyFacade) CouponSystemSingleton.getInstance().login(loginInfo.getUserName(),
					loginInfo.getPassword(), loginInfo.getUserType());
			if (company == null)
				return new ApplicationMessage(ResponseCodes.OTHER_ERROR,
						"The information you have provided is not good.");

			HttpSession session = request.getSession();
			session.setAttribute("facade", company);
			return new ApplicationMessage(ResponseCodes.SUCCESS, "Success");
		} catch (MyException e) {
			return new ApplicationMessage(ResponseCodes.SYSTEM_EXCEPTION, e.getMessage());
		}

	}

	public CompanyService() {
	}

	@Path("coupon")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@SessionFilterAnnotation
	public Object createCoupon(Coupon coupon) {
		HttpSession session = request.getSession();
		CompanyFacade company = (CompanyFacade) session.getAttribute("facade");
		System.out.println(coupon);
		try {
			company.createCoupon(coupon);
			return new ApplicationMessage(ResponseCodes.SUCCESS, "Success");
		} catch (MyException e) {
			return new ApplicationMessage(ResponseCodes.SYSTEM_EXCEPTION, e.getMessage());
		}
	}

	@Path("coupon/{id}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@SessionFilterAnnotation
	public Object removeCoupon(@PathParam("id") int id) {
		HttpSession session = request.getSession();
		CompanyFacade company = (CompanyFacade) session.getAttribute("facade");

		try {
			company.removeCoupon(company.getCoupon(id));
			return new ApplicationMessage(ResponseCodes.SUCCESS, "coupon have been removed successfully");

		} catch (MyException e) {
			return new ApplicationMessage(ResponseCodes.SYSTEM_EXCEPTION, e.getMessage());
		}

	}

	@Path("coupon")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@SessionFilterAnnotation
	public Object updateCoupon(Coupon coupon) {
		HttpSession session = request.getSession();
		CompanyFacade company = (CompanyFacade) session.getAttribute("facade");

		try {
			company.updateCoupon(coupon);
			return new ApplicationMessage(ResponseCodes.SUCCESS, "Success");
		} catch (MyException e) {
			return new ApplicationMessage(ResponseCodes.SYSTEM_EXCEPTION, e.getMessage());
		}
	}

	@Path("coupon/{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@SessionFilterAnnotation
	public Object getCoupon(@PathParam("id") int id) {
		HttpSession session = request.getSession();
		CompanyFacade company = (CompanyFacade) session.getAttribute("facade");

		try {
			return company.getCoupon(id);
		} catch (MyException e) {
			return new ApplicationMessage(ResponseCodes.SYSTEM_EXCEPTION, e.getMessage());
		}
	}

	@Path("coupon")
	@GET
	@SessionFilterAnnotation
	public Object getAllCoupon() {
		HttpSession session = request.getSession();
		CompanyFacade company = (CompanyFacade) session.getAttribute("facade");

		try {
			return company.getAllCoupons();
		} catch (MyException e) {
			return new ApplicationMessage(ResponseCodes.SYSTEM_EXCEPTION, e.getMessage());
		}
	}

	@Path("couponByType/{couponType}")
	@GET
	@SessionFilterAnnotation
	public Object getAllCouponByType(@PathParam("couponType") CouponType couponType) {
		HttpSession session = request.getSession();
		CompanyFacade company = (CompanyFacade) session.getAttribute("facade");

		try {
			return company.getCouponByType(couponType);
		} catch (MyException e) {
			return new ApplicationMessage(ResponseCodes.SYSTEM_EXCEPTION, e.getMessage());
		}

	}

}
