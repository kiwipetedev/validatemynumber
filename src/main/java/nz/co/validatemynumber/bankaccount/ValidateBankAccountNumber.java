package nz.co.validatemynumber.bankaccount;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import nz.co.validatemynumber.main.ValidateMyNumber;

/**
 * This class implements a RESTful web service for validating New Zealand bank account numbers.
 * These are numbers issued to customers by New Zealand banks. They are comprised of four parts:
 * a bank code of 2 digits, a branch code of up to 4 digits, an account number of up to 8 digits,
 * and a suffix of up to 4 digits. There are various algorithms for validating bank account numbers
 * which depends on the bank code.
 */
@Path("bankaccount/{bankaccount}")
public class ValidateBankAccountNumber extends ValidateMyNumber
{
    private static final Properties props;
    
    static
    {
        props = new Properties();
        
        try
        {
            InputStream is = ValidateBankAccountNumber.class.getClassLoader().getResourceAsStream("bankaccount.properties");
            
            if (is != null)
            {
                props.load(is);
            }
            else
            {
                throw new FileNotFoundException("Cannot find 'bankaccount.properties'");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Validates a bank account number. Bank account number can be specified in one of the
     * following formats:
     * <ul>
     * <li>999999999999999999 (18 digits)
     * <li>99-9999-[9]9999999-[99]99 (bank code of 2 digits, branch code of 4 digits, account number of 7 or 8 digits, suffix of 2 - 4
     * digits
     * </ul>
     * @param bankAccountNumber
     * @return
     * @throws IOException
     */
    @GET
    public ValidateBankAccountNumberResponse isValid(@PathParam("bankaccount") String bankAccountNumber) throws IOException
    {
        String ipAddress = getIPAddress();
        LOG.info("Validating bank account number {} from ip address {}", bankAccountNumber, ipAddress);
        
        if (!bankAccountNumber.matches("[0-9]{18}") && !bankAccountNumber.matches("[0-9]{2}-[0-9]{2,4}-[0-9]{7,8}-[0-9]{2,4}"))
        {
            return new ValidateBankAccountNumberResponse(false, bankAccountNumber);
        }
        
        String bankCode;
        String branch;
        String account;
        String suffix;
        
        if (bankAccountNumber.contains("-"))
        {
            String[] accountNumberParts = bankAccountNumber.split("-");
            bankCode = String.format("%1$2s", accountNumberParts[0]).replace(' ', '0');
            branch = String.format("%1$4s", accountNumberParts[1]).replace(' ', '0');
            account = String.format("%1$8s", accountNumberParts[2]).replace(' ', '0');
            suffix = String.format("%1$4s", accountNumberParts[3]).replace(' ', '0');
        }
        else
        {
            bankCode = bankAccountNumber.substring(0, 2);
            branch = bankAccountNumber.substring(2, 6);
            account = bankAccountNumber.substring(6, 14);
            suffix = bankAccountNumber.substring(14);
        }
        
        if (!isValidBranch(bankCode, branch))
        {
            return new ValidateBankAccountNumberResponse(false, bankAccountNumber);
        }
        
        BankAccountValidator validator;
        
        if (bankCode.equals("08"))
        {
            validator = new AlgorithmDValidator();
        }
        else if (bankCode.equals("09"))
        {
            validator = new AlgorithmEValidator();
        }
        else if (bankCode.equals("25") || bankCode.equals("33"))
        {
            validator = new AlgorithmFValidator();
        }
        else if (bankCode.equals("26") || bankCode.equals("28") || bankCode.equals("29"))
        {
            validator = new AlgorithmGValidator();
        }
        else if (bankCode.equals("31"))
        {
            validator = new AlgorithmXValidator();
        }
        else
        {
            if (new BigInteger(account).compareTo(new BigInteger("00990000")) < 0)
            {
                validator = new AlgorithmAValidator();
            }
            else
            {
                validator = new AlgorithmBValidator();
            }
        }
        
        boolean valid = validator.isValid(bankCode, branch, account, suffix);
        StringBuilder bankAccountNumberFormatted = new StringBuilder();
        
        if (valid)
        {
            bankAccountNumberFormatted.append(bankCode);
            bankAccountNumberFormatted.append("-");
            bankAccountNumberFormatted.append(branch);
            bankAccountNumberFormatted.append("-");
            bankAccountNumberFormatted.append(account);
            bankAccountNumberFormatted.append("-");
            bankAccountNumberFormatted.append(suffix);
        }
        else
        {
            bankAccountNumberFormatted.append(bankAccountNumber);
        }
        
        return new ValidateBankAccountNumberResponse(valid, bankAccountNumberFormatted.toString());
    }
    
    private boolean isValidBranch(String bankCode, String branch)
    {
        String validBranches = props.getProperty(bankCode);
        
        if (validBranches == null)
        {
            return false;
        }
        
        String[] branches = validBranches.split("\\s*,\\s*");
        boolean validBranch = false;
        
        for (String branchRange : branches)
        {
            String branchRangeRegex = "^(\\d+)-(\\d+)$";
            Pattern pattern = Pattern.compile(branchRangeRegex);
            Matcher matcher = pattern.matcher(branchRange);
            
            if (matcher.matches())
            {
                String branchStart = matcher.group(1);
                String branchEnd = matcher.group(2);
                
                if (Integer.parseInt(branchStart) <= Integer.parseInt(branch) && Integer.parseInt(branchEnd) >= Integer.parseInt(branch))
                {
                    validBranch = true;
                    break;
                }
            }
            else
            {
                String branchRegex = "^(\\d+)$";
                pattern = Pattern.compile(branchRegex);
                matcher = pattern.matcher(branchRange);
                
                if (matcher.matches() && matcher.group(1).equals(branch))
                {
                    validBranch = true;
                    break;
                }
            }
        }
        
        return validBranch;
    }
    
    @Override
    protected void setRequest(HttpServletRequest request)
    {
        super.setRequest(request);
    }
}
