package cn.bearever.mosaicserver.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TopicRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 获取数据库保存的话题列表
     *
     * @return
     */
    public List<TopicDao> getList() {
        String sql = "SELECT * FROM `topic` ORDER BY sort DESC";
        return jdbcTemplate.query(sql, new RowMapper<TopicDao>() {
            @Override
            public TopicDao mapRow(ResultSet rs, int rowNum) throws SQLException {
                TopicDao dao = new TopicDao();
                dao.id = rs.getInt("id");
                dao.text = rs.getNString("text");
                dao.image = rs.getNString("image");
                dao.enable = rs.getBoolean("enable");
                dao.sort = rs.getInt("sort");
                dao.createTime = rs.getTimestamp("create_time");
                dao.updateTime = rs.getTimestamp("update_time");
                return dao;
            }
        });
    }
}
