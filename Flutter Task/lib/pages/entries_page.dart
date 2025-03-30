import 'package:flutter/material.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import '../services/firebase_service.dart';
import '../theme.dart'; // Import theme colors
import '../localization.dart'; // Import localization

class EntriesPage extends StatelessWidget {
  final FirebaseService _firebaseService = FirebaseService();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.backgroundColor,
      appBar: AppBar(
        title: Text(AppLocalizations.translate(context, "entries")), // 🔹 Localized title
        backgroundColor: AppColors.primaryColor,
        iconTheme: IconThemeData(color: Colors.white), // White back arrow
      ),
      body: StreamBuilder<QuerySnapshot>(
        stream: _firebaseService.getEntries(), // Fetch entries from Firestore
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return Center(child: CircularProgressIndicator());
          }

          if (!snapshot.hasData || snapshot.data!.docs.isEmpty) {
            print("⚠️ No entries found in Firestore");
            return Center(
              child: Text(
                AppLocalizations.translate(context, "noEntries"), // 🔹 Localized message
                style: TextStyle(fontSize: 16, color: Colors.black54),
              ),
            );
          }

          final entries = snapshot.data!.docs;

          print("📜 Loaded ${entries.length} entries from Firestore");

          return ListView.builder(
            padding: EdgeInsets.all(10),
            itemCount: entries.length,
            itemBuilder: (context, index) {
              var doc = entries[index];
              var data = doc.data() as Map<String, dynamic>?;

              if (data == null || !data.containsKey('entry') || !data.containsKey('mood') || !data.containsKey('date')) {
                print("⚠️ Skipping invalid entry: ${doc.id}");
                return SizedBox(); // Skip invalid entries
              }

              String entryText = data['entry'] ?? "No text";
              String mood = data['mood'] ?? "😐";
              Timestamp? timestamp = data['date'];
              String formattedDate = timestamp != null ? _formatTimestamp(timestamp) : "Unknown Date";

              print("📌 Entry: $entryText | Mood: $mood | Date: $formattedDate");

              return Card(
                color: AppColors.cardColor,
                margin: EdgeInsets.symmetric(vertical: 8, horizontal: 12),
                shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)),
                child: ListTile(
                  title: Text(
                    formattedDate,
                    style: TextStyle(fontWeight: FontWeight.bold, color: Colors.black87),
                  ),
                  subtitle: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      SizedBox(height: 5),
                      Text(
                        "${AppLocalizations.translate(context, "mood")}: $mood", // 🔹 Localized "Mood"
                        style: TextStyle(fontSize: 16, color: Colors.black87),
                      ),
                      SizedBox(height: 5),
                      Text(
                        entryText,
                        style: TextStyle(fontSize: 14, color: Colors.black54),
                      ),
                    ],
                  ),
                  trailing: IconButton(
                    icon: Icon(Icons.delete, color: Colors.red),
                    onPressed: () => _deleteEntry(doc.id, context),
                  ),
                ),
              );
            },
          );
        },
      ),
    );
  }

  // Convert Firestore Timestamp to readable date format
  String _formatTimestamp(Timestamp timestamp) {
    DateTime date = timestamp.toDate();
    return "${date.day}-${date.month}-${date.year} ${date.hour}:${date.minute}";
  }

  // Delete entry from Firestore
  void _deleteEntry(String docId, BuildContext context) async {
    try {
      await FirebaseFirestore.instance.collection('entries').doc(docId).delete();
      print("🗑️ Deleted entry ID: $docId");

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text(AppLocalizations.translate(context, "entryDeleted"))), // 🔹 Localized message
      );
    } catch (error) {
      print("❌ Error deleting entry: $error");

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text(AppLocalizations.translate(context, "deleteFailed"))), // 🔹 Localized message
      );
    }
  }
}
