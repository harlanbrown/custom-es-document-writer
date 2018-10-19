package org.nuxeo.sample;

import static org.nuxeo.ecm.automation.jaxrs.io.documents.JsonESDocumentWriter.MIME_TYPE;
import org.nuxeo.ecm.automation.jaxrs.io.documents.JsonESDocumentWriter;

import java.io.IOException;
import java.util.Map;

import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.nuxeo.ecm.core.api.DocumentModel;
import com.fasterxml.jackson.core.JsonGenerator;

@Provider
@Produces({ JsonESDocumentWriter.MIME_TYPE })
public class CustomJsonESDocumentWriter extends JsonESDocumentWriter {
    
    protected static Log log = LogFactory.getLog(CustomJsonESDocumentWriter.class);

    @Override
    public void writeDoc(JsonGenerator jg, DocumentModel doc, String[] schemas, Map<String, String> contextParameters,
            HttpHeaders headers) throws IOException {

        log.error("hi from custom JsonESDocumentWriter");
        jg.writeStartObject();
        writeSystemProperties(jg, doc);
        writeSchemas(jg, doc, schemas);
        writeContextParameters(jg, doc, contextParameters);
        writeCustom(jg);
        jg.writeEndObject();
        jg.flush();
    }

    protected void writeCustom(JsonGenerator jg) throws IOException {
        jg.writeStringField("beep", "boop");
    }

}

