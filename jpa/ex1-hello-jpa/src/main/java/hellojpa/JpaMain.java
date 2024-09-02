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

            Team team1 = new Team();
            team1.setName("A");
            em.persist(team1);

            Team team2 = new Team();
            team2.setName("A");
            em.persist(team2);

            Member member1 = new Member();
            member1.setUsername("hi1");
            member1.setTeam(team1);
            em.persist(member1);


            Member member2 = new Member();
            member2.setUsername("hi1");
            member2.setTeam(team2);
            em.persist(member2);

            em.flush();
            em.clear();

//            Member m = em.find(Member.class, member1.getId());

            List<Member> members = em.createQuery("select m from Member m join fetch m.team", Member.class).getResultList();



            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();


    }


}
