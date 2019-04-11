package com.example.dictionary.service;

import com.alibaba.fastjson.JSON;
import com.example.dictionary.filter.DictionarySerializeFilter;
import com.example.dictionary.pojo.TestBO;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:zhongzhangzhi@xiniaoyun.com">zhongzhangzhi(zhongzhangzhi@xiniaoyun.com)</a>
 */
@Service
public class SerializerService {

    @Autowired
    private DictionarySerializeFilter filter;

    public String serialize(String content) {
        byte[] bytes = Base64.decodeBase64(content);

        Schema<TestBO> schema = RuntimeSchema.getSchema(TestBO.class);
        TestBO testBO = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, testBO, schema);

        return JSON.toJSONString(testBO, filter);
    }
}


