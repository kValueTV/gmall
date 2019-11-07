package com.kvalue.gmall.service;

import com.kvalue.gmall.bean.UmsMember;
import com.kvalue.gmall.bean.UmsMemberReceiveAddress;
import com.kvalue.gmall.bean.UmsMember;
import com.kvalue.gmall.bean.UmsMemberReceiveAddress;

import java.util.List;

public interface UserService {
    List<UmsMember> getAllUser();

    List<UmsMemberReceiveAddress> getRecevieAddressesByMemberId(String memberId);
}
