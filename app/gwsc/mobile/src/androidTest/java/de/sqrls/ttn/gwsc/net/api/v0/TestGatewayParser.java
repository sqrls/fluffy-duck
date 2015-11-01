package de.sqrls.ttn.gwsc.net.api.v0;

import android.content.ContentValues;
import android.test.AndroidTestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import de.sqrls.ttn.gwsc.provider.GatewayColumns;

/**
 * Created by zero on 01.11.15.
 */
public class TestGatewayParser extends AndroidTestCase{

    private static final String SINGLE_GATEWAY_JSON = "{\"eui\":\"0000024B08060030\",\"location\":\"52.37267,4.90052\",\"status\":\"down\",\"last_seen\":\"2015-10-08 12:08:16\",\"remarks\":\"Waag Society\",\"created_at\":\"2015-09-22 10:02:23\",\"updated_at\":\"2015-11-01 13:45:01\"}";

    private static final String LIST_OF_TWO ="[{\"eui\":\"0000000000000000\",\"location\":null,\"status\":\"down\",\"last_seen\":null,\"remarks\":null,\"created_at\":\"2015-09-22 10:02:23\",\"updated_at\":\"2015-11-01 13:45:01\"},{\"eui\":\"0000024B08060030\",\"location\":\"52.37267,4.90052\",\"status\":\"down\",\"last_seen\":\"2015-10-08 12:08:16\",\"remarks\":\"Waag Society\",\"created_at\":\"2015-09-22 10:02:23\",\"updated_at\":\"2015-11-01 13:45:01\"}]";

    public void testParseGateway() throws JSONException {
        ContentValues values = null;
        GatewayParser parser = new GatewayParser();
        JSONObject jsonInput = new JSONObject(SINGLE_GATEWAY_JSON);

        values = parser.parseGateway(jsonInput);
        assertNotNull(values);
        assertTrue(values.containsKey(GatewayColumns.EUI));
        assertTrue(values.containsKey(GatewayColumns.GW_STATUS));
        assertTrue(values.containsKey(GatewayColumns.GW_CREATED_AT));
        assertTrue(values.containsKey(GatewayColumns.GW_UPDATED_AT));
        assertTrue(values.containsKey(GatewayColumns.REMARKS));
        assertTrue(values.containsKey(GatewayColumns.LOC_ALT));
        assertTrue(values.containsKey(GatewayColumns.LOC_LAT));
        assertTrue(values.containsKey(GatewayColumns.LOC_LON));

        assertEquals("0000024B08060030", values.getAsString(GatewayColumns.EUI));
        assertEquals("down", values.getAsString(GatewayColumns.GW_STATUS));
        assertEquals("52.37267",values.getAsString(GatewayColumns.LOC_LAT));
        assertEquals("4.90052",values.getAsString(GatewayColumns.LOC_LON));
        assertEquals("Waag Society",values.getAsString(GatewayColumns.REMARKS));
    }


    public void testParseGateways() throws JSONException{
        GatewayParser parser = new GatewayParser();
        JSONArray jsonInput = new JSONArray(LIST_OF_TWO);

        List<ContentValues> valuesList = null;

        valuesList = parser.parseGateways(jsonInput);

        assertNotNull(valuesList);
        assertTrue(valuesList.size()==2);
    }
}
