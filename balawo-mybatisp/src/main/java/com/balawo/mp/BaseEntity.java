package com.balawo.mp;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author yan
 * @date 2022-06-30
 */
@Data
public class  BaseEntity implements Serializable {
    private static final Long serialVersionUID = 1L;

    /** 主键ID **/
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /** 创建时间 **/
    @TableField(fill = FieldFill.INSERT)
    private Timestamp createdAt;

//    /** 更新人 **/
//    @TableField(fill = FieldFill.INSERT_UPDATE)
//    protected Integer updatedBy;

    /** 更新时间 **/
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Timestamp updatedAt;

    /**
     * 是否删除
     */
//    @TableLogic(delval = "1", value = "0")
//    private Integer deletedAt;

    //LocalDateTime
    @TableLogic(delval = "now()", value = "null")
    private LocalDateTime deleteAt;
}

