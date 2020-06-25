package com.example.demo;

import com.example.demo.com.example.demo.model.Cabs;
import com.example.demo.entities.CabEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Random;

@Component
public class TransactionDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public TransactionDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(value = "transactionManagerConfig", propagation = Propagation.REQUIRED)
    List<CabEntity>  getAllCabs(String areaCode)
    {
        TypedQuery<CabEntity> query = sessionFactory.getCurrentSession().createQuery("from CabEntity " +
                        "c where c.areaCode=:areaCode",
                CabEntity.class);

        query.setParameter("areaCode", areaCode);
        List<CabEntity> cabs=query.getResultList();
         return cabs;

    }

    @Transactional(value = "transactionManagerConfig", propagation = Propagation.REQUIRED)
    Cabs addNewCab(Cabs cab)
    {
        Session session= sessionFactory.getCurrentSession();
        Transaction tx=session.beginTransaction();
        CabEntity cabEntity= new CabEntity();
        cabEntity.setAreaCode(cab.getAreaCode());
        cabEntity.setDriverName(cab.getDriverName());
        cabEntity.setModelNumber(cab.getModelNumber());
        Random random =new Random();
        Integer cabid =random.nextInt();
        String str= cabid.toString();
        cabEntity.setCabId(str);
       session.persist(cabEntity);
       tx.commit();
        cab.setCabId(str);
        return cab;



    }

    @Transactional(value = "transactionManagerConfig", propagation = Propagation.REQUIRED)
    String deleteCab(String cabId)
    {
        Session session=sessionFactory.getCurrentSession();
        Transaction tx=session.beginTransaction();
       CabEntity cab= session.load(CabEntity.class,cabId);
        session.delete(cab);
        tx.commit();
       return "Booked!!";



    }



}
