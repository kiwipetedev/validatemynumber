package nz.co.validatemynumber.bankaccount;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "validate-bank-account-number-response")
public class ValidateBankAccountNumberResponse
{
    @XmlElement
    private boolean valid;
    
    @XmlElement(name = "formatted-bank-account-number")
    private String formattedBankAccountNumber;
    
    public ValidateBankAccountNumberResponse()
    {
    }
    
    public ValidateBankAccountNumberResponse(boolean valid, String formattedBankAccountNumber)
    {
        this.valid = valid;
        this.formattedBankAccountNumber = formattedBankAccountNumber;
    }
    
    public boolean isValid()
    {
        return valid;
    }
    
    public String getFormattedBankAccountNumber()
    {
        return formattedBankAccountNumber;
    }
}
