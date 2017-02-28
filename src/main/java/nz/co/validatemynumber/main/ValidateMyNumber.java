package nz.co.validatemynumber.main;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ValidateMyNumber
{
    protected static final Logger LOG = LogManager.getLogger(ValidateMyNumber.class);
    
    @Context
    protected HttpServletRequest request;
    
    protected String getIPAddress()
    {
        String ip = request.getHeader("X-Forwarded-For");
        
        if (isBlankOrNullOrUnknown(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        
        if (isBlankOrNullOrUnknown(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        
        if (isBlankOrNullOrUnknown(ip))
        {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        
        if (isBlankOrNullOrUnknown(ip))
        {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        
        if (isBlankOrNullOrUnknown(ip))
        {
            ip = request.getRemoteAddr();
        }
        
        return ip;
    }
    
    private boolean isBlankOrNullOrUnknown(String ip)
    {
        return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
    }
    
    protected void setRequest(HttpServletRequest request)
    {
        this.request = request;
    }
}
