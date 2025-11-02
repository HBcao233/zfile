package im.zhaojun.zfile.module.storage.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/** 支持的字符集枚举 */
public enum CharsetEnum implements IEnum {

  // 国际通用
  UTF_8("UTF-8", "UTF-8"),
  UTF_16("UTF-16", "UTF-16"),
  UTF_16BE("UTF-16BE", "UTF-16BE"),
  UTF_16LE("UTF-16LE", "UTF-16LE"),
  UTF_32("UTF-32", "UTF-32"),
  UTF_32BE("UTF-32BE", "UTF-32BE"),
  UTF_32LE("UTF-32LE", "UTF-32LE"),

  // 简体中文
  GBK("GBK", "简体中文 - Windows（最常用）"),
  GB2312("GB2312", "简体中文 - 旧标准"),
  GB18030("GB18030", "简体中文 - 新国标"),

  // 繁体中文
  BIG5("Big5", "繁体中文（台湾、香港）"),

  // 日文
  SHIFT_JIS("Shift_JIS", "日文 - Windows"),
  EUC_JP("EUC-JP", "日文 - Unix/Linux"),
  ISO_2022_JP("ISO-2022-JP", "日文 - 邮件"),

  // 韩文
  EUC_KR("EUC-KR", "韩文"),

  // 西欧语言
  ISO_8859_1("ISO-8859-1", "西欧语言");

  @Schema(title = "字符集代码", example = "UTF-8")
  @EnumValue
  private final String code;

  @Schema(title = "字符集枚举描述", example = "UTF-8")
  private final String description;

  private static final Map<String, CharsetEnum> ENUM_MAP = new HashMap<>();

  static {
    for (CharsetEnum type : CharsetEnum.values()) {
      ENUM_MAP.put(type.getCode(), type);
    }
  }

  CharsetEnum(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public String getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }

  public String getValue() {
    return code;
  }

  @JsonCreator
  public static CharsetEnum of(String code) {
    if (code == null || code.isEmpty()) {
      return UTF_8;
    }
    CharsetEnum res = ENUM_MAP.get(code);
    if (res == null) {
      throw new IllegalArgumentException(
          "不支持的字符集: \"" + code + "\"。支持的字符集: " + String.join(", ", ENUM_MAP.keySet()));
    }
    return res;
  }

  public static boolean isValid(String code) {
    return ENUM_MAP.containsKey(code);
  }

  /** 获取 Java Charset 对象 */
  public Charset toCharset() {
    return Charset.forName(this.code);
  }
}
