package com.example.tiku11_15.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tiku11_15.R;
import com.example.tiku11_15.bean.CLWZRight;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @LogIn Name win10
 * @Create by 张瀛煜 on 2020/7/28 at 8:38 ：）
 */
public class CXJGRightAdapter extends ArrayAdapter<CLWZRight> {
    public CXJGRightAdapter(@NonNull Context context, @NonNull List<CLWZRight> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cxgj_right_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        CLWZRight clwzRight = getItem(position);
        holder.itemDl.setText(clwzRight.getKL());
        holder.itemFk.setText(clwzRight.getFk()+"");
        holder.itemKf.setText(clwzRight.getKf()+"");
        holder.itemMsg.setText(clwzRight.getMsg());
        holder.itemSj.setText(clwzRight.getSj());
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.item_sj)
        TextView itemSj;
        @BindView(R.id.item_dl)
        TextView itemDl;
        @BindView(R.id.item_msg)
        TextView itemMsg;
        @BindView(R.id.item_kf)
        TextView itemKf;
        @BindView(R.id.item_fk)
        TextView itemFk;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
