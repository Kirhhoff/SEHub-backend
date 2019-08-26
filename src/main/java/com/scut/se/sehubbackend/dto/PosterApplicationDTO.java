package com.scut.se.sehubbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scut.se.sehubbackend.domain.activity.ActivityBasicInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PosterApplicationDTO {

    Long id;

    ActivityBasicInfo activityBasicInfo;//申请表的公有信息，包括{名称，地点，开始时间，结束时间}等等

    @JsonProperty("checkInfo")
    CheckInfoDTO checkInfoDTO;//审核、发起者相关信息

    Date deadline;//海报制作截止时间

    String propagandaTextRequirement;//宣传文字要求

    String posterSize;//海报大小

    @JsonProperty("activityThisBelongsTo")
    Long relatedActivity;//相关活动的id
}
