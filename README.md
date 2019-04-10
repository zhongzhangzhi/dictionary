# dictionary
数据字典（注解实现）

- 数据字典为单独服务，避免字典缓存在每个服务中
- 使用阿里fastjson中的SerializeFilter在POJO序列化时，追加数据字典字段
