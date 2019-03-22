package com.shaohua.sheducation.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njcool.lzccommon.adapter.CoolStaticPagerAdapter;
import com.njcool.lzccommon.utils.CoolGlideUtil;
import com.njcool.lzccommon.vo.CoolADInfo;
import com.shaohua.sheducation.activity.CommonWebViewActivity;

import java.util.List;


/**
 * Created by lizhichuan on 16/7/6.
 */
public class TestNormalAdapter extends CoolStaticPagerAdapter {
    private List<CoolADInfo> adInfos;
    private Activity context;

    public List<CoolADInfo> getAdInfos() {
        return adInfos;
    }

    public void setAdInfos(List<CoolADInfo> adInfos) {
        this.adInfos = adInfos;
    }

    public TestNormalAdapter(Activity context) {
        this.context = context;
    }

    @Override
    public View getView(final ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(com.njcool.lzccommon.R.layout.item_home_pic, null);

        ImageView img = (ImageView) view.findViewById(com.njcool.lzccommon.R.id.img_pic);
        TextView tv_tips = (TextView) view.findViewById(com.njcool.lzccommon.R.id.tv_tips);
        if (adInfos.get(position).getImgUrl().endsWith(".gif")) {
            CoolGlideUtil.GifInto(context, adInfos.get(position).getImgUrl(), img);
        } else {
            CoolGlideUtil.urlInto(context, adInfos.get(position).getImgUrl(), img, com.njcool.lzccommon.R.mipmap.img_banner_failure);
        }

//        tv_tips.setText(adInfos.get(position).getContent());
//        android.support.v4.view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        img.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(adInfos.get(position).getTargetUrl())) {

                    context.startActivity(new Intent(context, CommonWebViewActivity.class).putExtra("id",
                            adInfos.get(position).getId()).
                            putExtra("url", adInfos.get(position).getTargetUrl()).putExtra("type", "5"));
                    context.overridePendingTransition(com.njcool.lzccommon.R.anim.bg_alpha_in, com.njcool.lzccommon.R.anim.bg_alpha_out);
                }
            }
        });

        return view;
    }

    @Override
    public int getCount() {
        if (this.adInfos != null) {
            return adInfos.size();
        }
        return 0;
    }
}
