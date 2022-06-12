# Mobile Development
Product Based Capstone Bangkit 2022

## Screenshots
### Relasia Helper App
<img src="https://github.com/junaediakbar/Capstone-Bangkit-2022-Relasia/blob/c5821947e88b29625c7baff520af974a4eebcbcd/Mobile%20Development/assets/volunteer-1.jpg" width="1000"/>

<img src="https://github.com/junaediakbar/Capstone-Bangkit-2022-Relasia/blob/c5821947e88b29625c7baff520af974a4eebcbcd/Mobile%20Development/assets/volunteer-2.jpg" width="1000"/>

### Relasia Helpseeker App
<img src="https://github.com/junaediakbar/Capstone-Bangkit-2022-Relasia/blob/c5821947e88b29625c7baff520af974a4eebcbcd/Mobile%20Development/assets/helpseeker-1.jpg" width="1000"/>

<img src="https://github.com/junaediakbar/Capstone-Bangkit-2022-Relasia/blob/c5821947e88b29625c7baff520af974a4eebcbcd/Mobile%20Development/assets/helpseeker-2.jpeg" width="1000"/>

## Features

### Relasia Helper App
- Splash Screen
- Support Indonesia & English Language
- Email & Gmail Authentication
- Profile Customization
- Search Missions
- Bookmark Missions
- Filter Applied Missions History
- Mission Details
- Apply Missions
- Recommendation System
- Volunteer Registration

### Relasia Helpseeker App
- Splash screen
- Support Indonesia & English Language
- Email & Gmail Authentication
- Profile customization
- Bookmark Volunteer
- Posting Mission
- Volunteer details
- Accept or Reject Volunteers from mission
- Applied Missions History Filter
- Posting Missions History
- Mission Details

## Build With
- [Kotlin](https://kotlinlang.org/)
- [Retrofit2](https://github.com/square/retrofit)
- [ViewPager2](https://developer.android.com/jetpack/androidx/releases/viewpager2)
- [Room Paging](https://developer.android.com/topic/libraries/architecture/paging/v3-network-db)
- [Room Database](https://developer.android.com/jetpack/androidx/releases/room)
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [GSON](https://github.com/google/gson)
- [GSON Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/gson)
- [OkHttp3](https://square.github.io/okhttp/)
- [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started)
- [Glide](https://github.com/bumptech/glide)
- [Google Maps Services, Place API](https://developers.google.com/maps/documentation)
- [Smart Material Spinner](https://github.com/Chivorns/SmartMaterialSpinner)
- [Authentication Firebase Service](https://firebase.google.com/docs/auth)
- [Storage Firebase Service](https://firebase.google.com/docs/storage)
- [Cloud Messaging Firebase](https://firebase.google.com/docs/cloud-messaging)
- [Firestore Firebase Service](https://firebase.google.com/docs/firestore)

## Package Structure

### Relasia Helper App
    .com.c22ps099.relasiahelperapp  # Roor Package
    ├── adapter                     # Adapter for RecyclerView
    │
    ├── data                        # For data handling
    │   ├── dao                     # For local bookmark feature
    │   ├── database                # Store data entity locally     
    |   ├── repository              # Single source of data     
    │   └── paging-source           # For paging3 handling
    |
    ├── network                     # Remote Data Handlers
    │   ├── api                     # Retrofit API for remote endpoint
    │   └── responses               # Store data entity remote
    │
    ├── ui                          # Activity/View layer
    │   ├── custom-view             # Text validation handlers
    │   ├── fragment                # View for Navigation
    │   ├── activity                # View for Splash and Main
    │   └── view-model              # ViewHolder for RecyclerView
    |
    └── utils                       # Utility Classes / Kotlin extensions
    
### Relasia Helpseeker App
    .com.c22ps099.relasiahelpseekerapp  # Roor Package
    ├── data                            # For data handling
    │   ├── adapter                     # Adapter for RecyclerView
    │   ├── api                         # For data from/to Rest API     
    │   │   ├── response                # For response from Rest API   
    |   └── repository                  # Single source of data     
    |
    ├── di                          # For Injecting Dependency
    ├── misc                        # Utility Classes / Kotlin extensions
    ├── models                      # Define Model for Transfer Data
    ├── services                    # For Service from Firebase
    ├── ui                          # Activity/View layer
    └── view                        # For Custom view handlers
    
## Architecture
This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture.

<img src="https://github.com/junaediakbar/Capstone-Bangkit-2022-Relasia/blob/4fe9956e2d916815f1868e370bb6aa1347077906/Mobile%20Development/assets/mvvm.jpg" width="700"/>
