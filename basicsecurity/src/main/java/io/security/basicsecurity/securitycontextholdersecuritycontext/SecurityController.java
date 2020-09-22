package io.security.basicsecurity.securitycontextholdersecuritycontext;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

//@RestController
public class SecurityController {

    @GetMapping(value = "/")
    public String index(HttpSession session){

        SecurityContext context = (SecurityContext)session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        //세션에서 추출

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //ContextHolder에서 추출
        Authentication authentication1 = context.getAuthentication();
        // 위와 같은 객체

        return "home";
    }

    @GetMapping(value = "/thread")
    public String thread(){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {
                        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                        //자식 쓰레드에서 확인해보자
                    }
                }

        ).start();

        return "thread";

    }
}
