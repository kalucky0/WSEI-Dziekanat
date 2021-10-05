package dev.kalucky0.wsei

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerImageLoader
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import okhttp3.Request

class DrawerImgLoader(private val context: Context) :
    AbstractDrawerImageLoader() {
    override fun set(imageView: ImageView, uri: Uri, placeholder: Drawable, tag: String?) {
        if (tag == DrawerImageLoader.Tags.PROFILE.name || tag == DrawerImageLoader.Tags.PROFILE_DRAWER_ITEM.name) {
            val picasso = Picasso.Builder(context)
                .downloader(OkHttp3Downloader(Utils.downloaderClient))
                .build()

            picasso.load(uri).placeholder(placeholder).into(imageView)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        } else {
            Picasso.get().load(uri).placeholder(placeholder).into(imageView)
        }
    }

    override fun cancel(imageView: ImageView) {
        Picasso.get().cancelRequest(imageView)
    }
}