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
@TableName("article_tags")
@ApiModel(value = "ArticleTags对象", description = "")
public class ArticleTags extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("tag_id")
    private Long tagId;

    @TableField("article_id")
    private Long articleId;
}
