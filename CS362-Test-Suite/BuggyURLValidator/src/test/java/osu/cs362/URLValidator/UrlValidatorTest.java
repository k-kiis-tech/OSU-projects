package osu.cs362.URLValidator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Test;
import org.junit.BeforeClass;
import java.util.*;

public class UrlValidatorTest {
    UrlValidator validator;
    String url;
    private static Random rand;

    /* Input Domain Partitions for isValid method:
     * B_1 - Default configuration
     * B_1_1 - Valid URL format
     * B_1_1_1 - Scheme of http, https, or ftp
     * B_1_1_1_1 - With username
     * B_1_1_1_1_1 - With password
     * B_1_1_1_1_2 - Without password
     * B_1_1_1_2 - With port
     * B_1_1_1_3 - With path
     * B_1_1_1_4 - With query
     * B_1_1_1_4_1 - Single argument
     * B_1_1_1_4_2 - Multiple arguments
     * B_1_1_1_5 - With fragment
     * B_1_1_2 - With any other valid scheme
     * B_1_2 - Invalid URL format
     * B_1_2_1 - Valid scheme, valid authority, valid path, invalid query
     * B_1_2_2 - Valid scheme, valid authority, invalid path, valid query
     * B_1_2_3 - Valid scheme, invalid authority, valid path, valid query
     * B_1_2_4 - Invalid scheme, valid authority, valid path, valid query
     * B_1_2_5 - Empty string
     * B_1_2_6 - Invalid char in scheme
     * B_1_2_7 - Invalid char in authority
     * B_1_2_8 - Invalid char in path
     * B_1_2_9 - Invalid char in query
     * B_1_2_10 - Label too long
     * B_1_2_11 - Domain too long
     * B_1_2_12 - No scheme
     * B_1_2_13 - Null string
     * B_1_2_14 - IP address
     * B_1_2_15 - Extra text at end of authority
     * B_1_2_16 - Malicious URLs
     * B_1_2_17 - Completely invalid URLs
     * B_2 - Custom configuration
     * B_2_1 - All schemes off, 2 slashes off, No fragments off, Local URLs on
     * B_2_2 - All schemes off, 2 slashes off, No fragments on, Local URLs off
     * B_2_3 - All schemes off, 2 slashes on, No fragments off, Local URLs off
     * B_2_4 - All schemes on, 2 slashes off, No fragments off, Local URLs off
     * B_2_5 - Custom schemes
     * B_3 - Singleton instance
     */

    /**
     * Number of times to test a randomly-generated URL.
     */
    private static final int TEST_COUNT = 10000;

    @BeforeClass
    public static void initialize() {
        long seed = System.currentTimeMillis();
        rand = new Random(seed);
        System.out.printf("Current seed value: %d%n", seed);
    }

    /**
     * Default configuration valid default scheme, username, and password.
     * B_1_1_1_1_1 
     */
    @Test public void defaultConfigWithUsernamePassword() {
        validator = new UrlValidator();
        url = "http://username:password@www.osu.edu";
        assertTrue(url, validator.isValid(url));
        url = "http://us^rname:password@www.osu.edu";
        assertFalse(url, validator.isValid(url));
    }

    /**
     * Default configuration with valid default scheme and username.
     * B_1_1_1_1_2 
     */
    @Test public void defaultConfigWithUsername() {
        validator = new UrlValidator();
        url = "http://username@www.osu.edu";
        assertTrue(url, validator.isValid(url));
        url = "http://us^rname@www.osu.edu";
        assertFalse(url, validator.isValid(url));
    }

    /**
     * Default configuration with valid default scheme, and port.
     * B_1_1_1_2 
     */
    @Test public void defaultConfigWithPort() {
        validator = new UrlValidator();
        url = "http://www.osu.edu:" + Integer.toString(UrlHelper.MAX_PORT_NUM);
        assertTrue(url, validator.isValid(url));
        url = "http://www.osu.edu:" + Integer.toString(UrlHelper.MAX_PORT_NUM + 1);
        assertFalse(url, validator.isValid(url));
        url = "http://www.osu.edu:-1";
        assertFalse(url, validator.isValid(url));
    }

    /**
     * Default configuration with valid default scheme, and path.
     * B_1_1_1_3
     */
    @Test public void defaultConfigWithPath() {
        validator = new UrlValidator();
        url = "http://www.osu.edu/foo/bar/baz.html";
        assertTrue(url, validator.isValid(url));
        url = "http://www.osu.edu/f oo/bar/baz.html";
        assertFalse(url, validator.isValid(url));
    }

