package com.martinboy.carouselbanner

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.martinboy.carouselbannerlib.CarouseBanner

class MainActivity : AppCompatActivity() {

    var banner : CarouseBanner? = null
//    var netImage: String? = "https://fiverr-res.cloudinary.com/images/t_main1,q_auto,f_auto/gigs/115729418/original/2534e50d923357383b0c64674e9b8692ceb1ef61/design-a-carousel-banner.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        banner = findViewById(R.id.banner)
        val imageArray = intArrayOf(R.drawable.banner, R.drawable.banner2, R.drawable.banner3)
        banner?.setResourceImageList(imageArray, 166)
//        val imageArray = arrayOf(netImage, netImage, netImage, netImage, netImage, netImage, netImage)
//        banner?.setNetImageList(imageArray, 300)
        banner?.buildIndicator(20, 20, 10)
        banner?.startPlay(5000)
    }
}
