package im.zhaojun.zfile.module.log.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
@TableName(value = "login_log")
public class LoginLog implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.INPUT)
  private Integer id;

  @TableField(value = "username")
  private String username;

  @TableField(value = "`password`")
  private String password;

  @TableField(value = "create_time", fill = FieldFill.INSERT)
  private Date createTime;

  @TableField(value = "ip")
  private String ip;

  @TableField(value = "user_agent")
  private String userAgent;

  @TableField(value = "referer")
  private String referer;

  @TableField(value = "`result`")
  private String result;
}
