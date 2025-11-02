package im.zhaojun.zfile.module.storage.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.HashMap;
import java.util.Map;

/**
 * 存储源类型枚举
 *
 * @author zhaojun
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CompressTypeEnum implements IEnum {

  /** 当前系统支持的所有压缩包类型 */
  ZIP("zip", "zip 压缩包", ".zip"),
  SEVEN_Z("7z", "7-Zip 压缩包", ".7z"),
  RAR("rar", "rar 压缩包", ".rar"),
  GZ("gz", "gzip 压缩包", ".tar.gz");

  private static final Map<String, CompressTypeEnum> ENUM_MAP = new HashMap<>();

  static {
    for (CompressTypeEnum type : CompressTypeEnum.values()) {
      ENUM_MAP.put(type.getKey(), type);
    }
  }

  @Schema(title = "存储源类型枚举 Key", example = "zip")
  @EnumValue
  private final String key;

  @Schema(title = "压缩包类型枚举描述", example = ".zip 压缩包")
  private final String description;

  @Schema(title = "压缩包后缀名", example = ".zip")
  private final String ext;

  CompressTypeEnum(String key, String description, String ext) {
    this.key = key;
    this.description = description;
    this.ext = ext;
  }

  public String getKey() {
    return key;
  }

  public String getDescription() {
    return description;
  }

  public String getExt() {
    return ext;
  }

  @JsonIgnore
  public String getValue() {
    return key;
  }

  public static boolean isValid(String key) {
    return ENUM_MAP.containsKey(key);
  }

  public static CompressTypeEnum of(String key) {
    return ENUM_MAP.get(key);
  }
}
