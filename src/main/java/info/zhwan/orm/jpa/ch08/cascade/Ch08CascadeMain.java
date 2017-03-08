package info.zhwan.orm.jpa.ch08.cascade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author zhwan
 */
@SpringBootApplication
public class Ch08CascadeMain {
  public static void main(String[] args) {
    try (ConfigurableApplicationContext context = SpringApplication.run(Ch08CascadeMain.class, args)) {
      CascadeTester tester = context.getBean(CascadeTester.class);

      tester.insertCascadeInsert();

      System.out.println("=====================================================");
      tester.removeCascadeTest();

      System.out.println("=====================================================");
      tester.insertCascadeInsert();
      tester.removeOrphanremovalTest();

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

    public void removeCascadeTest() {
      Parent parent = em.find(Parent.class, 1L);
      em.remove(parent); // cascade.remove 테스트
    }

    public void removeOrphanremovalTest() {
      Parent parent = em.find(Parent.class, 4L);
      parent.getChilds().clear(); // 고아객체 테스트
    }

    /**
     * 책 내용 처럼 되지 않는다. parent_id 가 null 임
     */
    public void addCascadeTest() {
      Parent parent = em.find(Parent.class, 4L);
      parent.getChilds().add(new Child());
    }

    public void debug() {
      Child child = em.find(Child.class, 7L);
      org.hibernate.Hibernate.initialize(child);
      System.err.println("child table parent fk is null : " + child);
    }
  }
}
