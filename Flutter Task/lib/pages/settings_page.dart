import 'package:flutter/material.dart';
import '../theme.dart'; // Import theme colors
import '../localization.dart'; // Import localization

class SettingsPage extends StatefulWidget {
  final Function(Locale) setLocale;

  SettingsPage(this.setLocale);

  @override
  _SettingsPageState createState() => _SettingsPageState();
}

class _SettingsPageState extends State<SettingsPage> {
  late Locale _selectedLocale;

  @override
  void initState() {
    super.initState();
    _selectedLocale = Locale('en'); // Default value, will be updated in didChangeDependencies
  }

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    _selectedLocale = Localizations.localeOf(context); // Get current locale properly
  }

  void _changeLanguage(Locale locale) {
    setState(() {
      _selectedLocale = locale;
    });
    widget.setLocale(locale); // Apply the language change
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.backgroundColor,
      appBar: AppBar(
        title: Text(AppLocalizations.translate(context, 'settings')),
        backgroundColor: AppColors.primaryColor,
        iconTheme: IconThemeData(color: Colors.white),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              AppLocalizations.translate(context, 'language'),
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            SizedBox(height: 10),

            // Language Selector
            Container(
              padding: EdgeInsets.symmetric(horizontal: 16, vertical: 5),
              decoration: BoxDecoration(
                color: AppColors.cardColor,
                borderRadius: BorderRadius.circular(10),
                border: Border.all(color: AppColors.primaryColor, width: 1),
              ),
              child: DropdownButtonHideUnderline(
                child: DropdownButton<Locale>(
                  value: _selectedLocale,
                  items: [
                    DropdownMenuItem(value: Locale('en'), child: Text("English")),
                    DropdownMenuItem(value: Locale('es'), child: Text("Español")),
                  ],
                  onChanged: (locale) => _changeLanguage(locale!),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
