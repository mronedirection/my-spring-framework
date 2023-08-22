package com.pro.service.solo;

import com.pro.entity.bo.ShopCategory;
import com.pro.entity.dto.Result;

import java.util.List;

/**
 * @author RenHao
 * @create 2023-08-14 21:14
 */
public interface ShopCategoryService {
    Result<Boolean> addShopCategory(ShopCategory shopCategory);
    Result<Boolean> removeShopCategory(int shopCategoryId);
    Result<Boolean> modifyShopCategory(ShopCategory shopCategory);
    Result<ShopCategory> queryShopCategoryById(int shopCategoryId);
    Result<List<ShopCategory>> queryShopCategory(ShopCategory shopCategoryCondition, int pageIndex, int pageSize);
}
