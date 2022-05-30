import 'package:flutter/material.dart';

class SideBar extends StatelessWidget {
  const SideBar({super.key});

  @override
  Widget build(BuildContext context) {
    return ListView(
      children: [
        Padding(
          padding: const EdgeInsets.all(20),
          child: CircleAvatar(
            radius: 50,
            backgroundColor: Colors.white,
          ),
        ),
      ],
    );
  }
}
