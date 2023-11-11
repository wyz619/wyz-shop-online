package com.example.shop.service;

import com.example.shop.entity.UserShippingAddress;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shop.vo.AddressVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ycshang
 * @since 2023-11-07
 */
public interface UserShippingAddressService extends IService<UserShippingAddress> {

    Integer saveShippingAddress(AddressVO addressVO);
    Integer editSgippingAddress(AddressVO addressVO);

    Integer editShippingAddress(AddressVO addressVO);

    List<AddressVO> getList(Integer userId);
}
