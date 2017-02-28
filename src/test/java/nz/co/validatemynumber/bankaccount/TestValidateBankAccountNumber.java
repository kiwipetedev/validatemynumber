package nz.co.validatemynumber.bankaccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class TestValidateBankAccountNumber
{
    private static ValidateBankAccountNumber validateBankAccountNumber;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(mockedRequest.getRemoteAddr()).thenReturn("0.0.0.0");
        validateBankAccountNumber = new ValidateBankAccountNumber();
        validateBankAccountNumber.setRequest(mockedRequest);
    }
    
    @Test
    public void testInvalidFormat() throws IOException
    {
        ValidateBankAccountNumberResponse response = validateBankAccountNumber.isValid("11111");
        assertFalse(response.isValid());
        assertEquals("11111", response.getFormattedBankAccountNumber());
    }
    
    @Test
    public void testInvalidBank() throws IOException
    {
        ValidateBankAccountNumberResponse response = validateBankAccountNumber.isValid("04-0001-00000000-0000");
        assertFalse(response.isValid());
        assertEquals("04-0001-00000000-0000", response.getFormattedBankAccountNumber());
    }
    
    @Test
    public void testInvalidBranch() throws IOException
    {
        ValidateBankAccountNumberResponse response = validateBankAccountNumber.isValid("01-0000-00000000-0000");
        assertFalse(response.isValid());
        assertEquals("01-0000-00000000-0000", response.getFormattedBankAccountNumber());
    }
    
    @Test
    public void testAlgorithmA() throws IOException
    {
        ValidateBankAccountNumberResponse response = validateBankAccountNumber.isValid("01-0001-00000000-0000");
        assertFalse(response.isValid());
        assertEquals("01-0001-00000000-0000", response.getFormattedBankAccountNumber());
        response = validateBankAccountNumber.isValid("01-902-0068389-00");
        assertTrue(response.isValid());
        assertEquals("01-0902-00068389-0000", response.getFormattedBankAccountNumber());
        response = validateBankAccountNumber.isValid("010902000683890000");
        assertTrue(response.isValid());
        assertEquals("01-0902-00068389-0000", response.getFormattedBankAccountNumber());
    }
    
    @Test
    public void testAlgorithmB() throws IOException
    {
        ValidateBankAccountNumberResponse response = validateBankAccountNumber.isValid("123450009900080000");
        assertTrue(response.isValid());
        assertEquals("12-3450-00990008-0000", response.getFormattedBankAccountNumber());
        response = validateBankAccountNumber.isValid("123450009900070000");
        assertFalse(response.isValid());
        assertEquals("123450009900070000", response.getFormattedBankAccountNumber());
    }
    
    @Test
    public void testAlgorithmD() throws IOException
    {
        ValidateBankAccountNumberResponse response = validateBankAccountNumber.isValid("08-6523-1954512-001");
        assertTrue(response.isValid());
        assertEquals("08-6523-01954512-0001", response.getFormattedBankAccountNumber());
        response = validateBankAccountNumber.isValid("086523019545120001");
        assertTrue(response.isValid());
        assertEquals("08-6523-01954512-0001", response.getFormattedBankAccountNumber());
        response = validateBankAccountNumber.isValid("086523019545130001");
        assertFalse(response.isValid());
        assertEquals("086523019545130001", response.getFormattedBankAccountNumber());
    }
    
    @Test
    public void testAlgorithmE() throws IOException
    {
        ValidateBankAccountNumberResponse response = validateBankAccountNumber.isValid("09-0000-00000000-0000");
        assertTrue(response.isValid());
        assertEquals("09-0000-00000000-0000", response.getFormattedBankAccountNumber());
        response = validateBankAccountNumber.isValid("09-0000-11111111-1111");
        assertFalse(response.isValid());
        assertEquals("09-0000-11111111-1111", response.getFormattedBankAccountNumber());
    }
    
    @Test
    public void testAlgorithmF() throws IOException
    {
        ValidateBankAccountNumberResponse response = validateBankAccountNumber.isValid("25-2500-12345677-0000");
        assertTrue(response.isValid());
        assertEquals("25-2500-12345677-0000", response.getFormattedBankAccountNumber());
        response = validateBankAccountNumber.isValid("25-2500-12345678-0000");
        assertFalse(response.isValid());
        assertEquals("25-2500-12345678-0000", response.getFormattedBankAccountNumber());
        response = validateBankAccountNumber.isValid("33-6700-12345677-0000");
        assertTrue(response.isValid());
        assertEquals("33-6700-12345677-0000", response.getFormattedBankAccountNumber());
    }
    
    @Test
    public void testAlgorithmG() throws IOException
    {
        ValidateBankAccountNumberResponse response = validateBankAccountNumber.isValid("26-2600-0320871-032");
        assertTrue(response.isValid());
        assertEquals("26-2600-00320871-0032", response.getFormattedBankAccountNumber());
        response = validateBankAccountNumber.isValid("262600003208710032");
        assertTrue(response.isValid());
        assertEquals("26-2600-00320871-0032", response.getFormattedBankAccountNumber());
        response = validateBankAccountNumber.isValid("262600003208710033");
        assertFalse(response.isValid());
        assertEquals("262600003208710033", response.getFormattedBankAccountNumber());
        response = validateBankAccountNumber.isValid("28-2149-0320871-0032");
        assertTrue(response.isValid());
        assertEquals("28-2149-00320871-0032", response.getFormattedBankAccountNumber());
        response = validateBankAccountNumber.isValid("29-2150-0320871-0032");
        assertTrue(response.isValid());
        assertEquals("29-2150-00320871-0032", response.getFormattedBankAccountNumber());
    }
    
    @Test
    public void testAlgorithmX() throws IOException
    {
        ValidateBankAccountNumberResponse response = validateBankAccountNumber.isValid("31-1000-12345678-0000");
        assertFalse(response.isValid());
        assertEquals("31-1000-12345678-0000", response.getFormattedBankAccountNumber());
        response = validateBankAccountNumber.isValid("31-2800-12345678-0000");
        assertTrue(response.isValid());
        assertEquals("31-2800-12345678-0000", response.getFormattedBankAccountNumber());
    }
}
