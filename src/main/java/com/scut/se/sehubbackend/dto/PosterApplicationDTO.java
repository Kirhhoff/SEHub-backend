package com.scut.se.sehubbackend.dto;

import com.scut.se.sehubbackend.domain.activity.ActivityMainInfo;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PosterApplicationDTO {

    public static final String TYPE= PosterApplicationDTO.class.getTypeName();

    Long id;

    ActivityMainInfo activityMainInfo;//申请表的公有信息，包括{名称，地点，开始时间，结束时间}等等

    Date deadline;//海报制作截止时间

    String propagandaTextRequirement;//宣传文字要求

    String posterSize;//海报大小

    String type;//申请表类别（礼仪、主持人..）

    Boolean hasRelatedActivityApplication;//是否有相关的活动申请表
}
