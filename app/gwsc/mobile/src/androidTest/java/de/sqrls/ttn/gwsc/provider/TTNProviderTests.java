package de.sqrls.ttn.gwsc.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;

/**
 * Created by zero on 29.10.15.
 */
public class TTNProviderTests extends ProviderTestCase2 {

    static final String LAT ="70.023123";
    static final String LON ="-51.958591";
    static final String ALT ="100";
    private static final String GW_EUI = "test123";
    private static final String LOG_TAG = TTNProviderTests.class.getSimpleName();

    public TTNProviderTests() {
        super(TTNProvider.class, Contract.CONTENT_AUTHORITY);
    }

    /**
     * running test all at once in order makes sure we have data to operate on and clean up.
     */

    public void testProviderActions(){
        insert();
        query();
        update();
        delete();

    }



    public void insert(){
        android.util.Log.d(LOG_TAG,"insert");
        ContentValues values = new ContentValues();
        final long currentTime = System.currentTimeMillis();

        values.put(GatewayColumns.EUI,GW_EUI);
        values.put(GatewayColumns.GW_STATUS,"up");
        values.put(GatewayColumns.LAST_SEEN,String.valueOf(currentTime));
        values.put(GatewayColumns.GW_CREATED_AT,String.valueOf(currentTime));
        values.put(GatewayColumns.GW_UPDATED_AT,String.valueOf(currentTime));
        values.put(GatewayColumns.LOC_ALT, ALT);
        values.put(GatewayColumns.LOC_LAT, LAT);
        values.put(GatewayColumns.LOC_LON, LON);


        Uri url = getMockContentResolver().insert(GatewayColumns.CONTENT_URI, values);
        assertNotNull(url);

    }


    private void query() {
        android.util.Log.d(LOG_TAG,"query");
        Cursor cursor = getMockContentResolver().query(
            GatewayColumns.CONTENT_URI,
            GatewayColumns.DEFAULT_PROJECTION_MAP,
            GatewayColumns.EUI+" like'"+GW_EUI+"'"
                ,null,null

        );

        assertNotNull(cursor);
        assertTrue(cursor.getCount() == 1);
        assertTrue(cursor.moveToFirst());
        assertEquals(GW_EUI, cursor.getString(cursor.getColumnIndex(GatewayColumns.EUI)));
        cursor.close();
    }

    private void update() {
        android.util.Log.d(LOG_TAG,"update");
        ContentValues values = new ContentValues();
        final long currentTime = System.currentTimeMillis();

        values.put(GatewayColumns.LAST_SEEN, String.valueOf(currentTime));

        final int modrows=getMockContentResolver().update(
                GatewayColumns.CONTENT_URI,
                values,
                GatewayColumns.EUI+" like'"+GW_EUI+"'"
                ,null
        );

        assertTrue(1 == modrows);
    }


    private void delete() {
        android.util.Log.d(LOG_TAG,"delete");
        final int modrows=getMockContentResolver().delete(
                GatewayColumns.CONTENT_URI,
                GatewayColumns.EUI + " like'" + GW_EUI + "'"
                , null
        );

        assertTrue(1 == modrows);
    }

}
