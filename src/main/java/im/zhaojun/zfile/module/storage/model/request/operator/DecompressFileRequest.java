package im.zhaojun.zfile.module.storage.model.request.operator;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 压缩(文件/文件夹)请求
 *
 * @author HBcao233
 */
@Data
@Schema(description = "解压压缩包请求")
public class DecompressFileRequest {
  @Schema(title = "存储源 key", requiredMode = Schema.RequiredMode.REQUIRED, example = "local")
  @NotBlank(message = "存储源 key 不能为空")
  private String storageKey;

  @Schema(title = "请求路径", example = "/", description = "表示要解压的文件所在的文件夹")
  @NotBlank
  private String path;

  @Schema(title = "压缩包文件名", example = "movie", description = "表示要解压的文件名称")
  @NotEmpty
  private String name;

  @Schema(title = "输出文件夹路径", example = "/")
  @NotBlank
  private String output;

  @Schema(
      title = "是否解压到压缩包同名文件夹",
      example = "false",
      description = "是否解压到压缩包同名文件夹",
      defaultValue = "true")
  private Boolean createDir = true;

  @Schema(title = "解压密码", example = "123456")
  private String password;

  @Schema(title = "字符集", example = "UTF-8", defaultValue = "UTF-8", required = false)
  private String charset;

  @Schema(title = "源文件夹密码, 如果文件夹需要密码才能访问，则支持请求密码", example = "123456")
  private String pathPassword;

  @Schema(title = "目标文件夹密码, 如果文件夹需要密码才能访问，则支持请求密码", example = "123456")
  private String outputPassword;
}
