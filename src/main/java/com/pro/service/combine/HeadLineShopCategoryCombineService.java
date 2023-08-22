package com.pro.service.combine;

import com.pro.entity.dto.MainPageInfoDTO;
import com.pro.entity.dto.Result;

/**
 * @author RenHao
 * @create 2023-08-14 21:16
 */
public interface HeadLineShopCategoryCombineService {
    Result<MainPageInfoDTO> getMainPageInfo();
}
