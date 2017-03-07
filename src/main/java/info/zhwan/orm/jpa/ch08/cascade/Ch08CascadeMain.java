package info.zhwan.orm.jpa.ch08.cascade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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
@SpringBootApplication @Transactional
public class Ch08CascadeMain implements CommandLineRunner {
  public static void main(String[] args) {
    try (ConfigurableApplicationContext context = SpringApplication.run(Ch08CascadeMain.class, args)) {
    }
  }

  @Autowired CascadeTester tester;

  @Override
  public void run(String... args) throws Exception {
    tester.insert();

    System.out.println("=====================================================");
    tester.remove();

    System.out.println("=====================================================");
    tester.add();
  }

  @Component
  static class CascadeTester {
    @PersistenceContext
    EntityManager em;

    void insert() {
      Parent parent = new Parent();
      Child child1 = new Child();
      Child child2 = new Child();

      child1.setParent(parent);
      child2.setParent(parent);
      parent.getChilds().add(child1);
      parent.getChilds().add(child2);

      em.persist(parent);
    }

    void remove() {
      Parent parent = em.find(Parent.class, 1L);

//      em.remove(parent); // cascade.remove 테스트

      parent.getChilds().clear(); // 고아객체 테스트

    }

    /**
     * 책 내용 처럼 되지 않는다. parent_id 가 null 임
     */
    void add() {
      Parent parent = em.find(Parent.class, 1L);
      parent.getChilds().add( new Child() );
    }
  }
}
