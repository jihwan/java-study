package info.zhwan.orm.jpa.ch05;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Transactional(readOnly = true)
public class Tester implements InitializingBean, ApplicationContextAware {

  private EntityManager em;

//  private final EntityManager em;
//
//  public Tester(EntityManager em) {
//    this.em = em;
//  }

  @Transactional(readOnly = false)
  public void initialize() throws Exception {
    Team team1 = new Team("team1");
    em.persist(team1);

    Member aaa = new Member("aaa");
    Member bbb = new Member("bbb");
    aaa.setTeam(team1);
    bbb.setTeam(team1);
//    team1.addMemember(aaa);
//    team1.addMemember(bbb);

    em.persist(aaa);
    em.persist(bbb);
  }

  public void search() {

//    Member member = em.find(Member.class, 2L, LockModeType.PESSIMISTIC_WRITE);

    Member member = em.find(Member.class, 2L, LockModeType.PESSIMISTIC_FORCE_INCREMENT);

//    Map<String, Object> properties = new HashMap<>();
//    properties.put("javax.persistence.lock.timeout", 1000);
//    Member member = em.find(Member.class, 2L, LockModeType.PESSIMISTIC_WRITE, properties);

    Team team = member.getTeam();
    System.err.println("member is " + member);
    System.err.println("team is " + team);
  }

  public void searchJqpl() {

    String jpql =
      "select m \n" +
        "from Member m\n" +
        "    join m.team t\n" +
        "where\n" +
        "    t.name = :teamName";

    List<Member> resultList = em.createQuery(jpql, Member.class).setParameter("teamName", "team1").getResultList();
    for (Member m : resultList) {
      System.err.println("member is " + m);
    }
//    resultList.forEach(System.out::println);
  }

//  @Transactional(readOnly = false)
//  public void update() {
//    Team team2 = new Team("team2");
//    em.persist(team2);
//
//    Member member = em.find(Member.class, 2L);
//    member.setTeam(team2);
//  }

  @Transactional(readOnly = false)
  public void delete() {
    Member member1 = em.find(Member.class, 1L);
    Member member2 = em.find(Member.class, 2L);
    member1.setTeam(null);
    member2.setTeam(null);


    Team team = em.find(Team.class, 1L);
    em.remove(team);
  }

  public void biDirection() {
    Team team = em.find(Team.class, 1L);

    System.err.println("team is " + team);

//    team.getMembers().forEach(System.out::println);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    EntityManager bean = this.applicationContext.getBean(EntityManager.class);
    this.em = bean;;
  }

  ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}