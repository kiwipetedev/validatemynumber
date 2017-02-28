package nz.co.validatemynumber.irdnumber;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "validate-ird-number-response")
public class ValidateIRDNumberResponse
{
    @XmlElement
    private boolean valid;
    
    @XmlElement(name = "formatted-ird-number")
    private String formattedIRDNumber;
    
    public ValidateIRDNumberResponse()
    {
    }
    
    public ValidateIRDNumberResponse(boolean valid, String formattedIRDNumber)
    {
        this.valid = valid;
        this.formattedIRDNumber = formattedIRDNumber;
    }
    
    public boolean isValid()
    {
        return valid;
    }
    
    public String getFormattedIRDNumber()
    {
        return formattedIRDNumber;
    }
}
