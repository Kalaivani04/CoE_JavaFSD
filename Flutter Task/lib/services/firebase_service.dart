import 'package:cloud_firestore/cloud_firestore.dart';

class FirebaseService {
  final CollectionReference _entries = FirebaseFirestore.instance.collection('entries');

  Future<void> saveEntry(String mood, String entry) async {
    await _entries.add({
      'date': DateTime.now().toIso8601String(),
      'mood': mood,
      'entry': entry,
    });
  }

  Stream<QuerySnapshot> getEntries() {
    return _entries.orderBy('date', descending: true).snapshots();
  }
}
