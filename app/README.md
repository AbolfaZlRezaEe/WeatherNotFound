## How Sample Project Works?

Before building the project, you should pay attention to one thing! in this directory we have a file called `weatherNotFound.properties`, in this file several parameters defined but no value passed. please read the following definitions about each parameter and then set your own value:

- `OpenWeatherApiKey`: This is your apikey that you receives from [OpenWeatherMap](https://openweathermap.org/). If you don't have any, click [here](https://home.openweathermap.org/users/sign_up) and follow the steps.

> NOTE: When you create your APIKey, it takes time to enabling it. if you enter it into library an faced with APIKey error log, you should wait some minutes(or hours in some cases) and than try again!

- `OpenWeatherResponseLanguage`: This is an optional parameter you can specify for the library. With this, you tell the library which language you choose for OpenWeatherMap responses. If you don't define this parameter, it will be `en` as the default value!
- `OpenWeatherResponseUnit`: This is another optional parameter you can specify for the library. With this, you tell the library which unit you want to receive the weather statuses. If you don't define this parameter, it will be `metric` as the default value!

> For more information about what's going on, you can check [OpenWeatherMap](https://openweathermap.org/)!

After you set these values, Sample project is ready to go!