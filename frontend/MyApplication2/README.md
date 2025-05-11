# Countries App README

This Android application displays a list of countries and allows users to view details about a specific country. It uses a Spring Boot backend to serve country data.

## Features

- View a list of all countries with their flags
- Click on a country to view more details
- Display of basic country information like name, capital, population, etc.

## Project Structure

- **UI Layer**: Activities and ViewModels to display countries and details
- **Data Layer**: Repository pattern to fetch data from the API
- **API Layer**: Retrofit service for network calls
- **DI**: Hilt for dependency injection

## Setup Instructions

### 1. Configure Backend URL

Update the base URL in `AppModule.kt`:

```kotlin
@Provides
@Singleton
fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/") // For local development with emulator
        // Use your actual backend URL in production
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
```

### 2. Update API Service

Ensure your CountryApiService matches the backend endpoints:

```kotlin
@GET("countries/all")
suspend fun getCountries(): List<CountryListResponse>

@GET("countries/{name}")
suspend fun getCountryByName(@Path("name") name: String): CountryDetailsResponse
```

### 3. Add Response Models

Create data classes that match your backend response structure:

```kotlin
data class CountryListResponse(
    val name: String,
    val flag: String
)

data class CountryDetailsResponse(
    val name: String,
    val population: Long,
    val capital: String,
    val flag: String
)
```

### 4. Build and Run

- Open the project in Android Studio
- Connect a device or start an emulator
- Click the "Run" button to build and install the app

## Requirements

- Android Studio Ladybug or newer
- Minimum SDK: 21 (Android 5.0 Lollipop)
- JDK 17+
- Spring Boot backend running on localhost:8080 or remote server

## Troubleshooting

If you encounter the error "Expected BEGIN_ARRAY but was BEGIN_OBJECT", this means the API response structure doesn't match what your code expects. Double-check that your data classes match the actual JSON structure from your backend.