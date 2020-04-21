<%@ page language="java" import="java.util.*"  pageEncoding="UTF-8" isELIgnored="false" %>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html;" charset="UTF-8">
    <title>JSP Expression Language</title>
    <style>

    </style>
</head>

<body>
    <%--
     EL表达式:expression language 即支持JSP脚本元素中的表达式 <%= %>
     只能读取作用域中的数据不能写入数据，不支持控制语句，不能读取Jsp页面中定义的<% 局部变量; %>
     ${el } --- 表达式|算术运算符|关系运算符
     \${el } --- 原样输出
     ${MapsObj['key'] } --- 获取Map容器中的某个键对应的值
     ${Arrays[index] } --- 获取Array数组中的某下标对应的值
     ${Object.attribute|key } --- 获取某个对象中的属性或键值
     ${Scope.attribute } --- 从指定的域中查找属性Scope:"pageScope(默认)|requestScope|sessionScope|applicationScope"
     注:找得到返回,找不到返回空字符串
    --%>

    <pre>
    <h1>EL表达式:expression language 即支持JSP脚本元素中的表达式</h1>
    <%--
        几种获得请求的路径的方法
        <%= request.getContextPath() %>
        <%= ((HttpServletRequest)pageContext.getRequest()).getContextPath() %>
        ${pageContext.request.contextPath }
     --%>
    This web app‘s ContextPath by EL \${pageContext.request.contextPath } :<b>${pageContext.request.contextPath }</b>

    This web app‘s Request HeaderValues by EL \${headerValues }<b>
        Host: ${headerValues["host"][0] }
        User-Agent: ${headerValues["user-agent"][0] }
        Accept: ${headerValues["accept"][0] }
        Accept-Encoding: ${headerValues["accept-encoding"][0] }
        Accept-Language: ${headerValues["accept-language"][0] }
        Cookie: ${headerValues["cookie"][0] }
        </b>

    Arithmetic Expression by EL:<b>
       \${0 lt 1 && 0 < 1 } : ${0 lt 1 && 0 < 1 }
       \${0 le 1 && 0 <= 1 } : ${0 le 1 && 0 <= 1 }
       \${0 gt 1 && 0 > 1 } : ${0 gt 1 && 0 > 1 }
       \${0 ge 1 && 0 >= 1 } : ${0 ge 1 && 0 >= 1 }
       \${0 ne 1 && 0 != 1 } : ${0 ne 1 && 0 != 1 }
       \${0 eq 1 && 0 == 1 } : ${0 eq 1 && 0 == 1 }
       \${(true and true) && true } : ${(true and true) && true }
       \${(true or false) || false } : ${(true or false) || false }
       \${!true or (not false) } : ${!true or (not false) }
       </b>
    </pre>
</body>
</html>
