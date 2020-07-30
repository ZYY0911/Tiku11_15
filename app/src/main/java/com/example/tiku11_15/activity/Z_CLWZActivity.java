package com.example.tiku11_15.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.example.tiku11_15.R;
import com.example.tiku11_15.bean.CLWZLeft;
import com.example.tiku11_15.bean.CLWZRight;
import com.example.tiku11_15.bean.WZXX;
import com.example.tiku11_15.net.VolleyLo;
import com.example.tiku11_15.net.VolleyTo;
import com.example.tiku11_15.util.MyUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @LogIn Name win10
 * @Create by 张瀛煜 on 2020/7/26 at 22:08 ：）
 */
public class Z_CLWZActivity extends BaseActivity {

    @BindView(R.id.et_cp)
    EditText etCp;
    @BindView(R.id.bt_query)
    Button btQuery;
    @BindView(R.id.center_layout)
    LinearLayout centerLayout;
    private List<WZXX> allInfos, wzxxes2;
    public static List<CLWZLeft> clwzLefts;
    public static List<CLWZRight> clwzRights;

    public static List<CLWZLeft> getClwzLefts() {
        return clwzLefts;
    }

    public static List<CLWZRight> getClwzRights() {
        return clwzRights;
    }

    @Override
    public int getLayout() {
        return R.layout.clwz_layout;
    }

    @Override
    public String setTitle() {
        return "车辆违章";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        clwzLefts = new ArrayList<>();
        clwzRights = new ArrayList<>();
        setVolley();
    }

    private void setVolley() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("get_all_car_peccancy")
                .setJsonObject("UserName", "user1")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        allInfos = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString(), new TypeToken<List<WZXX>>() {
                        }.getType());
                        setVolley2();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    private void setVolley2() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("get_pessancy_infos")
                .setJsonObject("UserName", "user1")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        wzxxes2 = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString(), new TypeToken<List<WZXX>>() {
                        }.getType());
                        setListAll();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    private void setListAll() {
        for (int i = 0; i < allInfos.size(); i++) {
            WZXX wzxx = allInfos.get(i);
            for (int j = 0; j < wzxxes2.size(); j++) {
                WZXX wzxx1 = wzxxes2.get(j);
                if (wzxx.getInfoid().equals(wzxx1.getInfoid())) {
                    wzxx.setRoad(wzxx1.getRoad());
                    wzxx.setMessage(wzxx1.getMessage());
                    wzxx.setDeduct(wzxx1.getDeduct());
                    wzxx.setFine(wzxx1.getFine());
                }
            }
        }
    }

    PaletInfp paletInfp;

    @OnClick(R.id.bt_query)
    public void onViewClicked() {
        if (TextUtils.isEmpty(etCp.getText())) {
            MyUtils.showDialog(this, "车牌号不能为空");
            return;
        }
        final String Cp = "鲁" + etCp.getText().toString().toUpperCase();
        if (Cp.length() != 7) {
            MyUtils.showDialog(this, "请输入正确的车牌号");
            return;
        }
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("get_peccancy_plate")
                .setJsonObject("UserName", "user1")
                .setJsonObject("plate", Cp)
                .setDialog(this)
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        paletInfp = new Gson().fromJson(jsonObject.toString(), PaletInfp.class);
                        if (paletInfp.id.size() == 0) {
                            MyUtils.showDialog(Z_CLWZActivity.this, "没有查询到" + Cp + "车的违章数据！");
                            return;
                        }
                        List<Integer> list = paletInfp.getId();
                        int Kf = 0, Fk = 0;
                        for (int i = 0; i < clwzLefts.size(); i++) {
                            CLWZLeft clwzLeft = clwzLefts.get(i);
                            if (clwzLeft.getCp().equals(Cp)){
                                clwzLefts.remove(i);
                                clwzLefts.add(0,clwzLeft);
                                startActivity( new Intent(Z_CLWZActivity.this,Z_CXJGActivity.class));
                                etCp.setText("");
                                return;
                            }
                        }
                        for (int i = 0; i < list.size(); i++) {
                            for (int j = 0; j < allInfos.size(); j++) {
                                WZXX wzxx = allInfos.get(j);
                                if (wzxx.getId() == list.get(i)) {
                                    Kf += wzxx.getDeduct();
                                    Fk += wzxx.getFine();
                                    clwzRights.add(new CLWZRight(Cp, wzxx.getTime(), wzxx.getRoad(), wzxx.getMessage(), wzxx.getDeduct(), wzxx.getFine()));
                                }
                            }
                        }
                        clwzLefts.add(0,new CLWZLeft(Cp,list.size()+"",Kf,Fk));
                        startActivity( new Intent(Z_CLWZActivity.this,Z_CXJGActivity.class));
                        etCp.setText("");
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();


    }

    class PaletInfp {

        /**
         * id : [1,7]
         * RESULT : S
         */

        private String RESULT;
        private List<Integer> id;

        public String getRESULT() {
            return RESULT;
        }

        public void setRESULT(String RESULT) {
            this.RESULT = RESULT;
        }

        public List<Integer> getId() {
            return id;
        }

        public void setId(List<Integer> id) {
            this.id = id;
        }
    }
}
