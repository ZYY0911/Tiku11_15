package com.example.tiku11_15.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.VolleyError;
import com.example.tiku11_15.R;
import com.example.tiku11_15.adapter.HLDAdapter;
import com.example.tiku11_15.bean.HLD;
import com.example.tiku11_15.dialog.HLDDialog;
import com.example.tiku11_15.net.VolleyLo;
import com.example.tiku11_15.net.VolleyTo;
import com.example.tiku11_15.util.MyUtils;
import com.github.mikephil.charting.data.PieData;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @LogIn Name win10
 * @Create by 张瀛煜 on 2020/7/26 at 8:47 ：）
 */
public class Z_HLDGLActivity extends BaseActivity {
    @BindView(R.id.sp_type)
    Spinner spType;
    @BindView(R.id.bt_query)
    Button btQuery;
    @BindView(R.id.bt_setting)
    Button btSetting;
    @BindView(R.id.list_view)
    ListView listView;
    private List<HLD> hlds;
    private HLDAdapter adapter;

    @Override
    public int getLayout() {
        return R.layout.hldgl_layout;
    }

    @Override
    public String setTitle() {
        return "红绿灯管理";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        setData();
    }

    private void setData() {
        if (hlds == null) {
            hlds = new ArrayList<>();
        } else {
            hlds.clear();
        }
        for (int i = 1; i <= 5; i++) {
            setVolley(i);
        }
    }

    private void setVolley(final int i) {
        VolleyTo volleyTo = new VolleyTo();
        if (i==5) {
            volleyTo.setDialog(this);
        }
        volleyTo.setUrl("get_traffic_light")
                .setJsonObject("UserName", "user1")
                .setJsonObject("number", i)
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        hlds.add(new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").optJSONObject(0).toString(), HLD.class));
                        if (hlds.size() == 5) {
                            setAdapter();
                        }
                        if (i==5&&hlds.size()!=5){
                            MyUtils.showDialog(Z_HLDGLActivity.this,"获取数据失败");
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    private void setAdapter() {
        Collections.sort(hlds, new Comparator<HLD>() {
            @Override
            public int compare(HLD o1, HLD o2) {
                switch ((int) spType.getSelectedItemId()) {
                    case 0:
                        return o1.getNumber() - o2.getNumber();
                    case 1:
                        return o2.getNumber() - o1.getNumber();
                    case 2:
                        return o1.getRed() - o2.getRed();
                    case 3:
                        return o2.getRed() - o1.getRed();
                    case 4:
                        return o1.getYellow() - o2.getYellow();
                    case 5:
                        return o2.getYellow() - o1.getYellow();
                    case 6:
                        return o1.getGreen() - o2.getGreen();
                    case 7:
                        return o2.getGreen() - o1.getGreen();
                    default:
                        return 0;
                }
            }
        });
        if (adapter == null) {
            adapter = new HLDAdapter(this, hlds);
            adapter.setMyClick(new HLDAdapter.MyClick() {
                @Override
                public void click(int lx, int position, boolean xz) {
                    final HLD hld = hlds.get(position);
                    if (lx == 1) {
                        hld.setXz(xz);
                        hlds.set(position, hld);
                    } else {
                        HLDDialog hldDialog = new HLDDialog(hld.getNumber() + "");
                        hldDialog.show(getSupportFragmentManager(), "");
                        hldDialog.setDate(new HLDDialog.SetDate() {
                            @Override
                            public void upDate(int lx) {
                                if (lx==1){
                                    MyUtils.showDialog(Z_HLDGLActivity.this,"设置成功");
                                    setData();
                                }else if (lx==2) {
                                    for (int i = 0; i < hlds.size(); i++) {
                                        hlds.get(i).setXz(false);
                                    }
                                    adapter.notifyDataSetChanged();
                                }else if (lx==3){
                                    MyUtils.showDialog(Z_HLDGLActivity.this,"设置失败");
                                }
                            }
                        });
                    }
                }
            });
            listView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.bt_query, R.id.bt_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_query:
                setAdapter();
                break;
            case R.id.bt_setting:
                String Lk = "";
                for (int i = 0; i < hlds.size(); i++) {
                    HLD hld = hlds.get(i);
                    if (hld.isXz()) {
                        if ("".equals(Lk)) {
                            Lk = hld.getNumber() + "";
                        } else {
                            Lk += "," + hld.getNumber();
                        }
                    }
                }
                if (TextUtils.isEmpty(Lk)) {
                    MyUtils.showDialog(this, "没有选中的路口");
                    return;
                }
                HLDDialog hldDialog = new HLDDialog(Lk);
                hldDialog.show(getSupportFragmentManager(), "");
                hldDialog.setDate(new HLDDialog.SetDate() {
                    @Override
                    public void upDate(int lx) {
                        if (lx==1){
                            MyUtils.showDialog(Z_HLDGLActivity.this,"设置成功");
                            setData();
                        }else if (lx==2) {
                            for (int i = 0; i < hlds.size(); i++) {
                                hlds.get(i).setXz(false);
                            }
                            adapter.notifyDataSetChanged();
                        }else if (lx==3){
                            MyUtils.showDialog(Z_HLDGLActivity.this,"设置失败");
                        }
                    }
                });
                break;
        }
    }
}
