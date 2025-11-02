package im.zhaojun.zfile.module.user.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserPwdRequest {

  private String oldPassword;

  @NotBlank(message = "新密码不能为空")
  private String newPassword;

  @NotBlank(message = "确认密码不能为空")
  private String confirmPassword;
}
