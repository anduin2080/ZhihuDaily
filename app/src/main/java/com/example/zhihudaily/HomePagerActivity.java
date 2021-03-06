package com.example.zhihudaily;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.zhihudaily.Utils.BaseApplication;
import com.example.zhihudaily.Utils.LogUtils;
import com.example.zhihudaily.Utils.ThreadMgr;
import com.example.zhihudaily.Utils.UiUtils;
import com.example.zhihudaily.adapter.HomeRecylerViewAdapter;
import com.example.zhihudaily.adapter.MenuRecylerViewAdapter;
import com.example.zhihudaily.adapter.ThemeItemViewAdapter;
import com.example.zhihudaily.bean.HomeInfo;
import com.example.zhihudaily.bean.HomeStoriesInfo;
import com.example.zhihudaily.bean.HomeTopInfo;
import com.example.zhihudaily.bean.ThemeInfo;
import com.example.zhihudaily.bean.ThemeItemInfo;
import com.example.zhihudaily.protocol.HomeProtocol;
import com.example.zhihudaily.protocol.OldNewsProtocol;
import com.example.zhihudaily.protocol.ThemeItemProtocol;
import com.example.zhihudaily.protocol.ThemeProtocol;
import com.example.zhihudaily.view.LoadDataScrollController;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomePagerActivity extends AppCompatActivity implements HomePagerClickListener {

    private static final String TAG = "ContentActivity";
    private ActionBarDrawerToggle mDrawerToggle;
    private LoadDataScrollController loadDataCtl;
    private HomeInfo homeInfo;
    private HomeRecylerViewAdapter recylerViewAdapter;
    private ThemeInfo themeInfo;
    private ThemeItemInfo themeItemInfo;
    private MenuRecylerViewAdapter menuRecylerViewAdapter;
    private ThemeItemViewAdapter themeItemViewAdapter;
    private Toolbar.OnMenuItemClickListener ToolBarMenuItemListener;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.drawer)
    DrawerLayout drawerLayout;
    @InjectView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @InjectView(R.id.recyle_view)
    RecyclerView recylerView;
    @InjectView(R.id.menu_recyler_view)
    RecyclerView menuRecylerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ButterKnife.inject(this);
        initData();
        initView();
    }

    //?????????????????????
    private void initData() {
        loadHomePagerData();
    }

    //?????????????????????
    private void initView() {
        setHomePagerToolBar();
        setToolBarListener();
        setDrawerLayout();
        setSwipeLayout();
    }

    //???????????????????????????ToolBar??????
    private void setThemePagerToolBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setTitle(themeItemInfo.getName());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.getMenu().clear();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.inflateMenu(R.menu.toolbar_theme_add);
        }
    }

    //???????????????ToolBar
    private void setHomePagerToolBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setTitle(R.string.home_page);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.getMenu().clear();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.inflateMenu(R.menu.toolbar_menu);
        }

    }

    //??????ToolBar???????????????
    private void setToolBarListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ToolBarMenuItemListener = new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.theme_add:
                            UiUtils.showToast("theme add?????????");
                            break;
                        case R.id.theme_remove:
                            UiUtils.showToast("theme_remove add?????????");
                            break;
                        case R.id.message:
                            UiUtils.showToast("message?????????");
                            break;
                        case R.id.setting_night_mode:
                            UiUtils.showToast("setting_night_mode add?????????");
                            break;
                        case R.id.setting_option:
                            UiUtils.showToast("setting_option add?????????");
                            break;
                        default:
                            break;
                    }
                    return false;
                }
            };
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setOnMenuItemClickListener(ToolBarMenuItemListener);
        }
    }

    //??????????????????
    private void setSwipeLayout() {
        swipeRefreshLayout.setColorSchemeResources(R.color.blue);
    }

    //???????????????
    private void setDrawerLayout() {
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener((DrawerLayout.DrawerListener) mDrawerToggle);
        mDrawerToggle.syncState();
        loadSlideMenuData();
    }

    //????????????recylerview
    private void setHomeRecylerView() {
        recylerViewAdapter = new HomeRecylerViewAdapter(homeInfo);
        recylerViewAdapter.setOnItemClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(BaseApplication.getApplication());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recylerView.setLayoutManager(layoutManager);
        recylerView.setAdapter(recylerViewAdapter);
        setScrollController();
        setHomePagerToolBar();
    }

    //??????????????????recylerView
    private void setThemeRecylerView() {
        recylerView.removeOnScrollListener(loadDataCtl);
        themeItemViewAdapter = new ThemeItemViewAdapter(themeItemInfo);
        themeItemViewAdapter.setHomePagerClickListener(this);
        recylerView.setAdapter(themeItemViewAdapter);
        setThemePagerToolBar();
    }

    //???????????????????????????
    private void setScrollController() {
        loadDataCtl = new LoadDataScrollController(this);
        swipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) loadDataCtl);
        recylerView.addOnScrollListener(loadDataCtl);
    }

    //?????????????????????
    private void setSlideMenuRecylerView() {
        menuRecylerViewAdapter = new MenuRecylerViewAdapter(themeInfo);
        menuRecylerViewAdapter.setRecylerViewListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(BaseApplication.getApplication());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        menuRecylerView.setLayoutManager(layoutManager);
        menuRecylerView.setAdapter(menuRecylerViewAdapter);
    }

    //?????????????????????
    @Override
    public void onHomePagerRefresh() {
        BaseApplication.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(BaseApplication.getApplication(), "????????????", Toast.LENGTH_SHORT).show();
            }
        }, 3000);
    }

    //??????Home??????ToolBar???title
    @Override
    public void onHomePagerUpdateToolBarTitle(int pos) {
        if (pos == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                toolbar.setTitle(UiUtils.getString(R.string.home_page));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                toolbar.setTitle(recylerViewAdapter.getTitle(pos - 1));
            }
        }
    }

    //????????????????????????
    @Override
    public void onHomePagerEditorClick() {
        UiUtils.showToast("editor click!!");
    }

    //HomeRecylerView?????????????????????
    @Override
    public void onHomePagerItemClick(boolean isThemePagerItem, int position) {
        String id = null;
        if(isThemePagerItem == false){
            HomeStoriesInfo info = homeInfo.getHomeStoriesInfos().get(position);
            id = info.getId();
            LogUtils.e("ID 1 = "+id);
        }else{
            id = themeItemInfo.getStories().get(position).getId();
            LogUtils.e("ID = "+id);
        }
        Intent intent = new Intent();
        intent.putExtra("id", id);
        intent.putExtra("isThemePagerItem", false);
        intent.setClass(this, NewsPagerActivity.class);
        startActivity(intent);
    }

    //??????viewpager???????????????
    @Override
    public void onHomeViewPagerClick(View view, int position) {
        HomeTopInfo info = homeInfo.getHomeTopInfoList().get(position);
        String id = info.getId();
        Intent intent = new Intent();
        intent.putExtra("id", id);
        intent.putExtra("isThemePagerItem", false);
        intent.setClass(this, NewsPagerActivity.class);
        startActivity(intent);
    }

    //????????????????????????
    @Override
    public void onSlideMenuItemClick(View view, int position) {
        if (position == 0) {//??????
            loadHomePagerData();
        } else {
            loadThemePagerData(position);
        }
        drawerLayout.closeDrawer(Gravity.LEFT);
    }

    //??????Follow
    @Override
    public void onSlideMenuAddClick(View view, int position) {
        UiUtils.showToast("theme add ?????????");
    }

    //??????????????????????????????
    @Override
    public void onHomePagerloadMore() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                OldNewsProtocol protocol = new OldNewsProtocol();
                final List<HomeStoriesInfo> homeInfo = protocol.loadData();
                if (homeInfo != null) {
                    UiUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadDataCtl.setLoadingDataStatus(false);
                            recylerViewAdapter.add(homeInfo);
                        }
                    });
                }
            }
        };
        ThreadMgr.getThreadPool().execute(runnable);
    }

    //?????????????????????
    private void loadHomePagerData() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                HomeProtocol homeProtocol = new HomeProtocol("latest");
                homeInfo = homeProtocol.loadData();
                BaseApplication.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setHomeRecylerView();
                    }
                }, 500);
            }
        };
        ThreadMgr.getThreadPool().execute(runnable);
    }

    //????????????????????????
    private void loadThemePagerData(int position) {
        final String themeUrlId = themeInfo.getOthers().get(position - 1).getId();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ThemeItemProtocol protocol = new ThemeItemProtocol(themeUrlId);
                themeItemInfo = protocol.loadData();
                UiUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setThemeRecylerView();
                    }
                });
            }
        };
        ThreadMgr.getThreadPool().execute(runnable);
    }

    //??????????????????????????????
    private void loadSlideMenuData() {
        Runnable runable = new Runnable() {
            @Override
            public void run() {
                ThemeProtocol protocol = new ThemeProtocol();
                themeInfo = protocol.loadData();
                if (themeInfo != null) {
                    UiUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setSlideMenuRecylerView();
                        }
                    });
                }
            }
        };
        ThreadMgr.getThreadPool().execute(runable);
    }

    //????????????
    public void tvMyCollect(View view) {
        UiUtils.showToast("?????????????????????");
    }

    //????????????
    public void tvMyDownload(View view) {
        UiUtils.showToast("?????????????????????");
    }

    //????????????
    public void tvUserIcon(View view) {
        UiUtils.showToast("???????????????");
    }

}
