## How Sample Project Works?

You have two options for using Sample Project:

1. You can use auto-init mechanism which library provided. For that, you should modify the `Manifest` file of the project and enter your ApiKey into the `weather_not_found.open_weather_api_key` metadata. Also, you should change the value of `weather_not_found.auto_init_enabled` metadata to `true`. Don't forget to comment `init` function in `BaseApplication` class. the result should something like bellow:

```xml
<application>

        <meta-data
            android:name="weather_not_found.auto_init_enabled"
            android:value="true"/>

        <meta-data
            android:name="weather_not_found.open_weather_api_key"
            android:value="Your Open Weather ApiKey"/>

</application>
```

```kotlin
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

       /* WeatherNotFound.getInstance().init(
            context = this,
            openWeatherApiKey = BuildConfig.OpenWeatherApiKey,
            openWeatherResponseLanguage = BuildConfig.OpenWeatherResponseLanguage,
            openWeatherResponseUnit = BuildConfig.OpenWeatherResponseUnit,
            httpLoggingLevel = HttpLoggingInterceptor.Level.BODY,
            cacheMechanismEnabled = true
        )*/
    }
}
```

After you set these values, Sample project is ready to go!

2. Or, you can use manual initialization by disabling the auto init in `Manifest` file and use `init` function in `BaseApplication` class. The result will be something like bellow:

```xml
<application>

        <meta-data
            android:name="weather_not_found.auto_init_enabled"
            android:value="false"/>

</application>
```

```kotlin
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        WeatherNotFound.getInstance().init(
            context = this,
            openWeatherApiKey = BuildConfig.OpenWeatherApiKey,
            openWeatherResponseLanguage = BuildConfig.OpenWeatherResponseLanguage,
            openWeatherResponseUnit = BuildConfig.OpenWeatherResponseUnit,
            httpLoggingLevel = HttpLoggingInterceptor.Level.BODY,
            cacheMechanismEnabled = true
        )
    }
}
```

But before building the project, you should pay attention to one thing! in this directory we have a file called `weatherNotFound.properties`, in this file several parameters defined but no value passed. please read the following definitions about each parameter and then set your own value:

- `OpenWeatherApiKey`: This is your apikey that you receives from [OpenWeatherMap](https://openweathermap.org/). If you don't have any, click [here](https://home.openweathermap.org/users/sign_up) and follow the steps.

> NOTE: When you create your APIKey, it takes time to enabling it. if you enter it into library an faced with APIKey error log, you should wait some minutes(or hours in some cases) and than try again!

- `OpenWeatherResponseLanguage`: This is an optional parameter you can specify for the library. With this, you tell the library which language you choose for OpenWeatherMap responses. If you don't define this parameter, it will be `en` as the default value!
- `OpenWeatherResponseUnit`: This is another optional parameter you can specify for the library. With this, you tell the library which unit you want to receive the weather statuses. If you don't define this parameter, it will be `metric` as the default value!

> For more information about what's going on, you can check [OpenWeatherMap](https://openweathermap.org/)!

After you set these values, Sample project is ready to go!