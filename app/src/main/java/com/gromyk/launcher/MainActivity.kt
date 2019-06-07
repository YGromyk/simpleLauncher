package com.gromyk.launcher

import android.app.Activity
import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : Activity() {
    private var installedAppsNames = mutableListOf<String>()
    private var installedApps = mutableListOf<ResolveInfo>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        installedApps = getInstalledApps()
        installedAppsNames = installedApps.map { it.activityInfo.name }.toMutableList()
        initView()
    }

    private fun getInstalledApps() = Unit.let {
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val pkgAppsList = application.packageManager.queryIntentActivities(mainIntent, 0)
        pkgAppsList
    }

    private fun initView() {
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            installedAppsNames
        )
        listView.adapter = adapter
        listView.setOnItemClickListener { _, _, position, _ ->
            startIntent(installedApps[position])
        }
    }

    private fun startIntent(resolveInfo: ResolveInfo) {
        val intent = Intent()
        intent.setClassName(
            resolveInfo.activityInfo.applicationInfo.packageName,
            resolveInfo.activityInfo.name
        )
        startActivity(intent)

    }
}