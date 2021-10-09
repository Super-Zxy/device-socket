package com.config;


import com.service.RefundOpInfoService;
import com.util.Context;
import com.util.StringUtils;
import com.util.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录的拦截器
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Autowired
    RefundOpInfoService refundOpInfoService;

    /**
     * 请求达到controller之前
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        Operator localThreadOperator = new Operator();
//        NL_Operator nl_operator = new NL_Operator();
//        nl_operator.setCityId("14");
//        nl_operator.setOperatorId("121");
//        nl_operator.setOrgaId("11");
//        nl_operator.setOperatorName("121");
//        localThreadOperator.setNl_Operator(nl_operator);
//        OperatorParentThreadLocal.set(localThreadOperator);

        //校验要是有用户 则登录  没有用户则跳转到登录界面

        String url = request.getRequestURI();
        if (url.indexOf("/lampblack/LoginController/loginPage") ==-1) {
            //判断请求头中有没有token  如果没有token则就返回401  有token就验证token对不对 不对则返回401

            String token;
            String opId;
            if (Context.getAccFlag(url)) {
                token = request.getParameter("token");
                opId = request.getParameter("opId");
            }else {
                token = request.getHeader("token");
                opId = request.getHeader("opId");
            }


            logger.info("LoginInterceptor:opId"+opId+"===");

            if (StringUtils.isEmpty(token)) {
                response.setStatus(401);
                return false;
            } else {
                boolean flag = TokenUtils.verify(token);
                if (flag) {
                    Object nl_operator_temp = request.getSession().getAttribute("nl_operator");
                    if (nl_operator_temp == null) {
                        if(org.apache.commons.lang3.StringUtils.isNotEmpty(opId)) {
//                            //查询操作员的登录用户
//                            //初始化上下文
//                            AppContext context = new AppContext();
//                            //设置当前操作的数据源信息--地市
//                            optInfo.setDdrCity("99");
//                            //设置当前操作的数据源信息--数据库
//                            optInfo.setSubSysId(GlobalConst.SUB_SYS_REFUND);
//                            //设置操作信息上下文
//                            context.setOptInfo(this.optInfo);
//                            //设置数据源
//                            this.setDateSourceConfig(this.getRouterRule(context));
//
//                            Map<String, Object> paraMap = new HashMap<>();
//                            paraMap.put("opId", opId.trim());
//                            paraMap.put("status", 1);
//                            //设置登录用户得信息
//                            List<RefundOpInfo> list = refundOpInfoService.qryRefundOpInfo(paraMap);
//
//                            //查询到用户不是空
//                            if (CollectionUtils.isNotEmpty(list)) {
//                                Operator operator = new Operator();
//                                NL_Operator nl_operator = new NL_Operator();
//                                nl_operator.setCityId(String.valueOf(list.get(0).getAreaId()));
//                                nl_operator.setOrgaId(String.valueOf(list.get(0).getGroupId()));
//                                nl_operator.setOperatorId(String.valueOf(list.get(0).getOpId()));
//                                nl_operator.setOperatorName(list.get(0).getOpName());
//                                nl_operator.setRefundLevel(list.get(0).getRefundLevel());
//                                //权限类型
//                                nl_operator.setOperatorType(String.valueOf(list.get(0).getIsWeb()));
//                                operator.setNl_Operator(nl_operator);
//                                OperatorParentThreadLocal.set(operator);
//                            } else {
//                                //没有用户 需要重新登录
//                                response.setStatus(401);
//                                return false;
//                            }
                        }else {
                            //没有用户 需要重新登录
                            response.setStatus(401);
                            return false;
                        }
                    } else {
//                        Operator localThreadOperator = new Operator();
//                        localThreadOperator.setNl_Operator((NL_Operator) nl_operator_temp);
//                        OperatorParentThreadLocal.set(localThreadOperator);
//                        request.getSession().setAttribute("nl_operator", nl_operator_temp);
                    }
                    //设置值
                    return true;
                } else {
                    //验证失败 需要重新登录
                    response.setStatus(401);
                    return false;
                }
            }

//            Object nl_operator_temp = request.getSession().getAttribute("nl_operator");
//            if (nl_operator_temp == null) {
//                response.setStatus(401);
//                return false;
//            } else {
//                //重新设置登录信息
//                Operator localThreadOperator = new Operator();
//                localThreadOperator.setNl_Operator((NL_Operator) nl_operator_temp);
//                OperatorParentThreadLocal.set(localThreadOperator);
//                request.getSession().setAttribute("nl_operator", nl_operator_temp);
//                //设置值
//                return true;
//            }
        }

        return true;
    }

    /**
     * controller处理之后 但是还并未传递到网页模板进行渲染
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    /**
     * 页面被渲染之后即将返回给用户的时候
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
