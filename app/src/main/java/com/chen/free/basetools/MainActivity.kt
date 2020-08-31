package com.chen.free.basetools

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (checkESMPermission()) {
            //执行全部文件的查询操作
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.e("CHEN", "onResume:" + checkESMPermission())
        if (checkESMPermission()) {
            val file = File("/sdcard/Download/我的ansi.txt")
            val readBytes = file.readBytes()
            for (item: Byte in readBytes) {
                Log.e("CHEN", item.toString())
            }

        }
    }

    /**
     * 在 Android 11 判断是否有中访问所有文档权限
     * 不在Android 11 条件下不需要该权限，默认就含有权限
     * todo 这里还需要判断 Android 10 的访问文档权限，因为 Android 10也施行了分区存储机制，但没有强制执行
     */
    private fun checkESMPermission(): Boolean {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) Environment.isExternalStorageManager() else true
    }
}