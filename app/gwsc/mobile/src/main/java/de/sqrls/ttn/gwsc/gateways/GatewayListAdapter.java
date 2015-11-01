package de.sqrls.ttn.gwsc.gateways;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import de.sqrls.ttn.gwsc.R;
import de.sqrls.ttn.gwsc.net.api.ApiConstants;
import de.sqrls.ttn.gwsc.provider.GatewayColumns;

/**
 * Created by zero on 01.11.15.
 */
public class GatewayListAdapter extends CursorAdapter{


    private final LayoutInflater mInflater;
    private int colIndexEui=-1;
    private int colIndexStatus=-1;
    private int colIndexLastSeen=-1;

    public GatewayListAdapter(Context context) {
        super(context, null,true);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View  view = mInflater.inflate(R.layout.gateway_list_content,viewGroup,false);
        Holder.attachToView(view);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if(view!=null && view.getTag()!=null){
            Resources resources = context.getResources();
            Holder holder = (Holder) view.getTag();
            holder.tvEUI.setText(cursor.getString(colIndexEui));
            holder.tvLastSeen.setText(cursor.getString(colIndexLastSeen));
            final String status = cursor.getString(colIndexStatus);
            if(ApiConstants.GW_STATUS.UP.equals(status)){
                holder.tvStatus.setText("UP");
                holder.tvStatus.setBackgroundColor(resources.getColor(R.color.GW_STATUS_UP));

            } else if(ApiConstants.GW_STATUS.DOWN.equals(status)){
                holder.tvStatus.setText("DOWN");
                holder.tvStatus.setBackgroundColor(resources.getColor(R.color.GW_STATUS_DOWN));
            }else{
                holder.tvStatus.setBackgroundColor(resources.getColor(R.color.GW_STATUS_UNKOWN));
            }
        }

    }

    @Override
    public Cursor swapCursor(Cursor newCursor) {
        if(newCursor!=null){
            colIndexEui = newCursor.getColumnIndex(GatewayColumns.EUI);
            colIndexStatus = newCursor.getColumnIndex(GatewayColumns.GW_STATUS);
            colIndexLastSeen = newCursor.getColumnIndex(GatewayColumns.LAST_SEEN);
        }
        return super.swapCursor(newCursor);
    }

    private static class Holder{
        public TextView tvEUI;
        public TextView tvLastSeen;
        public TextView tvStatus;

        public static void attachToView(View view){
            Holder holder = new Holder(view);
            view.setTag(holder);
        }

        public Holder(View view){
            tvEUI =(TextView)view.findViewById(R.id.tvEUI);
            tvLastSeen =(TextView)view.findViewById(R.id.tvLastSeen);
            tvStatus =(TextView)view.findViewById(R.id.tvStatus);
        }
    }


}
