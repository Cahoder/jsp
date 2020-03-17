package servlet;

import javax.servlet.*;
import java.io.IOException;
import java.util.Enumeration;

/**
 * servlet 是一套规范接口,需要各大服务器厂商实现其规范接口
 *
 * servlet 是 一种开发动态web资源的技术 可以在Java代码中嵌套 前端代码
 *         是 java 语言中的servlet接口
 *         指实现了这个接口的类
 *
 * servlet 的编程模型 基于 request response
 *         1.客户端请求发送到服务器
 *         2.服务器调用servlet程序,生成数据
 *         3.服务器将数据响应给客户端
 *         4.客户端渲染数据


 * servlet 的生命周期如下:
 */
public class LifeCycleServlet implements Servlet {
    /**
     * servlet 服务初始化,生命周期的第一步,并且仅执行一次
     * @param servletConfig 启动servlet服务的配置信息
     */
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        /**@see web.xml -> <load-on-startup> -> 此servlet的init()方法会在tomcat已启动就执行*/

        //各大服务器实现的 javax.servlet.ServletConfig 并提供的配置信息
        ServletConfig config = servletConfig;
        System.out.println(config); //tomcat提供的实现ServletConfig接口的类是:org.apache.catalina.core.StandardWrapperFacade

        /**该servlet类的加载上下文信息
            什么叫上下文,上下文有什么用:
                "专业术语" -> 公共(信息/环境/容器)
                "作用" -> servletContext作为一个中间通道让servlet和tomcat服务器进行交互,它管理着当前WEB应用,并且为旗下所有servlet对象提供数据共享
            ServletContext怎么产生:
                Tomcat服务器的webapps中存在着多个WEB应用,它们在内存中也肯定是不同的内存块中放着,
                当每一个WEB应用启动时,它会为每个WEB应用程序都创建一个对应的ServletContext对象,它代表当前WEB应用
         */
        ServletContext context = config.getServletContext();
        System.out.println(context); //tomcat提供的实现ServletContext接口的类是:org.apache.catalina.core.ApplicationContextFacade

        /*
            可以对本WEB应用的ServletContext公共容器里的数据进行CURD操作,本WEB应用其他的servlet对象都会知道
            setAttribute()    getAttribute()    removeAttribute()
            可以WEB-INF/web.xml用户为此ServletContext对象提供的初始化配置属性 <context-param>
         */
        context.setAttribute("attr1","I am an Attribute in this Webapp no change !");

        //该servlet服务类的别名
        System.out.println(config.getServletName());

        //获取WEB-INF/web.xml用户为当前servlet类提供的初始化配置信息 <init-param>
        Enumeration<String> initParameters = config.getInitParameterNames();
        while (initParameters.hasMoreElements()){
            String key = initParameters.nextElement();
            System.out.println("key:" + key + "--->" + "value:" + config.getInitParameter(key));
        }
        //获取WEB-INF/web.xml用户为此webapp应用提供的初始化配置信息 <context-param>
        Enumeration<String> initAttributes = context.getInitParameterNames();
        while (initAttributes.hasMoreElements()){
            String attr = initAttributes.nextElement();
            System.out.println("attr:" + attr + "--->" + "value:" + context.getInitParameter(attr));
        }

        System.out.println("init-complete");
    }

    /**
     * 浏览器每请求一次服务都会调用一次service()方法
     * @param servletRequest 浏览器请求信息,内部包含了请求行/请求头/请求体/一个socket 等等    <----   该对象由各大厂商服务器创建
     * @param servletResponse 服务器响应信息,内部返回的是响应行/响应头/响应体/与请求头同一个socket 等等 <----   该对象由各大厂商服务器创建
     */
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

        System.out.println(servletRequest); //tomcat提供的实现ServletRequest接口的类是:org.apache.catalina.connector.RequestFacade
        System.out.println(servletResponse); //tomcat提供的实现ServletResponse接口的类是:org.apache.catalina.connector.ResponseFacade

        System.out.println("service-processing");
    }

    /**
     * servlet 服务停止,生命周期的最后一步,并且仅执行一次
     */
    @Override
    public void destroy() {
        System.out.println("destroy");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }


    @Override
    public String getServletInfo() {
        return null;
    }
}
