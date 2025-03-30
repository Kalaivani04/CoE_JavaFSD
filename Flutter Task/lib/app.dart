import 'package:flutter/material.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'theme.dart';
import 'localization.dart';
import 'pages/home_page.dart';
import 'pages/quotes_page.dart';
import 'pages/today_page.dart';
import 'pages/entries_page.dart';
import 'pages/settings_page.dart';

class InkSoulApp extends StatefulWidget {
  @override
  _InkSoulAppState createState() => _InkSoulAppState();
}

class _InkSoulAppState extends State<InkSoulApp> {
  Locale _locale = Locale('en');

  void setLocale(Locale locale) {
    setState(() {
      _locale = locale;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'InkSoul',
      theme: AppTheme.lightTheme,
      locale: _locale,
      supportedLocales: [Locale('en'), Locale('es')],
      localizationsDelegates: [
        AppLocalizations.delegate,
        GlobalMaterialLocalizations.delegate,
        GlobalWidgetsLocalizations.delegate,
      ],
      initialRoute: '/',
      routes: {
        '/': (context) => HomePage(),
        '/quotes': (context) => QuotesPage(),
        '/today': (context) => TodayPage(),
        '/entries': (context) => EntriesPage(),
        '/settings': (context) => SettingsPage(setLocale),
      },
    );
  }
}
