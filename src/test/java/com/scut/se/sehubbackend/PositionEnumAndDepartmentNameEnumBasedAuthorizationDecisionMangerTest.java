package com.scut.se.sehubbackend;

import com.scut.se.sehubbackend.security.authorization.interfaces.AuthorityMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
public class PositionEnumAndDepartmentNameEnumBasedAuthorizationDecisionMangerTest {
//
////    @Spy3
//    PositionAndDepartmentBasedAuthorizationDecisionManger decisionManger=
//            new PositionAndDepartmentBasedAuthorizationDecisionManger();
//
//    AuthorityMapper mapper=new HashAuthorityMapper();
//    UserAuthentication luXinNan;//陆鑫楠
//    UserAuthentication xieKun;//解坤
//    UserAuthentication pengTianXiang;//彭天祥
//    UserAuthentication chenYang;//陈扬
//    UserAuthentication guoJunWei;//郭俊炜
//    UserAuthentication liuYiXi;//刘逸曦
//    UserAuthentication zhangYiYun;//张翊雲
//    UserAuthentication sunJiaYu;//孙嘉雨
//
//    @Before
//    public void createUsers(){
//        luXinNan=UserAuthentication.builder().userHistory(UserHistory.builder().positionEnum(PositionEnum.StandingCommittee).build()).build();//陆鑫楠
//        xieKun=UserAuthentication.builder().userHistory(UserHistory.builder().positionEnum(PositionEnum.StandingCommittee).build()).build();//解坤
//        pengTianXiang=UserAuthentication.builder().userHistory(UserHistory.builder().positionEnum(PositionEnum.Minister).departmentNameEnum(DepartmentNameEnum.Research).build()).build();//彭天祥
//        chenYang=UserAuthentication.builder().userHistory(UserHistory.builder().positionEnum(PositionEnum.Minister).departmentNameEnum(DepartmentNameEnum.Research).build()).build();//陈扬
//        guoJunWei=UserAuthentication.builder().userHistory(UserHistory.builder().positionEnum(PositionEnum.Minister).departmentNameEnum(DepartmentNameEnum.Quality).build()).build();//郭俊炜
//        liuYiXi=UserAuthentication.builder().userHistory(UserHistory.builder().positionEnum(PositionEnum.Staff).departmentNameEnum(DepartmentNameEnum.Research).build()).build();//刘逸曦
//        zhangYiYun=UserAuthentication.builder().userHistory(UserHistory.builder().positionEnum(PositionEnum.Staff).departmentNameEnum(DepartmentNameEnum.Research).build()).build();//张翊雲
//        sunJiaYu=UserAuthentication.builder().userHistory(UserHistory.builder().positionEnum(PositionEnum.Staff).departmentNameEnum(DepartmentNameEnum.Media).build()).build();//孙嘉雨
//    }
//
//    @Test
//    public void testDecide(){
//        /**
//         * 相同职位间不具备修改权限的权限
//         */
//        //常委
//        assertEquals(false,decisionManger.decide(luXinNan,xieKun,mapper.map(PositionEnum.StandingCommittee,null)));
//        assertEquals(false,decisionManger.decide(luXinNan,xieKun,mapper.mapDynamic(null,null)));
//        //部长
//        assertEquals(false,decisionManger.decide(pengTianXiang,chenYang,mapper.map(PositionEnum.Minister, DepartmentNameEnum.Research)));
//        assertEquals(false,decisionManger.decide(pengTianXiang,chenYang,mapper.mapDynamic(DepartmentNameEnum.Research,null)));
//        assertEquals(false,decisionManger.decide(pengTianXiang,guoJunWei,mapper.map(PositionEnum.Minister, DepartmentNameEnum.Quality)));
//        assertEquals(false,decisionManger.decide(pengTianXiang,guoJunWei,mapper.mapDynamic(DepartmentNameEnum.Quality,null)));
//        //部员
//        assertEquals(false,decisionManger.decide(liuYiXi,zhangYiYun,mapper.map(PositionEnum.Staff, DepartmentNameEnum.Research)));
//        assertEquals(false,decisionManger.decide(liuYiXi,zhangYiYun,mapper.mapDynamic(DepartmentNameEnum.Research,null)));
//        assertEquals(false,decisionManger.decide(liuYiXi,sunJiaYu,mapper.map(PositionEnum.Staff, DepartmentNameEnum.Media)));
//        assertEquals(false,decisionManger.decide(liuYiXi,sunJiaYu,mapper.mapDynamic(DepartmentNameEnum.Media,null)));
//
//        /**
//         *下级对上级无权修改权限
//         */
//        //部长对常委
//        assertEquals(false,decisionManger.decide(pengTianXiang,xieKun,mapper.map(PositionEnum.StandingCommittee,null)));
//        assertEquals(false,decisionManger.decide(pengTianXiang,xieKun,mapper.mapDynamic(null,null)));
//        assertEquals(false,decisionManger.decide(guoJunWei,luXinNan,mapper.map(PositionEnum.StandingCommittee,null)));
//        assertEquals(false,decisionManger.decide(guoJunWei,luXinNan,mapper.mapDynamic(null,null)));
//        //部员对部长
//        assertEquals(false,decisionManger.decide(liuYiXi,chenYang,mapper.map(PositionEnum.Minister, DepartmentNameEnum.Research)));
//        assertEquals(false,decisionManger.decide(liuYiXi,chenYang,mapper.mapDynamic(DepartmentNameEnum.Research,null)));
//        assertEquals(false,decisionManger.decide(zhangYiYun,guoJunWei,mapper.map(PositionEnum.Minister, DepartmentNameEnum.Quality)));
//        assertEquals(false,decisionManger.decide(zhangYiYun,pengTianXiang,mapper.mapDynamic(DepartmentNameEnum.Research,null)));
//        //部员对常委
//        assertEquals(false,decisionManger.decide(liuYiXi,xieKun,mapper.map(PositionEnum.StandingCommittee,null)));
//        assertEquals(false,decisionManger.decide(liuYiXi,xieKun,mapper.mapDynamic(null,null)));
//        assertEquals(false,decisionManger.decide(sunJiaYu,luXinNan,mapper.map(PositionEnum.StandingCommittee,null)));
//        assertEquals(false,decisionManger.decide(sunJiaYu,luXinNan,mapper.mapDynamic(null,null)));
//
//        /**
//         * 不同部门之间无权修改权限
//         */
//        //不同部门的部长对干事
//        assertEquals(false,decisionManger.decide(guoJunWei,zhangYiYun,mapper.map(PositionEnum.Staff, DepartmentNameEnum.Research)));
//        assertEquals(false,decisionManger.decide(guoJunWei,zhangYiYun,mapper.mapDynamic(DepartmentNameEnum.Research,null)));
//        assertEquals(false,decisionManger.decide(guoJunWei,sunJiaYu,mapper.map(PositionEnum.Staff, DepartmentNameEnum.Media)));
//        assertEquals(false,decisionManger.decide(guoJunWei,sunJiaYu,mapper.mapDynamic(DepartmentNameEnum.Media,null)));
//
//        /**
//         *常委对部长、部员的职位不可修改
//         */
//        //常委对部长
//        assertEquals(false,decisionManger.decide(luXinNan,guoJunWei,mapper.map(PositionEnum.Minister, DepartmentNameEnum.Quality)));
//        assertEquals(false,decisionManger.decide(luXinNan,pengTianXiang,mapper.map(PositionEnum.Minister, DepartmentNameEnum.Research)));
//        //常委对部员
//        assertEquals(false,decisionManger.decide(xieKun,liuYiXi,mapper.map(PositionEnum.Staff, DepartmentNameEnum.Research)));
//        assertEquals(false,decisionManger.decide(xieKun,zhangYiYun,mapper.map(PositionEnum.Staff, DepartmentNameEnum.Research)));
//        assertEquals(false,decisionManger.decide(xieKun,sunJiaYu,mapper.map(PositionEnum.Staff, DepartmentNameEnum.Media)));
//
//        /**
//         *常委对部长、部员的动态权限有权修改
//         */
//        assertEquals(true,decisionManger.decide(luXinNan,pengTianXiang,mapper.mapDynamic(DepartmentNameEnum.Research,null)));
//        assertEquals(true,decisionManger.decide(luXinNan,guoJunWei,mapper.mapDynamic(DepartmentNameEnum.Quality,null)));
//        assertEquals(true,decisionManger.decide(luXinNan,liuYiXi,mapper.mapDynamic(DepartmentNameEnum.Research,null)));
//        assertEquals(true,decisionManger.decide(luXinNan,sunJiaYu,mapper.mapDynamic(DepartmentNameEnum.Media,null)));
//
//        /**
//         *部长对同部门部员的动态权限有权修改
//         */
//        assertEquals(true,decisionManger.decide(pengTianXiang,liuYiXi,mapper.mapDynamic(DepartmentNameEnum.Research,null)));
//        assertEquals(true,decisionManger.decide(chenYang,zhangYiYun,mapper.mapDynamic(DepartmentNameEnum.Research,null)));
//    }
//
//    @Test
//    public void testIsNotImmutable() throws Exception {
//        assertEquals(true,Whitebox.invokeMethod(decisionManger,"isNotImmutable",new SimpleGrantedAuthority("Dynamic_")));//测试仅有Dynamic_前缀
//        assertEquals(true,Whitebox.invokeMethod(decisionManger,"isNotImmutable",new SimpleGrantedAuthority("Dynamic_xxxx")));//测试Dynamic_前缀起头的任意权限
//        assertEquals(false,Whitebox.invokeMethod(decisionManger,"isNotImmutable",new SimpleGrantedAuthority("kjfsdfDynamic_dds")));//测试不以Dynamic_起头但含有Dynamic_的权限
//        assertEquals(false,Whitebox.invokeMethod(decisionManger,"isNotImmutable",new SimpleGrantedAuthority("StandingCommittee_")));//测试常委
//        assertEquals(false,Whitebox.invokeMethod(decisionManger,"isNotImmutable",new SimpleGrantedAuthority("Minister_Research")));//测试部长
//        assertEquals(false,Whitebox.invokeMethod(decisionManger,"isNotImmutable",new SimpleGrantedAuthority("Staff_Research")));//测试部员
//    }

}
