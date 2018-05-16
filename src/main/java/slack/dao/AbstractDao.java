package slack.dao;

import slack.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.lang.reflect.ParameterizedType;
import java.util.function.Function;

public abstract class AbstractDao<T> {

    private Class<T> genericClass;
    private Session session;
    private Transaction tx;

    protected void start() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        tx = session.beginTransaction();
    }

    public long save(T object) {
        return this.execute((s) -> (long) s.save(object));
    }

    public void delete(T object) {
        this.execute((s) -> {
            s.delete(object);
            return null;
        });
    }

    public void merge(T object) {
        start();
        session.merge(object);
        session.getTransaction().commit();
        session.close();
    }

    public T get(long id) {
        return this.execute((s) -> (T) session.get(getGenericType(), id));
    }

    public <A> A execute(Function<Session, A> fn) {
        start();
        A result = fn.apply(session);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    protected Session getSession() {
        return session;
    }

    protected Transaction getTx() {
        return tx;
    }

    private String getGenericType() {
        return ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
    }
}
