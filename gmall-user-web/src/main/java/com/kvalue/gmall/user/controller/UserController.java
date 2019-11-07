package com.kvalue.gmall.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kvalue.gmall.service.UserService;
import com.kvalue.gmall.bean.UmsMember;
import com.kvalue.gmall.bean.UmsMemberReceiveAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {
    @Reference
    UserService userService;



    @RequestMapping("getRecevieAddressesByMemberId")
    @ResponseBody
    public List<UmsMemberReceiveAddress>  getRecevieAddressesByMemberId( String memberId) {
        List<UmsMemberReceiveAddress> umsMemberReceiveAddress =  userService.getRecevieAddressesByMemberId(memberId);
        return umsMemberReceiveAddress;
    }

    @RequestMapping("getAllUser")
    @ResponseBody
    public List<UmsMember>  getAllUser() {
        List<UmsMember> umsMembers =  userService.getAllUser();
        return umsMembers;
    }

    @RequestMapping("index")
    @ResponseBody
    public String index() {
        return "hello index";
    }
}
