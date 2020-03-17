package servlet;

import javax.servlet.*;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 缺省适配器模式
 * sun 公司 已经提供该类
 * @see javax.servlet.GenericServlet
 */
public abstract class MyGenericServlet implements Servlet {

    private transient ServletConfig config;

    /**
     * 初始化 Servlet 对象, 并且禁止子类重写
     * ps: 不加final较灵活,加了较安全
     */
    @Override
    public final void init(ServletConfig servletConfig) throws ServletException {
        config = servletConfig;
        init();
    }

    /**
     * 由于不允许重写本身的init(ServletConfig )方法,所以对子类提供可扩展的init()方法
     */
    public void init() throws ServletException {

    }

    /**
     * 获取web.xml的初始化配置信息
     * @param s 初始化配置信息键名
     * @return 初始化配置信息值
     */
    public String getInitParameter(String s) {
        if (config == null) throw new IllegalArgumentException("err.servlet_config_not_initialized");
        return config.getInitParameter(s);
    }

    /**
     * @return 获取web.xml的初始化所有配置信息
     */
    public Enumeration<String> getInitParameters() {
        if (config == null) throw new IllegalArgumentException("err.servlet_config_not_initialized");
        return config.getInitParameterNames();
    }

    /**
     * @return Servlet类的别名
     */
    public String getServletName(){
        if (config == null) throw new IllegalArgumentException("err.servlet_config_not_initialized");
        return config.getServletName();
    }

    /**
     * @return Servlet类加载上下文信息
     */
    public ServletContext getServletContext(){
        if (config == null) throw new IllegalArgumentException("err.servlet_config_not_initialized");
        return config.getServletContext();
    }

    /**
     * @return Servlet的配置信息
     */
    @Override
    public ServletConfig getServletConfig() {
        return config;
    }

    /**
     * 强制要求子类实现service()方法
     * @param request 厂商服务器提供的ServletRequest对象
     * @param response 厂商服务器提供的ServletResponse对象
     */
    @Override
    public abstract void service(ServletRequest request, ServletResponse response) throws ServletException, IOException;

    @Override
    public String getServletInfo() {
        return "";
    }

    @Override
    public void destroy() {

    }
}