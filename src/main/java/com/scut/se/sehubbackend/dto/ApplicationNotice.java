package com.scut.se.sehubbackend.dto;

import com.scut.se.sehubbackend.domain.activity.ActivityBasicInfo;
import lombok.Builder;
import lombok.Data;

/**
 * <p>关于申请表的通知，每个申请表对应一条</p>
 * <p>这个通知相当于申请表的一个shortcut，所以只列出了大家共有的部分，设置一个{@link ApplicationNotice#type}字段来</p>
 * <p>来标志这个申请表的类型</p>
 * <p>在进行前后端通信时，前端可以根据id和type去请求申请表的完整内容</p>
 * <p>而在一开始获取这些申请表通知时，返回的是申请表通知的数组，其中的每个ApplicationNotice对象可以是相同类型的，可以是不同类型的</p>
 * <p>因为不同类型是根据{@link ApplicationNotice#type}字段来标志的，所以不会导致不同类型表的Notice的json结构不同</p>
 * <p></p>
 * <p>申请表的共有信息见{@link com.scut.se.sehubbackend.domain.Application}</p>
 */
@Builder
@Data
public class ApplicationNotice {

    Long id;//申请表的id

    String type;//申请表的具体类型

    ActivityBasicInfo activityBasicInfo;//对应申请表的该字段

    CheckInfoDTO checkInfoDTO;//对应申请表的该字段（已经转化为DTO）
}
