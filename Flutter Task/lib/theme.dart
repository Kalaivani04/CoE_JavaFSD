import 'package:flutter/material.dart';

class AppColors {
  static const Color primaryColor = Colors.deepPurple;
  static const Color backgroundColor = Color(0xFFF3E5F5);
  static const Color textColor = Colors.black87;
  static const Color accentColor = Colors.purpleAccent;
  static const Color cardColor = Color(0xFFE1BEE7);
  static const Color buttonColor = Colors.deepPurpleAccent;
}

class AppTheme {
  static final ThemeData lightTheme = ThemeData(
    primaryColor: AppColors.primaryColor,
    scaffoldBackgroundColor: AppColors.backgroundColor,
    textTheme: TextTheme(bodyLarge: TextStyle(color: AppColors.textColor)),
    cardColor: AppColors.cardColor,
    elevatedButtonTheme: ElevatedButtonThemeData(
      style: ElevatedButton.styleFrom(
        backgroundColor: AppColors.buttonColor,
      ),
    ),
  );
}
