package org.example.userservice.controller;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;

import jakarta.ws.rs.core.Response;
import org.example.userservice.dto.*;
import org.example.userservice.ejb.*;
import org.example.userservice.entity.User;
import org.example.userservice.util.AuthUtil;
import org.example.userservice.util.ResponseUtil;
import org.example.userservice.util.SessionStore;

import java.util.HashMap;
import java.util.Map;

@Path("/customer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerController {

    @Inject
    private UserBean userBean;

    @Inject
    private WalletBean walletBean;

    @Inject
    private AuthUtil authUtil;

    @POST
    @Path("/register")
    public Object register(RegisterRequest req) {
        User user = userBean.register(req.username, req.password, "CUSTOMER", null);
        walletBean.createWallet(user.getId(), req.balance);
        return ResponseUtil.success("registered", user);
    }

    @POST
    @Path("/login")
    public Object login(LoginRequest req) {

        User user = userBean.login(req.username, req.password);

        if (user == null)
            return ResponseUtil.error("invalid credentials");

        String sessionId = SessionStore.createSession(user);

        Map<String, Object> data = new HashMap<>();
        data.put("sessionId", sessionId);
        data.put("role", user.getRole().name());
        data.put("userId", user.getId());

        return ResponseUtil.success("login success", data);
    }
    @POST
    @Path("/logout")
    public Object logout(@Context HttpHeaders headers) {

        String sessionId = headers.getHeaderString("X-SESSION-ID");

        if (sessionId == null)
            return ResponseUtil.error("no session found");

        SessionStore.remove(sessionId);

        return ResponseUtil.success("logged out successfully", null);
    }

    @GET
    @Path("/balance")
    public Object balance(@Context HttpHeaders headers) {

        User user = authUtil.getUser(headers);

        if (!authUtil.isCustomer(user))
            return ResponseUtil.error("not authorized");

        return ResponseUtil.success(
                "balance",
                walletBean.getBalance(user.getId())
        );
    }

    @POST
    @Path("/add-funds")
    public Object add(AddFundsRequest req, @Context HttpHeaders headers) {

        User user = authUtil.getUser(headers);

        if (user == null)
            return ResponseUtil.error("invalid session");

        if (!authUtil.isCustomer(user))
            return ResponseUtil.error("only customers allowed");

        String result = walletBean.addFunds(user.getId(), req.amount);

        return ResponseUtil.success("result", result);
    }

    /*@POST
    @Path("/wallet/deduct")
    public Object deduct(DeductRequest req) {

        boolean success = walletBean.deduct(req.userId, req.amount);

        if (!success)
            //return error not responseUtl
            return new WalletResponse(false, "insufficient balance");

        return new WalletResponse(true, "deducted");
    }*/
//    @POST
//    @Path("/wallet/deduct")
//    public Response deduct(DeductRequest req) {
//
//        boolean success = walletBean.deduct(req.userId, req.amount);
//
//        if (!success) {
//            return Response
//                    .status(400)
//                    .entity(new WalletResponse(false, "insufficient balance"))
//                    .build();
//        }
//
//        return Response
//                .ok(new WalletResponse(true, "deducted"))
//                .build();
//    }

    @POST
    @Path("/wallet/deduct")
    public Boolean deduct(DeductRequest req) {

        boolean success = walletBean.deduct(req.userId, req.amount);

            return success;

    }


    @POST
    @Path("/wallet/refund")
    public Object refund(DeductRequest req) {

        walletBean.refund(req.userId, req.amount);

        return ResponseUtil.success("refunded", null);
    }

}
