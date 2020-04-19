package cn.bearever.mosaicserver.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 话题的数据结构
 */

public class TopicDao implements Serializable {
    public int id;
    public String text;
    public String image;
    public boolean enable;
    public int sort;
    public Timestamp createTime;
    public Timestamp updateTime;

}
