package nz.co.validatemynumber.bankaccount;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

@Provider
@Produces("text/xml")
public class ValidateBankAccountNumberResponseWriter implements MessageBodyWriter<ValidateBankAccountNumberResponse>
{
    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
    {
        return type == ValidateBankAccountNumberResponse.class;
    }
    
    @Override
    public long getSize(ValidateBankAccountNumberResponse t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
    {
        return 0;
    }
    
    @Override
    public void writeTo(ValidateBankAccountNumberResponse t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException
    {
        try
        {
            JAXBContext jaxbContext = JAXBContext.newInstance(ValidateBankAccountNumberResponse.class);
            jaxbContext.createMarshaller().marshal(t, entityStream);
        }
        catch (JAXBException jaxbException)
        {
            throw new ProcessingException("Error serializing a ValidateBankAccountNumberResponse to the output stream", jaxbException);
        }
    }
}
