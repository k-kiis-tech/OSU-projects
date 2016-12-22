package osu.cs362.URLValidator;

import static org.junit.Assert.*;
import org.junit.Test;

public class DomainTest{
    // Test valid domain names with isValid
    @Test
    public void validDomain(){
        DomainValidator dv = DomainValidator.getInstance(true);

        // Infrastructure
        assertTrue(dv.isValid("xyz.arpa"));
        
        // Generic
        assertTrue(dv.isValid("xyz.com"));
        assertTrue(dv.isValid("xyz.int"));
        assertTrue(dv.isValid("xyz.mil"));
        
        // Country code
        assertTrue(dv.isValid("xyz.ac"));
        assertTrue(dv.isValid("xyz.it"));
        assertTrue(dv.isValid("xyz.zw"));
        
        // Local
        assertTrue(dv.isValid("xyz.localhost"));
        assertTrue(dv.isValid("xyz.localdomain"));
    }

    // Test invalid domain names with isValid
    @Test
    public void invalidDomain(){
        DomainValidator dv = DomainValidator.getInstance(); 

        assertFalse(dv.isValid("xyz"));
        assertFalse(dv.isValid("xyz.madeuptld"));
        assertFalse(dv.isValid("xyz.123"));
        assertFalse(dv.isValid("xyz*com"));
        assertFalse(dv.isValid(""));
    }

    // Test valid TLDs with isValidTld
    @Test
    public void validTld(){
        DomainValidator dv = DomainValidator.getInstance(true);

        // Infrastructure
        assertTrue(dv.isValidTld(".arpa"));

        // Generic
        assertTrue(dv.isValidTld(".com"));
        assertTrue(dv.isValidTld(".int"));
        assertTrue(dv.isValidTld(".mil"));

        // Country code
        assertTrue(dv.isValidTld(".ac"));
        assertTrue(dv.isValidTld(".it"));
        assertTrue(dv.isValidTld(".zw"));

        // Local
        assertTrue(dv.isValidTld(".localhost"));
        assertTrue(dv.isValidTld(".localdomain"));
    }
 
    // Test invalid TLDs with isValidTld
    @Test
    public void invalidTld(){
        DomainValidator dv = DomainValidator.getInstance();

        assertFalse(dv.isValidTld(".madeuptlx"));
        assertFalse(dv.isValidTld(".123"));
        assertFalse(dv.isValidTld(".***"));
        assertFalse(dv.isValidTld(""));
    }

    // Test valid infrastructure TLDs with isValidInfrastructureTld
    @Test
    public void validInfraTld(){
        DomainValidator dv = DomainValidator.getInstance();

        assertTrue(dv.isValidInfrastructureTld(".arpa"));
    }

    // Test invalid infrastructure TLDs with isValidInfrastructureTld
    @Test
    public void invalidInfraTld(){
        DomainValidator dv = DomainValidator.getInstance();

        assertFalse(dv.isValidInfrastructureTld(".com"));
        assertFalse(dv.isValidInfrastructureTld(".localhost"));
        assertFalse(dv.isValidInfrastructureTld(".madeuptld"));
        assertFalse(dv.isValidInfrastructureTld(".123"));
        assertFalse(dv.isValidInfrastructureTld(".***"));
        assertFalse(dv.isValidInfrastructureTld(""));
    }

    // Test valid generic TLDs with isValidGenericTld
    @Test
    public void validGenericTld(){
        DomainValidator dv = DomainValidator.getInstance();

        assertTrue(dv.isValidGenericTld(".com"));
        assertTrue(dv.isValidGenericTld(".org"));
        assertTrue(dv.isValidGenericTld(".mil"));
    }

    // Test invalid generic TLDs with isValidGenericTld
    @Test
    public void invalidGenericTld(){
        DomainValidator dv = DomainValidator.getInstance();

        assertFalse(dv.isValidGenericTld(".arpa"));
        assertFalse(dv.isValidGenericTld(".localhost"));
        assertFalse(dv.isValidGenericTld(".madeuptld"));
        assertFalse(dv.isValidGenericTld(".123"));
        assertFalse(dv.isValidGenericTld(".***"));
        assertFalse(dv.isValidGenericTld(""));
    }

    // Test valid country code TLDs with isValidCountryCodeTld
    @Test
    public void validCountryTld(){
        DomainValidator dv = DomainValidator.getInstance();

        assertTrue(dv.isValidCountryCodeTld(".ac"));
        assertTrue(dv.isValidCountryCodeTld(".it"));
        assertTrue(dv.isValidCountryCodeTld(".zw"));
    }

    // Test invalid country code TLDs with isValidCountryCodeTld
    @Test
    public void invalidCountryTld(){
        DomainValidator dv = DomainValidator.getInstance();

        assertFalse(dv.isValidCountryCodeTld(".com"));
        assertFalse(dv.isValidCountryCodeTld(".localhost"));
        assertFalse(dv.isValidCountryCodeTld(".madeuptld"));
        assertFalse(dv.isValidCountryCodeTld(".123"));
        assertFalse(dv.isValidCountryCodeTld(".***"));
        assertFalse(dv.isValidCountryCodeTld(""));
    }

    // Test valid local domains with isValidLocalTld
    @Test
    public void validLocalTld(){
        DomainValidator dv = DomainValidator.getInstance(true);

        assertTrue(dv.isValidLocalTld(".localhost"));
        assertTrue(dv.isValidLocalTld(".localdomain"));
    }

    // Test invalid local domains with isValidLocalTld
    @Test
    public void invalidLocalTld(){
        DomainValidator dv = DomainValidator.getInstance(true);

        assertFalse(dv.isValidLocalTld(".com"));
        assertFalse(dv.isValidLocalTld(".arpa"));
        assertFalse(dv.isValidLocalTld(".madeuptld"));
        assertFalse(dv.isValidLocalTld(".123"));
        assertFalse(dv.isValidLocalTld(".***"));
        assertFalse(dv.isValidLocalTld(""));
    }
}
