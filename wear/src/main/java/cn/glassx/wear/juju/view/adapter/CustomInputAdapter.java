package cn.glassx.wear.juju.view.adapter;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.glassx.wear.juju.R;

/**
 * Created by Fanz on 4/15/15.
 */
public class CustomInputAdapter extends WearableListView.Adapter {

    String[] answers ;
    Context context;
    LayoutInflater mInflater;

    public CustomInputAdapter(Context context) {
        super();
        this.context = context;
        mInflater = LayoutInflater.from(context);
        answers = context.getResources().getStringArray(R.array.customAnswer);
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new JujuListAdapter.ItemViewHolder(mInflater.inflate(R.layout.custom_item,null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        JujuListAdapter.ItemViewHolder itemHolder = (JujuListAdapter.ItemViewHolder)holder;
        TextView textView = (TextView)itemHolder.itemView.findViewById(R.id.custom_item);
        textView.setText(answers[position]);
        ((JujuListAdapter.ItemViewHolder) holder).itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return answers.length;
    }
}
