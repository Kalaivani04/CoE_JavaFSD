import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

import '../theme.dart'; // Ensure AppColors is used for styling

class QuotesPage extends StatefulWidget {
  @override
  _QuotesPageState createState() => _QuotesPageState();
}

class _QuotesPageState extends State<QuotesPage> {
  String _quote = "Select a category to get a quote";
  String? _selectedCategory;

  final List<String> _categories = [
    'love',
    'life',
    'inspiration',
    'happiness',
    'success',
    'friendship',
    'wisdom',
    'perseverance'
  ];

  Future<void> fetchQuote(String tag) async {
    final url = Uri.parse('http://api.quotable.io/random?tag=$tag');
    try {
      final response = await http.get(url);
      if (response.statusCode == 200) {
        setState(() {
          _quote = json.decode(response.body)['content'];
        });
      } else {
        setState(() {
          _quote = "Failed to load quote. Try again.";
        });
      }
    } catch (e) {
      setState(() {
        _quote = "An error occurred. Check your internet connection.";
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.backgroundColor,
      appBar: AppBar(
        title: Text("Quotes"),
        backgroundColor: AppColors.primaryColor,
        leading: IconButton(
          icon: Icon(Icons.arrow_back, color: Colors.white),
          onPressed: () => Navigator.pop(context),
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            // Category Selection Dropdown
            DropdownButton<String>(
              value: _selectedCategory,
              isExpanded: true,
              hint: Text("Select a category"),
              items: _categories.map((tag) {
                return DropdownMenuItem(
                  value: tag,
                  child: Text(tag[0].toUpperCase() + tag.substring(1)), // Capitalize first letter
                );
              }).toList(),
              onChanged: (tag) {
                setState(() {
                  _selectedCategory = tag;
                });
                fetchQuote(tag!);
              },
            ),

            SizedBox(height: 20),

            // Quote Display Box
            Card(
              color: AppColors.cardColor,
              elevation: 4,
              shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)),
              child: Padding(
                padding: EdgeInsets.all(16),
                child: Text(
                  _quote,
                  textAlign: TextAlign.center,
                  style: TextStyle(fontSize: 18, fontStyle: FontStyle.italic),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
