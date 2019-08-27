package com.scut.se.sehubbackend.controller;

import com.scut.se.sehubbackend.dto.ActivityApplicationDTO;
import com.scut.se.sehubbackend.exception.InvalidIdException;
import com.scut.se.sehubbackend.service.ActivityApplicationService;
import com.scut.se.sehubbackend.utils.DTOUtil;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ActivityApplicationController {

    private final ActivityApplicationService activityApplicationService;
    private final DTOUtil dtoUtil;

    public ActivityApplicationController(ActivityApplicationService activityApplicationService, DTOUtil dtoUtil) {
        this.activityApplicationService = activityApplicationService;
        this.dtoUtil = dtoUtil;
    }

    @RequestMapping(value = "/application/activity/{id}",method = RequestMethod.GET)
    public ActivityApplicationDTO getById(@PathVariable("id")Long id) throws InvalidIdException {
        return dtoUtil.toDTO(activityApplicationService.findById(id));
    }
    /* 获取当前用户所在部门发起过的所有活动申请表 */
    @GetMapping("/application/activity")
    public List<ActivityApplicationDTO> list() {
        List<ActivityApplicationDTO> activityApplicationDTOList =
                activityApplicationService.getAllActivityApplicationOfCurrentDepartment();
        return activityApplicationDTOList;
    }

    /* 以当前用户的身份发起一张活动申请表 */
    @PostMapping("/application/activity")
    public void apply(@Valid@RequestBody ActivityApplicationDTO activityApplicationDTO) {
        activityApplicationService.create(activityApplicationDTO);
    }

//    /* 以当前用户的身份修改一张活动申请表 */
//    @PutMapping("/application/activity")
//    public void modify(@Valid ActivityApplicationDTO activityApplicationDTO) throws InvalidIdException {
//        activityApplicationService.findById(activityApplicationDTO.getId()); // Id不存在抛出异常
//        activityApplicationService.create(activityApplicationDTO); // saveAndFlush：增量修改
//    }

}
