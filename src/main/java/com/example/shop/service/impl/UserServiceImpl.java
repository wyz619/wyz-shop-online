package com.example.shop.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.shop.common.exception.ServerException;
import com.example.shop.common.utils.AliyunResource;
import com.example.shop.common.utils.FileResource;
import com.example.shop.common.utils.GeneratorCodeUtils;
import com.example.shop.common.utils.JWTUtils;
import com.example.shop.convert.UserConvert;
import com.example.shop.entity.User;
import com.example.shop.mapper.UserMapper;
import com.example.shop.query.UserloginQuery;
import com.example.shop.service.RedisService;
import com.example.shop.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shop.vo.LoginResultVO;
import com.example.shop.vo.UserTokenVO;
import com.example.shop.vo.UserVO;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.server.ServerCloneException;
import java.util.UUID;

import static com.example.shop.Constant.APIConstant.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ycshang
 * @since 2023-11-07
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final RedisService redisService;
    private final FileResource fileResource;
    private final AliyunResource aliyunResource;

    @Override
    public LoginResultVO login(UserloginQuery query) {
        //  1、获取openId
        String url = "https://api.weixin.qq.com/sns/jscode2session?" +
                "appid=" + APP_ID +
                "&secret=" + APP_SECRET +
                "&js_code=" + query.getCode() +
                "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String openIdResult = restTemplate.getForObject(url, String.class);
        if (StringUtils.contains(openIdResult, WX_ERR_CODE)) {
            throw new ServerException("openid获取失败" + openIdResult);
        }
//
        JSONObject jsonObject = JSON.parseObject(openIdResult);
        String openid = jsonObject.getString(WX_OPENID);
        User user = baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getOpenId, openid));
        //
        if (user == null) {
            user = new User();
            String account = "用户" + GeneratorCodeUtils.generateCode();
            user.setAccount(account);

            user.setAvatar(DEFAULT_AVATAR);
            user.setNickname(account);
            user.setOpenId(openid);
            user.setMobile(".");
            baseMapper.insert(user);
        }
        LoginResultVO userVO = UserConvert.INSTANCE.convertToLoginResultVO(user);

        //
        UserTokenVO tokenVO = new UserTokenVO(userVO.getId());
        String token = JWTUtils.generateToken(JWT_SECRET, tokenVO.toMap());
        redisService.set(APP_NAME + userVO.getId(), token, APP_TOKEN_EXPIRE_TIME);
        userVO.setToken(token);
        return userVO;

    }

    @Override
    public User getUserInfo(Integer userId) throws ServerCloneException {
        User user = baseMapper.selectById(userId);
        if (user == null) {
            throw new ServerCloneException("用户不存在");
        }
        return user;
    }


    @Override
    public UserVO editUserInfo(UserVO userVO) {
        User user = baseMapper.selectById(userVO.getId());
        if (user == null) {
            throw new ServerException("用户不存在");
        }
        User userConvert = UserConvert.INSTANCE.convert(userVO);
        updateById(userConvert);
        return userVO;
    }

    @Override
    public String editUserAvatar(Integer userId, MultipartFile file) {
        String endpoint = fileResource.getEndpoint();
        String accessKeyId = aliyunResource.getAccessKeyId();
        String accessKeySecret = aliyunResource.getAccessKeySecret();

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String filename = file.getOriginalFilename();

        assert filename != null;
        String[] fileNameArr = filename.split("\\.");
        String suffix = fileNameArr[fileNameArr.length - 1];

        String uploadFileName = fileResource.getObjectName() + UUID.randomUUID() + "." + suffix;

        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            throw new ServerException("上传失败");
        }

        ossClient.putObject(fileResource.getBucketName(), uploadFileName, inputStream);


        ossClient.shutdown();

        User user = baseMapper.selectById(userId);
        if (user == null) {
            throw new ServerException("用户不存在");
        }
        uploadFileName = fileResource.getOssHost() + uploadFileName;
        user.setAvatar(uploadFileName);
        baseMapper.updateById(user);
        return uploadFileName;
    }
}





