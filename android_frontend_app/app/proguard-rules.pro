# Keep Hilt generated classes
-keep class dagger.hilt.** { *; }
-keep class **_HiltModules_* { *; }
-dontwarn javax.annotation.**

# Keep WorkManager
-keep class androidx.work.** { *; }
-dontwarn androidx.work.**

# Compose
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**
