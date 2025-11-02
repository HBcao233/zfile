package im.zhaojun.zfile.module.link.model.dto;

import java.util.Date;
import lombok.Data;

@Data
public class CacheInfo<K, V> {

  private K key;

  private V value;

  private Date expiredTime;

  private Long ttl;
}
