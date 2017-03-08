package info.zhwan.orm.jpa.ch08.cascade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * vm opion 에 -ea 를 넣을 것!!!
 *
 * @author zhwan
 */
@SpringBootApplication
public class Ch08CascadeMain {
  public static void main(String[] args) {
    try (ConfigurableApplicationContext context = SpringApplication.run(Ch08CascadeMain.class, args)) {
      CascadeTester tester = context.getBean(CascadeTester.class);

      tester.insertCascadeInsert();

      System.out.println("=====================================================");
      tester.removeParentAndChild();
      tester.removeDebug(0, 0);

      System.out.println("=====================================================");
      tester.insertCascadeInsert();
      tester.removeChildren();
      tester.removeDebug(1, 0);

      System.out.println("=====================================================");
      tester.addCascadeTest();

      System.out.println("=====================================================");
      tester.debug();
    }
  }

  @Component
  @Transactional
  static class CascadeTester {
    @PersistenceContext
    EntityManager em;

    public void insertCascadeInsert() {
      Parent parent = new Parent();
      Child child1 = new Child();
      Child child2 = new Child();

      // 각 관계를 맺은 객체간 연관관계를 맺어 주어야만 FK 값이 들어간다.
      child1.setParent(parent);
      child2.setParent(parent);
      parent.getChilds().add(child1);
      parent.getChilds().add(child2);

      em.persist(parent);
    }

    /**
     * cascade.remove 테스트
     */
    public void removeParentAndChild() {
      Parent parent = em.find(Parent.class, 1L);
      em.remove(parent);
    }

    /**
     * 고아객체 테스트
     */
    public void removeChildren() {
      Parent parent = em.find(Parent.class, 4L);
      parent.getChilds().clear();
    }

    public void removeDebug(int parentCount, int childCount) {
      List<Parent> parents = em.createQuery("select p from Parent p", Parent.class).getResultList();
      assert parentCount == parents.size();

      List<Child> children = em.createQuery("select c from Child c", Child.class).getResultList();
      assert childCount == children.size();
    }

    /**
     * 책 내용에는 (p. 313) addChild 라는 함수를 이용하는데, 아마 내부 코드에서 양방향 관계설정을 하는 편의 메소드 일 것이다.
     * 역시, 관계 설정은 사용자의 몫임을 잊지 말자!!!
     */
    public void addCascadeTest() {
      Parent parent = em.find(Parent.class, 4L);
      Child child = new Child();
      child.setParent(parent);
      parent.getChilds().add(child);
    }

    public void debug() {
      Child child = em.find(Child.class, 7L);
      org.hibernate.Hibernate.initialize(child);
      assert child.getParent() != null;
    }
  }
}
