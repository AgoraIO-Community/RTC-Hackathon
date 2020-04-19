import 'dart:async';

import 'package:flutter/material.dart';

//加载界面
class LoadingView extends StatefulWidget {
  final Widget child;
  final bool loading;

  LoadingView({@required this.child, this.loading=false})
      : assert(child != null),
        assert(loading != null);

  @override
  State<StatefulWidget> createState() => LoadingViewState();
}

class LoadingViewState extends State<LoadingView> {
  @override
  Widget build(BuildContext context) {
    return Stack(
      children: <Widget>[
        widget.child,
        GestureDetector(
          onTap: (){

          },
          child: Container(
            width: double.infinity,
            height: double.infinity,
            child: Center(
              child: widget.loading
                  ?  _Loading()
                  : SizedBox(width: 8, height: 8,),
            ),
          ),
        )
      ],
    );
  }
}

class _Loading extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _LoadingState();
}

class _LoadingState extends State<_Loading> {
  bool hasDispose = false;
  List<bool> loadingState = [true, false, false];

  @override
  void initState() {
    super.initState();
  }

  void loop() async {
    Timer(Duration(milliseconds: 500), () {
      int count = 0;
      for (int i = 0; i < loadingState.length; i++) {
        if (loadingState[i]) {
          count = i;
          break;
        }
      }
      loadingState[count] = false;
      loadingState[(count + 1) % 3] = true;
      if (hasDispose != null && hasDispose) {
        return;
      } else {
        setState(() {});
      }
    });
  }

  @override
  void dispose() {
    // TODO: implement dispose
    super.dispose();
    hasDispose = true;
  }

  @override
  Widget build(BuildContext context) {
    print("显示界面");
    loop();
    return Container(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: loadingState
            .map((e) => Container(
                  width: 8,
                  height: 8,
                  margin: EdgeInsets.only(right: 14),
                  decoration: ShapeDecoration(
                      color: e ? Colors.white : Colors.grey,
                      shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.all(Radius.circular(20)))),
                ))
            .toList(),
      ),
    );
  }
}
