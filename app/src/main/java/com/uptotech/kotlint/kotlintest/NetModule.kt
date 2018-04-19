package com.uptotech.kotlint.kotlintest

import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.preference.PreferenceManager
import android.util.Log
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.uptotech.kotlint.kotlintest.api.ConfBookingApi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetModule {
    val TIMEOUT = 30L
    val TAG = "WebApiManager"
    var mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(message: Message) {
            Log.e(TAG, "handleMessage: " + message.obj.toString())
        }
    }


    // Dagger will only look for methods annotated with @Provides
    @Provides
    @Singleton
    fun providesSharedPreferences(application: App):
            SharedPreferences {
        Log.e("Diana", "providesSharedPreferences +")
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Provides
    @Singleton
    fun provideOkHttpCache(application: App): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val interceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                    //.addHeader("Authorization", "Token token=xxx")
                    .build()
            val response = chain.proceed(request)

            val showToast = response.code() == 400
            if (showToast) {
                val txt = "intercept: error in request" + request.method() + " " +
                        request.url() //+ " response: " + response.body().string();
                Log.e(TAG, txt)
                val message = mHandler.obtainMessage(0, txt)
                message.sendToTarget()
            }
            response
        }
        val client = OkHttpClient()

        return client.newBuilder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .followRedirects(true)
                .followSslRedirects(true)
                .addInterceptor(logging)
                .addInterceptor(interceptor)
                .cache(cache)
                .build()

    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.API_END_POINT)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

    }

    @Provides
    @Singleton
    fun providesConfBookingApi(retrofit: Retrofit):
            ConfBookingApi {
        return retrofit.create(ConfBookingApi::class.java)
    }
}