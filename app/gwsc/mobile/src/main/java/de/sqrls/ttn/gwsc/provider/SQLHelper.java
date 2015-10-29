package de.sqrls.ttn.gwsc.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zero on 29.10.15.
 */
public class SQLHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="ttnprovider.db";
    public static final String TABLE_GATEWAYS = "gateways";
    private static final String LOG_TAG = SQLHelper.class.getSimpleName();

    public SQLHelper(final Context context,final int version) {
        super(context, DATABASE_NAME,null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createGatewayTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP ALL TABLES");
        onCreate(sqLiteDatabase);
    }

    private void createGatewayTable(SQLiteDatabase sqLiteDatabase) {
        StringBuffer buffer = new StringBuffer("CREATE TABLE ");
        buffer.append(TABLE_GATEWAYS);
        buffer.append(" (");
        buffer.append(BaseColumns._ID);
        buffer.append(" INTEGER PRIMARY KEY, ");
        buffer.append(BaseColumns.LAST_MODIFIED);
        buffer.append(" INTEGER DEFAULT 0, ");
        buffer.append(BaseColumns.LAST_SYNCED);
        buffer.append(" INTEGER DEFAULT 0, ");
        buffer.append(BaseColumns.DELETED);
        buffer.append(" INTEGER DEFAULT 0, ");

        buffer.append(GatewayColumns.EUI);
        buffer.append(" String, ");

        buffer.append(GatewayColumns.GW_STATUS);
        buffer.append(" String, ");

        buffer.append(GatewayColumns.LAST_SEEN);
        buffer.append(" String, ");

        buffer.append(GatewayColumns.REMARKS);
        buffer.append(" String, ");

        buffer.append(GatewayColumns.GW_CREATED_AT);
        buffer.append(" INTEGER DEFAULT 0, ");

        buffer.append(GatewayColumns.GW_UPDATED_AT);
        buffer.append(" INTEGER DEFAULT 0, ");

        buffer.append(GatewayColumns.LOC_ALT);
        buffer.append(" String, ");

        buffer.append(GatewayColumns.LOC_LAT);
        buffer.append(" String, ");
        buffer.append(GatewayColumns.LOC_LON);
        buffer.append(" String ");
        buffer.append("); ");
        android.util.Log.v(LOG_TAG, "EXEC SQL\n" + buffer.toString());
        sqLiteDatabase.execSQL(buffer.toString());

    }

}
