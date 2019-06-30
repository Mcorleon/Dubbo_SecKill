package com.tqh.api;

import com.tqh.model.Address;
import com.tqh.model.Result;
import com.tqh.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author Mcorleon
 * @Date 2019/2/20 16:34
 */
public interface UserSerive {
     User findUserByName(String name);

     Result userLogin(String id, String psw, HttpServletRequest request);

     User getCurrentUser();

    List<Address> getAddressByNickName(String nickName);

    Address getAddressByAddressID(String address_id);
}
