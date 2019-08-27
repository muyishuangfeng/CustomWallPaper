package com.yk.silence.customwallpaper.widget.activity

import android.Manifest
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.base.BaseActivity
import com.yk.silence.customwallpaper.constance.Constants
import com.yk.silence.customwallpaper.impl.OnItemInviteListener
import com.yk.silence.customwallpaper.mvp.model.bean.ContactBean
import com.yk.silence.customwallpaper.util.cache.SPUtils
import com.yk.silence.customwallpaper.util.contact.ContactUtil
import com.yk.silence.customwallpaper.widget.adapter.ContactListAdapter
import com.yk.silent.permission.HiPermission
import com.yk.silent.permission.impl.PermissionCallback
import com.yk.silent.permission.model.PermissionItem
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_contact_list.*
import java.util.ArrayList

class ContactListActivity : BaseActivity(), OnItemInviteListener,
        SwipeRefreshLayout.OnRefreshListener, TextWatcher {


    private var mList: MutableList<ContactBean>? = ArrayList()
    private var mAdapter: ContactListAdapter? = null

    override fun getLayoutID(): Int {
        return R.layout.activity_contact_list
    }

    override fun initData() {
        srl_contact_list.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)
        srl_contact_list.isRefreshing = true
        srl_contact_list.setOnRefreshListener(this)
        edt_search_contact.addTextChangedListener(this)
        initPermission()

    }

    override fun onInviteClick(position: Int) {
        val mBean = mList?.get(position)
        Toast.makeText(this, mBean.toString(), Toast.LENGTH_LONG).show()
    }

    override fun onRefresh() {
        srl_contact_list.isRefreshing = mList!!.isEmpty()
        loadData("")
    }

    override fun afterTextChanged(s: Editable?) {
        if (s!!.isNotEmpty()) {
            loadData(s.toString())
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    /**
     * 运行时权限
     */
    private fun initPermission() {
        val permissionItems = ArrayList<PermissionItem>()
        permissionItems.add(PermissionItem(Manifest.permission.READ_CONTACTS,
                resources.getString(R.string.text_read), R.drawable.permission_ic_storage))
        HiPermission.create(this)
                .title(resources.getString(R.string.permission_get))
                .msg(resources.getString(R.string.permission_desc))
                .permissions(permissionItems)
                .checkMutiPermission(object : PermissionCallback {
                    override fun onClose() {
                    }

                    override fun onDeny(permission: String?, position: Int) {
                    }

                    override fun onFinish() {
                        loadData("")

                    }

                    override fun onGuarantee(permission: String?, position: Int) {

                    }
                })
    }

    /**
     * 初始化适配器
     */
    private fun initAdapter() {
        mAdapter = ContactListAdapter(this, mList!!)
        rlv_invite_contact.layoutManager = LinearLayoutManager(this)
        rlv_invite_contact.adapter = mAdapter
        mAdapter!!.mListener = this
        mAdapter!!.notifyDataSetChanged()
    }

    /**
     * 加载数据
     */
    private fun loadData(mName: String?) {
        Observable.create<MutableList<ContactBean>> { subscriber ->
            mList = if (mName!!.isNotEmpty()) {
                ContactUtil.searchPhone(this, mName)
            } else {
                ContactUtil.searchAllContacts(this)

            }
            srl_contact_list.isRefreshing = true
            subscriber.onNext(mList!!)
            subscriber.onComplete()
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<MutableList<ContactBean>> {
                    override fun onComplete() {
                        srl_contact_list.isRefreshing = false

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: MutableList<ContactBean>) {
                        initAdapter()
                    }

                    override fun onError(e: Throwable) {
                    }

                })
    }

}
