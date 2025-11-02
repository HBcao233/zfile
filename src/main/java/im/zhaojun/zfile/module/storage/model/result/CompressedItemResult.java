package im.zhaojun.zfile.module.storage.model.result;

import im.zhaojun.zfile.module.storage.model.enums.FileTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

/**
 * 压缩文件信息结果类
 *
 * @author zhaojun
 */
@Data
@Schema(title = "压缩文件信息结果类")
public class CompressedItemResult implements Serializable {

  @Schema(title = "文件名", example = "a.mp4")
  private String name;

  @Schema(title = "类型", example = "file")
  private FileTypeEnum type;

  @Schema(title = "是否加密", example = "false")
  private Boolean encrypted;

  @Schema(title = "大小", example = "1024")
  private Long size;

  public CompressedItemResult(String name, FileTypeEnum type, Boolean encrypted, Long size) {
    this.name = name;
    this.type = type;
    this.encrypted = encrypted;
    this.size = size;
  }
}
