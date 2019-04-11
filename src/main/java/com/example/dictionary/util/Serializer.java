package com.example.dictionary.util;

import com.example.dictionary.pojo.TestBO;
import io.protostuff.LinkedBuffer;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import io.protostuff.ProtostuffIOUtil;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhongzhangzhi@xiniaoyun.com">zhongzhangzhi(zhongzhangzhi@xiniaoyun.com)</a>
 */
public class Serializer {


    public static void main(String[] args) {
        TestBO testBO = new TestBO();
        testBO.setName("zhongzhangzhi");

        // this is lazily created and cached by RuntimeSchema
        // so its safe to call RuntimeSchema.getSchema(Foo.class) over and over
        // The getSchema method is also thread-safe
        Schema<TestBO> schema = RuntimeSchema.getSchema(TestBO.class);

        // Re-use (manage) this buffer to avoid allocating on every serialization
        LinkedBuffer buffer = LinkedBuffer.allocate(512);

        // ser
        final byte[] protostuff;
        try
        {
            protostuff = ProtostuffIOUtil.toByteArray(testBO, schema, buffer);
        }
        finally
        {
            buffer.clear();
        }

        String b64 = Base64.encodeBase64String(protostuff);

        try {
            String urlNameString = "http://localhost:8080/serialize?content=" + b64;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0(compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line, result = "";
            while ((line = in.readLine()) != null) {
                result += line;
            }
            System.out.println(result);
        }catch (Exception e) {

        }

    }
}