    /**
     * Default configuration with valid default scheme 
     * and query with single argument.
     * B_1_1_1_4_1
     */
    @Test public void defaultConfigWithSingleQuery() {
        validator = new UrlValidator();
        url = "http://www.osu.edu?foo=bar";
        assertTrue(url, validator.isValid(url));
        url = "http://www.osu.edu?foo=b^r";
        assertFalse(url, validator.isValid(url));
    }

    /**
     * Default configuration with valid default scheme
     * and query with multiple arguments.
     * B_1_1_1_4_2
     */
    @Test public void defaultConfigWithMultiQuery() {
        validator = new UrlValidator();
        url = "http://www.osu.edu?foo=bar&fizz=buzz";
        assertTrue(url, validator.isValid(url));
        url = "http://www.osu.edu?foo=bar;fizz=buzz";
        assertTrue(url, validator.isValid(url));
        url = "http://www.osu.edu?foo=bar&fizz=b^zz";
        assertFalse(url, validator.isValid(url));
        url = "http://www.osu.edu?foo=bar;fizz=b^zz";
        assertFalse(url, validator.isValid(url));
    }

    /**
     * Default configuration with valid default scheme, and fragment.
     * B_1_1_1_5
     */
    @Test public void defaultConfigWithFragment() {
        validator = new UrlValidator();
        url = "http://www.osu.edu#foo.bar-baz";
        assertTrue(url, validator.isValid(url));
        url = "http://www.osu.edu#foo.b>r";
        assertFalse(url, validator.isValid(url));
    }

    /**
     * Default configuration with valid non-default scheme.
     * B_1_1_2
     */
    @Test public void defaultConfigNonDefaultScheme() {
        validator = new UrlValidator();
        url = "file://c:/test.txt";
        assertFalse(url, validator.isValid(url));
    }

    /**
     * Default configuration with a full URL and invalid query format.
     * B_1_2_1
     */
    @Test public void defaultConfigFullUrlInvalidQuery() {
        validator = new UrlValidator();
        url = "https://www.example.com/test/valid/path?asdf =123\"&foo=bar#baz";
        assertFalse(url, validator.isValid(url));
    }

    /**
     * Default configuration with a full URL and invalid path format.
     * B_1_2_2
     */
    @Test public void defaultConfigFullUrlInvalidPath() {
        validator = new UrlValidator();
        url = "https://www.example.com/test/in&valid/path?foo=bar#baz";
        assertFalse(url, validator.isValid(url));
    }

    /**
     * Default configuration with a full URL and invalid authority format.
     * B_1_2_3
     */
    @Test public void defaultConfigFullUrlInvalidAuthority() {
        validator = new UrlValidator();
        url = "https://www.ex&ample.com/test/valid/path?foo=bar#baz";
        assertFalse(url, validator.isValid(url));
    }

    /**
     * Default configuration with a full URL and invalid scheme.
     * B_1_2_4
     */
    @Test public void defaultConfigFullUrlInvalidScheme() {
        validator = new UrlValidator();
        url = "httx://www.example.com/test/valid/path?foo=bar#baz";
        assertFalse(url, validator.isValid(url));
    }

    /**
     * Default configuration with empty string.
     * B_1_2_5
     */
    @Test public void defaultConfigEmptyString() {
        validator = new UrlValidator();
        url = "";
        assertFalse(url, validator.isValid(url));
    }

    /**
     * Default configuration with invalid character in scheme.
     * B_1_2_6
     */
    @Test public void defaultConfigInvalidScheme() {
        validator = new UrlValidator();
        url = "ht%p://www.test.com";
        assertFalse(url, validator.isValid(url));
    }


    /**
     * Default configuration with invalid character in authority.
     * B_1_2_7
     */
    @Test public void defaultConfigInvalidAuthority() {
        validator = new UrlValidator();
        url = "http://www.t&st.com";
        assertFalse(url, validator.isValid(url));
    }


    /**
     * Default configuration with invalid character in path.
     * B_1_2_8
     */
    @Test public void defaultConfigInvalidPath() {
        validator = new UrlValidator();
        url = "http://www.test.com/inv alid/path";
        assertFalse(url, validator.isValid(url));
    }


    /**
     * Default configuration with invalid character in query.
     * B_1_2_9
     */
    @Test public void defaultConfigInvalidQuery() {
        validator = new UrlValidator();
        url = "http://www.test.com?invalid=qu^ry";
        assertFalse(url, validator.isValid(url));
    }

