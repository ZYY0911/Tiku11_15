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
import com.example.tiku11_15.bean.SHZS;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @LogIn Name win10
 * @Create by 张瀛煜 on 2020/7/28 at 15:15 ：）
 */
public class SHZSAdapter extends ArrayAdapter<SHZS> {
    private List<SHZS> shzsList;
    private String title, msg;
    private int index;
    private int[] imges = {R.mipmap.zhiwaixianzhishu, R.mipmap.ganmaozhisu, R.mipmap.chuanyizhisu, R.mipmap.yundongzhisu, R.mipmap.kongqiwurankuoanzhisu};

    public SHZSAdapter(@NonNull Context context, @NonNull List<SHZS> objects) {
        super(context, 0, objects);
        shzsList = objects;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.shzs_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SHZS shzs = shzsList.get(shzsList.size() - 1);
        switch (position) {
            case 0:
                index = shzs.getIllumination();
                if (index < 1000) {
                    title = "弱";
                    msg = "辐射较弱，涂擦SPF12~15、PA+护肤品";
                } else if (index > 3000) {
                    title = "强";
                    msg = "尽量减少外出，需要涂抹高倍数防晒霜";
                } else {
                    title = "中等";
                    msg = "涂擦SPF大于15、PA+防晒护肤品";
                }
                break;
            case 1:
                index = shzs.getTemperature();
                if (index < 8) {
                    title = "较易发";
                    msg = "温度低，风较大，较易发生感冒，注意防护";
                } else {
                    title = "少发";
                    msg = "无明显降温，感冒机率较低";
                }
                break;
            case 2:
                index = shzs.getTemperature();
                if (index < 12) {
                    title = "冷";
                    msg = "建议穿长袖衬衫、单裤等服装";
                } else if (index > 21) {
                    title = "热";
                    msg = "适合穿T恤、短薄外套等夏季服装";
                } else {
                    title = "舒适";
                    msg = "建议穿短袖衬衫、单裤等服装";
                }
                break;
            case 3:
                index = shzs.getCo2();
                if (index < 3000) {
                    title = "适宜";
                    msg = "气候适宜，推荐您进行户外运动";
                } else if (index > 6000) {
                    title = "较不宜";
                    msg = "空气氧气含量低，请在室内进行休闲运动";
                } else {
                    title = "中";
                    msg = "易感人群应适当减少室外活动";
                }
                break;
            case 4:
                index = shzs.getPm25();
                if (index < 30) {
                    title = "优";
                    msg = "空气质量非常好，非常适合户外活动，趁机出去多呼吸新鲜空气";
                } else if (index > 100) {
                    title = "污染";
                    msg = "空气质量差，不适合户外活动";
                } else {
                    title = "良";
                    msg = "易感人群应适当减少室外活动";
                }
                break;
        }
        holder.itemTitle.setText(title + "(" + index + ")");
        holder.itemMsg.setText(msg);
        holder.itemImage.setImageResource(imges[position]);
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.item_image)
        ImageView itemImage;
        @BindView(R.id.item_title)
        TextView itemTitle;
        @BindView(R.id.item_msg)
        TextView itemMsg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
