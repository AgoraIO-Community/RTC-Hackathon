package cn.bearever.mosaicserver.match.get;

import cn.bearever.mosaicserver.BaseResult;
import cn.bearever.mosaicserver.match.MatchManager;
import cn.bearever.mosaicserver.topic.TopicDao;
import cn.bearever.mosaicserver.topic.TopicRepository;
import cn.bearever.mosaicserver.topic.TopicService;
import io.agora.media.RtcTokenBuilder;
import io.agora.media.RtmTokenBuilder;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 匹配的接口
 */
@RestController
public class MatchGetController {
    private static String appId = "e6c014ba465c44dca97cb4352ea830fd";
    private static String appCertificate = "ccdf492d365b420592feeda2177d3e3e";
    private static int expirationTimeInSeconds = 3600;
    private Map<String, List<TopicDao>> topicMap = new HashMap<>();

    @Autowired
    private TopicService topicService;

    @GetMapping("/match/get")
    public BaseResult match(@RequestParam(value = "uid", defaultValue = "") String uid) {
        if (StringUtils.isEmpty(uid)) {
            BaseResult result = new BaseResult();
            result.setCode(BaseResult.CODE_FAILED);
            result.setMsg("uid不能为空");
            return result;
        }

        MatchManager.MatchData matchData = MatchManager.getInstance().getChannel(uid);
        if (matchData == null) {
            BaseResult result = new BaseResult();
            result.setCode(BaseResult.CODE_FAILED);
            result.setMsg("还没有匹配上的频道");
            return result;
        } else {
            //生成token
            RtcTokenBuilder rtcTokenBuilder = new RtcTokenBuilder();
            int timestamp = (int) (System.currentTimeMillis() / 1000 + expirationTimeInSeconds);
            String rtcToken = rtcTokenBuilder.buildTokenWithUserAccount(appId, appCertificate, matchData.channel,uid,
                    RtcTokenBuilder.Role.Role_Publisher, timestamp);
            RtmTokenBuilder rtmTokenBuilder = new RtmTokenBuilder();
            String rtmToken = rtmTokenBuilder.buildToken(appId, appCertificate, uid, RtmTokenBuilder.Role.Rtm_User, timestamp);
            //获取话题信息 todo 多线程同步问题
            List<TopicDao> list = null;
            if (topicMap.get(matchData.channel) != null) {

                list = topicMap.get(matchData.channel);
                topicMap.remove(matchData.channel);
            } else {
                list = topicService.getTopics(uid);
                topicMap.put(matchData.channel, list);
            }

            MatchResult result = new MatchResult();
            result.setMsg("频道获取成功");
            result.setRtcToken(rtcToken);
            result.setRtmToken(rtmToken);
            result.setRemoteUid(matchData.uid);
            result.setChannel(matchData.channel);
            result.setList(list);
            return result;
        }
    }
}
