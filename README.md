# Start-Spring-Security
스프링 시큐리티를 학습합니다. Authentication 구현 관련 앱 입니다. 해당 프로젝트는 수강을 따라가며 작성한 앱입니다.

중점은 AuthetnicationFlow 와 AuthorizationFlow 의 이해와 구현입니다. 해당 앱을 작성 후 스스로 적용한 사례는 Spring-React 에 있습니다.
 
* Model 2 방식을 따르며, Mvc 패턴을 따릅니다.

<hr>

![](https://github.com/soominJung0413/Start-Spring-Security/blob/master/%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B01.PNG)

<hr>

![](https://github.com/soominJung0413/Start-Spring-Security/blob/master/%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B02.PNG)

<hr>

 **하단 정보를 포함한 학습 기록 사항은 개인 기술 블로그에 있습니다.**
 * https://doli0413.tistory.com/category/%EC%8B%9C%EC%9E%91%ED%95%98%EC%9E%90SpringSecurity

**학습 내용**
formLogin().
 * UsernamePasswordAuthenticationFilter
 * AuthenticatinManager
 * AuthenticationProvider
 * UsernamePasswordAuthenticationToken
 * InsufficientAuthenticationException
 * UsernameNotFoundException
 * BadCredentialException
 * AuthenticationEntryPoint
 * AuthenticationFailureHandler
 * AuthenticationSuccessHandler
 * SimpleGrantedAuthorities
 * SecurityContext, SecurityContextHolder
 * UserDetailsService
 * User(SpringSecurity)
 
logout().
 * LogoutFilter 
 * AntPathRequestMatcher
 * LogoutHandler, SecurityContextLogoutHandler 
 * SimpleUrlLogoutSuccessHandler 
 
rememberMe().
 * RememberMeAuthenticationFilter 
 * Remeber-Me Cookie
 * RememberMeServices, TokenBasedRememberMeServices(메모리) , PersistentTokenBasedRememberMeServices(DB) 
 * RememberMeAuthenticationToken 
 * UserDetailsService

anoymous
 * AnonymousAuthticationFilter 
 * AnonymousAuthenticationToken (AnonymousUser, ROLE_ANONYMOUS)
 * AuthenticationcredentialsNotFoundException < AuthenticationToken 이 null 이라면 발생 이를 회피하기 위해 Anonymous 작동
 
sessionManagement().: 동시 세션제어, 기존 사용자 세션만료 전략 or 현재 사용자 인증 실패 전략으로 나뉨
 * 세션 고정 공격 : 공격자가 서버에 인증 성공후 받은 JSESSIONID 를 피공격자의 Cookie 로 심는다. 피 공격자가 인증에 성공할시 공격자와 피공격자는 같은 JESSIONID 를 공유하게 되어 
                   공격자가 사용자의 정보를 공유할 수 있게된다. 이를 방지하고자 Spring Security 는 JSESSIONID 값을 매번 새로 만든다.
 * SessionManagementFilter : 세션 관리 (사용자의 세션정보를 등록,삭제,조회) , 동시적 세션 제어 (동일 계정 허용 세션 수 제한) , 세션 고정 보호 (JSESSIONID를 새로 발급하여 공격자의 쿠키조작을 방지)
 * ConcurrentSessionFilter : 요청마다 세션의 만료여부 확인, 만료시 바로 세션을 만료처리
 * ConcurrentSessionControlAuthenticationStrategy : 세션 카운트 확인 ( 기존 사용자 만료 전략일 경우 세션만료요청, 현재 사용자 인증 실패 전략일 경우 SessionAuthenticationException)
 * ChangeSessionIdAuthenticationStrategy : 세션 고정 공격 보호 ( 세션아이디 변경)
 * RegisterSessionAutheticationStarategy : 새로운 세션을 만들어 등록
 
인가 API의 표현식
 * authenticated() : 인증된 사용자의 접근 허용
 * fullyAuthenticated() : RememberMe 기능을 제외한 인증된 사용자 접근 허용
 * permiAll() : 모든 접근 허용
 * denyAll() : 모든 접근 불가
 * anonymous() : 익명 사용자만의 접근을 허용
 * rememberMe() : RemeberMe 기능으로의 접근만을 허용
 * access(String) : SpEL 의 평가 결과가 true라면 접근 허용
 * hasRole(String) : 사용자가 주어진 역할이 있다면 접근허용, **매개값에 "ROLE" 을 적으면 예외 발생**
 * hasAuthority(String) : 사용자가 주어진 권한이 있다면 접근 허용, **매개값에 "ROLE"을 적지않으면 예외 발생**
 * hasAnyRole(String ...) : **여러개의 역할 중 하나라도 있다면** 접근 허용, **매개 값에 "ROLE"을 적으면 예외 발생**
 * hasAnyAuthority(String ...) : **여러개의 권한중 하나라도 있다면 접근 허용**, **매개 값에 "ROLE"을 적지않으면 예외 발생**
 * hasIpAddress(String) : 주어진 **IP로 요청이 오면** 접근 허용
 
exceptionHandling()
 * ExceptionTranslationFilter ( AuthenticationException, AccessDeniedException 처리) , FilterSecurityInterceptor 의 예외 발생을 catch 하는 주체
 * AuthenticationEntryPoint : 인증관련 예외, Anonymous 로 인한 인증 권한 없음, RememberMe 기능으로 인한 접근에 권한이 없을 경우 실행.
 * RequestCache (HttpSessionRequestCache) : SavedRequest에 사용자가 접근하려고 했던 요청 경로가 들어있다. 인증성공시 해당 요청경로로 이동시킨다.
 * AccessDeniedHandler : 인증사용자이나 권한이 없을경우 호출 됨

csrf()
 * 사용자가 인증완료시 공격자가 이메일 등의 링크를 피공격에게 전달, 피공격자가 해당링크로 접근하게 되면 인증된 피공격자의 SESSIONID 값으로 이미지파일에 
   있는 src 경로를 통하여 서버에 접속하게 됨 서버는 JSESSIONID 를 파악하나 인증된 피공격자의 JSESSION 를 통한 요청이므로 접근을 허용 함. 피공격자의 인지없이 공격자의 배송지가 등록
 * CsrfFilter : Spring Security 는 모든 요청에 대해 무작위값으로 생성해준 CsrfToken을 요구한다. CSRF 공격을 하더라도 해당 Request 에대한 Csrf 토큰이 없기 때문에 요청은 무시된다.
 * PUT, DELETE, POST,PATCH 방식을 채용함
 * Thymeleaf 나 SpCustomTag 인 form 사용시 csrf hidden 인풋은 자동으로 생성된다.
 

<hr>

**언어**
 * Java, Servlet, JavaScript, 

**웹표준**
 * HTML, CSS
 
**프레임워크 및 플러그인**
 * SpringBoot, Spring Data Jpa, Thymeleaf, BootStrap, Jquery, Ajax

**데이터 베이스**
 * PostgresQL
