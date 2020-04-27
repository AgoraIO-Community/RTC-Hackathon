package com.asdf.funny.images.util.mongodb;

import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: zyh
 * @date: 2018/7/6
 */
public class JsonResultController {
    private static final String PAGE = "page";
    private static final String LIMIT = "limit";
    /**
     * layui表格统一JSON返回
     * @param code
     * @param msg
     * @param count
     * @param data
     * @param <T>
     * @return
     */
    protected <T> TableResultValue<T> tableJsonResult(Integer code, String msg, Integer count, T data) {
        TableResultValue<T> tableResultValue = new TableResultValue<>();
        tableResultValue.setCode(code);
        tableResultValue.setMsg(msg);
        tableResultValue.setCount(count);
        tableResultValue.setData(data);
        return tableResultValue;
   }

    /**
     * vue方式提交给前台
     * @param code
     * @param msg
     * @param count
     * @param data
     * @param userId
     * @param <T>
     * @return
     */
    protected <T> TableResultValue<T> tableJsonResult(Integer code, String msg, Integer count, T data,Long userId) {
            TableResultValue<T> tableResultValue = new TableResultValue<>();
            tableResultValue.setCode(code);
            tableResultValue.setMsg(msg);
            tableResultValue.setCount(count);
            tableResultValue.setData(data);
            tableResultValue.setUserId(userId);
            return tableResultValue;
        }

    /**
     * 统一JSON返回
     * @param code
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    protected <T> ResultValue<T> jsonResult(Integer code, String msg, T data) {
        ResultValue<T> resultValue = new ResultValue<>();
        resultValue.setCode(code);
        resultValue.setMsg(msg);
        if (data != null) {
            resultValue.setData(data);
        }
        return resultValue;
    }
    /**
     * 统一状态返回
     * @param code
     * @return ResultValue
     */
    protected <T> ResultValue<T> result(Integer code) {
        ResultValue<T> resultValue = new ResultValue<>();
        resultValue.setCode(code);
        return resultValue;
    }
    /**
     * 统一错误状态返回
     * @param code
     * @return ResultValue
     */
    protected <T> ResultValue<T> errorResult(Integer code,String msg) {
        ResultValue<T> resultValue = new ResultValue<>();
        resultValue.setCode(code);
        resultValue.setMsg(msg);
        return resultValue;
    }
    /**
     * 统一正常状态返回
     * @param code
     * @return ResultValue
     */
    protected <T> ResultValue<T> successResult(Integer code,String msg) {
        ResultValue<T> resultValue = new ResultValue<>();
        resultValue.setCode(code);
        resultValue.setMsg(msg);
        return resultValue;
    }
    /**
     * layer富文本返回
     * @param code
     * @param msg
     * @param data
     * @param src
     * @param title
     * @param <T>
     * @return
     */
    protected <T> RichText<T> richTextResult(String code, String msg, T data
            , String []src, String title) {
        RichText<T> richText = new RichText<>();
        richText.setCode(code);
        richText.setMsg(msg);
        richText.setData(data);
        richText.setSrc(src);
        richText.setTitle(title);
        return richText;
    }


    /**
     * 封装分页查询,使用方法
     * (传递map集合直接在需要分页的sql最后加上“LIMIT ${limit}”即可)
     * @param request
     * @return
     */
    protected Map<String,Object> setQueryMap(HttpServletRequest request){
        Map<String,Object> map = new ConcurrentHashMap<String,Object>();
        int page = ServletRequestUtils.getIntParameter(request, PAGE, 1);
        int limit =ServletRequestUtils.getIntParameter(request, LIMIT, 10);
        map.put(PAGE,page);
        map.put(LIMIT,(page*limit-limit)+","+(limit));
        return map;
    }
}
