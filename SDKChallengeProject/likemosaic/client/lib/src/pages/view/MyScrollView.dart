import 'package:flutter/material.dart';

class MyScrollView extends StatelessWidget {
  MyScrollView({@required this.child});

  final Widget child;

  @override
  Widget build(BuildContext context) {
    return CustomScrollView(
      slivers: <Widget>[
        SliverToBoxAdapter(
          child: child,
        )
      ],
    );
  }
}
