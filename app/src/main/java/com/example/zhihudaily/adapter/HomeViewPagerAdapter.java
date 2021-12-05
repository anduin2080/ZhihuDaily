package com.example.zhihudaily.adapter;

import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhihudaily.HomePagerClickListener;
import com.example.zhihudaily.R;
import com.example.zhihudaily.Utils.BaseApplication;
import com.example.zhihudaily.bean.HomeTopInfo;
import com.squareup.picasso.Picasso;
import java.util.List;
import butterknife.ButterKnife;


public class HomeViewPagerAdapter extends PagerAdapter {
    private List<HomeTopInfo> homeTopInfoList;
    private HomePagerClickListener mListener;

    public HomeViewPagerAdapter(List<HomeTopInfo> imageViewList) {
        this.homeTopInfoList = imageViewList;
    }

    //设置item listener
    public void setOnItemClickListener(HomePagerClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(BaseApplication.getApplication());
        View RootView = layoutInflater.inflate(R.layout.top_view_pager, viewGroup, false);
        //响应viewPager的点击事件
        RootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onHomeViewPagerClick(null, position);
                }
            }
        });
        ButterKnife.inject(RootView);
        TextView topTitle = ButterKnife.findById(RootView, R.id.top_title);
        ImageView topImage = ButterKnife.findById(RootView, R.id.top_image);
        topTitle.setText(homeTopInfoList.get(position).getTitle());
        Picasso.with(BaseApplication.getApplication()).load(homeTopInfoList.get(position).getImage()).fit().into(topImage);
        viewGroup.addView(RootView);
        return RootView;
    }

    @Override
    public int getCount() {
        return homeTopInfoList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup viewGroup, int position, Object object) {
        viewGroup.removeView((View) object);
    }
}
