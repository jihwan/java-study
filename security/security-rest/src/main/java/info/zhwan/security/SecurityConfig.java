package info.zhwan.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * security java configuration
 *
 * @author Jihwan, Hwang
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()

//        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        .and()
        .exceptionHandling()
        .authenticationEntryPoint(new Http401AuthenticationEntryPoint("what?"))

        .and()
        .formLogin().permitAll()
        .usernameParameter("username") // parameter name customizing 가능
        .passwordParameter("password") // parameter name customizing 가능
        .successHandler(new SaveRequestAwareAuthenticationOkHandler()) // spring security version up이 되어 기능이 제공된다면, 이 클래스는 사라지게 될 것임.
        .failureHandler((request, response, exception) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage()))

        .and()
        .logout().permitAll()
        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())


        .and()
        .authorizeRequests().antMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")

        .and()
        .authorizeRequests().anyRequest().authenticated();
    ;
  }


  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser("user").password("user").roles("USER")

        .and()
        .withUser("admin").password("admin").roles("ADMIN")
    ;
  }

  @Bean
  @Override
  public UserDetailsService userDetailsServiceBean() throws Exception {
    return super.userDetailsServiceBean();
  }
}

/**
 * spring security version up이 된다면, 이 클래스는 사라지게 될 것임.
 *
 * @author Jihwan Hwang
 */
class SaveRequestAwareAuthenticationOkHandler extends SimpleUrlAuthenticationSuccessHandler {
  protected final Log logger = LogFactory.getLog(this.getClass());

  private RequestCache requestCache = new HttpSessionRequestCache();

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
                                      HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
    SavedRequest savedRequest
        = requestCache.getRequest(request, response);

    if (savedRequest == null) {
      clearAuthenticationAttributes(request);
      return;
    }
    String targetUrlParam = getTargetUrlParameter();
    if (isAlwaysUseDefaultTargetUrl()
        || (targetUrlParam != null
        && StringUtils.hasText(request.getParameter(targetUrlParam)))) {
      requestCache.removeRequest(request, response);
      clearAuthenticationAttributes(request);
      return;
    }

    clearAuthenticationAttributes(request);
  }

  public void setRequestCache(RequestCache requestCache) {
    this.requestCache = requestCache;
  }
}
