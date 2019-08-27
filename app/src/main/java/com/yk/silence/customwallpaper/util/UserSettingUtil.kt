package com.yk.silence.customwallpaper.util

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.util.Log
import android.widget.TextView
import com.yk.silence.customwallpaper.R
import com.yk.silence.customwallpaper.constance.AppConfig
import com.yk.silence.customwallpaper.ui.dialog.ChooseSexDialog
import com.yk.silence.customwallpaper.util.cache.CacheUtil
import com.yk.silence.locationpicker.citywheel.CityConfig
import com.yk.silence.locationpicker.impl.OnCityItemClickListener
import com.yk.silence.locationpicker.model.CityBean
import com.yk.silence.locationpicker.model.DistrictBean
import com.yk.silence.locationpicker.model.ProvinceBean
import com.yk.silence.locationpicker.ui.CityPickerView
import com.yk.silenct.datepicker.DatePicker
import com.yk.silenct.datepicker.util.DateUtils


object UserSettingUtil {

    fun chooseDate(context: Activity, mTxtResult: TextView) {
        val picker = DatePicker(context)
        picker.setCanLoop(true)
        picker.setWheelModeEnable(true)
        picker.setTopPadding(15)
        picker.setCancelText(context.resources.getString(R.string.text_cancel))
        picker.setSubmitText(context.resources.getString(R.string.text_confirm))
        picker.setRangeStart(1900, 8, 29)
        picker.setRangeEnd(DateUtils.getCurrentYear(), DateUtils.getCurrentMonth(), DateUtils.getCurrentDay())
        picker.setSelectedItem(1990, 6, 12)
        picker.setWeightEnable(true)
        picker.setCancelTextSize(12)
        picker.setCancelTextColor(Color.parseColor("#FF808080"))
        picker.setSubmitTextColor(context.resources.getColor(R.color.colorAccent))
        picker.setCancelTextSize(12)
        picker.setSelectedTextColor(context.resources.getColor(R.color.colorBlack))
        picker.setUnSelectedTextColor(context.resources.getColor(R.color.colorPrimary))
        picker.setLineColor(context.resources.getColor(R.color.colorPrimary))
        picker.setOnDatePickListener(object : DatePicker.OnYearMonthDayPickListener {
            override fun onDatePicked(year: String, month: String, day: String) {
                if ((year + month + day) > DateUtils.SDF.format(System.currentTimeMillis())) {
                    mTxtResult.text = ""
                } else {
                    mTxtResult.text = ("$day/$month/$year")
                    CacheUtil.instance.putValue(AppConfig.USER_BIRTHDAY, "$day/$month/$year")
                }
            }
        })
        picker.show()
    }

    /**
     * 选择性别
     */
    fun chooseSex(context: Activity, mTxtSex: TextView) {
        val mDialog = ChooseSexDialog(context)
        mDialog.setCancelable(false)
        mDialog.setCanceledOnTouchOutside(true)
        mDialog.setOnSexClickListener(object : ChooseSexDialog.OnSexClickListener {
            override fun onBoyClick() {
                mTxtSex.text = context.resources.getString(R.string.text_boy)
                CacheUtil.instance.putValue(AppConfig.USER_SEX, context.resources.getString(R.string.text_boy))
            }

            override fun onGirlClick() {
                mTxtSex.text = context.resources.getString(R.string.text_girl)
                CacheUtil.instance.putValue(AppConfig.USER_SEX, context.resources.getString(R.string.text_girl))
            }
        })
        mDialog.show()
    }

    /**
     * 地区选择
     */
    fun chooseArea(context: Activity, mDefaultProvince: String, mDefaultCity: String,
                   mDefaultDistrict: String, mTxtArea: TextView) {
        val mCityPicker = CityPickerView()
        mCityPicker.init(context)
        val cityConfig = CityConfig.Builder()
                .title(context.resources.getString(R.string.text_choose_area))
                .visibleItemsCount(5)
                .province(mDefaultProvince)
                .city(mDefaultCity)
                .district(mDefaultDistrict)
                .provinceCyclic(true)
                .cityCyclic(true)
                .districtCyclic(true)
                .setCityWheelType(CityConfig.WheelType.PRO_CITY_DIS)
                .setCustomItemLayout(R.layout.item_city)//自定义item的布局
                .setCustomItemTextViewId(R.id.txt_city_name)
                .setShowGAT(true)
                .build()

        mCityPicker.setConfig(cityConfig)
        mCityPicker.setOnCityItemClickListener(object : OnCityItemClickListener() {
            override fun onSelected(province: ProvinceBean?, city: CityBean?, district: DistrictBean?) {
                val sb = StringBuilder()
                if (province != null) {
                    sb.append(province.name).append("-")
                }

                if (city != null) {
                    sb.append(city.name).append("-")
                }

                if (district != null) {
                    sb.append(district.name)
                }
                mTxtArea.text = sb.toString()
                CacheUtil.instance.putValue(AppConfig.USER_AREA, sb.toString())
            }

            override fun onCancel() {}
        })
        mCityPicker.showCityPicker()
    }
}