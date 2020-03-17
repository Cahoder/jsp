<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html;" charset="UTF-8">
    <title>JSP Run LifeCycle</title>
    <style>
        .introduce{
            font-size:20px;
            color:red;
        }
    </style>
</head>

<body>
<xmp class="introduce">
    Jsp 全称是 Java Server Page 目的是可以在html代码中嵌套java代码
    执行过程如下:
    1.tomcat 服务器会拦截 *.jsp 文件
       可以去看 tomcatPath/conf/web.xml 中会有如下定义
           <servlet>
                   <servlet-name>jsp</servlet-name>
                   <servlet-class>org.apache.jasper.servlet.JspServlet</servlet-class>
                   注: JspServlet就是tomcat用于解析jsp文件生成servlet的引擎
           </servlet>
           <servlet-mapping>
               <servlet-name>jsp</servlet-name>
               <url-pattern>*.jsp</url-pattern>
               <url-pattern>*.jspx</url-pattern>
           </servlet-mapping>

    2.JspServlet引擎 就会在本地WebRoot下读取并分析该 *.jsp 文件,在 tomcatPath/work/ 下生成jsp文件相应的 *.java 文件
        例如： 当前jsp文件会生成jstl.java 文件

        public final class jstl_jsp extends org.apache.jasper.runtime.HttpJspBase
            implements org.apache.jasper.runtime.JspSourceDependent {           }

        @其中 public abstract class HttpJspBase extends HttpServlet {         }

    3.将 .java 文件编译生成相应的 .class 文件
    4.最后该Web应用的 ServletContext 引擎处理 .class 文件 进行输出
</xmp>
</body>
</html>