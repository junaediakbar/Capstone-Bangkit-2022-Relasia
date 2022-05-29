import 'package:flutter/material.dart';
import 'package:web/responsive/desktop_body.dart';
import 'package:web/responsive/mobile_body.dart';
import 'package:web/responsive/responsive_layout.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: ResponsiveLayout(
        desktopBody: DesktopBody(),
        mobileBody: MobileBody(),
      ),
    );
  }
}
