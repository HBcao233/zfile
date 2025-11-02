package im.zhaojun.zfile.module.link.model.request;

import java.util.List;
import lombok.Data;

/**
 * @author zhaojun
 */
@Data
public class BatchDeleteRequest {

  private List<Integer> ids;
}
