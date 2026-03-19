# Add project specific ProGuard rules here.
-keep class com.cascadesim.** { *; }
-keepclassmembers class * {
    @androidx.room.Query <methods>;
}
