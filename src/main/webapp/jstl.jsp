<%@ page language="java" import="java.util.*"  pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html;" charset="UTF-8">
    <title>JSP Standard Tag Library</title>
    <style>
        .ppt{
            font-size: 15px;
        }
    </style>
</head>

<body>
    <h1> JSP Standard Tag Library : 标准通用函数标签库 </h1>
    <pre class="ppt">
        简介:
            jstl + el 替代了传统在 jsp 页面中嵌入 java 程序的做法,提高程序的可读性和维护性
            jar包下载 https://tomcat.apache.org/download-taglibs.cgi
        标签库分类:
            核心core --- < %@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
            国际化i18n --- < %@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
            数据库sql --- < %@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
            xml --- < %@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="xml"%>
            函数标签 --- < %@ taglib uri="http://java.sun.com/jsp/jstl/fn" prefix="fn"%>
        使用步骤:
            1.导入jar包
            2.jsp文件中taglib标签声明使用 < %@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
            3.使用
        使用语法:
            set标签:用来将变量存储到jsp范围域或者javabean的属性
                格式一: <<span>c:set var="变量名" value="变量值" [scope="page(默认)|request|session|application"]><</span>/c:set>
                格式二: <<span>c:set target="Map|JavaBean" property="Map键|JavaBean属性" value="Map值|JavaBean属性值" [scope="page(默认)|request|session|application"]><</span>/c:set>
            remove标签:用来移除变量或者javabean的属性
                格式一: <<span>c:remove var="变量名" [scope="page(默认)|request|session|application"]><</span>/c:remove>
                格式二: <<span>c:remove target="Map" property="Map键" [scope="page(默认)|request|session|application"]><</span>/c:remove>
            out标签:用来输出变量
                格式一: <<span>c:out value="\${支持EL}" escapeXml="true(默认,H5标签原样)|false(H5标签执行)" default="默认值" ><</span>/c:out>
            if标签:判断变量,注意没有else标签
                格式一: <<span>c:if test="Boolean|\${支持EL}"><</span>/c:if>
            choose when otherwise标签:相当于switch判断变量
                格式一: <<span>c:choose>
                        <<span>c:when test="Boolean|\${支持EL}">
                            expression1
                        <</span>/c:when>
                        <<span>c:when test="Boolean|\${支持EL}">
                            expression2
                        <</span>/c:when>
                        ...
                        <<span>c:otherwise>
                            default expression
                        <</span>/c:otherwise>
                       <</span>/c:choose>
            foreach标签:进行循环遍历
                格式一: <<span>c:forEach items="遍历对象\${支持EL}" var="遍历出的每个对象" begin="开始位" end="结束位" step="步长" varStatus="" ><</span>/c:forEach>
                演示: This web app‘s Request HeaderValues by EL expression and JSTL forEach tag
                     <c:forEach items="${headerValues }" var="item" > ${item.key } : <c:forEach items="${item.value }" var="iitem" > ${iitem } </c:forEach>
                     </c:forEach>
            ***更多请<a target="_blank" href="https://how2j.cn/k/jsp/jsp-jstl/578.html#step1689">点击</a>
    </pre>
</body>
</html>
