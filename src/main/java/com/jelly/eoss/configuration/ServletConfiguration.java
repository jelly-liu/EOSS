package com.jelly.eoss.configuration;

import com.jelly.eoss.servlet.ICodeServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 3:09 PM 2019/1/9
 * @Description：${description}
 */

//@Configuration
public class ServletConfiguration {
    public ServletRegistrationBean icodeServlet(){
        return new ServletRegistrationBean(new ICodeServlet(), "/vCode.jpg");
    }
}
