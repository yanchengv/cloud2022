package com.balawo.mp.entity;

import com.balawo.mp.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("payment")
@ApiModel(value = "Payment对象", description = "")
public class Payment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("serial")
    private String serial;
}
