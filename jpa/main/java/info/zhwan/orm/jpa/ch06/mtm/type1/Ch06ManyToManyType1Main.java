package info.zhwan.orm.jpa.ch06.mtm.type1;

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
public class Ch06ManyToManyType1Main implements CommandLineRunner {
  public static void main(String[] args) {
    try (ConfigurableApplicationContext context = SpringApplication.run(Ch06ManyToManyType1Main.class, args)) {
    }
  }

  @PersistenceContext
  EntityManager em;

  @Override
  public void run(String... args) throws Exception {

    Product p1 = new Product("p1");
    Product p2 = new Product("p2");

    Member m1 = new Member("m1");
    m1.getProducts().add(p1);
    m1.getProducts().add(p2);

    em.persist(p1);
    em.persist(p2);
    em.persist(m1);
  }
}
