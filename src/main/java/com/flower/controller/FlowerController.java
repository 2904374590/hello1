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

import com.flower.entity.Flower;
import com.flower.entity.Sort;
import com.flower.entity.User;
import com.flower.service.FlowerService;
import com.flower.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/Flower")
public class FlowerController {
    @Autowired
    private FlowerService flowerService;
    @Value("${photo.file.dir}")
    private String realPath;
    /*后台页面====================================================================================================*/
    //通过鲜花名搜索鲜花（搜索框）模糊搜索
    @RequestMapping("/flowerList")
    public String findSortByFname(String fname,Integer sid,Model model){
        //通过鲜花名搜索鲜花,由于是模糊搜索，所以返回的是一个list！
        List<Flower> flowers = flowerService.findFlowerByFname(fname,sid);
        model.addAttribute("flowers",flowers);
        return "main/sort";
    }

    //添加鲜花
    @RequestMapping("/addFlower")
    public String addFlower(Flower flower, MultipartFile img) throws IOException {
            String originalFilename = img.getOriginalFilename();
            String newFileName = upLoadPhoto(img, originalFilename);
            flower.setFimage(newFileName);
            //添加新的鲜花
            flowerService.addFlower(flower);
            Integer sid = flower.getSid();
            String b ="redirect:/Flower/detail?sid="+sid;
            return b;
    }

