package cn.glassx.wear.juju.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.glassx.wear.juju.R;
import cn.glassx.wear.juju.model.JUJUer;

/**
 * Created by Fanz on 4/7/15.
 */
public   class JujuListAdapter extends WearableListView.Adapter {
    private List<JUJUer> jujUers;
    private final Context mContext;
    private final LayoutInflater mInflater;

    public JujuListAdapter(Context context, List<JUJUer> jujUers) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.jujUers = jujUers;
    }

    public static class ItemViewHolder extends WearableListView.ViewHolder {

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }


    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.person_item, null));
    }


    public void onBindViewHolder(WearableListView.ViewHolder holder,
                                 int position) {
        ItemViewHolder itemHolder = (ItemViewHolder) holder;
        TextView view = (TextView)itemHolder.itemView.findViewById(R.id.name);
        CircledImageView imageView = (CircledImageView)itemHolder.itemView.findViewById(R.id.circle);
        view.setText(jujUers.get(position).getName());
        imageView.setImageDrawable(new BitmapDrawable(mContext.getResources(),jujUers.get(position).getPortrail()));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return jujUers.size();
    }
}
