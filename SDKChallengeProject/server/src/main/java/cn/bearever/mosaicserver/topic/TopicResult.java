package cn.bearever.mosaicserver.topic;

import cn.bearever.mosaicserver.BaseResult;

import java.util.List;

public class TopicResult extends BaseResult {
    private List<TopicDao> list;

    public List<TopicDao> getList() {
        return list;
    }

    public void setList(List<TopicDao> list) {
        this.list = list;
    }
}
