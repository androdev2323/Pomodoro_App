# 📱 Pomodoro Focus App (Ongoing Project)
A productivity app combining a custom animated Pomodoro timer with a real-time app-blocking system to help users stay focused throughout the day.

---

## 🎥 Project Showcase  
Watch a quick demo of the app here:

👉 **YouTube Demo:** *youtube.com/shorts/EO-r25XRSnA?feature=share*

---

## 🚀 Features

### 🕒 Pomodoro Timer  
- Fully custom circular timer built using **Jetpack Compose Canvas APIs**  
- Smooth progress animations with **Lottie**  
- Gradient ring, tick markers, rotating indicators  
- Work/break cycles with intuitive UI  

### 🚫 Real-Time App Blocking  
- Detects the currently active app using `UsageStatsManager`  
- Blocks selected apps by showing a **full-screen overlay**  
- Persistent background implementation using a **Service**  
- Overlay UI rendered using **Jetpack Compose** inside the service  
- Reactive blocklist updates via Room + Flow  

### 🗄 Data & Architecture  
- **Hilt Dependency Injection** across the entire app  
- **MVVM + Clean Architecture**  
- Local storage using **Room Database + Kotlin Flow**  
- Foreground app monitoring loop optimized with coroutines  

---

## 🏗 Tech Stack

- Kotlin  
- Jetpack Compose  
- Hilt (Dependency Injection)  
- Room Database  
- Coroutines & Flows  
- Lottie Animations  
- UsageStatsManager  
- Overlay Services  

---