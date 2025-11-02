package im.zhaojun.zfile.core.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerMapping;

/**
 * @author zhaojun
 */
public class SpringMvcUtils {

  public static String getExtractPathWithinPattern() {
    HttpServletRequest httpServletRequest = RequestHolder.getRequest();
    String path =
        (String)
            httpServletRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
    String bestMatchPattern =
        (String) httpServletRequest.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
    AntPathMatcher apm = new AntPathMatcher();
    return apm.extractPathWithinPattern(bestMatchPattern, path);
  }
}
