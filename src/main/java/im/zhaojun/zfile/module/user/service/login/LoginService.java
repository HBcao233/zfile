package im.zhaojun.zfile.module.user.service.login;

import im.zhaojun.zfile.module.user.model.request.UserLoginRequest;
import im.zhaojun.zfile.module.user.service.login.verify.LoginVerifyService;
import jakarta.annotation.Resource;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginService {

  @Resource private List<LoginVerifyService> loginVerifyServiceList;

  public void verify(UserLoginRequest userLoginRequest) {
    loginVerifyServiceList.forEach(
        loginVerifyService -> {
          loginVerifyService.verify(userLoginRequest);
        });
  }
}
