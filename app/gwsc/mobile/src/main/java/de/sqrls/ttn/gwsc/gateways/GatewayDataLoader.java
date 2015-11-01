package de.sqrls.ttn.gwsc.gateways;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import de.sqrls.ttn.gwsc.provider.GatewayColumns;

/**
 * Created by zero on 01.11.15.
 */
public class GatewayDataLoader implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final String LOG_TAG = GatewayDataLoader.class.getSimpleName();

    public interface IGatewayListDataHandler{
        void onGatewayListDataAvailable(Cursor data);
    }

    public interface IGatewayDetailsDataHandler{
        void onGatewayDetailsDataAvailable(Cursor data);
    }

    public static final int GATEWAY_LIST = 0xC0FFE;
    public static final int GATEWAY_DETAIL = 0xAFFE;

    private final android.content.Context mContext;

    private final IGatewayListDataHandler listDataHandler;
    private final IGatewayDetailsDataHandler detailsDataHandler;


    public GatewayDataLoader(Context mContext, IGatewayListDataHandler listDataHandler, IGatewayDetailsDataHandler gatewayDetailsDataHandler) {
        this.mContext = mContext;
        this.listDataHandler = listDataHandler;
        this.detailsDataHandler = gatewayDetailsDataHandler;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case GATEWAY_LIST:
                return new CursorLoader(
                        mContext,
                        GatewayColumns.CONTENT_URI,
                        GatewayColumns.DEFAULT_PROJECTION_MAP,
                        null,null,null
                );
            case GATEWAY_DETAIL:
                if(args!=null && args.containsKey(GatewayColumns.EUI)){
                    return new CursorLoader(
                            mContext,
                            GatewayColumns.CONTENT_URI,
                            GatewayColumns.DEFAULT_PROJECTION_MAP,
                            GatewayColumns.EUI+" like '"+args.getString(GatewayColumns.EUI)+"'",
                            null,null
                    );
                }else{
                    Log.e(LOG_TAG, "arguments was null or did not have EUI key");
                }
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        final int id =loader.getId();
        switch (id){
            case GATEWAY_LIST:
                if(listDataHandler!=null){
                    listDataHandler.onGatewayListDataAvailable(cursor);
                }
                break;
            case GATEWAY_DETAIL:
                if(detailsDataHandler !=null){
                    detailsDataHandler.onGatewayDetailsDataAvailable(cursor);
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
