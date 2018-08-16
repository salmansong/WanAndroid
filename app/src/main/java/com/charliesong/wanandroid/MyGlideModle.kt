package com.charliesong.wanandroid

import android.content.Context
import android.os.Environment
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import android.os.Environment.getExternalStorageDirectory
import java.io.File
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory

@GlideModule
class MyGlideModle:AppGlideModule(){

    var diskCacheSize = 500 * 1024 * 1024L
    private val diskCacheFolder = File(getExternalStorageDirectory(), "WanAndroid")
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)

        //磁盘缓存配置（默认缓存大小250M，默认保存在内部存储中）
        //设置磁盘缓存保存在外部存储，且指定缓存大小
//        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, diskCacheSize));
        //设置磁盘缓存保存在自己指定的目录下，且指定缓存大小
        builder.setDiskCache(DiskLruCacheFactory(DiskLruCacheFactory.CacheDirectoryGetter { diskCacheFolder }, diskCacheSize))
        println("=====================apply options")
        //内存缓存配置（不建议配置，Glide会自动根据手机配置进行分配）
        //设置内存缓存大小
//        builder.setMemoryCache(new LruResourceCache(memoryCacheSize));
        //设置Bitmap池大小
//        builder.setBitmapPool(new LruBitmapPool(bitmapPoolSize));
    }
}