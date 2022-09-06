package com.balawo.mp.entity;

import com.balawo.mp.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@Getter
@Setter
@TableName("articles")
@ApiModel(value = "Articles对象", description = "")
public class Articles extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(" 文章标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("文章子标题")
    @TableField("subtitle")
    private String subtitle;

    @ApiModelProperty("文章内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("标题ID")
    @TableField("tag_id")
    private Long tagId;

    @ApiModelProperty("文章状态 1=开启,2=关闭")
    @TableField("status")
    private String status;

    @ApiModelProperty("标签名称")
    @TableField("name")
    private String name;
}
