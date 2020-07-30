package com.example.tiku11_15.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.tiku11_15.R;
import com.example.tiku11_15.bean.HLD;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @LogIn Name win10
 * @Create by 张瀛煜 on 2020/7/26 at 21:23 ：）
 */
public class HLDAdapter extends ArrayAdapter<HLD> {
    public interface MyClick{
        void click(int lx,int position,boolean xz);
    }
    private MyClick myClick;

    public void setMyClick(MyClick myClick) {
        this.myClick = myClick;
    }

    public HLDAdapter(@NonNull Context context, @NonNull List<HLD> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder ;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hld_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        HLD hld = getItem(position);
        holder.itemLk.setText(hld.getNumber()+"");
        holder.itemRed.setText(hld.getRed()+"");
        holder.itemYellow.setText(hld.getYellow()+"");
        holder.itemGreen.setText(hld.getGreen()+"");
        holder.itemCb.setChecked(hld.isXz());
        holder.itemCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                myClick.click(1,position, isChecked);
            }
        });
        holder.itemBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClick.click(2,position,false);
            }
        });
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.item_lk)
        TextView itemLk;
        @BindView(R.id.item_red)
        TextView itemRed;
        @BindView(R.id.item_yellow)
        TextView itemYellow;
        @BindView(R.id.item_green)
        TextView itemGreen;
        @BindView(R.id.item_cb)
        CheckBox itemCb;
        @BindView(R.id.item_bt)
        Button itemBt;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
