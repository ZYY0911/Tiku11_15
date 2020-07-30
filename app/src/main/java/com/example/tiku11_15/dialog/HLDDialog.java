package com.example.tiku11_15.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.example.tiku11_15.R;
import com.example.tiku11_15.net.VolleyLo;
import com.example.tiku11_15.net.VolleyTo;
import com.example.tiku11_15.util.MyUtils;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @LogIn Name win10
 * @Create by 张瀛煜 on 2020/7/26 at 21:47 ：）
 */
@SuppressLint("ValidFragment")
public class HLDDialog extends DialogFragment {
    @BindView(R.id.et_red)
    EditText etRed;
    @BindView(R.id.et_yellow)
    EditText etYellow;
    @BindView(R.id.et_green)
    EditText etGreen;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    @BindView(R.id.bt_quit)
    Button btQuit;
    Unbinder unbinder;
    private String Lk;
    private String[] Lks;



    public interface SetDate {
        void upDate(int lx);
    }

    private SetDate date;

    public void setDate(SetDate date) {
        this.date = date;
    }

    public HLDDialog(String lk) {
        Lk = lk;
        Lks = lk.split(",");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        View view = inflater.inflate(R.layout.hld_dialog, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.bt_submit, R.id.bt_quit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_submit:
                if (TextUtils.isEmpty(etRed.getText())){
                    MyUtils.showDialog(getContext(),"红灯时长不能为空");
                    return;
                }
                if (TextUtils.isEmpty(etRed.getText())){
                    MyUtils.showDialog(getContext(),"黄灯时长不能为空");
                    return;
                }
                if (TextUtils.isEmpty(etRed.getText())){
                    MyUtils.showDialog(getContext(),"绿灯时长不能为空");
                    return;
                }
                for (int i = 0; i < Lks.length; i++) {
                    //{"UserName":"user1","number":"1","red":"1","yellow":"1","green":"1"}
                    VolleyTo volleyTo = new VolleyTo();
                    final int finalI = i;
                    if (i==Lks.length-1){
                        volleyTo.setDialog(getContext());
                    }
                    volleyTo.setUrl("set_traffic_light")
                            .setJsonObject("UserName","user1")
                            .setJsonObject("number",Lks[i])
                            .setJsonObject("red",etRed.getText())
                            .setJsonObject("yellow",etYellow.getText())
                            .setJsonObject("green",etGreen.getText())
                            .setVolleyLo(new VolleyLo() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    if (finalI ==Lks.length-1){
                                        date.upDate(1);
                                        dismiss();
                                    }
                                }

                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    date.upDate(3);
                                    dismiss();
                                }
                            }).start();
                }
                break;
            case R.id.bt_quit:
                date.upDate(2);
                dismiss();
                break;
        }
    }
}