    //头像方法
    public String upLoadPhoto(MultipartFile img, String originalFilename) throws IOException {
        String fileNamePrefix = new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date());//前缀
        String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = fileNamePrefix+fileNameSuffix;
        //上传新的头像
        img.transferTo(new File(realPath,newFileName));
        return newFileName;
    }

    //通过fid跳转到修改flower的界面
    @RequestMapping("/upFlower")
    public String updateByFid(Integer fid,Model model,HttpSession session){
        //前端传来fid，然后通过fid查询flower
        Flower flower = flowerService.findFlowerByFid(fid);
        session.setAttribute("flower",flower);
        model.addAttribute("flower",flower);
        return "main/updateFlower";
    }

    //更新Flower
    @RequestMapping("/updateFlower")
    public String updateFlower(Flower flower,Model model,MultipartFile img,HttpSession session) throws IOException {
        boolean notEmpty = !img.isEmpty();
        Flower flowerList = (Flower) session.getAttribute("flower");
        //获取旧头像
        String oldFimage = flowerList.getFimage();
        //如果不上传新头像，依旧选择旧头像
        flowerList.setFimage(oldFimage);
        if (notEmpty){
            //删除老的头像，上传新的头像，根据原来的头像名字返回file文件，进而可以删除文件
            File file = new File(realPath,oldFimage);
            //删除文件
            if (file.exists()) file.delete();
            //处理头像的上传&修改文件名称
            String originalFilename = img.getOriginalFilename();
            String newFileName = upLoadPhoto(img, originalFilename);
            flower.setFimage(newFileName);
        }
        //如果没传头像的话，直接修改鲜花的信息，将修改后的flower放入model中
        model.addAttribute("flower",flower);
        flowerService.updateFlower(flower);
        Integer sid = flower.getSid();
        String d = "redirect:/Flower/detail?sid="+sid;
        return d;
    }

    //通过fid删除鲜花
    @RequestMapping("/deleteByFid")
    public String deleteFlower(Integer fid){
        Flower flowerByFid = flowerService.findFlowerByFid(fid);
        Integer sid = flowerByFid.getSid();
        flowerService.deleteByFid(fid);
        //根据fid查询sid（注意：一个sid对应多个fid）
        String a ="redirect:/Flower/detail?sid="+sid;
        return a;
    }

   //通过分类名查询分类（搜索框）
   @RequestMapping("/snameList")
   public String findSortBySname(String sname,Model model){
       List<Sort> sorts = flowerService.queryByUsername(sname);
       model.addAttribute("sortList",sorts);
       return "main/SortClassification";
   }

    //修改分类名称
    @RequestMapping("/updateSname")
    public String updateBySid(Sort sort,Model model){
        //通过sort对象修改该分类名称
        //修改类别名的同时要修改里面所有鲜花的类别！！！
        try {
            flowerService.updateSname(sort);
        } catch (RuntimeException e) {
            e.printStackTrace();
            String msg = "已有该分类，请重新修改";
            model.addAttribute("msg",msg);
            return "main/index";
        }
        return "redirect:/Flower/lists";
    }

   //通过sid跳转到修改sname的界面
    @RequestMapping("/up")
    public String up(Integer sid,Model model){
        //前端传来sid，然后通过sid查询sort
        Sort sort= flowerService.findSortBySid(sid);
        model.addAttribute("sort",sort);
        return "main/updateSort";
    }

    //通过sid删除分类 thymeleaf
    @RequestMapping("/deleteBySid")
    public String deleteSort(Integer sid){
        flowerService.deleteBySid(sid);
        //删除该分类要删除该分类下的所有鲜花(根据sid删除鲜花)
        flowerService.deleteFlowerBySid(sid);
       return "redirect:/Flower/lists";
    }

    //在分类界面显示所有的鲜花信息
    @RequestMapping("/detail")
    public String sortList(Integer sid, Model model, HttpSession session){
        //根据sid查询鲜花的所有信息,一个sid对应多个鲜花，所以是list类型
        List<Flower> flowers = flowerService.findFlowerBySid(sid);
        model.addAttribute("flowers",flowers);
        Sort sort = flowerService.findSortBySid(sid);
        String sname = sort.getSname();
        session.setAttribute("Sname",sname);
        session.setAttribute("Sid",sid);
        return "main/sort";
    }

    //显示鲜花的所有类别
    @RequestMapping("lists")
    public String sortLists(Model model){
        //从数据库中取数据
        List<Sort> sortList = flowerService.lists();
        model.addAttribute("sortList",sortList);
        return "main/SortClassification";
    }

    //添加鲜花分类
    @RequestMapping("addSort")
    public String addSort(String sname,Model model){
        try {
            flowerService.addSort(sname);
        } catch (RuntimeException e) {
            e.printStackTrace();
            String msg = "已有该分类，请重新添加!!!";
            model.addAttribute("msg",msg);
            return "main/index";
        }
        return "redirect:/Flower/lists";
    }

    /*前台页面====================================================================================================*/
    //展示该鲜花的所有信息
    @RequestMapping("/con")
    public String con(Integer fid,Model model){
        Flower flowerByFid = flowerService.findFlowerByFid(fid);
        model.addAttribute("flowerByFid",flowerByFid);
        return "herb_01";
    }

    //查询鲜花(模糊查询出所有鲜花，不分类别)
    @RequestMapping("/queryFlowers")
    public String queryFlowers(String fname,Model model){
        List<Flower> flowerLists = flowerService.qureyFlowers(fname);
        model.addAttribute("flowerLists",flowerLists);
        return "Flowers";
    }

    //展示所有鲜花
    @RequestMapping("/herb")
    public String herb(Model model,Integer sid){
        //查询该分类下的所有鲜花
        List<Flower> flowerLists = flowerService.findFlowerBySid(sid);
        Sort sort = flowerService.findSortBySid(sid);
        model.addAttribute("s",sort);
        /*List<Flower> flowerLists =  flowerService.qureyFlowers();*/
       /* List<Sort> lists = flowerService.lists();*/
        model.addAttribute("flowerLists",flowerLists);
        return "herb";
    }

    //跳转到首页，不然首页的内容显示不出来
    @RequestMapping("home")
    public String home(Model model){
        //查询所有的鲜花分类
        List<Sort> lists = flowerService.lists();
        model.addAttribute("lists",lists);
        //查询所有的鲜花
        List<Flower> flowers = flowerService.queryFlower();
        model.addAttribute("flowers",flowers);
        return "homePage";
    }
}
