package org.zly.blogbackjava.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;

public class SafeSvgValidator implements ConstraintValidator<SafeSvg, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (value.isEmpty()) {
            return true;
        }
        if (value.isBlank()) {
            return false;
        }

        try {
            return hasSvgRoot(SvgSanitizer.sanitize(value));
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private boolean hasSvgRoot(String svg) {
        try {
            XMLStreamReader reader = XMLInputFactory.newFactory()
                    .createXMLStreamReader(new StringReader(svg));
            while (reader.hasNext()) {
                if (reader.next() == XMLStreamConstants.START_ELEMENT) {
                    return "svg".equals(reader.getLocalName());
                }
            }
            return false;
        } catch (XMLStreamException e) {
            return false;
        }
    }
}
