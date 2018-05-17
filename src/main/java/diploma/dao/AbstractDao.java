package diploma.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository(value = "abstractDao")
public abstract class AbstractDao {

    @Autowired
    protected SessionFactory sessionFactory;
    protected Session session(){
        return sessionFactory.getCurrentSession();
    }

}
