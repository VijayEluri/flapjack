package flapjack.parser;

import flapjack.layout.FieldDefinition;
import flapjack.layout.RecordLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Breaks up the array of bytes into fields and converts them to Strings
 */
public class StringRecordFieldParser implements RecordFieldParser {
    public Object parse(byte[] bytes, RecordLayout recordLayout) throws ParseException {
        List fields = new ArrayList();

        if (bytes.length > 0) {
            String line = new String(bytes);
            StringFieldParser fieldParser = new StringFieldParser();


            Iterator it = recordLayout.getFieldDefinitions().iterator();
            while (it.hasNext()) {
                FieldDefinition fieldDefinition = (FieldDefinition) it.next();
                try {
                    fields.add(fieldParser.parse(line, fieldDefinition));
                } catch (Exception err) {
                    throw new ParseException(err, recordLayout, fieldDefinition);
                }
            }

        }

        return fields;
    }


}
