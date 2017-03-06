package info.zhwan.orm.jpa.ch06.mtm.type4;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author zhwan
 */
@SpringBootApplication
@EntityScan(basePackageClasses = {Ch06ManyToManyType4Main.class, Jsr310JpaConverters.class})
@Transactional
public class Ch06ManyToManyType4Main implements CommandLineRunner {
  public static void main(String[] args) {
    try (ConfigurableApplicationContext context = SpringApplication.run(Ch06ManyToManyType4Main.class, args)) {
    }
  }

  @PersistenceContext
  EntityManager em;

  @Override
  public void run(String... args) throws Exception {
    Member m1 = new Member("m1");
    em.persist(m1);

    Product p1 = new Product("p1");
    Product p2 = new Product("p2");
    em.persist(p1);
    em.persist(p2);

    Orders orders = new Orders();
    orders.setMember(m1);
    orders.setProduct(p2);
    orders.setOrderAmount(100);
    em.persist(orders);
  }
}
