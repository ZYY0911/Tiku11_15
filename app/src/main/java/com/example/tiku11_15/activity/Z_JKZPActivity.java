package com.example.tiku11_15.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.tiku11_15.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @LogIn Name win10
 * @Create by 张瀛煜 on 2020/7/28 at 8:55 ：）
 */
public class Z_JKZPActivity extends BaseActivity {


    @BindView(R.id.iv_1)
    ImageView iv1;
    @BindView(R.id.iv_2)
    ImageView iv2;
    @BindView(R.id.iv_3)
    ImageView iv3;
    @BindView(R.id.iv_4)
    ImageView iv4;
    @BindView(R.id.bt_exit)
    Button btExit;

    @Override
    public int getLayout() {
        return R.layout.jkzp_layout;
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
    }

    @OnClick({R.id.iv_1, R.id.iv_2, R.id.iv_3, R.id.iv_4})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, Z_JKZPActivity2.class);
        switch (view.getId()) {
            case R.id.iv_1:
                intent.putExtra("index", 0);
                break;
            case R.id.iv_2:
                intent.putExtra("index", 1);
                break;
            case R.id.iv_3:
                intent.putExtra("index", 2);
                break;
            case R.id.iv_4:
                intent.putExtra("index", 3);
                break;
        }
        startActivity(intent);
    }

    @OnClick(R.id.bt_exit)
    public void onViewClicked() {
        finish();
    }
}
