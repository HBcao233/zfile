package im.zhaojun.zfile.module.link.dto;

import java.lang.reflect.Method;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

@Data
@AllArgsConstructor
public class DynamicRegisterMappingHandlerDTO {

  private RequestMappingInfo requestMappingInfo;

  private Object object;

  private Method method;
}
