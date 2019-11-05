package edu.ncsu.csc540.health.dao;

import edu.ncsu.csc540.health.model.Address;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;


public interface AddressDAO {

    @SqlUpdate("insert into addresses (num, street, city, state, country) values (:streetNum, :street, :city, :state, :country)")
    @GetGeneratedKeys("id")
    Integer create(@BindBean Address address);

    @SqlQuery("select * from addresses where id = :id")
    @RegisterConstructorMapper(Address.class)
    Address findById(@Bind("id") Integer id);

    @SqlUpdate("update addresses " +
               "set num = :streetNum, " +
               "    street = :street, " +
               "    city = :city, " +
               "    state = :state, " +
               "    country = :country " +
               "where id = :id")
    void update(@BindBean Address address);
}
