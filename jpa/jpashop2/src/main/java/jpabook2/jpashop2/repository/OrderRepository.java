package jpabook2.jpashop2.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jpabook2.jpashop2.api.OrderSimpleApiController;
import jpabook2.jpashop2.domain.*;
import jpabook2.jpashop2.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

//    public List<Order> findAll(OrderSearch orderSearch) {
//
//    }

    public List<Order> findAllByString(OrderSearch orderSearch) {

        String jpql = "select o From Order o join o.member m";
        boolean isFirstCondition = true;

        // 주문상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }

            jpql += " o.status = :status";
        }

        // 회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {

            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }

        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                .setMaxResults(1000);

        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }

        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }

        return query.getResultList();
    }

    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Order, Member> m = o.join("member", JoinType.INNER); // 회원과 조인

        List<Predicate> criteria = new ArrayList<>();

        // 주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }

        // 회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name = cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);

        return query.getResultList();

    }

    public List<Order> findAllWithMemberDelivery() {
        // fetch join으로 쿼리 1번만 호출
        return em.createQuery(
                "select o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", Order.class
        ).getResultList();

        // 엔티티를 페치 조인을 사용해 쿼리 1번에 모두 조회
        // 페치 조인으로 order -> member, order -> delivery 이미 조회된 상태이므로 지연로딩하지 않음

    }

    public List<SimpleOrderQueryDto> findOrderDtos() {

        return em.createQuery(
                "select new jpabook2.jpashop2.repository.SimpleOrderQueryDto(o.id, m.username, o.orderDate, o.status, d.address)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d", SimpleOrderQueryDto.class).getResultList();
        // select절에서 원하는 데이터만 조회

        // 일반적인 SQL을 사용할 때 처럼 원하는 값을 선택해서 조회
        // new 명령어를 사용하여 JPQL 결과를 DTO로 즉시 변환
        // DB -> 애플리케이션 네트워크 용량 최적화
        // 리포지토리의 재사용성이 떨어진다. -> API 스펙에 맞춘 코드가 리포지토리에 들어가는 단점이 있다.
    }


    public List<Order> findAllWithItem() {

        return em.createQuery(
                "select distinct o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d" +
                        " join fetch o.orderItems oi" +
                        " join fetch oi.item i", Order.class).getResultList();
        // distinct 사용 이유
        // 1대다 관계이기 때문에 데이터베이스 row가 중가하게 된다.
        // 그 결과 order 엔티티의 조회 수도 증가하게 되게 된다.
        // db에 distinct 키워드 날려주고, 엔티티가 중복인 경우 중복을 걸러서 컬렉션에 담아준다.
        // 하이버네이트6에서는 기본값으로 되어있다.

        // 컬렉션 페치 조인을 사용하면 페이징이 불가능하다. 하이버네이트가 경고 로그를 남기고 DB에서 모든 데이터를 읽어오고 메모리에서 페이징 해버린다. 매우 위험하다!
        // 컬렉션 페치 조인은 1개만 사용할 수 있다. 데이터가 부정합하게 조회될 수가 있다.
    }

    public List<Order> findAllWithMemberDelivery(int offset, int limit) {
        return em.createQuery(
                "select o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", Order.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Order> findAll(OrderSearch orderSearch) {

        JPAQueryFactory query = new JPAQueryFactory(em);

        QOrder order = QOrder.order;
        QMember member = QMember.member;


        return query.select(order)
                .from(order)
                .join(order.member,member)
                .where(statusEq(orderSearch.getOrderStatus()), nameLike(orderSearch, member))
                .limit(1000)
                .fetch();
    }

    private static BooleanExpression nameLike(OrderSearch orderSearch, QMember member) {
        if (!StringUtils.hasText(orderSearch.getMemberName())) {
            return null;
        }
        return QMember.member.username.like(member.username);
    }


    private BooleanExpression statusEq(OrderStatus statusCond) {
        if (statusCond == null) {
            return null;
        }
        return QOrder.order.status.eq(statusCond);
    }
}
