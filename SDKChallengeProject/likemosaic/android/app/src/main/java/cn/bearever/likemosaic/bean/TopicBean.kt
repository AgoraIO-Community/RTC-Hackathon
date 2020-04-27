package cn.bearever.likemosaic.bean

import java.io.Serializable
import java.sql.Timestamp


/**
 *
 * "id": 15,
"text": "鬼吹灯",
"image": "",
"enable": true,
"sort": 0,
"createTime": "2020-04-11T15:23:10.000+0000",
"updateTime": "2020-04-11T15:23:10.000+0000"
 * @author luoming
 * @date 2020/4/12
 */
data class TopicBean(var id: Int,
                     var text: String = "",
                     var image: String="",
                     var enable: Boolean = true,
                     var sort: Int = 0,
                     var createTime: String ="",
                     var updateTime: String="") :Serializable