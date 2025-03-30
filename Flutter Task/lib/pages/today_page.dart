import 'package:flutter/material.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import '../theme.dart'; // Import AppColors for styling
import '../localization.dart'; // Import localization

class TodayPage extends StatefulWidget {
  @override
  _TodayPageState createState() => _TodayPageState();
}

class _TodayPageState extends State<TodayPage> {
  final TextEditingController _entryController = TextEditingController();
  final CollectionReference _entries = FirebaseFirestore.instance.collection('entries');
  String _selectedMood = ""; // Store selected mood

  final List<String> _moodEmojis = ["😊", "😢", "😠", "😐"]; // Mood options

  // Save entry to Firestore
  Future<void> _saveEntry() async {
    if (_selectedMood.isEmpty || _entryController.text.trim().isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text(AppLocalizations.translate(context, "selectMood"))),
      );
      return;
    }

    final newEntry = {
      'date': Timestamp.now(), // Store as Firestore Timestamp
      'mood': _selectedMood,
      'entry': _entryController.text.trim(),
    };

    try {
      DocumentReference docRef = await _entries.add(newEntry);
      print("🔥 Entry saved: ${newEntry['entry']} | Mood: ${newEntry['mood']} | ID: ${docRef.id}");

      _entryController.clear();
      setState(() {
        _selectedMood = ""; // Reset mood after saving
      });

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text(AppLocalizations.translate(context, "save"))),
      );
    } catch (error) {
      print("❌ Error saving entry: $error");
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text("Failed to save entry. Try again.")), // Keep error in English for debugging
      );
    }
  }

  @override
  void dispose() {
    _entryController.dispose(); // Prevent memory leaks
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.backgroundColor,
      appBar: AppBar(
        title: Text(AppLocalizations.translate(context, "today")), // 🔹 Localized title
        backgroundColor: AppColors.primaryColor,
        iconTheme: IconThemeData(color: Colors.white),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              AppLocalizations.translate(context, "selectMood"), // 🔹 Localized "How are you feeling today?"
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),

            SizedBox(height: 10),

            // Mood Selector
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: _moodEmojis.map((mood) {
                return GestureDetector(
                  onTap: () {
                    setState(() {
                      _selectedMood = mood; // Update selected mood
                    });
                  },
                  child: Container(
                    padding: EdgeInsets.all(10),
                    margin: EdgeInsets.symmetric(horizontal: 6),
                    decoration: BoxDecoration(
                      shape: BoxShape.circle,
                      color: _selectedMood == mood ? AppColors.accentColor : Colors.transparent,
                      border: Border.all(color: AppColors.primaryColor, width: 2),
                    ),
                    child: Text(
                      mood,
                      style: TextStyle(fontSize: 30),
                    ),
                  ),
                );
              }).toList(),
            ),

            SizedBox(height: 20),

            // Text Field
            TextField(
              controller: _entryController,
              maxLines: 5,
              decoration: InputDecoration(
                hintText: AppLocalizations.translate(context, "writeToday"), // 🔹 Localized placeholder
                filled: true,
                fillColor: AppColors.cardColor,
                border: OutlineInputBorder(borderRadius: BorderRadius.circular(10)),
              ),
            ),

            SizedBox(height: 20),

            // Save Button
            Center(
              child: ElevatedButton(
                onPressed: _saveEntry,
                style: ElevatedButton.styleFrom(
                  backgroundColor: AppColors.buttonColor,
                  padding: EdgeInsets.symmetric(horizontal: 40, vertical: 15),
                ),
                child: Text(
                  AppLocalizations.translate(context, "save"), // 🔹 Localized button text
                  style: TextStyle(color: Colors.white, fontSize: 16),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