    /**
     * Default configuration with label longer than max 63 characters.
     * B_1_2_10
     */
    @Test public void defaultConfigLabelTooLong() {
        validator = new UrlValidator();
        StringBuilder sb = new StringBuilder("http://www.");
        for (int i = 0; i < UrlHelper.MAX_LABEL_LENGTH + 1; ++i) {
            sb.append("a");
        }
        sb.append(".com");
        assertFalse(sb.toString(), validator.isValid(sb.toString()));
    }

    /**
     * Default configuration with domain string longer than 253 characters.
     * B_1_2_11
     */
    @Test public void defaultConfigDomainTooLong() {
        validator = new UrlValidator();
        StringBuilder sb = new StringBuilder("http://");
        for (int i = 0; i < UrlHelper.MAX_DOMAIN_LENGTH + 1; ++i) {
            if (i > 0 && (i % 10) == 0)
                sb.append(".");
            else
                sb.append("a");
        }
        assertFalse(sb.toString(), validator.isValid(sb.toString()));
    }

    /**
     * Default configuration without any scheme.
     * B_1_2_12
     */
    @Test public void defaultConfigNoScheme() {
        validator = new UrlValidator();
        url = "www.test.com";
        assertFalse(url, validator.isValid(url));
    }

    /**
     * Default configuration with null string.
     * B_1_2_13
     */
    @Test public void defaultConfigNullString() {
        validator = new UrlValidator();
        url = null;
        assertFalse(url, validator.isValid(url));
    }

    /**
     * Default configuration with IP address in authority.
     * B_1_2_14
     */
    @Test public void defaultConfigIpAddress() {
        validator = new UrlValidator();
        url = "http://192.168.0.1/test.html";
        assertTrue(url, validator.isValid(url));
    }

    /**
     * Default configuration with extra text at end of authority.
     * B_1_2_15
     */
    @Test public void defaultConfigAuthorityWithExtra() {
        validator = new UrlValidator();
        url = "http://www.test.com:80asdf/path";
        assertFalse(url, validator.isValid(url));
        url = "http://www.test.com:80  /path";
        assertFalse(url, validator.isValid(url));
    }

    /**
     * Default configuration with malicious URL.
     * B_1_2_16
     */
    @Test public void defaultConfigMaliciousUrl() {
        validator = new UrlValidator();
        url = "http://www.test.com/../../../../etc";
        assertFalse(url, validator.isValid(url));
        url = "http://www.test.com/../../../etc/passwd";
        assertFalse(url, validator.isValid(url));
        url = "http://www.chase.com/account?q=asdf@malicious.website.com/give/me/your?bank=account&number";
        assertTrue(url, validator.isValid(url));
    }

    /**
     * Default configuration with broken URL.
     * B_1_2_17
     */
    @Test public void defaultConfigBrokenUrl() {
        validator = new UrlValidator();
        url = "aaaa";
        assertFalse(url, validator.isValid(url));
        url = "^^^^";
        assertFalse(url, validator.isValid(url));
    }

    /**
     * Custom configuration with local URL option:
     *  - all schemes:  off
     *  - 2 slashes:    off
     *  - no fragments: off
     *  - local URLs:   on
     * B_2_1
     */
    @Test public void customConfigLocalUrl() {
        validator = new UrlValidator(UrlValidator.ALLOW_LOCAL_URLS);
        url = "file://localhost";
        assertFalse(url, validator.isValid(url));
        url = "http://localhost//test";
        assertFalse(url, validator.isValid(url));
        url = "http://localhost/#fragment";
        assertTrue(url, validator.isValid(url));
        url = "http://localhost";
        assertTrue(url, validator.isValid(url));
    }


    /**
     * Custom configuration with local URL:
     *  - all schemes:  off
     *  - 2 slashes:    off
     *  - no fragments: on
     *  - local URLs:   off 
     * B_2_2
     */
    @Test public void customConfigNoFragmentsOn() {
        validator = new UrlValidator(UrlValidator.NO_FRAGMENTS);
        url = "file:///c:/test/test.txt";
        assertFalse(url, validator.isValid(url));
        url = "http://www.test.com//test";
        assertFalse(url, validator.isValid(url));
        url = "http://www.test.com#fragment";
        assertFalse(url, validator.isValid(url));
        url = "http://localhost";
        assertFalse(url, validator.isValid(url));
    }


    /**
     * Custom configuration with local URL:
     *  - all schemes:  off
     *  - 2 slashes:    on
     *  - no fragments: off
     *  - local URLs:   off
     * B_2_3
     */
    @Test public void customConfig2SlashesOn() {
        validator = new UrlValidator(UrlValidator.ALLOW_2_SLASHES);
        url = "file:///c:/test/test.txt";
        assertFalse(url, validator.isValid(url));
        url = "http://www.test.com//test";
        assertTrue(url, validator.isValid(url));
        url = "http://www.test.com#fragment";
        assertTrue(url, validator.isValid(url));
        url = "http://localhost";
        assertFalse(url, validator.isValid(url));
    }


