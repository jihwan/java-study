package info.zhwan.orm.jpa.ch06.oto.type1.biderection;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author zhwan
 */
@SpringBootApplication @Transactional
public class Ch06OneToOneType1BiDirectionMain implements CommandLineRunner {
  public static void main(String[] args) {
    try (ConfigurableApplicationContext context = SpringApplication.run(Ch06OneToOneType1BiDirectionMain.class, args)) {
    }
  }

  @PersistenceContext
  EntityManager em;

  @Override
  public void run(String... args) throws Exception {
    Locker l1 = new Locker("l1");
    Member m1 = new Member("m1");
    m1.setLocker(l1);

    em.persist(l1);
    em.persist(m1);

    System.err.println("member is " + m1);
    System.err.println("locker is " + l1);
  }
}
