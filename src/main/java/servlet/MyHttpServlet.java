package servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 缺省适配器模式
 * sun 公司 已经提供该类
 * @see javax.servlet.http.HttpServlet
 *
 * HttpServlet extends javax.servlet.GenericServlet implements Servlet
 *             其封装了许多http请求会用到的方法
 */
public abstract class MyHttpServlet extends MyGenericServlet {

    /**
     * 不用abstract修饰,但是子类如果没有重写此方法就自动返回错误
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String protocol = req.getProtocol();
        String msg = "http.method_post_not_supported";
        if (protocol.endsWith("1.1")) {
            resp.sendError(405, msg);
        } else {
            resp.sendError(400, msg);
        }
    }

    /**
     * 不用abstract修饰,但是子类如果没有重写此方法就自动返回错误
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String protocol = req.getProtocol();
        String msg = "http.method_get_not_supported";
        if (protocol.endsWith("1.1")) {
            resp.sendError(405, msg);
        } else {
            resp.sendError(400, msg);
        }
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        if (req instanceof HttpServletRequest && res instanceof HttpServletResponse) {
            HttpServletRequest request = (HttpServletRequest)req; // 因为 HttpServletRequest extends ServletRequest
            HttpServletResponse response = (HttpServletResponse)res; // 因为 HttpServletResponse extends ServletResponse
            this.service(request, response);
        } else {
            throw new ServletException("non-HTTP request or response");
        }
    }

    /**
     * 根据请求方法,分别调用相应的响应方法,简化http开发
     */
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestMethod = request.getMethod();
        //简易版
        if (requestMethod.equals("GET")) doGet(request,response);
        else if (requestMethod.equals("POST")) doPost(request,response);
    }

}

