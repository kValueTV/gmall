package com.kvalue.gmall.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kvalue.gmall.bean.UmsMember;
import com.kvalue.gmall.bean.UmsMemberReceiveAddress;
import com.kvalue.gmall.service.UserService;
import com.kvalue.gmall.user.mapper.UmsMemberReceiveAddressMapper;
import com.kvalue.gmall.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;
    @Override
    public List<UmsMember> getAllUser() {
//        List<UmsMember> umsMemberList= userMapper.selectAllUser();
        List<UmsMember> umsMemberList= userMapper.selectAll();
        return umsMemberList;
    }

    @Override
    public List<UmsMemberReceiveAddress> getRecevieAddressesByMemberId(String memberId) {
        UmsMemberReceiveAddress umsMemberReceiveAddress = new UmsMemberReceiveAddress();
        umsMemberReceiveAddress.setMemberId(memberId);
        List<UmsMemberReceiveAddress> umsMembedrReceiveAddresses = umsMemberReceiveAddressMapper.select(umsMemberReceiveAddress);

//        Example example = new Example(UmsMemberReceiveAddress.class);
//        example.createCriteria().andEqualTo("memberId",memberId);
//        List<UmsMemberReceiveAddress> umsMembedrReceiveAddresses = umsMemberReceiveAddressMapper.selectByExample(example);


        return umsMembedrReceiveAddresses;
    }


}
