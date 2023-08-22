package com.pro.entity.dto;

import com.pro.entity.bo.HeadLine;
import com.pro.entity.bo.ShopCategory;
import lombok.Data;

import java.util.List;

/**
 * @author RenHao
 * @create 2023-08-14 21:17
 */
@Data
public class MainPageInfoDTO {
    private List<HeadLine> headLineList;
    private List<ShopCategory> shopCategoryList;
}
