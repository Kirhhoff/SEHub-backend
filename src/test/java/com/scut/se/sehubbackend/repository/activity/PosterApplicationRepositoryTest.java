package com.scut.se.sehubbackend.repository.activity;

import com.scut.se.sehubbackend.Domain.activityN.ActivityMainInfo;
import com.scut.se.sehubbackend.Domain.activityN.PosterApplication;
import com.scut.se.sehubbackend.Enumeration.PosterSizeEnum;
import com.scut.se.sehubbackend.Repository.activityN.PosterApplicationRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PosterApplicationRepositoryTest {

    @Autowired
    private PosterApplicationRepository posterApplicationRepository;

    @Test
    public void saveTest() {
        ActivityMainInfo activityMainInfo = new ActivityMainInfo();
        activityMainInfo.setActivityId(new Long(0));
        activityMainInfo.setName("黑框框");
        activityMainInfo.setLocation("B8");
        activityMainInfo.setStartTime(new Date());
        activityMainInfo.setEndTime(new Date());
        activityMainInfo.setDescription("大佬激情互殴");

        PosterApplication posterApplication = new PosterApplication();
        posterApplication.setActivityMainInfo(activityMainInfo);
        posterApplication.setId(activityMainInfo.getActivityId());
        posterApplication.setDeadline(new Date());
        posterApplication.setPosterSize(PosterSizeEnum.K8.getCode());
        posterApplication.setPropagandaTextRequirement("大力宣传");

        PosterApplication result = posterApplicationRepository.save(posterApplication);
        Assert.assertNotNull(result);
    }
}
