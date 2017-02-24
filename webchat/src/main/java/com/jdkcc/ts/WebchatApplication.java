package com.jdkcc.ts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.request.RequestContextListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@EnableAspectJAutoProxy //默认已开启
@EnableTransactionManagement //默认已开启
//@EnableJpaRepositories({"cn.cherish.mboot.repository","cn.cherish.mboot.extra.solr"}) //默认已开启
//@ImportResource({"classpath:spring-shiro.xml","classpath:spring-other.xml"}) //需要引入xml的话
@SpringBootApplication
public class WebchatApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebchatApplication.class);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        /*
         * 为了方便 RequestHolder 获取 request response session application 四大web对象
         * @see cn.cherish.mboot.util.RequestHolder
         */
        servletContext.addListener(new RequestContextListener());
    }

    public static void main(String[] args) {
        SpringApplication.run(WebchatApplication.class, args);
    }


}
