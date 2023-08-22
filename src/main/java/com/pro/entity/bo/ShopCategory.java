package com.pro.entity.bo;

import lombok.Data;

import java.util.Date;

/**
 * @author RenHao
 * @create 2023-08-14 20:45
 */
@Data
public class ShopCategory {
    private Long shopCategoryId;
    private String shopCategoryName;
    private String shopCategoryDesc;
    private String shopCategoryImg;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
    private ShopCategory parent;
}
