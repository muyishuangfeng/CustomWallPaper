package com.yk.silence.customwallpaper.widget.activity

import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.hwangjr.rxbus.thread.EventThread
import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.base.BaseActivity
import com.yk.silence.customwallpaper.constance.Constants
import com.yk.silence.customwallpaper.impl.OnItemInstallListener
import com.yk.silence.customwallpaper.mvp.model.bean.AppBean
import com.yk.silence.customwallpaper.service.WebService
import com.yk.silence.customwallpaper.ui.dialog.WifiDialogFragment
import com.yk.silence.customwallpaper.util.AppUtils
import com.yk.silence.customwallpaper.widget.adapter.AppAdapter
import com.yk.silence.toolbar.CustomTitleBar
import kotlinx.android.synthetic.main.activity_app.*
import rx.Observable
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class AppActivity : BaseActivity(), OnItemInstallListener, CustomTitleBar.TitleClickListener,
        SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {


    var mAdapter: AppAdapter? = null
    var mList: MutableList<AppBean>? = ArrayList()
    private val mFragment: WifiDialogFragment? = WifiDialogFragment()


    override fun getLayoutID(): Int {
        return R.layout.activity_app
    }

    override fun initData() {
        RxBus.get().register(this)
        title_app.setTitleClickListener(this)
        fab_app.setOnClickListener(this)
        initAdapter()
    }


    override fun onInstallClick(position: Int) {
    }

    override fun onUnInstallClick(position: Int) {
    }


    override fun onLeftClick() {
        onBackPressed()
    }

    override fun onRightClick() {
    }

    override fun onRefresh() {
        RxBus.get().post(Constants.RxBusEventType.LOAD_BOOK_LIST, 0)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fab_app -> {
                WebService.start(this)
                mFragment?.show(supportFragmentManager, "")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        WebService.stop(this)
        RxBus.get().unregister(this)
    }


    /**
     * 初始化适配器
     */
    private fun initAdapter() {
        srl_app.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)
        srl_app.setOnRefreshListener(this)
        mAdapter = AppAdapter(this, mList!!)
        rlv_app.layoutManager = LinearLayoutManager(this)
        mAdapter?.mListener = this
        rlv_app.adapter = mAdapter
        RxBus.get().post(Constants.RxBusEventType.LOAD_BOOK_LIST, 0)
    }

    /**
     * 获取数据
     */
    @Subscribe(thread = EventThread.IO, tags = [Tag(Constants.RxBusEventType.LOAD_BOOK_LIST)])
    public fun loadData(type: Int) {
        val listArr = java.util.ArrayList<AppBean>()
        val dir = Constants.DIR
        if (dir.exists() && dir.isDirectory) {
            val fileNames = dir.listFiles()
            if (fileNames != null) {
                for (fileName in fileNames) {
                    handleApk( fileName.absolutePath, fileName.length(), listArr)
                }
            }
        }
        runOnUiThread {
            srl_app.isRefreshing = false
            if (mList!!.isNotEmpty()) {
                mList?.clear()
                mList?.addAll(listArr)
                if (mAdapter != null) {
                    mAdapter?.notifyDataSetChanged()
                }
            }

        }
    }

    @Deprecated("")
    private fun loadData() {
        Observable.create<List<AppBean>> { subscriber ->
            val list = java.util.ArrayList<AppBean>()
            val dir = Constants.DIR
            if (dir.exists() && dir.isDirectory) {
                val fileNames = dir.listFiles()
                if (fileNames != null) {
                    for (fileName in fileNames) {
                        handleApk( fileName.absolutePath, fileName.length(), list)
                    }
                }
            }
            subscriber.onNext(list)
            subscriber.onCompleted()
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<AppBean>> {
                    override fun onCompleted() {
                        mAdapter?.notifyDataSetChanged()
                    }

                    override fun onError(e: Throwable) {
                        mAdapter?.notifyDataSetChanged()
                    }

                    override fun onNext(books: List<AppBean>) {
                        mList?.clear()
                        mList?.addAll(books)
                    }
                })
    }

   private fun handleApk( path: String, fileSize: Long, list: MutableList<AppBean>) {
        val infoModel: AppBean? = AppBean()
        var archiveFilePath = ""
        archiveFilePath = path
        val pm = packageManager
        val info = pm.getPackageArchiveInfo(archiveFilePath, 0)

        if (info != null) {
            val appInfo = info.applicationInfo
            appInfo.sourceDir = archiveFilePath
            appInfo.publicSourceDir = archiveFilePath
            val packageName = appInfo.packageName  //得到安装包名称
            val version = info.versionName       //得到版本信息
            var icon: Drawable? = pm.getApplicationIcon(appInfo)
            var appName = pm.getApplicationLabel(appInfo).toString()
            if (TextUtils.isEmpty(appName)) {
                appName = AppUtils.getApplicationName(this, packageName)
            }
            if (icon == null) {
                icon = AppUtils.getIconFromPackageName(packageName, this) // 获得应用程序图标
            }
            infoModel?.name = appName
            infoModel?.packageName = packageName
            infoModel?.path = path
            infoModel?.size = AppUtils.getFileSize(fileSize)
            infoModel?.version = version
            infoModel?.icon = icon
            infoModel?.installed = AppUtils.isAvilible(this, packageName)
            infoModel?.let { list.add(it) } ?: infoModel?.let { mList?.add(it) }
        }

    }

}
