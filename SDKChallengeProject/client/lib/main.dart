import 'package:agora_flutter_quickstart/src/pages/call.dart';

import "src/pages/CallPage.dart";
import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.purple,
      ),
      home: CallPage(
        channelName: "bearever",
      ),
    );
  }
}
