![WeatherNotFound Banner](https://github.com/AbolfaZlRezaEe/WeatherNotFound/assets/73066290/52b6d69c-633d-4507-9039-72c956b468bf)

<p align="center">
<img src="https://jitpack.io/v/AbolfaZlRezaEe/WeatherNotFound.svg" >
</p>

## What is WeatherNotFound?

Did you ever think about having an Android application that shows Weather information to users? or Do you have any Android application with the subject of Weather status? I think you would love to check this library out =)))

**WeatherNotFound** provides everything you need to access the latest status of weather all over the world. also, it will provide you with cache functionalities for a better experience in your application.

## Using WeatherNotFound in your project

To use WeatherNotFound library, you should follow the steps below:

1. In `setting.gradle` file of your project, you should add `jitpack` repository like the following snippet:

```kotlin
pluginManagement {
    repositories {
        ...
        maven("https://jitpack.io")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        ...
        maven("https://jitpack.io")
    }
}
```

2. In `build.gradle` file of your project, you must add the following dependency like the below:

```kotlin
dependencies {
    implementation("com.github.AbolfaZlRezaEe:WeatherNotFound:{latest_version}")
}
```

3. After you followed the two steps above, you should sync the project and wait for Gradle to download all dependencies it needs. when syncing is finished, you should provide OpenWeather api key in `AndroidManifest`:
```xml
<meta-data
    android:name="weather_not_found.open_weather_token"
    android:value="YOUR_KEY" />
```

4. Finally this is the time you can use the library! in your activity, you can use [OpenWeatherMap](https://openweathermap.org/) services like this:

> NOTE: For now, we just have [CurrentWeather Api](https://openweathermap.org/current) and [5 Day 3 Hour Forecast](https://openweathermap.org/forecast5)! Other APIs will be added as soon as possible.

```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WeatherNotFound.getInstance().getCurrentWeatherInformation(
            latitude = latitude,
            longitude = longitude,
            weatherNotFoundCallback = object :
                WeatherNotFoundCallback<WeatherNotFoundResponse<CurrentWeatherModel>, WeatherNotFoundError> {
                override fun onSuccess(response: WeatherNotFoundResponse<CurrentWeatherModel>) {
                    // Do whatever you want...
                }

                override fun onError(error: WeatherNotFoundError) {
                    error.exception?.printStackTrace()
                }
            }
        )
    }
}
```


## More Configurations

WeatherNotFound initialized itself by a default configuration in application's startup automatically.
If you want to override them you should disable `auto_init` by providing it in AndroidManifest:

```xml
<meta-data
    android:name="weather_not_found.auto_init"
    android:value="false" />
```

And then initialize it with your configurations (Recommended place is in Application class):

```kotlin
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        WeatherNotFound.getInstance().init(
            context = this,
            openWeatherResponseLanguage = /* Your value */,
            openWeatherResponseUnit = /* Your value */,
            httpLoggingLevel = HttpLoggingInterceptor.Level.BODY,
            cacheMechanismEnabled = true,
            readTimeoutInSeconds = /* Your value */,
            connectTimeoutInSeconds =  /* Your value */
        )
    }
}
```


> For more information you can see [Sample Application](https://github.com/AbolfaZlRezaEe/WeatherNotFound/tree/develop/app)!

## WeatherNotFound Errors

In some situations, WeatherNotFound will log several errors which tells you what the problem is. for better understanding, we described the reason for each error in following section:

- `Please ensure you have INTERNET permission in your application!`: This run-time exception will occur when you don't have **Internet permission** on your application. make sure you added this permission in the `AndroidManifest.xml` file.

- `You didn't call init() function of WeatherNotFound!`: This run-time exception will occur when you don't call the `init()` function of **WeatherNotFound** library in your `Application` class. make sure you call it!

- `Validation failed! Your Api key is not working...`: This is a log that you might see in your logcat section of Android Studio. if you saw this, you should check `weatherNotFound.properties` file and make sure you specified the needed parameter for ApiKey section(if you don't know how, check [here](https://github.com/AbolfaZlRezaEe/WeatherNotFound/tree/develop#how-weathernotfound-works)). if you specified and you still have this log, you should check the [OpenWeatherMap](https://openweathermap.org/) website and make sure your ApiKey is enabled.

> Don't forget that if you just created your Apikey, it takes some time to enabling it from [OpenWeatherMap](https://openweathermap.org/). So wait some minutes and then try again.

## Contribution
Make me happy by contributing to this project! You can help me fix bugs, add features and resolve issues so WeatherNotFound can grow.
To start your contribution, submit new issues and create pull requests. You can also check out the list of problems in the **[Issues Section](https://github.com/AbolfaZlRezaEe/WeatherNotFound/issues)**.
