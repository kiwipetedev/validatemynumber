package nz.co.validatemynumber.irdnumber;

import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import nz.co.validatemynumber.main.ValidateMyNumber;

/**
 * This class implements a RESTful web service for validating New Zealand IRD numbers. These are numbers issued to taxpayers by New
 * Zealand's tax administration authority, and are comprised of 8 or 9 digits, made up of a 7- or 8-digit base number and a check
 * digit. This web service accepts a full IRD number and verifies that the check digit is indeed the one expected.
 */
@Path("irdnumber/{irdnumber}")
public class ValidateIRDNumber extends ValidateMyNumber
{
    private static final long MIN_IRD_NUMBER = 10000000; // 10-000-000
    private static final long MAX_IRD_NUMBER = 150000000; // 150-000-000
    private static final int[] WEIGHT_FACTORS_1 = {3, 2, 7, 6, 5, 4, 3, 2};
    private static final int[] WEIGHT_FACTORS_2 = {7, 4, 3, 2, 5, 2, 7, 6};
    
    /**
     * Verifies that the IRD number passed in has the correct check digit. The IRD number can be in one of the following formats:
     * <ul>
     * <li>99999999 (8 digits)
     * <li>999999999 (9 digits)
     * <li>99-999-999 (8 digits formatted with hyphens in the positions indicated)
     * <li>999-999-999 (9 digits formatted with hyphens in the positions indicated)
     * </ul>
     * Note for 9 digit IRD numbers, with or without hyphens, the leading digit must be zero.
     * 
     * @param irdNumber
     * @return true or false indicating if the IRD number has the correct check digit
     */
    @GET
    public ValidateIRDNumberResponse isValid(@PathParam("irdnumber") String irdNumber)
    {
        String ipAddress = getIPAddress();
        LOG.info("Validating IRD Number {} from ip address {}", irdNumber, ipAddress);
        
        if (!irdNumber.matches("[0-9]{8}") && !irdNumber.matches("[0-9]{9}") && !irdNumber.matches("[0-9]{2,3}-[0-9]{3}-[0-9]{3}"))
        {
            return new ValidateIRDNumberResponse(false, irdNumber);
        }
        
        irdNumber = irdNumber.replace("-", "");
        BigInteger bigInteger = new BigInteger(irdNumber);
        
        if (bigInteger.compareTo(BigInteger.valueOf(MIN_IRD_NUMBER)) <= 0)
        {
            return new ValidateIRDNumberResponse(false, irdNumber);
        }
        
        if (bigInteger.compareTo(BigInteger.valueOf(MAX_IRD_NUMBER)) >= 0)
        {
            return new ValidateIRDNumberResponse(false, irdNumber);
        }
        
        StringBuilder irdNumberDigitsPadded = new StringBuilder(irdNumber);
        
        if (irdNumber.length() == 8)
        {
            irdNumberDigitsPadded.insert(0, "0");
        }
        
        String baseNumber = irdNumberDigitsPadded.substring(0, 8);
        int modulus = calculateModulus(baseNumber, WEIGHT_FACTORS_1);
        int checkDigit;
        
        if (modulus == 0)
        {
            checkDigit = 0;
        }
        else
        {
            checkDigit = 11 - modulus;
            
            if (checkDigit > 9)
            {
                modulus = calculateModulus(baseNumber, WEIGHT_FACTORS_2);
                
                if (modulus == 0)
                {
                    checkDigit = 0;
                }
                else
                {
                    checkDigit = 11 - modulus;
                }
            }
        }
        
        if (Integer.parseInt(irdNumberDigitsPadded.substring(8, 9)) == checkDigit)
        {
            // Format the returned IRD number as [0]12-345-678
            StringBuilder irdNumberFormatted = new StringBuilder(irdNumber);
            
            if (irdNumber.length() == 8)
            {
                irdNumberFormatted.insert(2, "-");
            }
            else
            {
                irdNumberFormatted.insert(3, "-");
            }
            
            irdNumberFormatted.insert(irdNumber.length() - 2, "-");
            return new ValidateIRDNumberResponse(true, irdNumberFormatted.toString());
        }
        
        return new ValidateIRDNumberResponse(false, irdNumber);
    }
    
    private int calculateModulus(String baseNumber, int[] weightFactors)
    {
        int sum = 0;
        
        for (int i = 0; i < weightFactors.length; i++)
        {
            sum += Integer.parseInt(String.valueOf(baseNumber.charAt(i))) * weightFactors[i];
        }
        
        int modulus = sum % 11;
        return modulus;
    }
    
    @Override
    protected void setRequest(HttpServletRequest request)
    {
        super.setRequest(request);
    }
}
