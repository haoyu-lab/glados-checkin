package com.example.gladoscheckin.common.filter;

import com.alibaba.fastjson.JSON;
import com.example.gladoscheckin.common.Result;
import com.example.gladoscheckin.common.hander.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@WebFilter(filterName = "SecurityFilter",urlPatterns ="/*")
public class SecurityFilter implements Filter {

    @Value("${security.ignores:/login}")
    String ignores;

    @Value("${security.open:false}")
    Boolean open;

    @Value("${security.ignore:false}")
    Boolean ignoreInfo;

//    @Resource
//    private RedisTemplate redisTemplate;


    private List<String> ignoreList = new ArrayList<>();

    /**
     * 修改配置之后，可能会丢失忽略列表，所以需要添加处理方法
     * @return
     */
    public List<String> getIgnoreList() {
        if(ignoreList==null || ignoreList.size()==0){
            String[] split = ignores.split(",");
            ignoreList = Arrays.asList(split);
        }
        return ignoreList;
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("创建过滤器成功");
        String[] split = ignores.split(",");
        ignoreList= Arrays.asList(split);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String pathInfo = request.getRequestURI();
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        String token = request.getHeader("token");

        Boolean ignore = false;
        if (!open) {
            ignore = true;
        } else {
            List<String> mIgnoreList = this.getIgnoreList();
            for (String one : mIgnoreList) {
                if (pathInfo.indexOf(one) != -1) {
                    ignore = true;
                    break;
                }
            }
        }
        if(!ignore){
            if(!StringUtils.isEmpty(token) && "haoyujiayou".equals(token)){
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                log.info("token错误");
                handerMessage(response,"token错误");
            }
        }else{
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {

    }

    private void handerMessage(HttpServletResponse response, String bizException) {
        GlobalExceptionHandler.setHeader(response);
        try (PrintWriter writer = response.getWriter()) {
            writer.write(JSON.toJSONString(Result.build(500, bizException, null)));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
