package com.pro.service.solo;

import com.pro.entity.bo.HeadLine;
import com.pro.entity.dto.Result;

import java.util.List;

/**
 * @author RenHao
 * @create 2023-08-14 21:13
 */
public interface HeadLineService {
    Result<Boolean> addHeadLine(HeadLine headLine);
    Result<Boolean> removeHeadLine(int headLineId);
    Result<Boolean> modifyHeadLine(HeadLine headLine);
    Result<HeadLine> queryHeadLineById(int headLineId);
    Result<List<HeadLine>>queryHeadLine(HeadLine headLineCondition, int pageIndex, int pageSize);
}
