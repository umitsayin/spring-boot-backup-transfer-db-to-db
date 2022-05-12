package com.example.dbtodb.config.steps.writer;

import com.example.dbtodb.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class UserWriter implements ItemWriter<User> {

    public void write(List<? extends User> list)  {
        SessionFactory factory = new Configuration()
                .configure("backup/hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

        try{
            session.beginTransaction();
            System.out.println(list.size());
            User user = new User();
            user.setId(list.get(0).getId());
            user.setName(list.get(0).getName());
            user.setEmail(list.get(0).getEmail());

            session.save(user);

            session.getTransaction().commit();

            System.out.println("Şehir eklendi.");
        }finally {
            factory.close();
        }
    }
}
