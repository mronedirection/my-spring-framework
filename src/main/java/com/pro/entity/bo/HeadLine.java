package com.pro.entity.bo;

import lombok.Data;

import java.util.Date;

/**
 * @author RenHao
 * @create 2023-08-14 20:43
 */
@Data
public class HeadLine {
    private Long lineId;
    private String lineName;
    private  String lineLink;
    private String lineImg;
    private Integer priority;
    private Integer enableStatus;
    private Date createTime;
    private Date lastEditTime;
}
