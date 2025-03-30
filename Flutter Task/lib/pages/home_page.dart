import 'package:flutter/material.dart';
import '../theme.dart';
import '../localization.dart';

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  bool _showCards = false; // Controls card visibility

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.backgroundColor,
      appBar: _showCards
          ? AppBar(
              backgroundColor: AppColors.primaryColor,
              title: Text(AppLocalizations.translate(context, 'title')),
              leading: IconButton(
                icon: Icon(Icons.arrow_back, color: Colors.white),
                onPressed: () {
                  setState(() {
                    _showCards = false; // Reset to initial state
                  });
                },
              ),
            )
          : null, // No AppBar when Get Started is visible

      body: _showCards
          ? _buildCardsView(context) // Show cards after "Get Started" is clicked
          : _buildInitialView(context), // Show initial screen
    );
  }

  /// **Initial View (Centered Logo, Title, and Get Started Button)**
  Widget _buildInitialView(BuildContext context) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Image.asset('assets/logo.png', height: 100),
          SizedBox(height: 20),
          Text(
            AppLocalizations.translate(context, 'title'),
            style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
          ),
          SizedBox(height: 20),
          ElevatedButton(
            style: ElevatedButton.styleFrom(
              backgroundColor: AppColors.buttonColor, // Button color
              padding: EdgeInsets.symmetric(horizontal: 32, vertical: 12),
            ),
            onPressed: () {
              setState(() {
                _showCards = true; // Show cards when button is clicked
              });
            },
            child: Text(
              AppLocalizations.translate(context, 'getStarted'),
              style: TextStyle(color: Colors.white), // White text
            ),
          ),
        ],
      ),
    );
  }

  /// **Cards View (Appears after clicking "Get Started")**
  Widget _buildCardsView(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(16.0),
      child: GridView.count(
        crossAxisCount: 2,
        crossAxisSpacing: 16,
        mainAxisSpacing: 16,
        children: [
          _buildNavigationCard(
            context,
            title: AppLocalizations.translate(context, 'quotes'),
            icon: Icons.format_quote,
            route: '/quotes',
          ),
          _buildNavigationCard(
            context,
            title: AppLocalizations.translate(context, 'today'),
            icon: Icons.calendar_today,
            route: '/today',
          ),
          _buildNavigationCard(
            context,
            title: AppLocalizations.translate(context, 'entries'),
            icon: Icons.book,
            route: '/entries',
          ),
          _buildNavigationCard(
            context,
            title: AppLocalizations.translate(context, 'settings'),
            icon: Icons.settings,
            route: '/settings',
          ),
        ],
      ),
    );
  }

  /// **Reusable method to create a navigation card**
  Widget _buildNavigationCard(BuildContext context, {required String title, required IconData icon, required String route}) {
    return InkWell(
      onTap: () => Navigator.pushNamed(context, route),
      child: Card(
        color: AppColors.cardColor,
        elevation: 4,
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(icon, size: 40, color: AppColors.primaryColor),
            SizedBox(height: 10),
            Text(title, style: TextStyle(fontSize: 18, fontWeight: FontWeight.w600)),
          ],
        ),
      ),
    );
  }
}
