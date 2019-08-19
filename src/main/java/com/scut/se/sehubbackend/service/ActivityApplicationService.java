package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.dao.activity.ActivityApplicationRepository;
import com.scut.se.sehubbackend.domain.activity.ActivityApplication;
import com.scut.se.sehubbackend.domain.member.Department;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.dto.ActivityApplicationDTO;
import com.scut.se.sehubbackend.security.ContextHelper;
import com.scut.se.sehubbackend.utils.CheckInfoUtil;
import com.scut.se.sehubbackend.utils.DTOUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityApplicationService {

    final ActivityApplicationRepository activityApplicationRepository;
    final DTOUtil dtoUtil;
    final ContextHelper<Member> contextHelper;
    final EtiquetteApplicationService etiquetteApplicationService;
    final HostApplicationService hostApplicationService;
    final LectureTicketApplicationService lectureTicketApplicationService;
    final PosterApplicationService posterApplicationService;
    final CheckInfoUtil checkInfoUtil;

    public ActivityApplicationService(ActivityApplicationRepository activityApplicationRepository, DTOUtil dtoUtil, ContextHelper<Member> contextHelper, EtiquetteApplicationService etiquetteApplicationService, HostApplicationService hostApplicationService, LectureTicketApplicationService lectureTicketApplicationService, PosterApplicationService posterApplicationService, CheckInfoUtil checkInfoUtil) {
        this.activityApplicationRepository = activityApplicationRepository;
        this.dtoUtil = dtoUtil;
        this.contextHelper = contextHelper;
        this.etiquetteApplicationService = etiquetteApplicationService;
        this.hostApplicationService = hostApplicationService;
        this.lectureTicketApplicationService = lectureTicketApplicationService;
        this.posterApplicationService = posterApplicationService;
        this.checkInfoUtil = checkInfoUtil;
    }

    //
//    @Autowired
//    private ActivityApplicationRepository activityApplicationRepository;
//
//    @Autowired
//    private EtiquetteApplicationService etiquetteApplicationService;
//
//    @Autowired
//    private HostApplicationService hostApplicationService;
//
//    @Autowired
//    private LectureTicketApplicationService lectureTicketApplicationService;
//
//    @Autowired
//    private PosterApplicationService posterApplicationService;
//
//    // DTO的目的是没有必要返回给前端礼仪申请等各种辅助信息，辅助信息可选展示即点击按钮再展示
//    @Transactional
//    public ActivityApplicationDTO save(ActivityApplicationDTO activityApplicationDTO) {
//        Long id = KeyUtil.genUniqueKey();
//
//        ActivityApplication activityApplication = new ActivityApplication();
//
//        activityApplication.setId(id);
//        BeanUtils.copyProperties(activityApplicationDTO, activityApplication);
//
//
//        return activityApplicationDTO;
//    }
//

    /**
     * 获取当前用户所在部门的所有申请表
     * @return 当前用户所在部门的所有申请表
     */
    public List<ActivityApplicationDTO> getAllActivityApplicationOfCurrentDepartment() {
        Member currentMember= contextHelper.getCurrentPrincipal();
        Department departmentOfCurrentMember=currentMember.getDepartment();
        List<ActivityApplication> activityApplications=activityApplicationRepository.findAllByDepartment(departmentOfCurrentMember);
        return dtoUtil.toDTO(activityApplications);
    }

    /**
     * 根据请求的数据创建一张
     * @param activityDTO 请求中包含的DTO请求表
     */
    public void create(ActivityApplicationDTO activityDTO){
        ActivityApplication activityApplication=ActivityApplication.builder()
                .activityBasicInfo(activityDTO.getActivityBasicInfo())
                .activitySupplementaryInfo(activityDTO.getActivitySupplementaryInfo())
                .checkInfo(checkInfoUtil.initialCheckInfo())
                .build();
        if (activityDTO.getEtiquetteApplication()!=null)
            activityApplication.setEtiquetteApplication(
                    etiquetteApplicationService.toDO(
                            activityDTO.getEtiquetteApplication(),
                            activityApplication
                    )
            );
        if (activityDTO.getHostApplication()!=null)
            activityApplication.setHostApplication(
                    hostApplicationService.toDO(
                            activityDTO.getHostApplication(),
                            activityApplication
                    )
            );
        if (activityDTO.getLectureTicketApplication()!=null)
            activityApplication.setLectureTicketApplication(
                    lectureTicketApplicationService.toDO(
                            activityDTO.getLectureTicketApplication(),
                            activityApplication
                    )
            );
        if (activityDTO.getPosterApplication()!=null)
            activityApplication.setPosterApplication(
                    posterApplicationService.toDO(
                            activityDTO.getPosterApplication(),
                            activityApplication
                    )
            );
        activityApplicationRepository.saveAndFlush(activityApplication);
    }

//
//    public ActivityApplicationDTO findById(Long id) {
//        ActivityApplication activityApplication = activityApplicationRepository.findById(id).orElse(null);
//
//        ActivityApplicationDTO activityApplicationDTO = new ActivityApplicationDTO();
//
//        BeanUtils.copyProperties(activityApplicationDTO, activityApplication);
//
//        return activityApplicationDTO;
//    }
//
//    public EtiquetteApplication findEtiquetteApplicationById(Long id) {
//        return etiquetteApplicationService.findById(id);
//    }
//
//    public HostApplication findHostApplicationById(Long id) {
//        return hostApplicationService.findById(id);
//    }
//
//    public LectureTicketApplication findLectureTicketApplicationById(Long id) {
//        return lectureTicketApplicationService.findById(id);
//    }
//
//    public PosterApplication findPosterApplicationById(Long id) {
//        return posterApplicationService.findById(id);
//    }
//
//    @Transactional
//    public ActivityApplicationDTO pass(ActivityApplicationDTO activityApplicationDTO) {
//        ActivityApplication activityApplication = new ActivityApplication();
//
//        //判断审核状态
//        if (!activityApplicationDTO.getCheckStatus().equals(CheckStatusEnum.WAIT.getCode())) {
//            log.error("【审核通过】活动申请状态不正确, activityId={}, activityCheckStatus={}", activityApplicationDTO.getId(), activityApplicationDTO.getCheckStatus());
//            throw new ActivityApplicationException(ResultEnum.ACTIVITY_APPLICATION_STATUS_ERROR);
//        }
//
//        //修改审核状态
//        activityApplicationDTO.setCheckStatus(CheckStatusEnum.PASS.getCode());
//        BeanUtils.copyProperties(activityApplicationDTO, activityApplication);
//        ActivityApplication updateResult = activityApplicationRepository.save(activityApplication);
//        if (updateResult == null) {
//            log.error("【审核通过】更新失败, activityApplication={}", activityApplication);
//            throw new ActivityApplicationException(ResultEnum.ACTIVITY_APPLICATION_UPDATE_FAILED);
//        }
//
//        return activityApplicationDTO;
//    }
//
//    @Transactional
//    public ActivityApplicationDTO nopass(ActivityApplicationDTO activityApplicationDTO) {
//        ActivityApplication activityApplication = new ActivityApplication();
//
//        //判断审核状态
//        if (!activityApplicationDTO.getCheckStatus().equals(CheckStatusEnum.WAIT.getCode())) {
//            log.error("【审核不通过】活动申请状态不正确, activityId={}, activityCheckStatus={}", activityApplicationDTO.getId(), activityApplicationDTO.getCheckStatus());
//            throw new ActivityApplicationException(ResultEnum.ACTIVITY_APPLICATION_STATUS_ERROR);
//        }
//
//        //修改审核状态
//        activityApplicationDTO.setCheckStatus(CheckStatusEnum.NOPASS.getCode());
//        BeanUtils.copyProperties(activityApplicationDTO, activityApplication);
//        ActivityApplication updateResult = activityApplicationRepository.save(activityApplication);
//        if (updateResult == null) {
//            log.error("【审核不通过】更新失败, activityApplication={}", activityApplication);
//            throw new ActivityApplicationException(ResultEnum.ACTIVITY_APPLICATION_UPDATE_FAILED);
//        }
//
//        return activityApplicationDTO;
//    }
}
