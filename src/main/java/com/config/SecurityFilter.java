package com.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Properties;

public class SecurityFilter
        implements Filter {
    private static String tag = "SecurityFilter====";
    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    public static String errorInput = "";
    public static String errorInputInTestBUG = "";
    public static String errorUrl = "";
    public static String whiteURLs = "";
    public static String blackURLs = "";
    public static String notCheckXMLURLs = "";
    public static String hostUrl = "";
    public static String checkURLs = "";
    public static String noMultipartURLs = "";

    public static String rediectErrorUrl = "";

    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        String strReqUri = ((HttpServletRequest) servletRequest).getRequestURI().trim();
        logger.debug(tag + "请求的url:   " + strReqUri);
        //白名单
        if (this.whiteURLs.contains(strReqUri)) {
            logger.info(tag + "SUCCESS--whiteUrl--" + strReqUri);
            filterChain.doFilter(servletRequest, servletResponse);
        }
        String[] keyInTestBUG = errorInputInTestBUG.split(",");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        //host攻击校验
        String reqHost = req.getHeader("Host");
        logger.debug(tag + "reqHost:   " + reqHost);
//        if (hostUrl.indexOf(reqHost.split(":")[0]) <= -1)
//        {
//            logger.debug(tag+"this is a HostAttack : = " + reqHost);
//            res.sendRedirect("http://10.32.40.241:8000/ng/boss/error/xssError.jsp");
//            return;
//        }
        //OPTIONS方法校验
        String reqMethod = req.getMethod();
        logger.debug(tag + "reqMethod:   " + reqMethod);
        if ("OPTIONS".equals(reqMethod)) {
            logger.error(tag + "this is a OPTIONS Attack : = " + reqMethod);
            res.sendRedirect(rediectErrorUrl);
            return;
        }

        //校验URL关键字
        String[] errorUrlKeyArray = errorUrl.split(",");

        boolean errorUrlFindFlag = false;
        for (String errorUrlKey : errorUrlKeyArray) {
            if (strReqUri.indexOf(errorUrlKey) > -1) {
                logger.error(tag + "URL:" + strReqUri + " --contain key:" + errorUrlKey);
                errorUrlFindFlag = true;
                break;
            }
        }

        //校验XSS攻击
        if (!errorUrlFindFlag) {
            logger.debug(tag + "URL:" + strReqUri + " -- not multipart and goto check XSS");
            Enumeration ems = req.getParameterNames();
            String key = "";
            String value = "";
            while (ems.hasMoreElements()) {
                key = (String) ems.nextElement();
                value = req.getParameter(key).toLowerCase();
                for (String s : keyInTestBUG) {
                    if (value.indexOf(s) != -1) {
                        logger.error(tag + "goto error ,contain XSSkey:" + key + ",value:" + value);
                        res.sendRedirect(rediectErrorUrl);
                        return;
                    }
                }
            }
        } else {
            logger.error(tag + "this is a XSS Attack ， URL:" + strReqUri);
            res.sendRedirect(rediectErrorUrl);
        }

        logger.info(tag + "SUCCESS--Check End --" + strReqUri);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.rediectErrorUrl = filterConfig.getInitParameter("rediectErrorUrl");

        Properties pro = new Properties();
        InputStream in = new BufferedInputStream(getClass().getResourceAsStream("/xss.properties"));
        try {
            pro.load(new InputStreamReader(in, "UTF-8"));
            pro.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        errorInput = pro.getProperty("errorInput");
        errorUrl = pro.getProperty("errorUrl");
        whiteURLs = pro.getProperty("whiteURLs");
//        blackURLs = pro.getProperty("blackURLs");
//        notCheckXMLURLs = pro.getProperty("notCheckXMLURLs");
        hostUrl = pro.getProperty("hostUrl");
//        checkURLs = pro.getProperty("checkURLs");
        errorInputInTestBUG = pro.getProperty("errorInputInTestBUG");
//        noMultipartURLs = pro.getProperty("noMultipartURLs");
    }
}