package com.example.appml.common.networking;

import com.example.appml.BuildConfig;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ServiceBuilder {

    private static final int DEFAULT_TIME_OUT = 5;

    private ServiceBuilder() {
    }

    public static <T> T createService(final Class<T> serviceClass) {

        final long timeout;
        final TimeUnit timeUnit;
        final Timeout timeoutAnnotation = serviceClass.getAnnotation(Timeout.class);

        if (timeoutAnnotation == null) {
            timeout = DEFAULT_TIME_OUT;
            timeUnit = TimeUnit.SECONDS;
        } else {
            timeout = timeoutAnnotation.timeout();
            timeUnit = timeoutAnnotation.timeUnit();
        }

        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(timeout, timeUnit)
                .writeTimeout(timeout, timeUnit)
                .readTimeout(timeout, timeUnit)
                .addInterceptor(loggingInterceptor)
                .build();

        final Gson gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setPrettyPrinting()
                .setVersion(1.0)
                .create();

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(serviceClass);
    }
}
