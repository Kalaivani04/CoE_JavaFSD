import 'package:flutter/material.dart';

class AppLocalizations {
  static final delegate = _AppLocalizationsDelegate();

  static Map<String, Map<String, String>> _localizedValues = {
    'en': {
      'title': 'InkSoul',
      'getStarted': 'Get Started',
      'quotes': 'Quotes',
      'today': 'Today',
      'entries': 'Entries',
      'settings': 'Settings',
      'selectMood': 'Select Mood',
      'writeToday': 'Write about today...',
      'save': 'Save',
      'language': 'Change Language',
    },
    'es': {
      'title': 'AlmaTinta',
      'getStarted': 'Comenzar',
      'quotes': 'Citas',
      'today': 'Hoy',
      'entries': 'Entradas',
      'settings': 'Configuración',
      'selectMood': 'Seleccionar estado de ánimo',
      'writeToday': 'Escribe sobre hoy...',
      'save': 'Guardar',
      'language': 'Cambiar idioma',
    },
  };

  static String translate(BuildContext context, String key) {
    Locale locale = Localizations.localeOf(context);
    return _localizedValues[locale.languageCode]?[key] ?? key;
  }
}

class _AppLocalizationsDelegate extends LocalizationsDelegate<AppLocalizations> {
  @override
  bool isSupported(Locale locale) => ['en', 'es'].contains(locale.languageCode);

  @override
  Future<AppLocalizations> load(Locale locale) async => AppLocalizations();

  @override
  bool shouldReload(covariant LocalizationsDelegate<AppLocalizations> old) => false;
}
