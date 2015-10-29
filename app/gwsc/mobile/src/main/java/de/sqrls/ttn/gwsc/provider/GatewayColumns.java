package de.sqrls.ttn.gwsc.provider;

import android.net.Uri;

/**
 * Created by zero on 29.10.15.
 */
public interface GatewayColumns extends BaseColumns {

    String PATH = "gateways";
    public static final Uri CONTENT_URI = Uri.parse("content://"+Contract.CONTENT_AUTHORITY+"/"+PATH);



    public static final String EUI="eui";
    public static final  String GW_STATUS="gwstatus";
    public static final  String LAST_SEEN="lastseen";
    public static final  String REMARKS="remarks";
    public static final  String GW_CREATED_AT="created_at";
    public static final  String GW_UPDATED_AT ="updated_at";
    public static final  String LOC_LAT="loc_lat";
    public static final  String LOC_LON="loc_lon";
    public static final  String LOC_ALT="loc_alt";

    public static final String[] DEFAULT_PROJECTION_MAP={
            _ID,
            EUI,
            GW_STATUS,
            LAST_SEEN,
            REMARKS,
            GW_CREATED_AT,
            GW_UPDATED_AT,
            LOC_ALT,
            LOC_LAT,
            LOC_LON

    };


}
