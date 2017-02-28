package nz.co.validatemynumber.irdnumber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.servlet.http.HttpServletRequest;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class TestValidateIRDNumber
{
    private static ValidateIRDNumber validateIRDNumber;
    
    @BeforeClass
    public static void setUp()
    {
        HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(mockedRequest.getRemoteAddr()).thenReturn("0.0.0.0");
        validateIRDNumber = new ValidateIRDNumber();
        validateIRDNumber.setRequest(mockedRequest);
    }
    
    @Test
    public void testIRDNumberRanges()
    {
        ValidateIRDNumberResponse response = validateIRDNumber.isValid("9999999");
        assertFalse(response.isValid());
        assertEquals("9999999", response.getFormattedIRDNumber());
        response = validateIRDNumber.isValid("10000000");
        assertFalse(response.isValid());
        assertEquals("10000000", response.getFormattedIRDNumber());
        response = validateIRDNumber.isValid("150000000");
        assertFalse(response.isValid());
        assertEquals("150000000", response.getFormattedIRDNumber());
        response = validateIRDNumber.isValid("150000001");
        assertFalse(response.isValid());
        assertEquals("150000001", response.getFormattedIRDNumber());
    }
    
    @Test
    public void testInvalidString()
    {
        ValidateIRDNumberResponse response = validateIRDNumber.isValid("abc");
        assertFalse(response.isValid());
        assertEquals("abc", response.getFormattedIRDNumber());
    }
    
    @Test
    public void testCheckDigit8DigitIRDNumber()
    {
        ValidateIRDNumberResponse response = validateIRDNumber.isValid("22091540");
        assertFalse(response.isValid());
        assertEquals("22091540", response.getFormattedIRDNumber());
        response = validateIRDNumber.isValid("22091541");
        assertFalse(response.isValid());
        assertEquals("22091541", response.getFormattedIRDNumber());
        response = validateIRDNumber.isValid("22091542");
        assertFalse(response.isValid());
        assertEquals("22091542", response.getFormattedIRDNumber());
        response = validateIRDNumber.isValid("22091543");
        assertFalse(response.isValid());
        assertEquals("22091543", response.getFormattedIRDNumber());
        response = validateIRDNumber.isValid("22091544");
        assertFalse(response.isValid());
        assertEquals("22091544", response.getFormattedIRDNumber());
        response = validateIRDNumber.isValid("22091545");
        assertFalse(response.isValid());
        assertEquals("22091545", response.getFormattedIRDNumber());
        response = validateIRDNumber.isValid("22091546");
        assertFalse(response.isValid());
        assertEquals("22091546", response.getFormattedIRDNumber());
        response = validateIRDNumber.isValid("22091547");
        assertFalse(response.isValid());
        assertEquals("22091547", response.getFormattedIRDNumber());
        response = validateIRDNumber.isValid("22091548");
        assertFalse(response.isValid());
        assertEquals("22091548", response.getFormattedIRDNumber());
        response = validateIRDNumber.isValid("22091549");
        assertTrue(response.isValid());
        assertEquals("22-091-549", response.getFormattedIRDNumber());
    }
    
    @Test
    public void testModulusZeroIRDNumber()
    {
        ValidateIRDNumberResponse response = validateIRDNumber.isValid("22091530");
        assertTrue(response.isValid());
        assertEquals("22-091-530", response.getFormattedIRDNumber());
        response = validateIRDNumber.isValid("22091531");
        assertFalse(response.isValid());
        assertEquals("22091531", response.getFormattedIRDNumber());
        response = validateIRDNumber.isValid("22091532");
        assertFalse(response.isValid());
        assertEquals("22091532", response.getFormattedIRDNumber());
        response = validateIRDNumber.isValid("22091533");
        assertFalse(response.isValid());
        assertEquals("22091533", response.getFormattedIRDNumber());
        response = validateIRDNumber.isValid("22091534");
        assertFalse(response.isValid());
        assertEquals("22091534", response.getFormattedIRDNumber());
        response = validateIRDNumber.isValid("22091535");
        assertFalse(response.isValid());
        assertEquals("22091535", response.getFormattedIRDNumber());
        response = validateIRDNumber.isValid("22091536");
        assertFalse(response.isValid());
        assertEquals("22091536", response.getFormattedIRDNumber());
        response = validateIRDNumber.isValid("22091537");
        assertFalse(response.isValid());
        assertEquals("22091537", response.getFormattedIRDNumber());
        response = validateIRDNumber.isValid("22091538");
        assertFalse(response.isValid());
        assertEquals("22091538", response.getFormattedIRDNumber());
        response = validateIRDNumber.isValid("22091539");
        assertFalse(response.isValid());
        assertEquals("22091539", response.getFormattedIRDNumber());
    }
    
    @Test
    public void testIRDNumberWithDash()
    {
        ValidateIRDNumberResponse response = validateIRDNumber.isValid("22-091-549");
        assertTrue(response.isValid());
        assertEquals("22-091-549", response.getFormattedIRDNumber());
        response = validateIRDNumber.isValid("022-091-549");
        assertTrue(response.isValid());
        assertEquals("022-091-549", response.getFormattedIRDNumber());
    }
    
    @Test
    public void testWeightFactors2()
    {
        assertFalse(validateIRDNumber.isValid("14757660").isValid());
        assertFalse(validateIRDNumber.isValid("14757661").isValid());
        assertFalse(validateIRDNumber.isValid("14757662").isValid());
        assertFalse(validateIRDNumber.isValid("14757663").isValid());
        assertFalse(validateIRDNumber.isValid("14757664").isValid());
        assertFalse(validateIRDNumber.isValid("14757665").isValid());
        assertFalse(validateIRDNumber.isValid("14757666").isValid());
        assertTrue(validateIRDNumber.isValid("14757667").isValid());
        assertFalse(validateIRDNumber.isValid("14757668").isValid());
        assertFalse(validateIRDNumber.isValid("14757669").isValid());
    }
    
    @Test
    public void testWeightFactors2Modulus0()
    {
        assertTrue(validateIRDNumber.isValid("10000670").isValid());
        assertFalse(validateIRDNumber.isValid("10000671").isValid());
        assertFalse(validateIRDNumber.isValid("10000672").isValid());
        assertFalse(validateIRDNumber.isValid("10000673").isValid());
        assertFalse(validateIRDNumber.isValid("10000674").isValid());
        assertFalse(validateIRDNumber.isValid("10000675").isValid());
        assertFalse(validateIRDNumber.isValid("10000676").isValid());
        assertFalse(validateIRDNumber.isValid("10000677").isValid());
        assertFalse(validateIRDNumber.isValid("10000678").isValid());
        assertFalse(validateIRDNumber.isValid("10000679").isValid());
    }
    
    @Test
    public void testIRDExamples()
    {
        assertTrue(validateIRDNumber.isValid("49091850").isValid());
        assertFalse(validateIRDNumber.isValid("49091851").isValid());
        assertFalse(validateIRDNumber.isValid("49091852").isValid());
        assertFalse(validateIRDNumber.isValid("49091853").isValid());
        assertFalse(validateIRDNumber.isValid("49091854").isValid());
        assertFalse(validateIRDNumber.isValid("49091855").isValid());
        assertFalse(validateIRDNumber.isValid("49091856").isValid());
        assertFalse(validateIRDNumber.isValid("49091857").isValid());
        assertFalse(validateIRDNumber.isValid("49091858").isValid());
        assertFalse(validateIRDNumber.isValid("49091859").isValid());
        
        assertFalse(validateIRDNumber.isValid("35901980").isValid());
        assertTrue(validateIRDNumber.isValid("35901981").isValid());
        assertFalse(validateIRDNumber.isValid("35901982").isValid());
        assertFalse(validateIRDNumber.isValid("35901983").isValid());
        assertFalse(validateIRDNumber.isValid("35901984").isValid());
        assertFalse(validateIRDNumber.isValid("35901985").isValid());
        assertFalse(validateIRDNumber.isValid("35901986").isValid());
        assertFalse(validateIRDNumber.isValid("35901987").isValid());
        assertFalse(validateIRDNumber.isValid("35901988").isValid());
        assertFalse(validateIRDNumber.isValid("35901989").isValid());
        
        assertFalse(validateIRDNumber.isValid("49098570").isValid());
        assertFalse(validateIRDNumber.isValid("49098571").isValid());
        assertFalse(validateIRDNumber.isValid("49098572").isValid());
        assertFalse(validateIRDNumber.isValid("49098573").isValid());
        assertFalse(validateIRDNumber.isValid("49098574").isValid());
        assertFalse(validateIRDNumber.isValid("49098575").isValid());
        assertTrue(validateIRDNumber.isValid("49098576").isValid());
        assertFalse(validateIRDNumber.isValid("49098577").isValid());
        assertFalse(validateIRDNumber.isValid("49098578").isValid());
        assertFalse(validateIRDNumber.isValid("49098579").isValid());
        
        assertFalse(validateIRDNumber.isValid("136410130").isValid());
        assertFalse(validateIRDNumber.isValid("136410131").isValid());
        assertTrue(validateIRDNumber.isValid("136410132").isValid());
        assertFalse(validateIRDNumber.isValid("136410133").isValid());
        assertFalse(validateIRDNumber.isValid("136410134").isValid());
        assertFalse(validateIRDNumber.isValid("136410135").isValid());
        assertFalse(validateIRDNumber.isValid("136410136").isValid());
        assertFalse(validateIRDNumber.isValid("136410137").isValid());
        assertFalse(validateIRDNumber.isValid("136410138").isValid());
        assertFalse(validateIRDNumber.isValid("136410139").isValid());
        
        assertFalse(validateIRDNumber.isValid("136410130").isValid());
        assertFalse(validateIRDNumber.isValid("136410131").isValid());
        assertTrue(validateIRDNumber.isValid("136410132").isValid());
        assertFalse(validateIRDNumber.isValid("136410133").isValid());
        assertFalse(validateIRDNumber.isValid("136410134").isValid());
        assertFalse(validateIRDNumber.isValid("136410135").isValid());
        assertFalse(validateIRDNumber.isValid("136410136").isValid());
        assertFalse(validateIRDNumber.isValid("136410137").isValid());
        assertFalse(validateIRDNumber.isValid("136410138").isValid());
        assertFalse(validateIRDNumber.isValid("136410139").isValid());
        
        assertFalse(validateIRDNumber.isValid("9125568").isValid());
    }
}
