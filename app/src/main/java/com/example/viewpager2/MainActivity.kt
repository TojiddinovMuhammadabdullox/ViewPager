package com.example.viewpager2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.view.View
import androidx.viewpager.widget.ViewPager.PageTransformer
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.viewpager2.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.abs
import kotlin.math.max
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val data = listOf<CustomDateModel>(
            CustomDateModel(R.drawable.home, R.drawable.apricot,"O'rik" ),
            CustomDateModel(R.drawable.list, R.drawable.apple,"Olma" ),
            CustomDateModel(R.drawable.filter, R.drawable.grapes,"Uzum" ),
            CustomDateModel(R.drawable.info, R.drawable.peach,"Shoptoli" ),
        )

        val customViewPagerAdapter = CustomViewPagerAdapter(this, data.map { it.image })
        binding.myViewPAger.adapter = customViewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.myViewPAger){tab, position ->
            tab.text = data[position].title
            tab.setIcon(data[position].icon)
            val badge = tab.orCreateBadge
            badge.number = Random.nextInt(100)
        }.attach()


        binding.myViewPAger.apply {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3

        }

        binding.myViewPAger.apply {
            setPageTransformer(CompositePageTransformer().apply {
                addTransformer(MarginPageTransformer(40))
                addTransformer(object :ViewPager2.PageTransformer{
                    override fun transformPage(page: View, position: Float) {
                        page.apply {
                            scaleY = 0.85F + 0.15F*(1-(abs(position))).toFloat()
                        }
                    }

                })
            })
        }

    }
}