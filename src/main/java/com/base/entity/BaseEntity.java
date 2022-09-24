package com.base.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author lxk
 * @date 2022/9/22 11:05
 */
@Data
@Accessors(chain = true)
public class BaseEntity {

    /**
     * 主键
     */
    @Id
    @Column(name = "id_")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 创建人
     */
    @Column(name = "creator_")
    private String creator;

    /**
     * 修改人
     */
    @Column(name = "rewriter_")
    private String rewriter;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
