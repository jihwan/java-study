package info.zhwan.orm.jpa.ch06.mtm.type2;

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
@SpringBootApplication
@Transactional
public class Ch06ManyToManyType2Main implements CommandLineRunner {
  public static void main(String[] args) {
    try (ConfigurableApplicationContext context = SpringApplication.run(Ch06ManyToManyType2Main.class, args)) {
    }
  }

  @PersistenceContext
  EntityManager em;

  @Override
  public void run(String... args) throws Exception {

    Product p1 = new Product("pp1");
    Product p2 = new Product("pp2");

    Member m1 = new Member("mm1");
    m1.getProducts().add(p1);
    m1.getProducts().add(p2);
    p1.getMembers().add(m1);
    p2.getMembers().add(m1);

    em.persist(p1);
    em.persist(p2);
    em.persist(m1);
  }
}
