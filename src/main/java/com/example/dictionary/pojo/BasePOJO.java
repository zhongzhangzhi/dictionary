package com.example.dictionary.pojo;

import com.alibaba.fastjson.JSON;

/**
 * @author <a href="mailto:zhongzhangzhi@xiniaoyun.com">zhongzhangzhi(zhongzhangzhi@xiniaoyun.com)</a>
 */
public class BasePOJO implements POJO {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
