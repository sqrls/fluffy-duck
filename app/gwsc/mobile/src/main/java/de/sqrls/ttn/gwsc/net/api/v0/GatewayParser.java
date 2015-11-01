package de.sqrls.ttn.gwsc.net.api.v0;

import android.content.ContentValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.sqrls.ttn.gwsc.net.api.IGatewayParser;
import de.sqrls.ttn.gwsc.provider.GatewayColumns;

/**
 * Created by zero on 01.11.15.
 */
public class GatewayParser implements IGatewayParser {

    @Override
    public ContentValues parseGateway(JSONObject input) {
        try {

            ContentValues values = new ContentValues(9);
            values.put(GatewayColumns.EUI,input.getString("eui"));
            values.put(GatewayColumns.GW_STATUS,input.getString("status"));
            values.put(GatewayColumns.LAST_SEEN,input.getString("last_seen"));
            values.put(GatewayColumns.REMARKS,input.optString("remarks"));
            values.put(GatewayColumns.GW_CREATED_AT,input.getString("created_at"));
            values.put(GatewayColumns.GW_UPDATED_AT,input.optString("updated_at"));
            String location = input.getString("location");
            String[] coords = location.split(",");
            if(coords.length>1) {
                values.put(GatewayColumns.LOC_LAT, coords[0]);
                values.put(GatewayColumns.LOC_LON, coords[1]);
                values.put(GatewayColumns.LOC_ALT, "0");
            }else {
                values.put(GatewayColumns.LOC_LAT, "0");
                values.put(GatewayColumns.LOC_LON, "0");
                values.put(GatewayColumns.LOC_ALT, "0");
            }
            return values;
        }catch (JSONException e){
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public List<ContentValues> parseGateways(JSONArray input) {
        try {

            final int size = input.length();
            List<ContentValues> list = new ArrayList<>(size);
            JSONObject object;
            ContentValues values;
            for (int i = 0; i < size; i++) {
                object = input.getJSONObject(i);
                values = parseGateway(object);
                if(values!=null){
                    list.add(values);
                }
            }

            return list;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }


}
