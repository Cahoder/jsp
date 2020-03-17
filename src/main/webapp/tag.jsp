<%--

《《《《《JSP 指令标签---page指令---全局任意位置都可以定义,所有的属性只能出现一次,除了import属性》》》》》
    格式: <%@ page ... %>
    pageEncoding: 该jsp文件编译成java servlet源码和字节码文件时使用的编码
    contentType: 该servlet响应浏览器数据内容和浏览器解析数据内容时使用的编码
    注:上面两个只有一个,就会一个当两个都用;两个都有时,各司其职;
    import: 导包,可以多次使用
    language: 后端语言
    info: 该页面备注信息,可通过Servlet.getServletInfo()方法来获得
    session="true|false": 该页面是否可以使用session会话
    isErrorPage="true|false": 该页面是否会产生错误信息,想要捕获错误必须写true
    errorPage="xxx.jsp": 捕获显示错误信息的页面文件,本页面必须要有 isErrorPage="true"
    isELIgnored="true|false": 该页面是否忽略EL表达式,web.xml的version < 2.3 默认值为true
    buffer: 指定响应输出流的缓冲区大小,即将输出流的数据写到缓冲区,达到目标缓冲大小才输出
    autoFlush: 指定输出流缓冲区的内容是否自动清除,默认值为true
    isThreadSafe: 指定该页面是否支持多个用户同时请求(即多线程同步请求),默认值为true
    extends: 定义此JSP页面所产生的Servlet是继承自哪个父类,该父类必须实现javax.servlet.jsp.HttpJspBase接口,默认值为HttpJspBase

《《《《《JSP 指令标签---include指令---静态包含其他文件页面,编译阶段执行---合并生成一个servlet类文件源码》》》》》
    格式: <%@ include file="xxx.jspf" %>
    file:需要包含进来的文件名字,是相对服务器的文件路径,jsp建议文件以 *.jspf 结尾

《《《《《JSP 指令标签---taglib指令---使得JSP页面中可以使用自定义的标签》》》》》
    格式: <%@ taglib uri="tagURI" prefix="prefix" %>
    uri: 指定自定义标签文件的路径,可以是绝对或者相对路径,也可以是标签库的描述文件
    prefix:指定标签的前缀,不可使用java保留关键字,如java,javax,jsp,servlet,sun等

《《《《《JSP 脚本标签》》》》》
    1.JSP 脚本标签 --- 声明类脚本标签
        格式:<%! java的方法或者字段 %>
        声明的字段是全局的变量,声明的方法转化成相应servlet类内的方法,作用范围整个jsp页面
    2.JSP 脚本标签 --- 表达式类脚本标签
        格式:<%= java的表达式 %>
        相当于在相应servlet类中添加 out.println('java的表达式');
    2.JSP 脚本标签 --- 程序片段类脚本标签
        格式:<% java的代码块 %>
        这种相当于直接在相应servlet类中添加源代码,使用范围是该代码块位置以后service方法内

《《《《《JSP 动作标签》》》》》
    1.forward: 请求转发动作标签,浏览器的地址不会改变
        <jsp:forward page="xxx.jsp">
            <jsp:param value="参数值" name="参数名"></jsp:param>
            <jsp:param value="参数值" name="参数名"></jsp:param>
            ...
        </jsp:forward>
    2.include: 动态包含标签,执行请求期执行,两个jsp页面是分别两个不同的实体对象/work文件夹里会生成两个servlet源码文件
        <jsp:include page="xxx.jsp" flush="true|false">
            <jsp:param value="参数值" name="参数名"></jsp:param>
            <jsp:param value="参数值" name="参数名"></jsp:param>
            ...
        </jsp:include>
    3.<jsp:useBean>  <jsp:setProperty>  <jsp:getProperty>  与使用JavaBean有关

《《《《《JSP For循环标签-性能差》》》》》
    <%for (String word : words) {%>
        <%=word%>
    <%}%>
--%>


<%@ page language="java" import="java.util.*"  pageEncoding="UTF-8" isELIgnored="false" %>
<% request.setCharacterEncoding("UTF-8"); %>   <%--防止中文参数乱码--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html;" charset="UTF-8">
    <title>JSP Tag</title>
    <style>

    </style>
</head>

<body>
    <%!
        public final static String name = "CAIHONGDE";
        public static String ppp(){return name;}
    %>
    <%= "<h2>MY NAME IS "+ ppp() +" AND MY OLD IS "+(Calendar.getInstance().get(Calendar.YEAR)-1998)+"</h2>" %>
    <% System.out.println(Calendar.getInstance().getTime());%>

    <xmp style="color:grey;"><%@ include file = "/WEB-INF/web.xml" %></xmp>

</body>
</html>