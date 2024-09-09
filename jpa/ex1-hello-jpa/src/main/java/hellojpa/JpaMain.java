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
            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homecity", "street", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new AddressEntity("city1", "street1","10000"));
            member.getAddressHistory().add(new AddressEntity("city2", "street2","10000"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("===============start==============");
            Member findMember = em.find(Member.class, member.getId());

            Address findMemberAddress = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", findMemberAddress.getStreet(), findMemberAddress.getZipcode()));

            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            System.out.println("===============address==============");

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
