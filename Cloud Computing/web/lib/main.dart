import 'package:flutter/material.dart';
import 'package:web/homepage.dart';

void main() {
  runApp(const Relasia());
}

class Relasia extends StatelessWidget {
  const Relasia({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: HomePage(),
      theme: ThemeData(
        primarySwatch: Colors.amber,
      ),
    );
  }
}
