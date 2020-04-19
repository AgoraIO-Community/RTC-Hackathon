import 'package:agora_flutter_quickstart/src/pages/view/MyScrollView.dart';
import 'package:flutter/material.dart';

//聊天界面
class MyCallPage extends StatefulWidget {
  /// non-modifiable channel name of the page
  final String channelName;

  /// Creates a call page with given channel name.
  const MyCallPage({Key key, this.channelName}) : super(key: key);
  
  @override
  State<StatefulWidget> createState() => MyCallPageState();
}

class MyCallPageState extends State<MyCallPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        width: double.infinity,
        height: double.infinity,
        child: Stack(
          children: <Widget>[
            Column(
              children: <Widget>[
                Expanded(
                  child: _createVideoView(),
                ),
                Container(
                  height: 200,
                  width: double.infinity,
                  padding: EdgeInsets.all(10),
                  color: Color(0xFF271B4A),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.start,
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: <Widget>[
                      Padding(
                        padding: EdgeInsets.only(top: 6),
                      ),
                      _createProgress(),
                      Padding(
                        padding: EdgeInsets.only(top: 10),
                      ),
                      Expanded(
                        child: _createTag(),
                      )
                    ],
                  ),
                ),
              ],
            ),
            Container(
              width: double.infinity,
              height: double.infinity,
              child: Column(
                mainAxisAlignment: MainAxisAlignment.end,
                crossAxisAlignment: CrossAxisAlignment.end,
                children: <Widget>[
                  Padding(
                    child: Icon(
                      Icons.account_circle,
                      size: 100,
                      color: Colors.white,
                    ),
                    padding: EdgeInsets.only(bottom: 160, right: 0),
                  ),
                ],
              ),
            )
          ],
        ),
        color: Colors.purple,
      ),
    );
  }

  Widget _createVideoView() {
    return Stack(
      children: <Widget>[
        Container(
          color: Colors.purple,
        ),
        Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          mainAxisAlignment: MainAxisAlignment.end,
          children: <Widget>[
            Container(
              child: Row(
                children: <Widget>[
                  Visibility(
                    visible: false,
                    child: Icon(
                      Icons.image,
                      size: 20,
                      color: Colors.white,
                    ),
                  ),
                  SizedBox(
                    width: 200,
                    child: Text(
                      "快速选几个你感兴趣的话题吧！也许会发现兴趣重叠话题哦",
                      style: TextStyle(color: Colors.white, fontSize: 12),
                    ),
                  )
                ],
              ),
              width: 230,
              padding: EdgeInsets.only(top: 6, bottom: 6, left: 12, right: 12),
              margin: EdgeInsets.only(left: 8, bottom: 8),
              decoration: ShapeDecoration(
                  color: Color(0x33444444),
                  shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.all(Radius.circular(60)))),
            ),
          ],
        ),
        Container(
          margin: EdgeInsets.only(top: 24, left: 6, right: 6),
          child: Row(
            children: <Widget>[
              Icon(
                Icons.exit_to_app,
                color: Colors.white,
              ),
              Expanded(
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.end,
                  crossAxisAlignment: CrossAxisAlignment.end,
                  children: <Widget>[
                    Icon(
                      Icons.keyboard_voice,
                      color: Colors.white,
                    ),
                  ],
                ),
              ),
            ],
          ),
        ),
      ],
    );
  }

  Widget _createTag() {
    return MyScrollView(
      child: Wrap(
        direction: Axis.horizontal,
        children: _list
            .map((e) => e == null ? _createRefreshItem() : _createItem(e))
            .toList(),
      ),
    );
  }

  Widget _createRefreshItem() {
    return Container(
      margin: EdgeInsets.all(4),
      child: CircleAvatar(
        radius: 14,
        backgroundColor: Color(0xFF949AEC),
        foregroundColor: Colors.white,
        child: Icon(
          Icons.refresh,
          size: 20,
        ),
      ),
    );
  }

  Widget _createItem(_Data e) {
    return InkWell(
      onTap: (){
        e.clicked=!e.clicked;
        setState(() {

        });
      },
      child: Container(
        margin: EdgeInsets.all(4),
        padding: EdgeInsets.only(left: 10, right: 10, top: 4, bottom: 4),
        decoration: ShapeDecoration(
            gradient:e.clicked? LinearGradient(
              colors:[
                Color(0xffFF8585),
                Color(0xffEA45D5),
              ]
            ):null,
            color: e.clicked?null:Color(0xFF949AEC),
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.all(Radius.circular(20)),
            )),
        child: Text(
          e.title,
          style: TextStyle(color: Colors.white),
        ),
      ),
    );
  }

  Widget _createProgress() {
    return Row(
      children: <Widget>[
        Icon(
          Icons.image,
          size: 14,
          color: Colors.white,
        ),
        Padding(
          padding: EdgeInsets.only(left: 4),
        ),
        Container(
          decoration: BoxDecoration(
              border: Border.all(color: Colors.white),
              borderRadius: BorderRadius.all(Radius.circular(10))),
          width: 100,
          height: 10,
          padding: EdgeInsets.only(right: 70),
          child: Container(
            decoration: ShapeDecoration(
                gradient: LinearGradient(colors: [
                  Color(0xffFF8585),
                  Color(0xffEA45D5),
                ]),
                shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.all(Radius.circular(10)))),
          ),
        ),
        Padding(
          padding: EdgeInsets.only(left: 4),
        ),
        Text(
          "50/100",
          style: TextStyle(color: Colors.white, fontSize: 12),
        ),
      ],
    );
  }
}

class _Data {
  bool clicked;
  String title;

  _Data(this.title, {this.clicked = false});
}

List<_Data> _list = [
  null,
  _Data("悬疑电影"),
  _Data("户外运动"),
  _Data("制作美食"),
  _Data("植物"),
  _Data("吉他"),
  _Data("乐队的夏天"),
  _Data("特朗普"),
  _Data("特朗普gfgf"),
];
