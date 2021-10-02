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

class DrawerImgLoader(private val context: Context, private val sessionId: String) :
    AbstractDrawerImageLoader() {
    override fun set(imageView: ImageView, uri: Uri, placeholder: Drawable, tag: String?) {
        if (tag == DrawerImageLoader.Tags.PROFILE.name || tag == DrawerImageLoader.Tags.PROFILE_DRAWER_ITEM.name) {
            val client = OkHttpClient().newBuilder().addInterceptor { chain ->
                val original: Request = chain.request()
                val request: Request = original.newBuilder()
                    .header(
                        "User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0"
                    )
                    .header("Cookie", "ASP.NET_SessionId=$sessionId")
                    .method(original.method(), original.body())
                    .build()
                chain.proceed(request)
            }.build()

            val picasso = Picasso.Builder(context)
                .downloader(OkHttp3Downloader(client))
                .build()

            picasso.load(uri).placeholder(placeholder).into(imageView)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP;
        } else {
            Picasso.get().load(uri).placeholder(placeholder).into(imageView)
        }
    }

    override fun cancel(imageView: ImageView) {
        Picasso.get().cancelRequest(imageView)
    }
}