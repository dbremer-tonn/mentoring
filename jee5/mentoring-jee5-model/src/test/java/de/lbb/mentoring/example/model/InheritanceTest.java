package de.lbb.mentoring.example.model;

import de.lbb.mentoring.example.model.testdatabuilder.TestdataBuilder;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Query;

public class InheritanceTest extends AbstractEntityTest
{

    /**
     * Showing equality problem when loading same entity from different em
     */
    @Test
    public void testEmployeeInheritance()
    {
        Employee employee = TestdataBuilder.createEmployee("Hans", "Mueller");

        // saving Employee
        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();

        Employee parttimeEmployee = TestdataBuilder.createParttimeEmployee("Peter", "Schneider");

        // saving Employee
        em.getTransaction().begin();
        em.persist(parttimeEmployee);
        em.getTransaction().commit();

        // loading Employee from same EM
        Employee reloadedEmployee = em.find(Employee.class, parttimeEmployee.getId());
        Assert.assertTrue(reloadedEmployee instanceof ParttimeEmployee);

        // loading via query
        Query query = em.createQuery("from Employee where id = :id");
        query.setParameter("id", parttimeEmployee.getId());
        Employee result = (Employee) query.getSingleResult();
        Assert.assertTrue(result instanceof ParttimeEmployee);
    }
}