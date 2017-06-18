package ru.kaawork.dao;

import ru.kaawork.model.User;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by user on 18.06.17.
 */
@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

    static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public User findById(int id) {
        User user = getByKey(id);
        if(user != null) {
            Hibernate.initialize(user.getUserProfiles());
        }
        return user;
    }

    @Override
    public User findBySSO(String sso) {
        logger.info("SSO: {} ",sso);
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("ssoId",sso));
        User user = (User)crit.uniqueResult();
        //TODO find this
        if(user != null) {
            Hibernate.initialize(user.getUserProfiles());
        }
        return user;
    }

    @SuppressWarnings("unckecked")
    public List<User> findAllUsers() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("firstName"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<User> users = (List<User>) criteria.list();
        /** uncomment for eager
         *
         */
        /*
        for(User user : users){
            Hibernate.initialize(user.getUserProfiles());
        }*/
        return users;
    }

    @Override
    public void save(User user) {
        persist(user);
    }

    @Override
    public void deleteBySSO(String sso) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("ssoId",sso));
        User user = (User) crit.uniqueResult();
        delete(user);
    }


}
