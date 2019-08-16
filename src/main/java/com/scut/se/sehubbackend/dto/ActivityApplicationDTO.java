package com.scut.se.sehubbackend.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.scut.se.sehubbackend.domain.activityN.ActivityMainInfo;
import com.scut.se.sehubbackend.domain.activityN.ActivitySupplementaryInfo;
import com.scut.se.sehubbackend.enumeration.CheckStatusEnum;
import com.scut.se.sehubbackend.utils.Date2LongSerializer;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
@Getter
public class ActivityApplicationDTO {

    private String id;

    private ActivityMainInfo activityMainInfo;

    private ActivitySupplementaryInfo activitySupplementaryInfo;

    private boolean ifEtiquetteApplication;

    private boolean ifHostApplication;

    private boolean ifLectureApplication;

    private boolean ifPosterApplication;

    private Integer checkStatus = CheckStatusEnum.WAIT.getCode();

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date submissionDate;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date checkDate;

    private String CheckFeedback;

    // member的数据表保存似乎有BUG，而且不知道该数据是不是由前端传过来，似乎涉及到认证和权限管理，暂时不处理
}
