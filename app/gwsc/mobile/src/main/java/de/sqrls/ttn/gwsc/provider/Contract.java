package de.sqrls.ttn.gwsc.provider;

import android.net.Uri;

import de.sqrls.ttn.gwsc.BuildConfig;


/**
 * Created by zero on 29.10.15.
 */
public class Contract {



    public static final String CONTENT_AUTHORITY = buildAuthority();

    //this will allow us to have all different flavors installed on the same device
    //content provider urls are switched as needed.
    private static String buildAuthority() {
        String authority = "de.sqrls.ttn";

        if(!"prod".equals(BuildConfig.FLAVOR)) {
            //don't append build flavor for production releases
            authority +="."+ BuildConfig.FLAVOR;
        }

        if (BuildConfig.DEBUG) {
            authority += ".debug";
        }

        authority += ".gwsc";
        return authority;
    }

    public static final Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

}
