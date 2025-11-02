package im.zhaojun.zfile.module.config.model.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class LinkExpireDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer value;

  private String unit;

  private Long seconds;
}
