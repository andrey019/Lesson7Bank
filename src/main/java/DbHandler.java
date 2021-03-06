import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DbHandler implements DbInterface {
    private SessionFactory sessionFactory;

    public DbHandler() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @Override
    public boolean addEntityToDB(Object... objects) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            for (Object object : objects) {
                session.save(object);
            }
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            session.close();
        }
        return false;
    }

    public boolean addTransaction(TransactionBank transactionBank, Account giver, Account receiver) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.merge(transactionBank);
            session.merge(giver);
            session.merge(receiver);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            session.close();
        }
        return false;
    }

    @Override
    public long getUniqueAccountNumber() {
        Session session = sessionFactory.openSession();
        long number = 0;
        Object result = null;
        while (true) {
            try {
                Criteria criteria = session.createCriteria(Account.class);
                number = ThreadLocalRandom.current().nextLong(100000000, 999999999);
                criteria.add(Restrictions.eq("number", number));
                result = criteria.uniqueResult();
            } catch (Exception e) {}
            if (result == null) {
                session.close();
                return number;
            }
        }
    }

    @Override
    public boolean deleteEntityFromBD(Object... objects) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            for (Object object : objects) {
                session.delete(object);
            }
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            session.close();
        }
        return false;
    }

    @Override
    public List getAllEntities(Class objectClass) {
        Session session = sessionFactory.openSession();
        try {
            Criteria criteria = session.createCriteria(objectClass);
            List result = criteria.list();
            session.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            session.close();
            return null;
        }
    }

    @Override
    public Object getEntityById(Class objectClass, long id) {
        Session session = sessionFactory.openSession();
        try {
            Object entity = session.get(objectClass, id);
            session.close();
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            session.close();
        }
        return null;
    }

    @Override
    public Account getAccountByNumber(long number) {
        Session session = sessionFactory.openSession();
        try {
            Criteria criteria = session.createCriteria(Account.class);
            criteria.add(Restrictions.eq("number", number));
            Account result = (Account) criteria.uniqueResult();
            session.close();
            return result;
        } catch (Exception e){
            e.printStackTrace();
            session.close();
        }
        return null;
    }
}
