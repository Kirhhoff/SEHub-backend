package com.scut.se.sehubbackend.repository.activity;

import com.scut.se.sehubbackend.Domain.activityN.ActivityMainInfo;
import com.scut.se.sehubbackend.Domain.activityN.EtiquetteApplication;
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
public class EtiquetteApplicationRepositoryTest {

    @Autowired
    private EtiquetteApplicationRepository etiquetteApplicationRepository;

    @Test
    public void saveTest() {
        ActivityMainInfo activityMainInfo = new ActivityMainInfo();
        activityMainInfo.setActivityId(new Long(0));
        activityMainInfo.setName("黑框框");
        activityMainInfo.setLocation("B8");
        activityMainInfo.setStartTime(new Date());
        activityMainInfo.setEndTime(new Date());
        activityMainInfo.setDescription("大佬激情互殴");

        EtiquetteApplication etiquetteApplication = new EtiquetteApplication();
        etiquetteApplication.setActivityMainInfo(activityMainInfo);
        etiquetteApplication.setId(activityMainInfo.getActivityId());
        etiquetteApplication.setDescOfJob("女装大佬为巨佬跳舞");
        etiquetteApplication.setNumOfEtiquette(3);
        etiquetteApplication.setRehearsalTime(new Date());

        EtiquetteApplication result = etiquetteApplicationRepository.save(etiquetteApplication);
        Assert.assertNotNull(result);
    }
}
