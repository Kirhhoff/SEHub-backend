package com.scut.se.sehubbackend.unit;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
public class HibernateTest {

    @Autowired SessionFactory sessionFactory;

    @Test
//    @Column(columnDefinition = )
    public void test(){
        Session session=sessionFactory.openSession();
        Transaction transaction=session.beginTransaction();
        sessionFactory.getMetamodel();
        EntityManager entityManager;
//        entityManager.createQuery("",).get
    }
}
