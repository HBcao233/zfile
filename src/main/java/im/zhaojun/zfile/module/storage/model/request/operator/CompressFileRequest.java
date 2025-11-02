package im.zhaojun.zfile.module.storage.model.request.operator;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Data;

/**
 * 压缩(文件/文件夹)请求
 *
 * @author HBcao233
 */
@Data
@Schema(description = "压缩(文件/文件夹)请求")
public class CompressFileRequest {
  @Schema(title = "存储源 key", requiredMode = Schema.RequiredMode.REQUIRED, example = "local")
  @NotBlank(message = "存储源 key 不能为空")
  private String storageKey;

  @Schema(title = "请求路径", example = "/", description = "表示要压缩的文件所在的文件夹")
  @NotBlank
  private String path;

  @Schema(title = "文件名称", example = "movie", description = "表示要压缩的文件名称，支持多个")
  @NotEmpty
  private List<String> nameList;

  @Schema(title = "压缩包文件名", example = "/", description = "表示要创建的压缩包文件名")
  @NotBlank(message = "压缩包文件名不能为空")
  private String compressName;

  @Schema(title = "压缩包类型", example = "zip")
  @NotBlank
  private String compressType;

  @Schema(title = "压缩包分卷大小 (MB)", example = "100", defaultValue = "0", required = false)
  @Min(value = 0, message = "分卷大小不能为负数")
  @Max(value = 2048, message = "分卷大小不能超过 2GB")
  private Long splitSizeMB = 0L;

  @Schema(title = "源文件夹密码, 如果文件夹需要密码才能访问，则支持请求密码", example = "123456")
  private String password;
}
