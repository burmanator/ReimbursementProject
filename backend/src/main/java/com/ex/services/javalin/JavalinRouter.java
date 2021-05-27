package com.ex.services.javalin;
import com.ex.controller.EmployeeController;
import com.ex.controller.ManagerController;
import com.ex.pojos.Admin;
import com.ex.pojos.Employee;
import com.ex.pojos.Reimbursement;
import io.javalin.Javalin;
import io.javalin.websocket.WsContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JavalinRouter {

    public static void main(String[] args) throws UnknownHostException {

        Logger logger = LogManager.getLogger(Javalin.class);
        EmployeeController employeeController = new EmployeeController();
        ManagerController managerController = new ManagerController();


        Javalin app = Javalin.create(config -> {
            config.enableCorsForAllOrigins();
        }).start(9999);
        app.get("/", ctx -> ctx.render("/webapp/index.html"));


        app.ws("/viewPending", ws->{
            ws.onConnect(ctx -> {
                System.out.println("websocket opened");
            });
        });

        //LOGIN EMPLOYEE
        app.post("/login", ctx -> {
            Employee loginReq = ctx.bodyAsClass(Employee.class);
            int status = employeeController.login(loginReq);
            ctx.res.setContentType("application/json");
            ctx.json(status);
        });


        //VIEW PENDING REIMBURSEMENTS
        app.get("/pending", ctx -> {
            String username =(ctx.req.getParameter("username"));
            String password =(ctx.req.getParameter("password"));
            ctx.res.setContentType("application/json");
            ctx.json(employeeController.viewPendingReimbursements(username,password));
        });

        //VIEW PREV Reimbursements
        app.get("/prevReimbursements", ctx -> {
            String username =(ctx.req.getParameter("username"));
            String password =(ctx.req.getParameter("password"));
            ctx.res.setContentType("application/json");
            ctx.json(employeeController.prevReimbursements(username,password));
        });

        //SUBMIT A NEW REIMBURSEMENT
        app.post("/newReimbursementRequest", ctx -> {
            Reimbursement reimbursement = ctx.bodyAsClass(Reimbursement.class);
            int status = employeeController.submitNewReimbursement(reimbursement);
            ctx.res.setContentType("application/json");
            ctx.json(status);
        });

        //UPDATE USERNAME
        app.post("/updateUsername", ctx -> {
            String currUsername= ctx.req.getParameter("currUsername");
            String newUsername= ctx.req.getParameter("newUsername");
            System.out.println(currUsername);
            System.out.println(newUsername);
            int status = employeeController.updateUsername(currUsername, newUsername);
            ctx.res.setContentType("application/json");
            ctx.json(status);
        });

        //UPDATE EMAIL
        app.post("/updateEmail", ctx -> {
            String username= ctx.req.getParameter("username");
            String newEmail= ctx.req.getParameter("newEmail");
            int status = employeeController.updateEmail(username, newEmail);
            ctx.res.setContentType("application/json");
            ctx.json(status);
        });
        //Update Password
        app.post("/updatePassword", ctx -> {
            int status = employeeController.updatePassword(ctx.req.getParameter("username"),
                    ctx.req.getParameter("password"),
                    ctx.req.getParameter("newPassword1"));
            ctx.res.setContentType("application/json");
            ctx.json(status);
        });

        //Update Password
        app.post("/forgotUpdatePassword", ctx -> {
            String username= ctx.req.getParameter("username");
            String newPassword1= ctx.req.getParameter("password");
            //check if newUsername exist
            boolean result = employeeController.updateForgottenPassword(username, newPassword1);
            ctx.res.setContentType("application/json");
            ctx.json(result);
        });

        //forgot password
        app.post("/forgot", ctx -> {
            Employee employee = ctx.bodyAsClass(Employee.class);
            int status = employeeController.forgotPassword(employee);
            ctx.res.setContentType("application/json");
            ctx.json(status);
        });

        app.get("/getEmail", ctx -> {
            String username = ctx.req.getParameter("username");
            ctx.json(employeeController.getEmail(username));
        });

        //LOGIN ADMIN (MANAGER)
        app.post("/managerLogin", ctx -> {
           Admin admin = ctx.bodyAsClass(Admin.class);
           int status = managerController.login(admin);
            ctx.res.setContentType("application/json");
            ctx.json(status);
        });

        app.get("/getAdminEmail", ctx -> {
            String username = ctx.req.getParameter("username");
            ctx.json(managerController.getEmail(username));
        });

        //create new Admin
        app.post("/newAdmin", ctx->{
           Admin admin = ctx.bodyAsClass(Admin.class);
           int status = managerController.addAdmin(admin);
            ctx.res.setContentType("application/json");
            ctx.json(status);
        });

        //UPDATE USERNAME
        app.post("/updateAdminUsername", ctx -> {
            String currUsername= ctx.req.getParameter("currUsername");
            String newUsername= ctx.req.getParameter("newUsername");
            System.out.println(newUsername);
            int status = managerController.updateAdminUsername(currUsername, newUsername);
            ctx.res.setContentType("application/json");
            ctx.json(status);
        });

        //UPDATE EMAIL
        app.post("/updateAdminEmail", ctx -> {
            String username= ctx.req.getParameter("username");
            String newEmail= ctx.req.getParameter("newEmail");
            int status = managerController.updateAdminEmail(username, newEmail);
            ctx.res.setContentType("application/json");
            ctx.json(status);
        });
        //Update Password
        app.post("/updateAdminPassword", ctx -> {
            int status = managerController.updateAdminPassword(ctx.req.getParameter("username"),
                    ctx.req.getParameter("password"),
                    ctx.req.getParameter("newPassword1"));
            ctx.res.setContentType("application/json");
            ctx.json(status);
        });

        //Update Password
        app.post("/adminForgotUpdatePassword", ctx -> {
            String username= ctx.req.getParameter("username");
            String newPassword1= ctx.req.getParameter("password");
            //check if newUsername exist
            boolean result = managerController.updateForgottenPassword(username, newPassword1);
            ctx.res.setContentType("application/json");
            ctx.json(result);
        });

        //forgot password
        app.post("/adminForgot", ctx -> {
            Admin admin = ctx.bodyAsClass(Admin.class);
            int status = managerController.forgotPassword(admin);
            ctx.res.setContentType("application/json");
            ctx.json(status);
        });

        //create new Employee
        app.post("/newEmployee", ctx -> {
            Employee employee= ctx.bodyAsClass(Employee.class);
            //check if the user name has existed
            int status = employeeController.addEmployee(employee);
            ctx.res.setContentType("application/json");
            ctx.json(status);
        });

        //Manager views all pending reimbursement requests
        app.get("viewAllPending",ctx ->{
            String name= ctx.req.getParameter("name");
            ArrayList<Reimbursement> result = managerController.viewAllPending(name);
            ctx.res.setContentType("application/json");
            ctx.json(result);
        });

        //Approval of Reimbursement
        app.post("approve", ctx -> {
            Reimbursement reimbursement = ctx.bodyAsClass(Reimbursement.class);
            int success = managerController.updateReimbursement(reimbursement);
            ctx.res.setContentType("application/json");
            ctx.json(success);
        });

        //Denial of Reimbursement
        //Approval of Reimbursement
        app.post("deny", ctx -> {
            Reimbursement reimbursement = ctx.bodyAsClass(Reimbursement.class);
            int success = managerController.updateReimbursement(reimbursement);
            ctx.res.setContentType("application/json");
            ctx.json(success);
        });

        //Get all employees
        app.get("getEmployees", ctx -> {
            String name = ctx.req.getParameter("name");
            ArrayList<Employee> result = employeeController.getEmployees(name);
                ctx.json(result);
        });

        //Get all previous reimbursements
        app.get("allPrevReimbursements", ctx -> {
            String name = ctx.req.getParameter("name");
            ArrayList<Reimbursement> result = managerController.getAllPreviousReimbursements(name);
            ctx.json(result);
        });
    }
}