    /**
     * Custom configuration with local URL:
     *  - all schemes:  on
     *  - 2 slashes:    off
     *  - no fragments: off
     *  - local URLs:   off
     * B_2_4
     */
    @Test public void customConfigAllSchemesOn() {
        validator = new UrlValidator(UrlValidator.ALLOW_ALL_SCHEMES);
        url = "file:///c:/test/test.txt";
        assertTrue(url, validator.isValid(url));
        url = "http://www.test.com//test";
        assertFalse(url, validator.isValid(url));
        url = "http://www.test.com#fragment";
        assertTrue(url, validator.isValid(url));
        url = "http://localhost";
        assertFalse(url, validator.isValid(url));
    }

    /**
     * Verify all valid schemes when custom schemes are specified.
     * B_2_5
     */
    @Test public void customConfigCustomSchemes() {
        validator = new UrlValidator(UrlHelper.VALID_SCHEMES);
        for (String scheme : UrlHelper.VALID_SCHEMES) {
            url = String.format("%s://www.test.com", scheme);
            assertTrue(url, validator.isValid(url));
        }

        url = "invalid://www.test.com";
        assertFalse(url, validator.isValid(url));
    }

    /**
     * Verify that the custom authority validator is set and
     * correctly called. Always returns true.
     * B_2_6
     */
    @Test public void customConfigCustomValidatorTrue() {
        RegexValidator rv = mock(RegexValidator.class);
        when(rv.isValid(any(String.class))).thenReturn(true);

        validator = new UrlValidator(rv, UrlValidator.ALLOW_ALL_SCHEMES);
        url = "http://www.test.com";
        assertTrue(validator.isValid(url));
        url = "http://username:password@test.com";
        assertTrue(validator.isValid(url));
        verify(rv, times(2)).isValid(any(String.class));
    }

    /**
     * Verify that the custom authority validator is set and
     * correctly called. Always returns false.
     * B_2_7
     */
    @Test public void customConfigCustomValidatorFalse() {
        RegexValidator rv = mock(RegexValidator.class);
        when(rv.isValid(any(String.class))).thenReturn(false);

        validator = new UrlValidator(rv, 0);
        url = "http://www.test.com";
        assertFalse(validator.isValid(url));
        verify(rv).isValid(any(String.class));
    }

    /**
     * Verify that singleton instance functions as a default
     * configured UrlValidator object.
     * B_3
     */
    @Test public void singletonInstance() {
        url = "http://www.test.com";
        assertTrue(url, UrlValidator.getInstance().isValid(url));
        url = "file://www.test.com";
        assertFalse(url, UrlValidator.getInstance().isValid(url));
    }

    /**
     * Verify that all option flags can still be individually checked 
     * when combined.
     */
    @Test public void optionFlagTest() {
        long flags = UrlValidator.ALLOW_ALL_SCHEMES 
                     + UrlValidator.ALLOW_2_SLASHES
                     + UrlValidator.NO_FRAGMENTS
                     + UrlValidator.ALLOW_LOCAL_URLS;
        assertTrue("UrlValidator.ALLOW_ALL_SCHEMES", 
                (flags & UrlValidator.ALLOW_ALL_SCHEMES) > 0);
        assertTrue("UrlValidator.ALLOW_2_SLASHES",
                (flags & UrlValidator.ALLOW_2_SLASHES) > 0);
        assertTrue("UrlValidator.NO_FRAGMENTS",
                (flags & UrlValidator.NO_FRAGMENTS) > 0);
        assertTrue("UrlValidator.ALLOW_LOCAL_URLS",
                (flags & UrlValidator.ALLOW_LOCAL_URLS) > 0);
    }

    /**
     * Test a series of randomly-generated valid URL strings.
     * We will not use userinfo or query strings because they are
     * not correctly supported.
     */
    @Test public void randomValidUrls() {
        validator = new UrlValidator(
                UrlHelper.VALID_SCHEMES, 
                UrlValidator.ALLOW_2_SLASHES
                );
        boolean port, path, fragment;
        for (int i = 0; i < TEST_COUNT; ++i) {
            port = rand.nextInt(2) == 0;
            path = rand.nextInt(2) == 0;
            fragment = rand.nextInt(2) == 0;
            url = UrlHelper.getRandomValidUrl(true, false, port, path, false, fragment);
            assertTrue(url, validator.isValid(url));
        }
    }
}
