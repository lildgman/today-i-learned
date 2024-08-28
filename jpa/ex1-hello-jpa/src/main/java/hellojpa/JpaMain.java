package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        //code
        tx.begin();

        try {
            Member findMember = em.find(Member.class, 150L);
            findMember.setName("AAaaAA");

            em.clear();
            Member findMember2 = em.find(Member.class, 150L);

            System.out.println("=================");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();


    }

}
