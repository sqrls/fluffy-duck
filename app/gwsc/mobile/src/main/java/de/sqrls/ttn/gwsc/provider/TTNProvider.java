package de.sqrls.ttn.gwsc.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by zero on 29.10.15.
 */
public class TTNProvider extends ContentProvider {

    private static final int DB_VERSION = 1;
    private SQLHelper sqlHelper;

    @Override
    public boolean onCreate() {
        sqlHelper = new SQLHelper(getContext(),DB_VERSION);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case GATEWAYS_ALL:
                cursor= sqlHelper.getReadableDatabase().query(
                        SQLHelper.TABLE_GATEWAYS,
                        projection,
                        selection,
                        selectionArgs,
                        null,null,null
                );
                break;
            case GATEWAYS_ID:
                cursor= sqlHelper.getReadableDatabase().query(
                        SQLHelper.TABLE_GATEWAYS,
                        projection,
                        GatewayColumns._ID+"="+uri.getLastPathSegment(),
                        selectionArgs,
                        null,null,null
                );
                break;
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long rowId = -1;
        switch (uriMatcher.match(uri)){
            case GATEWAYS_ALL:
                rowId = sqlHelper.getWritableDatabase().insert(SQLHelper.TABLE_GATEWAYS, "", contentValues);
                return ContentUris.withAppendedId(GatewayColumns.CONTENT_URI,rowId);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String whereClause, String[] whereArgs) {
        int modrows = -1;
        switch (uriMatcher.match(uri)){
            case GATEWAYS_ALL:
                modrows = sqlHelper.getWritableDatabase().delete(SQLHelper.TABLE_GATEWAYS, whereClause, whereArgs);
                return modrows;
        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues,  String whereClause, String[] whereArgs) {
        int modrows = -1;
        switch (uriMatcher.match(uri)){
            case GATEWAYS_ALL:
                return upsert(uri,contentValues,whereClause,whereArgs);
        }
        return 0;

    }

    private int upsert(Uri uri, ContentValues contentValues,  String whereClause, String[] whereArgs) {
        int modrows = -1;
        switch (uriMatcher.match(uri)){
            case GATEWAYS_ALL:
                modrows = sqlHelper.getWritableDatabase().update(
                        SQLHelper.TABLE_GATEWAYS,
                        contentValues,
                        whereClause,
                        whereArgs
                );
                if(modrows==0){
                    return insert(uri,contentValues)!=null?1:0;
                }
                return modrows;
        }
        return 0;

    }


    private static final UriMatcher uriMatcher;

    private static final int GATEWAYS_ALL = 100;
    private static final int GATEWAYS_ID = 101;

    static {

        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Contract.CONTENT_AUTHORITY,GatewayColumns.PATH,GATEWAYS_ALL);
        uriMatcher.addURI(Contract.CONTENT_AUTHORITY,GatewayColumns.PATH+"/#",GATEWAYS_ID);
    }

}
