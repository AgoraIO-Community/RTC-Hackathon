

import 'dart:async';

import 'package:agora_flutter_quickstart/src/pages/view/LoadingView.dart';
import 'package:flutter/material.dart';

//陌生人匹配界面
class PairPage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => PairPageState();
}

class PairPageState extends State<PairPage> {
  bool loading = false;
  bool pairSuccess = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: <Widget>[
          Container(
            color: Colors.red,
          ),
          LoadingView(
            child: Container(
              width: double.infinity,
              height: double.infinity,
              child: Center(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: <Widget>[
                    Icon(
                      Icons.account_circle,
                      color: Colors.white,
                      size: 140,
                    ),
                    Padding(
                      padding: EdgeInsets.symmetric(vertical: 80),
                      child: pairSuccess
                          ? Text(
                              "匹配成功",
                              style: TextStyle(color: Color(0xFF949AEC)),
                            )
                          : SizedBox(),
                    ),
                    loading ? _createLogo() : _createBtn(),
                  ],
                ),
              ),
            ),
            loading: loading,
          )
        ],
      ),
    );
  }

  Widget _createBtn() {
    return Container(
      height: 40,
      width: 150,
      decoration: ShapeDecoration(
          gradient: LinearGradient(colors: [
            Color(0xffFF8585),
            Color(0xffEA45D5),
          ]),
          shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.all(Radius.circular(50)))),
      child: Center(
        child: InkWell(
          onTap: () {
            if (loading) {
              return;
            }
            loading = true;
            pairSuccess=false;
            Timer(Duration(milliseconds: 5000), () {
              loading = false;
              pairSuccess=true;
              setState(() {});
            });
            setState(() {});
            print("正在点击事件....");
          },
          child: Text(
            "开始随机匹配",
            style: TextStyle(color: Colors.white),
          ),
        ),
      ),
    );
  }

  Widget _createLogo() {
    return Icon(
      Icons.account_circle,
      color: Colors.white,
      size: 140,
    );
  }
}
