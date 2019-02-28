package com.example.demo.Controller;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class MyErrorController implements ErrorController{
	@Override
    public String getErrorPath() {
        return "/error";
    }
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        System.out.println("HandleError : Status : " + status);
        if(status != null){
            Integer statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.UNAUTHORIZED.value()){
                return "401";
            }
            if(statusCode == HttpStatus.FORBIDDEN.value()){
            	return "403";
            }
            if(statusCode == HttpStatus.NOT_FOUND.value()){
                return "404";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()){
                return "500";
            }
        }
        return "error";
    }
    /*
    @RequestMapping("/error")
    public @ResponseBody String handleError1(HttpServletRequest request){
    	
    	 Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
         Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
         return String.format("<html><body><h2>Error Page</h2><div>Status code: <b>%s</b></div>"
                         + "<div>Exception Message: <b>%s</b></div><body></html>",
                 statusCode, exception==null? "N/A": exception.getMessage());
    }
    */
}