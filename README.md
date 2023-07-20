![WeatherNotFound Banner](https://github.com/AbolfaZlRezaEe/WeatherNotFound/assets/73066290/52b6d69c-633d-4507-9039-72c956b468bf)

<p align="center">
<img src="https://jitpack.io/v/AbolfaZlRezaEe/WeatherNotFound.svg" >
</p>

## What is WeatherNotFound?

Did you ever think about having an Android application that shows Weather information to users? or Do you have any Android application with the subject of Weather status? I think you would love to check this library out =)))

**WeatherNotFound** provide everything you need to access the latest status of weather all over the world. also, it will provide you with cache functionalities for a better experience in your application.

## How WeatherNotFound works?

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

3. Before syncing the project, you should follow one more step! in the root directory of you android project, you should create a file and name it `weatherNotFound.properties`. after creating, open it and add the following parameters to the file:

- `OpenWeatherApiKey`: This is your apikey that you receives from [OpenWeatherMap](https://openweathermap.org/). If you don't have any, click [here](https://home.openweathermap.org/users/sign_up) and follow the steps.

> NOTE: When you create your APIKey, it takes time to enabling it. if you enter it into library an faced with APIKey error log, you should wait some minutes(or hours in some cases) and than try again!

- `OpenWeatherResponseLanguage`: This is an optional parameter you can specify for the library. With this, you tell the library which language you choose for OpenWeatherMap responses. If you don't define this parameter, it will be `en` as the default value!
- `OpenWeatherResponseUnit`: This is another optional parameter you can specify for the library. With this, you tell the library which unit you want to receive the weather statuses. If you don't define this parameter, it will be `metric` as the default value!
- `DatabaseSchemaShouldEnable`: Also This is another optional parameter you can specify for the library. With this, you tell the library that you want to export schema of database or not. If you define this and set it to `true`, the schema of database will be generated into `{projectDir}/schemas` directory. If you don't define this parameter, it will be `false` as the default value!

> For more information about what's going on, you can check [OpenWeatherMap](https://openweathermap.org/)!

After defining all the above parameters, you will endup something like this:

```properties
OpenWeatherApiKey=your_api_key
OpenWeatherResponseLanguage=en
OpenWeatherResponseUnit=metric
DatabaseSchemaShouldEnable=false
```

4. After you followed three steps above, you should sync the project and wait for gradle to download all dependencies it needs. when syncing finished, in your `Application` class, you should init the library with the parameters you want. the result will be something like this(for more information you can check the code and see documentation there): 

```kotlin
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        WeatherNotFound.getInstance().init(
            context = this,
            httpLoggingLevel = HttpLoggingInterceptor.Level.BODY,
            cacheMechanismEnabled = true,
            readTimeoutInSeconds = /* Your value */,
            connectTimeoutInSeconds =  /* Your value */
        )
    }
}
```

5. Finally this is the time you can use the library! in your activity, you can use [OpenWeatherMap](https://openweathermap.org/) services like this:

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

## WeatherNotFound Errors

In some situations, WeatherNotFound will log several errors which tells you what the problem is. for better undrestanding, we described the reason of each error in following section:

- `Please ensure you have INTERNET permission in your application!`: This run-time exception will occur when you don't have **Internet permission** on your application. make sure you added this permission in the `AndroidManifest.xml` file.

- `You didn't call init() function of WeatherNotFound!`: This run-time exception will occur when you don't call the `init()` function of **WeatherNotFound** library in your `Application` class. make sure you call it!

- `Validation failed! Your Api key is not working...`: This is a log that you might see in your logcat section of Android Studio. if you saw this, you should check `weatherNotFound.properties` file and make sure you specified the needed parameter for ApiKey section(if you don't know how, check [here]()). if you specified and you still have this log, you should check the [OpenWeatherMap](https://openweathermap.org/) website and make sure your ApiKey is enabled.

> Don't forget that if you just created your Apikey, it takes some time to enabling it from [OpenWeatherMap](https://openweathermap.org/). So wait some minutes and than try again.

- `weatherNotFound.properties didn't exits!! continuing...`: This is a compile-time error log which you might see! if you saw this during compile-time in build tab of Android Studio, make sure you created `weatherNotFound.properties` successfully!

## Contribution
Make me happy by contributing to this project! You can help me fix bugs, add features and resolve issues so WeatherNotFound can grow.
To start your contribution, submit new issues and create pull requests. You can also check out the list of problems in the **[Issues Section](https://github.com/AbolfaZlRezaEe/WeatherNotFound/issues)**.
