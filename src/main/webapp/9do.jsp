<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html;" charset="UTF-8">
    <title>JSP 9 Default Object</title>
    <style>
        .important{
            font-size:20px;
            color:red;
        }
    </style>
</head>
<body>

<h2>JSP 9大内置对象</h2>
<p>这些对象是先前定义于每个jsp相应的java servlet 类的_jspService方法源码中的</p>
@see  </br>
public void _jspService                                                                                         </br>
(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)    </br>
            throws java.io.IOException, javax.servlet.ServletException {                                        </br>
<pre>
        final javax.servlet.jsp.PageContext pageContext;
        javax.servlet.http.HttpSession session = null;
        java.lang.Throwable exception = org.apache.jasper.runtime.JspRuntimeLibrary.getThrowable(request);
        if (exception != null) {
           response.setStatus(javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        final javax.servlet.ServletContext application;
        final javax.servlet.ServletConfig config;
        javax.servlet.jsp.JspWriter out = null;
        final java.lang.Object page = this;
        javax.servlet.jsp.JspWriter _jspx_out = null;
        javax.servlet.jsp.PageContext _jspx_page_context = null;
</pre>

<pre class="important">
        Ⅰ:四大作用域
            1.final java.lang.Object page = this;
                --表示: 当前类引用
                --作用范围: 当前页面
            2.final javax.servlet.http.HttpServletRequest request
                --表示: 请求
                --作用范围: 一次请求(转发)
            3.javax.servlet.http.HttpSession session = null;
                --表示: 会话
                --作用范围: 一次会话
            4.final javax.servlet.ServletContext application;
                --表示: 当前web项目上下文
                --作用范围: 整个项目
        Ⅱ:其他
            5.final javax.servlet.jsp.PageContext pageContext;
                --表示: 当前JSP页面的上下文,它相当于页面中所有其他对象功能的集大成者,可以用它访问本页中所有的其他对象
                --作用范围: 当前页面
                [
                  pageContext的findAttribute(String key)方法会依次从四个作用域去找"即(page-->request-->session-->application)"
                  好处就是不用指定作用域的类型,只要这四个作用域中有指定的key,就肯定可以得到其对应的value
                ]
            6.final javax.servlet.ServletConfig config;
                --表示: 当前servlet的配置,因为JSP本质就是Servlet
                --作用范围: 整个项目
            7.final javax.servlet.http.HttpServletResponse response
                --表示: 响应
                --作用范围: 一次响应
            8.javax.servlet.jsp.JspWriter out = null;
                --表示: 向客户输出数据的输出流
                       管理服务输出缓冲区
                       内部使用PrintWriter,即 (out == response.getWriter()) 结果为 true
                       可以通过page指令的buffer指定缓冲大小
                --注意: out对象和PrintWriter对象同时使用时候,PrintWriter的数据优先于out输出,<a target="_blank" href="https://blog.csdn.net/zhd_superstar/article/details/6588222">详情点击</a>
            9.java.lang.Throwable exception = org.apache.jasper.runtime.JspRuntimeLibrary.getThrowable(request);
                --表示: 捕获JSP页面异常的对象
                       JSP页面必须指定 < %@ page isErrorPage="true" % > 才会出现此对象
</pre>
}
</body>
</html>