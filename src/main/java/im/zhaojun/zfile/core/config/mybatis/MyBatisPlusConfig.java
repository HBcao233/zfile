package im.zhaojun.zfile.core.config.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus 配置类
 *
 * @author zhaojun
 */
@Configuration
public class MyBatisPlusConfig {

  /** mybatis plus 分页插件配置 */
  @Bean
  public MybatisPlusInterceptor mybatisPlusInterceptor(DataSource dataSource) throws SQLException {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    String databaseProductName = dataSource.getConnection().getMetaData().getDatabaseProductName();
    DbType dbType = DbType.getDbType(databaseProductName);
    interceptor.addInnerInterceptor(new PaginationInnerInterceptor(dbType));
    return interceptor;
  }
}
