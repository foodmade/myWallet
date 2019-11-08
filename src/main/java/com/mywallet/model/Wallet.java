package com.mywallet.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 以太坊账户表
 * </p>
 *
 * @author linapex
 */
@Entity
@Table(name = "t_wallet")
public class Wallet implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    /**
     * 用户id
     */
    @Column(name = "uid")
    private Integer uid;
    /**
     * 钱包名
     */
    @Column(name = "name")
    private String name;
    /**
     * 地址
     */
    @Column(name = "address")
    private String address;
    /**
     * 密码
     */
    @Column(name = "password")
    private String password;
    /**
     * 私钥
     */
    @Column(name = "private_key")
    private String privateKey;
    /**
     * key_store
     */
    @Column(name = "key_store")
    private String keyStore;
    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;
    /**
     * 更新时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;


    public Integer getId() {
        return id;
    }

    public Wallet setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUid() {
        return uid;
    }

    public Wallet setUid(Integer uid) {
        this.uid = uid;
        return this;
    }

    public String getName() {
        return name;
    }

    public Wallet setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Wallet setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Wallet setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public Wallet setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
        return this;
    }

    public String getKeyStore() {
        return keyStore;
    }

    public Wallet setKeyStore(String keyStore) {
        this.keyStore = keyStore;
        return this;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public Wallet setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
        return this;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public Wallet setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
        return this;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                ", id=" + id +
                ", uid=" + uid +
                ", name=" + name +
                ", address=" + address +
                ", password=" + password +
                ", privateKey=" + privateKey +
                ", keyStore=" + keyStore +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                "}";
    }
}
