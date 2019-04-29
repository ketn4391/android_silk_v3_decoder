package com.ketian.android.silkv3

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private var mPagerAdapter: PagerAdapter? = null
    private var mFragments: MutableList<Fragment>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hint.text=""
        if (getPermission()) {
            initFragments()
            mPagerAdapter = PagerAdapter(supportFragmentManager)
            pager.adapter = mPagerAdapter
        }
    }

    fun getPermission(): Boolean {
        val permissionCheck1 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        val permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck1 != PackageManager.PERMISSION_GRANTED || permissionCheck2 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                    124)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 124) {
            if ((grantResults.isNotEmpty()) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                initFragments()
                mPagerAdapter = PagerAdapter(supportFragmentManager)
                pager.adapter = mPagerAdapter
            }
        }
    }

    private fun initFragments() {
        mFragments = ArrayList(2)
        mFragments?.add(VoiceFragment())
        mFragments?.add(ExportFragment())
    }

    fun wechatVoiceDecodeResult(rlt: Int, dest: String) {
        when(rlt){
            0->{
                hint.text="参数有误"
                Toast.makeText(this, "参数有误", Toast.LENGTH_SHORT).show()
            }
            1->{
                hint.text="转换成功,恭喜你成功扒了微信的底裤"
                Toast.makeText(this, "Convert to $dest OK", Toast.LENGTH_SHORT).show()
            }
            else->{}
        }
    }

    private inner class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            return mFragments?.get(position)
        }

        override fun getCount(): Int {
            return mFragments?.size ?: 0
        }
    }


}
