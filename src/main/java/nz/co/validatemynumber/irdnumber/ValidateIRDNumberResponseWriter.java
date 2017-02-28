package nz.co.validatemynumber.irdnumber;

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
public class ValidateIRDNumberResponseWriter implements MessageBodyWriter<ValidateIRDNumberResponse>
{
    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
    {
        return type == ValidateIRDNumberResponse.class;
    }
    
    @Override
    public long getSize(ValidateIRDNumberResponse t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
    {
        return 0;
    }
    
    @Override
    public void writeTo(ValidateIRDNumberResponse t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException
    {
        try
        {
            JAXBContext jaxbContext = JAXBContext.newInstance(ValidateIRDNumberResponse.class);
            jaxbContext.createMarshaller().marshal(t, entityStream);
        }
        catch (JAXBException jaxbException)
        {
            throw new ProcessingException("Error serializing a ValidateIRDNumberResponse to the output stream", jaxbException);
        }
    }
}
