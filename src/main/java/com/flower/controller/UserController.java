package com.flower.controller;/*
 * ctrl+Alt+T    代码包围
 * ctrl+shift+/   多行
 * ctrl+/         单行
 * shift+f6       重命名
 * alt+enter      百能快捷键
 * ctrl+n         搜索类
 * alt+insert     构造方法
 * ctrl+alt+v     自动生成对象
 * ctrl+H         继承树
 * alt+enter      导包，自动修正代码，解决出现的任何问题，也可以生成返回值
 * */


import com.flower.entity.User;
import com.flower.service.FlowerService;
import com.flower.service.UserService;
import com.flower.utils.VerifyCodeUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FlowerService flowerService;
    @Value("${photo.file.dir}")
    private String realPath;

    /*后台页面====================================================================================================*/

    //通过用户名查询用户
    @RequestMapping("/unameList")
    public String findUserByUname(String uname,Model model){
        List<User> users = userService.queryByUsername(uname);
        model.addAttribute("userList",users);
        return "main/MemberInforation";
    }

    /*删除员工*/
    @RequestMapping("delete")
    public String delete(Integer uid){
        String photo = userService.findById(uid).getUphotos();
        userService.delete(uid);
        File file = new File(realPath, photo);
        if (file.exists()) file.delete();
        return "redirect:/user/userLists";
    }

    //展示所有用户信息
    @RequestMapping("/userLists")
    public String userLists(Model model){
        //从数据库取出值放入userList中
        List<User> userList = userService.lists();
        //将查询到的数据传入前端
        model.addAttribute("userList",userList);
        return "main/MemberInforation";
    }

    //管理员登录
    @RequestMapping("/loginMain")
    public String loginMain(String username,String password,Model model){
        String userName="admin";
        String passWord="123456";
        if (username.equals(userName)&&password.equals(passWord)){//判断字符串是否相等要用equals
            return "redirect:/main";
        }else{
            model.addAttribute("msg","请重新输入！！");
            //或者是main/logmain,重定向是重新跳转到已经配置了的视图，转发是跳转到templates下面的html
            return "main/logmain";
        }
    }

    /*前台页面====================================================================================================*/
    /*修改用户*/
    public String upLoadPhoto(MultipartFile img, String originalFilename) throws IOException {
        String fileNamePrefix = new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date());//前缀
        String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = fileNamePrefix+fileNameSuffix;
        //上传新的头像
        img.transferTo(new File(realPath,newFileName));
        return newFileName;
    }

    @RequestMapping("/update")
    public String update(User user,HttpSession session,MultipartFile img) throws IOException {
        //判断是否更新头像,空的返回false，不为空返回true
        boolean notEmpty = !img.isEmpty();
        //获取存在session中的loginUser信息
        User loginUser = (User) session.getAttribute("loginUser");
        //获取旧头像
        String oldPhotos = loginUser.getUphotos();
        //如果不上传新头像，依旧选择旧头像
        user.setUphotos(oldPhotos);
        if (notEmpty){
            //删除老的头像，上传新的头像,根据原来的头像名字返回file文件，进而可以删除文件
            File file = new File(realPath, oldPhotos);
            //删除文件
            if (file.exists()) file.delete();
            //处理头像的上传&修改文件名称
            String originalFilename = img.getOriginalFilename();
            String newFileName = upLoadPhoto(img, originalFilename);
            //修改员工新的头像名称
            user.setUphotos(newFileName);
        }
        //如果没传头像的话，直接修改用户的信息，将修改后的user放入session中的loginUser
        session.setAttribute("loginUser",user);
        userService.update(user);
        return "redirect:/user/lists";
    }

    /*点击头像显示用户现在的所有信息*/
    @RequestMapping("lists")
    public String lists(HttpSession session,Model model){
        //获取登录的用户信息
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser",loginUser);
        return "old_infor";
    }

    /*用户注销*/
    @RequestMapping("/logout")
    public String logout(){
        Subject currentSubject = SecurityUtils.getSubject();
        currentSubject.logout();
        return "redirect:/Flower/home";
    }

    /*用户登录*/
    @RequestMapping("login")
    public String login(String uname,String upwd,Model model){
        //也就是说这里的参数要和login中输入框中的name参数名字相同
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(uname, upwd);
        try {
            subject.login(token);
            return "redirect:/Flower/home";
        } catch (UnknownAccountException e) {
            model.addAttribute("msg","请输入正确的用户名");
            return "login";
        }
        catch (IncorrectCredentialsException e) {
            model.addAttribute("msg","密码错误");
            return "login";
        }
    }

    /*用户注册*/
    @RequestMapping("register")
    public String register(User user, String code, HttpSession session,MultipartFile img,Model model) throws IOException {
        try {
            //判断验证码是否相同,你怎样判断验证码是否相同，通过前端传来的code来比较生成验证码session中的验证码是否相同
            String sessionCode = session.getAttribute("code").toString();
            if (!sessionCode.equalsIgnoreCase(code)) throw new RuntimeException("您的验证码错误，请重新输入!");
            //只要通过上一行，那么验证码就是正确的,现在就是实现service层中的注册功能,就要导入service,实现自动装配
            //如果相同，那么就写业务层的注册功能，将前端传来的User对象传到后端
            if (img.isEmpty()){//img为空返回true,img不为空，返回false
                //走到这里，此时img为空
                user.setUphotos("1.jpg");
                userService.register(user);
            }else {
                String originalFilename = img.getOriginalFilename();
                //处理头像的上传&修改文件名称
                String newFileName = upLoadPhoto(img, originalFilename);
                //保存员工信息
                user.setUphotos(newFileName);
                userService.register(user);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return "redirect:/register";//失败返回注册页面
        }
        return "login";//成功返回登录页面
    }
    /*生成验证码，通过跳转试图刷新验证码*/
    @RequestMapping("generateImageCode")
    public void generateImageCode(HttpSession session, HttpServletResponse response) throws IOException {
        //生成数字
        String code = VerifyCodeUtils.generateVerifyCode(4);
        //传进session中才不会丢失
        session.setAttribute("code",code);
        //转换为图片,以流的方式 传给前端
        response.setContentType("image/png");
        ServletOutputStream os = response.getOutputStream();
        VerifyCodeUtils.outputImage(220,60,os,code);
    }
}
