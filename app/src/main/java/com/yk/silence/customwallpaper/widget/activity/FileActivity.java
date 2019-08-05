package com.yk.silence.customwallpaper.widget.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.yk.silence.customwallpaper.R;
import com.yk.silence.customwallpaper.constance.Constants;
import com.yk.silence.customwallpaper.impl.OnItemInstallListener;
import com.yk.silence.customwallpaper.mvp.model.bean.AppBean;
import com.yk.silence.customwallpaper.service.WebService;
import com.yk.silence.customwallpaper.util.InstallUtils;
import com.yk.silence.customwallpaper.widget.adapter.FileAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class FileActivity extends AppCompatActivity
        implements Animator.AnimatorListener, View.OnClickListener, OnItemInstallListener,
        SwipeRefreshLayout.OnRefreshListener {
    Toolbar mToolbar;
    FloatingActionButton mFab;
    RecyclerView mRlvApp;
    SwipeRefreshLayout mSrlApp;
    List<AppBean> mList = new ArrayList<>();
    FileAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        initView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu, menu);//加载menu布局
        return true;
    }

    @Override
    public void onClick(View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mFab,
                "translationY", 0, mFab.getHeight() * 2)
                .setDuration(200L);
        objectAnimator.setInterpolator(new AccelerateInterpolator());
        objectAnimator.addListener(this);
        objectAnimator.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WebService.stop(this);
        RxBus.get().unregister(this);
    }

    @Subscribe(tags = {@Tag(Constants.RxBusEventType.POPUP_MENU_DIALOG_SHOW_DISMISS)})
    public void onPopupMenuDialogDismiss(Integer type) {
        if (type == Constants.MSG_DIALOG_DISMISS) {
            WebService.stop(this);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mFab,
                    "translationY", mFab.getHeight() * 2, 0).setDuration(200L);
            objectAnimator.setInterpolator(new AccelerateInterpolator());
            objectAnimator.start();
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(this);
        mRlvApp = findViewById(R.id.recyclerview);
        mSrlApp = findViewById(R.id.content_main);
        /*
         * menu item点击事件监听
         */
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.test_menu1:
                        if (!mList.isEmpty()) {
                            showDialog();
                        } else {
                            Toast.makeText(FileActivity.this, "暂无可删内容", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                return false;
            }
        });
        Timber.plant(new Timber.DebugTree());
        RxBus.get().register(this);
        initAdapter();
    }


    @Override
    public void onAnimationStart(Animator animation) {
        WebService.start(this);

    }

    @Override
    public void onAnimationEnd(Animator animation) {
    }

    @Override
    public void onAnimationCancel(Animator animation) {
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        mAdapter = new FileAdapter(mList, this);
        mRlvApp.setHasFixedSize(true);
        mRlvApp.setLayoutManager(new LinearLayoutManager(this));
        mRlvApp.setAdapter(mAdapter);
        RxBus.get().post(Constants.RxBusEventType.LOAD_BOOK_LIST, 0);

        mSrlApp.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSrlApp.setOnRefreshListener(this);
        mAdapter.setOnInstallListener(this);

    }



    @Deprecated
    private void loadAppList() {
        Observable.create(new Observable.OnSubscribe<List<AppBean>>() {
            @Override
            public void call(Subscriber<? super List<AppBean>> subscriber) {
                List<AppBean> list = new ArrayList<>();
                File dir = new File(Constants.INSTANCE.getINSTALL_PATH());
                if (dir.exists() && dir.isDirectory()) {
                    File[] fileNames = dir.listFiles();
                    if (fileNames != null) {
                        for (File fileName : fileNames) {
                            handleApk(fileName.getAbsolutePath(), fileName.length(), list);
                        }
                    }
                }
                subscriber.onNext(list);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<AppBean>>() {
                    @Override
                    public void onCompleted() {
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNext(List<AppBean> books) {
                        mList.clear();
                        mList.addAll(books);
                    }
                });
    }

    @Override
    public void onInstallClick(int position) {
        AppBean mBean = mList.get(position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean installAllowed = getPackageManager().canRequestPackageInstalls();
            if (installAllowed) {
                InstallUtils.installApkFile(FileActivity.this, new File(Objects.requireNonNull(mBean.getPath())));
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                InstallUtils.installApkFile(FileActivity.this, new File(Objects.requireNonNull(mBean.getPath())));
            }
        } else {
            InstallUtils.installApkFile(FileActivity.this, new File(Objects.requireNonNull(mBean.getPath())));
        }




    }

    @Override
    public void onUnInstallClick(int position) {
        AppBean mBean = mList.get(position);
        InstallUtils.delete(FileActivity.this, mBean.getPackageName());
    }




    @Override
    public void onRefresh() {
        RxBus.get().post(Constants.RxBusEventType.LOAD_BOOK_LIST, 0);
    }

    /**
     * 获取apk信息
     */
    private void handleApk(String path, long length, List<AppBean> list) {
        AppBean infoModel = new AppBean();
        String archiveFilePath = "";
        archiveFilePath = path;
        PackageManager pm = getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(archiveFilePath, 0);

        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            appInfo.sourceDir = archiveFilePath;
            appInfo.publicSourceDir = archiveFilePath;
            String packageName = appInfo.packageName;  //得到安装包名称
            String version = info.versionName;       //得到版本信息
            Drawable icon = pm.getApplicationIcon(appInfo);
            String appName = pm.getApplicationLabel(appInfo).toString();
            if (TextUtils.isEmpty(appName)) {
                appName = InstallUtils.getApplicationName(this, packageName);
            }
            if (icon == null) {
                icon = InstallUtils.getIconFromPackageName(packageName, this); // 获得应用程序图标
            }
            infoModel.setName(appName);
            infoModel.setPackageName(packageName);
            infoModel.setPath(path);
            infoModel.setSize(InstallUtils.getFileSize(length));
            infoModel.setVersion(version);
            infoModel.setIcon(icon);
            infoModel.setInstalled(InstallUtils.isAvilible(this, packageName));
            if (list == null)
                mList.add(infoModel);
            else
                list.add(infoModel);
        }
    }


    /**
     * 删除所有文件
     */
    private void deleteAll() {
        File dir = new File(Constants.INSTANCE.getINSTALL_PATH());
        if (dir.exists() && dir.isDirectory()) {
            File[] fileNames = dir.listFiles();
            if (fileNames != null) {
                for (File fileName : fileNames) {
                    fileName.delete();
                }
            }
        }
    }

    /**
     * 显示确认对话框
     */
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("温馨提示:");
        builder.setMessage("确定全部删除吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteAll();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }
}
