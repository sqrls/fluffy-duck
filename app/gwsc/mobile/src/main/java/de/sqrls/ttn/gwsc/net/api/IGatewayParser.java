package de.sqrls.ttn.gwsc.net.api;

import android.content.ContentValues;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Defines a Parser for Gateway structs. JSON goes in, contentvalues go out.
 */
public interface IGatewayParser {

    public ContentValues parseGateway(JSONObject input);

    public List<ContentValues> parseGateways(JSONArray input);


}
