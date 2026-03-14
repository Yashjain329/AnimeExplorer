# 📺 Anime Explorer

Anime Explorer is a modern Android application built using **Jetpack Compose**, **Kotlin**, and **MVVM architecture** that allows users to explore anime content using the **Jikan API (MyAnimeList unofficial API)**. The app focuses on clean UI, scalable architecture, and best practices suitable for production and internship-level evaluation.

**[Download APK] (https://github.com/Yashjain329/AnimeExplorer/releases/latest)**

---

## ✨ Features

* 🔥 Browse **Top Rated**, **Trending**, **Upcoming**, and **Current Season** (with **Pull-to-Refresh**)
* 🔍 Real-time anime search with debounce
* ❤️ Add / remove anime from **Favorites**
* 📄 Detailed anime view with:

    * Synopsis
    * Rating
    * Episodes
    * Genres
    * Characters
* 🌗 Light & Dark theme (Material 3)
* 💾 Offline persistence using **Room Database**
* 🧭 Bottom Navigation + Tab-based navigation
* ⚡ Optimized state handling using **StateFlow**

---
## 📸 Screenshots

| Home Screen | Search | Details |
|-------------|--------|---------|
| ![Home](screenshots/home.png) | ![Search](screenshots/search.png) | ![Details](screenshots/details.png) |

**Add these images to `/screenshots/` folder**

---

## 🧠 Architecture

The project follows **MVVM + Clean Architecture principles**:

```
com.example.animeexplorer
│
├── data
│   ├── models        → API response models
│   ├── network       → Retrofit API service
│   ├── database      → Room DB, entities & DAOs
│   └── repository    → Single source of truth
│
├── di                → Hilt dependency modules
│
├── presentation
│   ├── screens       → Compose UI screens
│   ├── components    → Reusable UI components
│   ├── viewmodel     → State & business logic
│   └── theme         → Material 3 theming
│
└── AnimeExplorerApplication.kt
```

---

## 🛠 Tech Stack

* **Language:** Kotlin
* **UI:** Jetpack Compose (Material 3)
* **Architecture:** MVVM
* **Dependency Injection:** Hilt
* **Networking:** Retrofit + OkHttp
* **Database:** Room
* **Async / State:** Coroutines + StateFlow
* **Image Loading:** Coil
* **API:** Jikan API (MyAnimeList)

---

## 🌐 API Reference

* **Base URL:** [https://api.jikan.moe/v4/](https://api.jikan.moe/v4/)
* Data source: **Jikan (Unofficial MyAnimeList API)**

---

## ▶️ How to Run the Project

### Prerequisites

* Android Studio Flamingo or newer
* Android SDK 24+
* JDK 17

### Steps

```bash
git clone <repository-url>
cd AnimeExplorer
```

Open the project in **Android Studio**, then:

```bash
./gradlew clean
./gradlew assembleDebug
```

Run on an emulator or a physical device.

---

## 📱 Screens Implemented

* Home (Tabbed Anime Lists)
* Search
* Favorites
* Anime Details

---

## ⚠️ Known Limitations

* Pagination currently replaces lists instead of appending
* Favorites screen UI is minimal (data persistence works correctly)
* No account-based sync (local storage only)
* Jikan API rate limits apply

---

## 🚀 Future Improvements

* Infinite scrolling pagination
* Skeleton loaders instead of spinners
* Watchlist screen UI
* Offline caching for anime lists
* UI & ViewModel tests

---

## 👨‍💻 Developer Notes

This project was built with **scalability, clarity, and clean code** in mind and is suitable for:

* Android internships
* Portfolio projects
* Real-world feature expansion

---

## 🎨 App Icon & Branding

The app uses a custom anime-themed launcher icon inspired by a **straw-hat explorer motif**, generated as an **adaptive launcher icon** following Android design guidelines.

---

## 📄 License

This project is intended for **educational and evaluation purposes**.
