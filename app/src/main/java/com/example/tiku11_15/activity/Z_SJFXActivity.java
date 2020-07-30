package com.example.tiku11_15.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.example.tiku11_15.R;
import com.example.tiku11_15.bean.SJFX;
import com.example.tiku11_15.fragment.SJFXFragment1;
import com.example.tiku11_15.fragment.SJFXFragment2;
import com.example.tiku11_15.fragment.SJFXFragment3;
import com.example.tiku11_15.fragment.SJFXFragment4;
import com.example.tiku11_15.fragment.SJFXFragment5;
import com.example.tiku11_15.fragment.SJFXFragment6;
import com.example.tiku11_15.fragment.SJFXFragment7;
import com.example.tiku11_15.net.VolleyLo;
import com.example.tiku11_15.net.VolleyTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @LogIn Name win10
 * @Create by 张瀛煜 on 2020/7/29 at 14:25 ：）
 */
public class Z_SJFXActivity extends BaseActivity {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.linear_layout)
    LinearLayout linearLayout;
    private List<Fragment> fragments;
    private List<SJFX> sjfxes,yes,no;
    private Map<String,Integer> map,mapyesAge,mapnoAge,mapyesSex,mapnoSex,mapyesType;
    private int a,b,c;    private Map<Integer,Integer> mapyesTime;
    private List<Map.Entry<String,Integer>>typeLists;
    @Override
    public int getLayout() {
        return R.layout.sjfx_layout;
    }

    @Override
    public String setTitle() {
        return "数据分析";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        fragments = new ArrayList<>();
        setVolley();
    }

    private void setVolley() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("get_peccancy")
                .setJsonObject("UserName", "user1")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        sjfxes = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString(), new TypeToken<List<SJFX>>() {
                        }.getType());
                        setDateList();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    private void setDateList() {
        yes = new ArrayList<>();
        no = new ArrayList<>();
        for (int i = 0; i < sjfxes.size(); i++) {
            SJFX sjfx = sjfxes.get(i);
            if (sjfx.getPaddr().length()==0){
                no.add(sjfx);
            }else{
                yes.add(sjfx);
            }
        }
        map = new HashMap<>();
        for (int i = 0; i < yes.size(); i++) {
            String id = yes.get(i).getCarnumber();
            Integer count = map.get(id);
            map.put(id,(count==null)?1:count+1);
        }
        for (Integer count:map.values()) {
            if (count<=2){
                a++;
            }else if (count>=3&&count<=5){
                b++;
            }else  {
                c++;
            }
        }
        mapyesAge = new HashMap<>();
        mapnoAge = new HashMap<>();
        for (int i = 0; i < yes.size(); i++) {
            String Birth = yes.get(i).getShengri().substring(0,4);
            String year = ((Integer.parseInt(Birth)-1900)/10)+"";
            Integer count = mapyesAge.get(year);
            mapyesAge.put(year,(count==null)?1:count+1);
        }
        for (int i = 0; i < no.size(); i++) {
            String Birth = no.get(i).getShengri().substring(0,4);
            String year = ((Integer.parseInt(Birth)-1900)/10)+"";
            Integer count = mapnoAge.get(year);
            mapnoAge.put(year,(count==null)?1:count+1);
        }
        mapyesSex = new HashMap<>();
        mapnoSex= new HashMap<>();
        for (int i = 0; i < yes.size(); i++) {
            String sex = yes.get(i).getSex();
            Integer count = mapyesSex.get(sex);
            mapyesSex.put(sex,(count==null)?1:count+1);
        }
        for (int i = 0; i < no.size(); i++) {
            String sex = no.get(i).getSex();
            Integer count = mapnoSex.get(sex);
            mapnoSex.put(sex,(count==null)?1:count+1);
        }
        mapyesTime = new HashMap<>();
        for (int i = 0; i < yes.size(); i++) {
            String index[] = yes.get(i).getDatetime().split(" ");
            String Time[] = index[1].split(":");
            if (Time[0].equals("00")) {
                Integer count = mapyesTime.get(0);
                mapyesTime.put(0, (count == null) ? 1 : count + 1);
            } else {
                Integer hour = Integer.parseInt(Time[0]) - 1;
                Integer count = mapyesTime.get(hour / 2);
                mapyesTime.put(hour / 2, (count == null) ? 1 : count + 1);
            }
        }
        mapyesType = new HashMap<>();
        for (int i=0;i<yes.size();i++){
            String type = yes.get(i).getPaddr();
            Integer count = mapyesType.get(type);
            mapyesType.put(type,(count ==null)?1:count+1);
        }
        typeLists = new ArrayList<>(mapyesType.entrySet());
        Collections.sort(typeLists, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        initView();
    }

    private void initView() {
        fragments.add(new SJFXFragment1(sjfxes,yes));
        fragments.add(new SJFXFragment2(yes,map));
        fragments.add(new SJFXFragment3(a,b,c));
        fragments.add(new SJFXFragment4(mapyesAge,mapnoAge));
        fragments.add(new SJFXFragment5(mapyesSex,mapnoSex));
        fragments.add(new SJFXFragment6(mapyesTime));
        fragments.add(new SJFXFragment7(typeLists));
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                for (int j = 0; j < linearLayout.getChildCount(); j++) {
                    ImageView imageView = (ImageView) linearLayout.getChildAt(j);
                    if (j == i) {
                        imageView.setImageResource(R.mipmap.page_indicator_focused);
                    } else {
                        imageView.setImageResource(R.mipmap.page_indicator_unfocused);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        for (int i = 0; i < fragments.size(); i++) {
            ImageView imageView = new ImageView(this);
            if (i == 0) {
                imageView.setImageResource(R.mipmap.page_indicator_focused);
            } else {
                imageView.setImageResource(R.mipmap.page_indicator_unfocused);
            }
            imageView.setLayoutParams(new ViewGroup.LayoutParams(50, 50));
            linearLayout.addView(imageView);
        }

    }

    class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
