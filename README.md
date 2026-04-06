# 💰 PennyPath – Personal Finance Tracker

PennyPath is a modern Android application built using **Jetpack Compose** that helps users track their income, expenses, savings, and financial goals.  
The app focuses on clean UI, interactive dashboards, and intuitive financial insights.

---

## ✨ Features

### 📊 Dashboard Overview
- Current Balance
- Total Income & Expenses
- Savings summary
- Visual charts (Pie + Line)

### 💸 Transaction Management
- Add / Update / Delete transactions
- Category-based tracking
- Custom categories
- Date selection support
- Filters (type, category, amount)

### 🎯 Goals System
- Create savings goals
- Track progress with indicators
- Add money to goals
- Delete goals

### 📈 Insights
- Weekly spending trends
- Category breakdown visualization

### 🎨 Modern UI
- Jetpack Compose UI
- Glassmorphism cards
- Smooth animations
- Interactive charts

---

## 🛠 Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose
- **Architecture:** MVVM
- **Dependency Injection:** Hilt
- **State Management:** StateFlow
- **Database:** Room (partial / planned integration)
- **Charts:** Custom Canvas-based charts

---

## 📦 Requirements

Make sure you have the following installed:

- Android Studio Hedgehog or newer
- JDK 21
- Android SDK 34
- Minimum SDK: 24
- Gradle (auto-managed)

---

## 🔧 Versions Used

AGP: 8.5.2  
Kotlin: 2.0.21  
Compose BOM: 2024.02.01  
Hilt: 2.51.1  
Room: 2.6.1

---

## 📁 Project Setup (From Git)

### 1. Clone the repository

```bash
git clone https://github.com/your-username/pennypath.git
cd pennypath
```

---

### 2. Open in Android Studio

- Open Android Studio
- Click **Open Project**
- Select the cloned folder

---

### 3. Sync Gradle

If not auto-synced:

- File → Sync Project with Gradle Files

---

### 4. Run the App

- Connect a device or emulator
- Click ▶️ Run

---

## ⚙️ Configuration Notes

### Hilt Setup
- Ensure `@HiltAndroidApp` is added in Application class

### Compose Enabled

```kotlin
buildFeatures {
    compose = true
}
```

### Java Version

```kotlin
compileOptions {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}
```

---

## 📊 Dependencies Overview

- androidx.compose.material3
- androidx.lifecycle.runtime.ktx
- androidx.activity.compose
- hilt-android
- room-runtime
- hilt-navigation-compose
- play-services-fitness

---

## 🧠 Architecture

The app follows **MVVM architecture**:

- Model → Data layer (Transaction, Goal)
- View → Jetpack Compose UI
- ViewModel → Business logic

Uses **StateFlow** for reactive UI updates.

---

## ⚠️ Known Limitations

- No cloud sync
- Basic analytics in Insights

---

## 🚀 Future Improvements

- Full Room database integration
- Advanced insights
- Budget tracking & alerts
- Firebase sync
- UI enhancements

---

## 👨‍💻 Developer Notes

This project focuses on:

- Modern Android practices
- Clean architecture
- Custom UI design
- Real-world problem solving
