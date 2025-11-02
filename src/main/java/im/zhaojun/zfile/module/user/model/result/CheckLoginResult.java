package im.zhaojun.zfile.module.user.model.result;

import java.io.Serializable;
import lombok.Data;

@Data
public class CheckLoginResult implements Serializable {

  private static final long serialVersionUID = 1L;

  private Boolean isLogin;

  private Boolean isAdmin;

  private String username;

  private String nickname;
}
