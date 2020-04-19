package cn.bearever.mosaicserver.topic;

import cn.bearever.mosaicserver.BaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class TopicController {
    @Autowired
    private TopicService topicManager;

    @GetMapping("topics")
    public BaseResult getTopicList(@RequestParam(value = "uid", defaultValue = "") String uid) {
        TopicResult result = new TopicResult();
        result.setList(topicManager.getTopics(uid));
        result.setMsg("获取话题成功");
        return result;
    }
}
