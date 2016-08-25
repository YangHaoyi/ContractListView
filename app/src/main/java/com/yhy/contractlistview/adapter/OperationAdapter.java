package com.yhy.contractlistview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yhy.contractlistview.R;
import com.yhy.contractlistview.data.Operation;

import java.util.ArrayList;


/**
 * 作者 : YangHaoyi on 2016/8/25.
 * 邮箱 ：yanghaoyi@neusoft.com
 */
public class OperationAdapter extends BaseAdapter {

    private ArrayList<Operation> items = new ArrayList<Operation>();
    private LayoutInflater mInflater = null;
    private Context context;

    public OperationAdapter(ArrayList<Operation> items, Context context){
        this.items = items;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            // 根据自定义的Item布局加载布局
            convertView = mInflater.inflate(R.layout.item, null);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.operation = (TextView) convertView.findViewById(R.id.operation);
            // 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.time.setText(items.get(position).getTime());
        holder.operation.setText(items.get(position).getOperation());
        return convertView;
    }
    // ViewHolder静态类
    private class ViewHolder {
        public TextView time;
        public TextView operation;
    }
}
