package com.example.tiku11_15.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tiku11_15.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @LogIn Name win10
 * @Create by 张瀛煜 on 2020/7/26 at 8:47 ：）
 */
public abstract class BaseActivity extends AppCompatActivity {
    @BindView(R.id.change)
    ImageView change;
    @BindView(R.id.title)
    TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        title.setText(setTitle());
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public abstract int getLayout();

    public abstract String setTitle();
}
