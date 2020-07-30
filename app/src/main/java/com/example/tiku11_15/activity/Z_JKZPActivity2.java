package com.example.tiku11_15.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.tiku11_15.R;
import com.example.tiku11_15.util.ImageListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @LogIn Name win10
 * @Create by 张瀛煜 on 2020/7/28 at 9:22 ：）
 */
public class Z_JKZPActivity2 extends BaseActivity {
    @BindView(R.id.iv_select)
    ImageView ivSelect;
    @BindView(R.id.bt_exit_2)
    Button btExit2;
    private int[] images = {R.mipmap.weizhang1, R.mipmap.weizhang2, R.mipmap.weizhang03, R.mipmap.weizhang4};

    @Override
    public int getLayout() {
        return R.layout.jkzp_layout2;
    }

    @Override
    public String setTitle() {
        return "监控抓拍";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        ivSelect.setImageResource(images[getIntent().getIntExtra("index", 0)]);
        ivSelect.setOnTouchListener(new ImageListener(ivSelect));
    }

    @OnClick(R.id.bt_exit_2)
    public void onViewClicked() {
        finish();
    }
}
