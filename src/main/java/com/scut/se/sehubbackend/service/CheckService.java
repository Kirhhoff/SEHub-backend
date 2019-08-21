package com.scut.se.sehubbackend.service;

import com.scut.se.sehubbackend.domain.Application;
import com.scut.se.sehubbackend.domain.activity.*;
import com.scut.se.sehubbackend.domain.member.Member;
import com.scut.se.sehubbackend.enumeration.CheckStatusEnum;
import com.scut.se.sehubbackend.exception.CheckHasBeenOperatedException;
import com.scut.se.sehubbackend.exception.InvalidIdException;
import com.scut.se.sehubbackend.security.ContextHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CheckService {

    final ActivityApplicationService activityApplicationService;
    final ContextHelper<Member> contextHelper;
    final EtiquetteApplicationService etiquetteApplicationService;
    final HostApplicationService hostApplicationService;
    final LectureTicketApplicationService lectureTicketApplicationService;
    final PosterApplicationService posterApplicationService;

    public CheckService(ActivityApplicationService activityApplicationService, ContextHelper<Member> contextHelper, EtiquetteApplicationService etiquetteApplicationService, HostApplicationService hostApplicationService, LectureTicketApplicationService lectureTicketApplicationService, PosterApplicationService posterApplicationService) {
        this.activityApplicationService = activityApplicationService;
        this.contextHelper = contextHelper;
        this.etiquetteApplicationService = etiquetteApplicationService;
        this.hostApplicationService = hostApplicationService;
        this.lectureTicketApplicationService = lectureTicketApplicationService;
        this.posterApplicationService = posterApplicationService;
    }

    /**
     * <p>活动申请的审核</p>
     * <p>要求具备常委角色</p>
     * @param id 申请表id
     * @param passed 通过情况
     * @param feedback 反馈
     * @throws InvalidIdException id不存在
     * @throws CheckHasBeenOperatedException 申请表已经审核过
     */
    @PreAuthorize("hasRole('StandingCommittee')")
    public void activityCheck(Long id,boolean passed, String feedback) throws InvalidIdException, CheckHasBeenOperatedException {
        activityApplicationService.save(
                (ActivityApplication) applicationCheck(
                                activityApplicationService.findById(id),passed, feedback)
        );
    }

    /**
     * <p>礼仪申请的审核</p>
     * <p>要求具备外联部角色和礼仪权限</p>
     * @param id 申请表id
     * @param passed 通过情况
     * @param feedback 反馈
     * @throws InvalidIdException id不存在
     * @throws CheckHasBeenOperatedException 申请表已经审核过
     */
    @PreAuthorize("hasRole('Relation') and hasAuthority('Etiquette')")
    public void etiquetteCheck(Long id,boolean passed, String feedback) throws InvalidIdException, CheckHasBeenOperatedException {
        etiquetteApplicationService.save(
                (EtiquetteApplication)applicationCheck(
                        etiquetteApplicationService.findById(id),passed,feedback
                )
        );
    }

    /**
     * <p>主持人申请的审核</p>
     * <p>要求具备秘书部角色和主持人权限</p>
     * @param id 申请表id
     * @param passed 通过情况
     * @param feedback 反馈
     * @throws InvalidIdException id不存在
     * @throws CheckHasBeenOperatedException 申请表已经审核过
     */
    @PreAuthorize("hasRole('Secretary') and hasAuthority('Host')")
    public void hostCheck(Long id,boolean passed, String feedback) throws InvalidIdException, CheckHasBeenOperatedException {
        hostApplicationService.save(
                (HostApplication) applicationCheck(
                        hostApplicationService.findById(id),passed,feedback
                )
        );
    }

    /**
     * <p>讲座票申请的审核</p>
     * <p>要求具备调研部角色和讲座票权限</p>
     * @param id 申请表id
     * @param passed 通过情况
     * @param feedback 反馈
     * @throws InvalidIdException id不存在
     * @throws CheckHasBeenOperatedException 申请表已经审核过
     */
    @PreAuthorize("hasRole('Research') and hasAuthority('LectureTicket')")
    public void lectureTicketCheck(Long id,boolean passed, String feedback) throws InvalidIdException, CheckHasBeenOperatedException {
        lectureTicketApplicationService.save(
                (LectureTicketApplication)applicationCheck(
                        lectureTicketApplicationService.findById(id),passed,feedback
                )
        );
    }

    /**
     * <p>海报申请的审核</p>
     * <p>要求具备宣传部角色和海报权限</p>
     * @param id 申请表id
     * @param passed 通过情况
     * @param feedback 反馈
     * @throws InvalidIdException id不存在
     * @throws CheckHasBeenOperatedException 申请表已经审核过
     */
    @PreAuthorize("hasRole('Propaganda') and hasAuthority('Poster')")
    public void posterCheck(Long id,boolean passed, String feedback) throws InvalidIdException, CheckHasBeenOperatedException {
        posterApplicationService.save(
                (PosterApplication)applicationCheck(
                        posterApplicationService.findById(id),passed,feedback
                )
        );
    }

    /**
     * 对申请表进行审核的具体细节，包括状态检查等等
     * @param application 待审核的申请表
     * @param passed 通过情况
     * @param feedback 反馈
     * @return 审核后的申请表
     * @throws CheckHasBeenOperatedException 申请表已被审核过
     */
    private Application applicationCheck(Application application, boolean passed, String feedback) throws CheckHasBeenOperatedException {
        //确保未审核过
        if (application.getCheckInfo().getCheckStatus()!= CheckStatusEnum.WAIT)
            throw new CheckHasBeenOperatedException();
        else {
            //更新审核信息
            CheckInfo preCheckInfo=application.getCheckInfo();
            preCheckInfo.setCheckStatus(passed?CheckStatusEnum.PASS:CheckStatusEnum.NOPASS);
            preCheckInfo.setCheckFeedback(feedback);
            preCheckInfo.setCheckDate(new Date());
            preCheckInfo.setLastModifier(contextHelper.getCurrentPrincipal());

            return application;
        }
    }
}
