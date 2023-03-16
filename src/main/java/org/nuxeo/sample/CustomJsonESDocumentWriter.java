package org.nuxeo.sample;

import com.fasterxml.jackson.core.JsonGenerator;
import java.io.IOException;
import java.util.Map;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.directory.Session;
import org.nuxeo.ecm.directory.api.DirectoryService;
import org.nuxeo.elasticsearch.io.JsonESDocumentWriter;
import org.nuxeo.runtime.api.Framework;

public class CustomJsonESDocumentWriter extends JsonESDocumentWriter {

    @Override
    public void writeESDocument(JsonGenerator jg, DocumentModel doc, String[] schemas, Map<String, String> contextParameters) throws IOException {
        jg.writeStartObject();
        writeSystemProperties(jg, doc);
        writeSchemas(jg, doc, schemas);
        writeContextParameters(jg, doc, contextParameters);
        writeCustom(jg, doc);
        jg.writeEndObject();
        jg.flush();
    }

    protected void writeCustom(JsonGenerator jg, DocumentModel doc) throws IOException {
        if (doc.getType().equals("File")) {
            DirectoryService directoryService = Framework.getService(DirectoryService.class);
            Session session = directoryService.open("custom");
            String id = (String) doc.getPropertyValue("custom:string");
            DocumentModel entry = session.getEntry(id);
            String label = (String) entry.getPropertyValue("label");
          	jg.writeStringField("custom", label);
        }
    }
}
