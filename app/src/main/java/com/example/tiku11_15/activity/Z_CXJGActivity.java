package com.example.tiku11_15.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.tiku11_15.R;
import com.example.tiku11_15.adapter.CXGLLeftAdapter;
import com.example.tiku11_15.adapter.CXJGRightAdapter;
import com.example.tiku11_15.bean.CLWZLeft;
import com.example.tiku11_15.bean.CLWZRight;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @LogIn Name win10
 * @Create by 张瀛煜 on 2020/7/26 at 22:44 ：）
 */
public class Z_CXJGActivity extends BaseActivity {
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.left_list_view)
    ListView leftListView;
    @BindView(R.id.right_list_view)
    ListView rightListView;
    private List<CLWZLeft> clwzLefts;
    private List<CLWZRight> clwzRights, indexRight;
    private CXGLLeftAdapter leftAdapter;
    private CXJGRightAdapter rightAdapter;
    private String nowCp;

    @Override
    public int getLayout() {
        return R.layout.cxgj_layout;
    }

    @Override
    public String setTitle() {
        return "查询结果";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        clwzLefts = Z_CLWZActivity.getClwzLefts();
        clwzRights = Z_CLWZActivity.getClwzRights();
        indexRight = new ArrayList<>();
        initView();
    }

    private void initView() {
        nowCp = clwzLefts.get(0).getCp();
        leftAdapter = new CXGLLeftAdapter(this, clwzLefts);
        leftListView.setAdapter(leftAdapter);
        leftAdapter.setMyClick(new CXGLLeftAdapter.MyClick() {
            @Override
            public void imageClick(int position) {
                CLWZLeft clwzLeft = clwzLefts.get(position);
                for (int i = clwzRights.size() - 1; i >= 0; i--) {
                    CLWZRight clwzRight = clwzRights.get(i);
                    if (clwzRight.getCp().equals(clwzLeft.getCp())) {
                        clwzRights.remove(i);
                    }
                }
                clwzLefts.remove(position);
                leftAdapter.notifyDataSetChanged();
                if (clwzLefts.size() != 0) {
                    nowCp = clwzLefts.get(0).getCp();
                }
                initRightView();
            }
        });
        leftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nowCp = clwzLefts.get(position).getCp();
                initRightView();
            }
        });
        initRightView();
    }

    private void initRightView() {
        indexRight.clear();
        for (int i = 0; i < clwzRights.size(); i++) {
            CLWZRight clwzRight = clwzRights.get(i);
            if (clwzRight.getCp().equals(nowCp)) {
                indexRight.add(clwzRight);
            }
        }
        if (rightAdapter == null) {
            rightAdapter = new CXJGRightAdapter(this, indexRight);
            rightListView.setAdapter(rightAdapter);
            rightListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startActivity(new Intent(Z_CXJGActivity.this, Z_JKZPActivity.class));
                }
            });
        } else {
            rightAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.iv_add)
    public void onViewClicked() {
        finish();
    }
}
