package cn.glassx.wear.juju.bluetooth.pair;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.glassx.wear.juju.R;

/**
 * Created by Fanz on 4/29/15.
 */
public   class DeviceListAdapter extends WearableListView.Adapter {
    private final LayoutInflater mInflater;
    private final List<BluetoothDevice> deviceList;

    public DeviceListAdapter(Context context,List<BluetoothDevice> deviceList) {
        mInflater = LayoutInflater.from(context);
        this.deviceList = deviceList;
    }

    public class ItemViewHolder extends WearableListView.ViewHolder {

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }


    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.deivce_select_item, null));
    }


    public void onBindViewHolder(WearableListView.ViewHolder holder,
                                 int position) {
        ItemViewHolder itemHolder = (ItemViewHolder) holder;
        TextView view = (TextView)itemHolder.itemView.findViewById(R.id.name);
        view.setText(deviceList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }
}
