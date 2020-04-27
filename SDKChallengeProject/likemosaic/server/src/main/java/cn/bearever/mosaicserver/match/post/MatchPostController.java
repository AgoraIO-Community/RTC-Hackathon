package cn.bearever.mosaicserver.match.post;

import cn.bearever.mosaicserver.BaseResult;
import cn.bearever.mosaicserver.match.MatchManager;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 匹配的接口
 */
@RestController
public class MatchPostController {

    @GetMapping("/match/post")
    public BaseResult match(@RequestParam(value = "uid", defaultValue = "") String uid) {
        //加入匹配队列
        BaseResult result = new BaseResult();
        if (!StringUtils.isEmpty(uid)) {
            MatchManager.getInstance().add(uid);
            result.setMsg("成功已加入匹配列表");
        } else {
            result.setCode(BaseResult.CODE_FAILED);
            result.setMsg("uid 不能为空");
        }
        return result;
    }
}
