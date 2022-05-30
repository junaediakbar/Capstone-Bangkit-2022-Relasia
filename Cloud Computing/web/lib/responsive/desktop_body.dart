import 'package:flutter/material.dart';
import 'package:web/responsive/sidebar.dart';

class DesktopBody extends StatelessWidget {
  const DesktopBody({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.amberAccent[300],
      body: Row(
        children: [
          Container(
            width: 250,
            color: Colors.deepPurple[300],
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: SideBar(),
            ),
          ),
          Expanded(
            child: Column(
              children: [
                // youtube video
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: AspectRatio(
                    aspectRatio: 16 / 9,
                    child: Container(
                      color: Colors.deepPurple[400],
                    ),
                  ),
                ),

                // comment section & recommended videos
                Expanded(
                  child: ListView.builder(
                    itemCount: 8,
                    itemBuilder: (context, index) {
                      return Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Container(
                          color: Colors.deepPurple[300],
                          height: 120,
                        ),
                      );
                    },
                  ),
                )
              ],
            ),
          ),
        ],
      ),
    );
  }
}
