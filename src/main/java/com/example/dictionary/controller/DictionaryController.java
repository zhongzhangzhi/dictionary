package com.example.dictionary.controller;

import com.example.dictionary.service.SerializerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:zhongzhangzhi@xiniaoyun.com">zhongzhangzhi(zhongzhangzhi@xiniaoyun.com)</a>
 */
@RestController
public class DictionaryController {

    @Autowired
    private SerializerService serializerService;

    @RequestMapping("serialize")
    public String serialize(@RequestParam("content") String content) {
        return serializerService.serialize(content);
    }
}
