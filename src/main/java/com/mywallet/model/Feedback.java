package com.mywallet.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author linapex
 */
@Entity
@Table(name = "t_feedback")
public class Feedback implements Serializable {

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
     * 用户名
     */
    @Column(name = "username")
    private String username;
    /**
     * 反馈内容
     */
    @Column(name = "content")
    private String content;
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

    public Feedback setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Feedback setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Feedback setContent(String content) {
        this.content = content;
        return this;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public Feedback setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
        return this;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public Feedback setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
        return this;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", uid=" + uid +
                ", username='" + username + '\'' +
                ", content='" + content + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }
}
