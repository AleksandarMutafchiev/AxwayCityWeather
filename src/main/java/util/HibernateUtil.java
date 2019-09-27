package util;

import entity.City;
import entity.Weather;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


    public class HibernateUtil {
        public static Session getHibernateSession() {
            final SessionFactory sf = new Configuration().configure("hibernate.cfg.xml")
                    .addAnnotatedClass(City.class).addAnnotatedClass(Weather.class).buildSessionFactory();
            final Session session = sf.openSession();
            return session;
        }
    }

