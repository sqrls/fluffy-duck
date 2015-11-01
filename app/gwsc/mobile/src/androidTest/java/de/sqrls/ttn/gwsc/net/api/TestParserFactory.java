package de.sqrls.ttn.gwsc.net.api;

import android.test.AndroidTestCase;

/**
 * Created by zero on 01.11.15.
 */
public class TestParserFactory extends AndroidTestCase {


    public void testCreateGatewayParser(){
        IGatewayParser result = null;

        result = ParserFactory.createGatewayParser(ApiConstants.API_V0);
        assertNotNull(result);
        assertTrue(result instanceof de.sqrls.ttn.gwsc.net.api.v0.GatewayParser );

        boolean hadException  = false;
        try {
            result = ParserFactory.createGatewayParser("random text ;)");
        }catch (IllegalArgumentException ia){
            hadException = true;
        }
        assertTrue(hadException);

    }
}
