package com.ssaw.ssawauthenticatecenterservice.authentication.encoder;

import com.ssaw.commons.security.RsaUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Objects;

/**
 * @author HuSen
 * @date 2019/4/10 9:14
 */
public class RsaPasswordEncoder implements PasswordEncoder {

    private PublicKey publicKey;

    private PrivateKey privateKey;

    /**
     * 构造方法 初始化PublicKey 和 PrivateKey
     *
     * @param publicKeyPath  公钥文件位置
     * @param privateKeyPath 私钥文件位置
     */
    public RsaPasswordEncoder(String publicKeyPath, String privateKeyPath) {
        PublicKey publicKeyTemp = RsaUtil.getPublicKey(publicKeyPath);
        PrivateKey privateKeyTemp = RsaUtil.getPrivateKey(privateKeyPath);
        Assert.state(Objects.nonNull(publicKeyTemp) && Objects.nonNull(privateKeyTemp), "the publicKey or privateKey can't load");
        this.publicKey = publicKeyTemp;
        this.privateKey = privateKeyTemp;
    }

    /**
     * 加密密码
     *
     * @param charSequence 待加密的密码
     * @return 加密后的密码
     */
    @Override
    public String encode(CharSequence charSequence) {
        String encrypt = RsaUtil.encrypt(charSequence.toString(), publicKey);
        if (StringUtils.isBlank(encrypt)) {
            throw new IllegalArgumentException("bad password");
        }
        return encrypt;
    }

    /**
     * 明文密码是否与加密后的密码匹配
     *
     * @param rawPassword     明文密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String decrypt = RsaUtil.decrypt(encodedPassword, privateKey);
        return StringUtils.equals(rawPassword.toString(), decrypt);
    }
}