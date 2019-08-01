package com.scut.se.sehubbackend.repository.activity;

import com.scut.se.sehubbackend.Domain.activityN.ActivityMainInfo;
import com.scut.se.sehubbackend.Domain.activityN.ActivitySupplementaryInfo;
import com.scut.se.sehubbackend.Domain.activityN.EtiquetteApplication;
import com.scut.se.sehubbackend.Repository.activityN.ActivityMainInfoRepository;
import com.scut.se.sehubbackend.Repository.activityN.ActivitySupplementaryInfoRepository;
import com.scut.se.sehubbackend.Repository.activityN.EtiquetteApplicationRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitySupplementaryRepositoryTest {

    @Autowired
    ActivitySupplementaryInfoRepository activitySupplementaryInfoRepository;

    @Test
    public void saveTest() {
        ActivityMainInfo activityMainInfo = new ActivityMainInfo();
        activityMainInfo.setActivityId(new Long(0));
        activityMainInfo.setName("黑框框");
        activityMainInfo.setLocation("B8");
        activityMainInfo.setStartTime(new Date());
        activityMainInfo.setEndTime(new Date());
        activityMainInfo.setDescription("大佬激情互殴");

        ActivitySupplementaryInfo activitySupplementaryInfo = new ActivitySupplementaryInfo();
        activitySupplementaryInfo.setActivityMainInfo(activityMainInfo);
        activitySupplementaryInfo.setId(activityMainInfo.getActivityId());
        activitySupplementaryInfo.setBackground("大家都闲得慌");
        activitySupplementaryInfo.setObjective("寻找巨佬");
        activitySupplementaryInfo.setOrganizer("学术部");
        activitySupplementaryInfo.setExpectedNumOfParticipants(new Integer(5));
        activitySupplementaryInfo.setNote("给多点经费行不行");

        ActivitySupplementaryInfo result = activitySupplementaryInfoRepository.save(activitySupplementaryInfo);
        Assert.assertNotNull(result);
    }
}
