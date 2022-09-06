package com.balawo.mp.entity;

import com.balawo.mp.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author yan
 * @since 2022-07-01
 */
@Data
@Getter
@Setter
@TableName("tags")
@ApiModel(value = "Tags对象", description = "")
public class Tags extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("标签名称")
    @TableField("name")
    private String name;


}
