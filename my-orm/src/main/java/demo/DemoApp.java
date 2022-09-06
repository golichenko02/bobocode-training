package demo;

import bibernate.session.impl.SimpleSessionFactory;
import demo.entity.Person;
import org.h2.jdbcx.JdbcDataSource;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class DemoApp {
    public static void main(String[] args) {
        var dataSource = initializeInMemoryH2DataSource();
        var sessionFactory = new SimpleSessionFactory(dataSource);
        var session = sessionFactory.createSession();
        Person person = session.find(Person.class, 1L);
        System.out.println(person);
        Person theSamePerson = session.find(Person.class, 1L);
        System.out.println(person == theSamePerson);
    }

    private static DataSource initializeInMemoryH2DataSource() {
        var dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:demo;INIT=runscript from 'classpath:db/init.sql'");
        dataSource.setUser("sa");
        return dataSource;
    }
}
