package com.scut.se.sehubbackend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ApplicationNoticeDTO {

    List<ActivityApplicationDTO> activityList;

    List<EtiquetteApplicationDTO> etiquetteList;

    List<HostApplicationDTO> hostList;

    List<LectureTicketApplicationDTO> lectureList;

    List<PosterApplicationDTO> posterList;
}
