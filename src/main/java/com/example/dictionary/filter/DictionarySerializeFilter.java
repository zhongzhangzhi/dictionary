package com.example.dictionary.filter;

import com.example.dictionary.annotation.Dictionary;
import com.example.dictionary.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @author <a href="mailto:zhongzhangzhi@xiniaoyun.com">zhongzhangzhi(zhongzhangzhi@xiniaoyun.com)</a>
 */
@Component
public class DictionarySerializeFilter extends BaseExtendedAttributeFilter<Dictionary> {

    private DictionaryService dictionaryService;

    @Autowired
    public DictionarySerializeFilter(DictionaryService dictionaryService) {
        super(Dictionary.class);
        this.dictionaryService = dictionaryService;
    }

    @Override
    protected void doWrite(Field field, Object value, Dictionary dictionary) {
        String key = field.getName() + "Desp";
        String val = dictionaryService.getDesp(dictionary.group(), value.toString());
        this.writeKeyValue(key, val);
    }
}
