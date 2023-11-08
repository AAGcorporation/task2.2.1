package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }
   @Transactional
   public List<User> getUserByCarModelAndSeries(String model, int series) {
      //HQL - имя класса = имя таблицы, имя поля = имя колонки

      /*По решению задачи не совсем понял, обязательно ли, что бы метод getUserByCarModelAndSeries
      возвращал только один результат, да и с такой реализацией возникли трудности, так как (если в таблице
      будут однофамильцы и полные тески и/или машины будут идентичны, то результат hql запроса будет не уникальным.
      По этой причине реализовал через getResultList(). Этот вариант работает как для полных тесок, так и для
      уникальных данных в БД.
      В остальном все сделал по инструкциям, которые нашел в интернете и OneToOne работает.
      */

      String hql = "FROM User users WHERE users.car.model =:model AND users.car.series =:series";
      Query query = sessionFactory.getCurrentSession().createQuery(hql);
      query.setParameter("model", model);
      query.setParameter("series", series);
      return query.getResultList();

   }
}
