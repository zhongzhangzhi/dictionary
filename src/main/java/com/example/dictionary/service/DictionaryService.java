package com.example.dictionary.service;

import org.springframework.stereotype.Service;

/**
 *
 * 数据库表结构
 *      分组 - value - desp
 *
 * @author <a href="mailto:zhongzhangzhi@xiniaoyun.com">zhongzhangzhi(zhongzhangzhi@xiniaoyun.com)</a>
 */
@Service
public class DictionaryService {

    /**
     * 从库中获取数据字典的描述值
     * @param group         分组
     * @param value         字段值
     * @return              描述值
     */
    public String getDesp(String group, String value) {
        return "测试Desp";
    }

}
