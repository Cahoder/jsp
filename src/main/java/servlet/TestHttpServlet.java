package servlet;


import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.zip.GZIPOutputStream;

/**
 * 测试 HttpServlet 对 HTTP 请求的响应 API Demo
 */
public class TestHttpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        servletAttribute();    //获取和修改 Servlet 配置的属性

//        responseByteStream(resp);   //响应字节流 编码问题
//        responseCharStream(resp);   //响应字符流 编码问题
//        System.out.println(new String(req.getParameter("name").getBytes(StandardCharsets.UTF_8),StandardCharsets.UTF_8));

//        redirect(resp);  //redirect重定向测试
//        forward(req, resp);   //forward重定向测试
//        include(req, resp);   //include重定向测试

//        compressResp(resp);  //数据压缩测试

//        IntervalRefresh(resp);  //定时刷新测试

//        FileDownload(resp); //文件下载测试

//        cookie(req,resp);   //cookie测试
//        session(req, resp); //session测试

        getRandomCode(req,resp);    //生成随机验证码
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        validateRCode(req, resp);
    }

    private void validateRCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setHeader("content-type","text/html;charset=UTF-8");

        String code = req.getParameter("code");
        HttpSession session = req.getSession();

        if (!code.equals("")){
            String vcode = session.getAttribute("validateCode").toString();

            if (code.equalsIgnoreCase(vcode)) resp.getWriter().write("验证成功!");
            else resp.getWriter().write("验证失败!");

        }else {
            resp.getWriter().write("验证码不可为空!");
        }

        session.removeAttribute("validateCode");

        resp.getWriter().write("------<a href='"+resp.encodeURL("/webapp1")+"'>三秒后自动跳转</a>");
        resp.setHeader("refresh","3;url='"+resp.encodeURL("/webapp1")+"'");
    }

    private void getRandomCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setHeader("content-type","text/html;charset=UTF-8");

        int width = 80;
        int codeLength = 4;
        int height = 30;

        Random semen = new Random();
        String validateCode = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);  //内存中创建图片
        Graphics graphics = image.getGraphics();    //获取画板
        graphics.setColor(Color.WHITE);
        graphics.fillRect(1,1,width-1,height-1);    //填充底色
        graphics.setFont(new Font("微软雅黑",Font.BOLD,height-(width/height)));    //设置字体


        //生成随机字符
        StringBuilder randomCode = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            int r = semen.nextInt(validateCode.length());
            String s = validateCode.substring(r, r + 1);

            graphics.setColor(new Color(semen.nextInt(255),semen.nextInt(255),semen.nextInt(255)));
            graphics.drawString(s,(width/codeLength*i)+(width/height),height-(width/height));       //画验证码

            randomCode.append(s);
        }

        //验证码 进行session 缓存
        HttpSession session = req.getSession();
        session.setAttribute("validateCode",randomCode.toString());

        //设置响应头数据类型
        resp.setContentType("image/jpeg");
        //输出到响应流
        ImageIO.write(image,"jpg",resp.getOutputStream());
    }

    private void session(HttpServletRequest req, HttpServletResponse resp) {
        //会话结束,sessionq其实没有失效,只是因为客户端发送的jsessionid改变了,即导致重新开辟了session内存
        HttpSession session = req.getSession(); //session 是基于cookie的技术,在服务端开辟一块内存保存,并用JSESSIONID标示
        System.out.println(session);    //tomcat实现session接口的类
        System.out.println(session.getId());    //JSESSIONID
        
        /* session.setMaxInactiveInterval(int interval);     //用户两次访问服务间隔时间超过设置的interval时间,session就会失效
           session.invalidate()    //直接清除session,使之失效
        */

        System.out.println(session.isNew());    //如果服务器还未创建过SESSION会自动创建一块内存保存,并在响应头通知浏览器保存JSESSIONID
        /* resp.addCookie(new Cookie("JSESSIONID",session.getId())); //这句话首次会自动执行
           类似于 Set-Cookie: JSESSIONID=FBEDF67F7902F820CD856A6100D82BBC; Path=/webapp1/; HttpOnly
        */

        /* 如果浏览器禁用了cookie,session内存块就会被不停创建
           可以要使用URL重写 -> http://localhost:8080/webapp1/servlet/test;jsessionid=xxx (xxx必须是已存在的JSESSIONID)
        */
        String url = resp.encodeURL("test");
        System.out.println(url); //判断浏览器是否禁用了cookie,禁用会在地址后自动加上';jsessionid=xxx' (地址写错不加上 ';jsessionid=xxx')
    }

    private void cookie(HttpServletRequest req,HttpServletResponse resp) throws UnsupportedEncodingException {

        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies)
            if (cookie.getName().equals("en")) return;

        //创建cookie
        Cookie en = new Cookie("en","caihongde");
        Cookie zh = new Cookie("zh", URLEncoder.encode("中文","UTF-8"));  //http 使用 iso-8859-1 传输数据,不支持中文,必须编码

        //cookie持久化,否则浏览器一关闭cookie就会消失
        en.setMaxAge(60*60);
        //上面等同于
        //resp.setHeader("Set-Cookie","en=caihongde; Expires=Fri, 21-Feb-2020 15:49:15 GMT");

        //设置cookie种植路径,否则父路径读取不到子路径种植的cookie(子可以读到父的)
        en.setPath("/webapp1");

        //服务器在响应头发起命令,让浏览器存储cookie（键值对形式存储）
        resp.addCookie(zh);
        resp.addCookie(en);
    }

    private void servletAttribute() {
        /** @see LifeCycleServlet .init()的setAttribute*/
        System.out.println(getServletContext().getAttribute("attr1"));
        getServletContext().setAttribute("attr1","I am an Attribute in this Webapp changed ");  //可以修改此属性,对其他的servlet都可见
    }

    private void responseByteStream(HttpServletResponse resp) throws IOException {
        /*知识点::
          所有在网络上传输的数据都是使用ISO 8859-1编码,它不支持数据内有中文
          content-type只是告知浏览器要用什么编码解析响应体数据;没写默认浏览器解析用GBK;*/
        resp.setHeader("content-type","text/html;charset=utf-8");
//        resp.getOutputStream().print("中文");  //HTTP Status 500 - Not an ISO 8859-1 character: 中
        //一个中文占两个字节,因此字节流不能够直接返回中文,要将字符变成字节并且要同content-type使用相同的编码
        resp.getOutputStream().write("中文".getBytes(StandardCharsets.UTF_8));
    }

    private void responseCharStream(HttpServletResponse resp) throws IOException {

        resp.setHeader("content-type","text/html");
        resp.setCharacterEncoding("GBK");  //设置返回数据的编码问题,并且在存在content-type响应头的情况下加上 charset='指定编码'

        resp.getWriter().write("中文");

    }

    private void FileDownload(HttpServletResponse resp) throws IOException {
        String path = this.getServletContext().getRealPath("");
        System.out.println(path);   //可以打印出发布的web项目的根目录 /home/cahoder/IdeaProjects/webapp1/src/main/webapp


        File file = new File(path + "/WEB-INF/web.xml");


        FileInputStream fin = new FileInputStream(file);


        byte[] buffer = new byte[1024];   //缓存读
        int len;
        OutputStream sout = resp.getOutputStream();  //字节流方式响应
        while ((len = fin.read(buffer)) > 0){
            sout.write(buffer,0,len);
        }

        resp.setHeader("content-disposition","attachment;filename=" + file.getName());
        resp.setHeader("content-type","application/octet-stream;charset=utf-8");

        sout.close();
        fin.close();

    }

    private void IntervalRefresh(HttpServletResponse resp) throws IOException {
        resp.setHeader("refresh","5;url='http://localhost:8080/webapp1/servlet/res'");
        resp.getWriter().write("Interval refresh !");   //字符流方式响应
    }

    private static void compressResp(HttpServletResponse resp) throws IOException {
        StringBuilder output = new StringBuilder("abc");
        for (int i = 0; i < 30000; i++) output.append("efg");
        System.out.println("压缩前数据原始大小:" + output.toString().getBytes().length);

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        GZIPOutputStream gout = new GZIPOutputStream(bout);   //使用gzip流压缩字符串
        gout.write(output.toString().getBytes());
        gout.close();

        resp.setHeader("Content-encoding","gzip");
        resp.setHeader("Content-length",bout.toByteArray().length + "");
        resp.getOutputStream().write(bout.toByteArray());  //写入响应体
    }

    private void include(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (getServletContext().getAttribute("include")==null){
            resp.getWriter().write("<p>---before include---</p>");
            getServletContext().setAttribute("include","<p>---including...---</p>");
            RequestDispatcher life = req.getRequestDispatcher("test"); //forward只能够重定向到同一个web应用内的其他servlet
            life.include(req,resp);
            resp.getWriter().write("<p>---after include---</p>"); //这里还是会继续输出
        }else resp.getWriter().write(getServletContext().getAttribute("include").toString()); //这里使用的流对象必须和调用方servlet相同
    }

    private void forward(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher life = req.getRequestDispatcher("life");  //forward只能够重定向到同一个web应用内的其他servlet,被请求的servlet获得req信息和此相同
        life.forward(req,resp);

        resp.getWriter().write("forwarded"); //这里是不会输出的了
    }

    private static void redirect(HttpServletResponse resp) throws IOException {
        resp.setStatus(302);   //设置响应状态码
        resp.setHeader("location","https://cn.bing.com/");   //设置重定向地址,可以任意地址,被请求的servlet获得req信息不一定和此相同
        //上面等价于
        //resp.sendRedirect("https://cn.bing.com/");

        resp.getWriter().write("redirected"); //这里是不会输出的了
    }

    @Override
    protected long getLastModified(HttpServletRequest req) {
        return super.getLastModified(req);
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doHead(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doOptions(req, resp);
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doTrace(req, resp);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        super.service(req, res);
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }
}
