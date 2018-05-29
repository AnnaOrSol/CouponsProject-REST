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

import com.coupon.beans.Company;
import com.coupon.beans.Customer;
import com.coupon.couponSystem.CouponSystemSingleton;
import com.coupon.exception.MyException;
import com.coupon.facade.AdminFacade;
import com.coupon.facade.UserType;
import com.coupons.annotations.LoginFilterAnnotation;
import com.coupons.annotations.SessionFilterAnnotation;
import com.coupons.classesPOJO.ApplicationMessage;
import com.coupons.classesPOJO.LoginInfo;
import com.coupons.classesPOJO.ResponseCodes;

@Path("AdminService")
public class AdminService {

	@Context
	HttpServletRequest request;

	// http://localhost:8080/CouponsWeb2018/rest/AdminService/login
	// Angular 4
	// http:HttpClient @angular/common/http
	// return
	// this.http.post("http://localhost:8080/CouponsWeb2018/rest/AdminService/login",
	// {userName: "123", password: "1234", userType: "ADMIN"}, {withCredentials:
	// true});
	@Path("login")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@LoginFilterAnnotation
	public Object login(LoginInfo loginInfo) {
		try {
			AdminFacade admin = (AdminFacade) CouponSystemSingleton.getInstance().login(loginInfo.getUserName(),
					loginInfo.getPassword(), UserType.ADMIN);
			if (admin == null)
				return new ApplicationMessage(ResponseCodes.OTHER_ERROR, "The information you have provided is not good." );

			HttpSession session = request.getSession();
			session.setAttribute("facade", admin);
			return new ApplicationMessage(ResponseCodes.SUCCESS, "Success") ;
		} catch (MyException e) {
			return new ApplicationMessage(ResponseCodes.SYSTEM_EXCEPTION, e.getMessage());
		}
	}

	@Path("company")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@SessionFilterAnnotation
	public Object createCompany(Company company) {
		HttpSession session = request.getSession();
		AdminFacade admin = (AdminFacade) session.getAttribute("facade");

		try {
			admin.createCompany(company);
			return new ApplicationMessage(ResponseCodes.SUCCESS, "Success") ;
		} catch (MyException e) {
			return new ApplicationMessage(ResponseCodes.SYSTEM_EXCEPTION, e.getMessage());
		}
	}

	// return this.http.delete("url/123", { withCredentials: true});
	@Path("company/{id}")
	@DELETE
	@Consumes(MediaType.TEXT_PLAIN)
	@SessionFilterAnnotation
	public Object removeCompany(@PathParam("id") int id) {
		HttpSession session = request.getSession();
		AdminFacade admin = (AdminFacade) session.getAttribute("facade");

		try {
			admin.removeCompany(admin.getCompany(id));
			return new ApplicationMessage(ResponseCodes.SUCCESS, "Success") ;
		} catch (MyException e) {
			return new ApplicationMessage(ResponseCodes.SYSTEM_EXCEPTION, e.getMessage());
		}
	}

	@Path("company")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@SessionFilterAnnotation
	public Object updateCompany(Company company) {
		HttpSession session = request.getSession();
		AdminFacade admin = (AdminFacade) session.getAttribute("facade");

		try {
			admin.updateCompany(company);
			return new ApplicationMessage(ResponseCodes.SUCCESS, "Success") ;
		} catch (MyException e) {
			return new ApplicationMessage(ResponseCodes.SYSTEM_EXCEPTION, e.getMessage());
		}
	}

	@Path("company/{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@SessionFilterAnnotation
	public Object getCompany(@PathParam("id") int id) {
		HttpSession session = request.getSession();
		AdminFacade admin = (AdminFacade) session.getAttribute("facade");
		try {
			return admin.getCompany(id);
		} catch (MyException e) {
			return new ApplicationMessage(ResponseCodes.SYSTEM_EXCEPTION, e.getMessage());
		}

	}

	@Path("company")
	@GET
	@SessionFilterAnnotation
	public Object getAllCompanies() {
		HttpSession session = request.getSession();
		AdminFacade admin = (AdminFacade) session.getAttribute("facade");
		try {
			return admin.getAllCompanys();
		} catch (MyException e) {
			return new ApplicationMessage(ResponseCodes.SYSTEM_EXCEPTION, e.getMessage());
		}

	}

	@Path("customer")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@SessionFilterAnnotation
	public Object createCustomer(Customer customer) {
		HttpSession session = request.getSession();
		AdminFacade admin = (AdminFacade) session.getAttribute("facade");
		try {
			admin.createCustomer(customer);
			return new ApplicationMessage(ResponseCodes.SUCCESS, "Success") ;
		} catch (MyException e) {
			return new ApplicationMessage(ResponseCodes.SYSTEM_EXCEPTION, e.getMessage());
		}
	}

	@Path("customer/{id}")
	@DELETE
	@Consumes(MediaType.TEXT_PLAIN)
	@SessionFilterAnnotation
	public Object removeCustomer(@PathParam("id") int id) {
		HttpSession session = request.getSession();
		AdminFacade admin = (AdminFacade) session.getAttribute("facade");
		try {
			admin.removeCustomer(admin.getCustomer(id));
			return new ApplicationMessage(ResponseCodes.SUCCESS, "customer has been removed successfuly.") ;
		} catch (MyException e) {
			return new ApplicationMessage(ResponseCodes.SYSTEM_EXCEPTION, e.getMessage());
		}
	}

	@Path("customer")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@SessionFilterAnnotation
	public Object updateCustomer(Customer customer) {
		HttpSession session = request.getSession();
		AdminFacade admin = (AdminFacade) session.getAttribute("facade");
		try {
			admin.updateCustomer(customer);
			return new ApplicationMessage(ResponseCodes.SUCCESS, "customer has been updated successfuly.") ;
		} catch (MyException e) {
			return new ApplicationMessage(ResponseCodes.SYSTEM_EXCEPTION, e.getMessage());
		}
	}

	@Path("customer/{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@SessionFilterAnnotation
	public Object getCustomer(@PathParam("id") int id) {
		HttpSession session = request.getSession();
		AdminFacade admin = (AdminFacade) session.getAttribute("facade");
		try {
			return admin.getCompany(id);
		} catch (MyException e) {
			return new ApplicationMessage(ResponseCodes.SYSTEM_EXCEPTION, e.getMessage());
		}

	}

	@Path("customer")
	@GET
	@SessionFilterAnnotation
	public Object getAllCustomers() {
		HttpSession session = request.getSession();
		AdminFacade admin = (AdminFacade) session.getAttribute("facade");
		try {
			return admin.getAllCustomers();
		} catch (MyException e) {
			return new ApplicationMessage(ResponseCodes.SYSTEM_EXCEPTION, e.getMessage());
		}
	}

}
