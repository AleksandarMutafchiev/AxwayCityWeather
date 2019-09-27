package repository;

import entity.City;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

public class CityRepository {


    public City createCity(City city) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getHibernateSession();
            transaction = session.beginTransaction();
            session.persist(city);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return city;
    }

    public City getCityById(Long id) {
        final Session session = HibernateUtil.getHibernateSession();
        final CriteriaBuilder cb = session.getCriteriaBuilder();
        final CriteriaQuery<City> cr = cb.createQuery(City.class);
        final Root<City> root = cr.from(City.class);
        Path path = root.get("id");
        cr.select(root).where(cb.equal(path, id));
        Query<City> query = session.createQuery(cr);
        City city = null;
        try {
            city = query.getSingleResult();

        } catch (NoResultException e) {

        }
        return city;
    }

    public List<City> getAllCities(){
        Session session = null;
        List city = null;
        try {
            session = HibernateUtil.getHibernateSession();
            city = session.createQuery("select c.id,c.name from city c").list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }  finally {
            if (session != null) {
                session.close();
            }
        }
        return city;
    }


}

