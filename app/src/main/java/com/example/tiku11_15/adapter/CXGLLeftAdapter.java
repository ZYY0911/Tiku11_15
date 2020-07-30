package com.example.tiku11_15.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tiku11_15.R;
import com.example.tiku11_15.bean.CLWZLeft;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @LogIn Name win10
 * @Create by 张瀛煜 on 2020/7/26 at 23:03 ：）
 */
public class CXGLLeftAdapter extends ArrayAdapter<CLWZLeft> {
    public interface MyClick{
        void imageClick(int position);
    }
    private MyClick myClick;

    public void setMyClick(MyClick myClick) {
        this.myClick = myClick;
    }

    public CXGLLeftAdapter(@NonNull Context context, @NonNull List<CLWZLeft> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cxgj_left_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        CLWZLeft clwzLeft = getItem(position);
        holder.itemCp.setText(clwzLeft.getCp());
        holder.itemFk.setText(clwzLeft.getFk()+"");
        holder.itemKf.setText(clwzLeft.getKf()+"");
        holder.itemCount.setText(clwzLeft.getCs());
        holder.itemRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClick.imageClick(position);
            }
        });
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.item_cp)
        TextView itemCp;
        @BindView(R.id.item_count)
        TextView itemCount;
        @BindView(R.id.item_kf)
        TextView itemKf;
        @BindView(R.id.item_fk)
        TextView itemFk;
        @BindView(R.id.item_remove)
        ImageView itemRemove;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
