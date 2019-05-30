package com.xtoolapp.file.filedemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.xtoolapp.file.filedemo.bitmapmesh.MeshActivity
import com.xtoolapp.file.filedemo.database.DataBaseActivity
import com.xtoolapp.file.filedemo.douyin.VideoTestActivity
import com.xtoolapp.file.filedemo.ext.getViewModel
import com.xtoolapp.file.filedemo.ext.logi
import com.xtoolapp.file.filedemo.ext.start
import com.xtoolapp.file.filedemo.fastjson.FastJsonActivity
import com.xtoolapp.file.filedemo.file.ScanFileActivity
import com.xtoolapp.file.filedemo.foregroundservice.ForegroundService
import com.xtoolapp.file.filedemo.mvvm.AActivity
import com.xtoolapp.file.filedemo.mvvm.AViewModel
import com.xtoolapp.file.filedemo.rebeal.FirstActivity
import com.xtoolapp.file.filedemo.wifi.WifiFileActivity
import com.xtoolapp.file.filedemo.xfermode.XfermodeActivity
import com.xtoolapp.file.filedemo.xfermode.sampleActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val manager: SplitInstallManager by lazy { SplitInstallManagerFactory.create(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startService(Intent(this@MainActivity, ForegroundService::class.java))
        setOnClickEvent()
        var viewModel = getViewModel(AViewModel::class.java)
        logi(viewModel.toString())
    }

    private fun setOnClickEvent() {
        bt_scan_file.setOnClickListener { start(ScanFileActivity::class.java) }
        bt_reveal.setOnClickListener { start(FirstActivity::class.java) }
        bt_xfermode_demo.setOnClickListener { start(sampleActivity::class.java) }
        bt_xfermode_test.setOnClickListener { start(XfermodeActivity::class.java) }
        bt_wifi.setOnClickListener { start(WifiFileActivity::class.java) }
        bt_layout_manager.setOnClickListener { start(VideoTestActivity::class.java) }
        bt_database.setOnClickListener { start(DataBaseActivity::class.java) }
        bt_mesh.setOnClickListener { start(MeshActivity::class.java) }
        bt_fastjson.setOnClickListener { start(FastJsonActivity::class.java) }
        bt_vm.setOnClickListener { start(AActivity::class.java) }
        bt_feature1.setOnClickListener {
            if (manager.installedModules.contains("feature1")) {
                Intent().setClassName(BuildConfig.APPLICATION_ID, "com.android.feature1.Feature1Activity")
                        .also { startActivity(it) }
            }
        }
        bt_feature2.setOnClickListener {
            if (manager.installedModules.contains("feature2")) {
                Intent().setClassName(BuildConfig.APPLICATION_ID, "com.android.feature2.Feature2Activity")
                        .also { startActivity(it) }
            }
        }
    }

}
