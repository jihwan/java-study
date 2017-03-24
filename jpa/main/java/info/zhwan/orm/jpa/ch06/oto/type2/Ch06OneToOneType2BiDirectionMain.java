package info.zhwan.orm.jpa.ch06.oto.type2;

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
public class Ch06OneToOneType2BiDirectionMain implements CommandLineRunner {
  public static void main(String[] args) {
    try (ConfigurableApplicationContext context = SpringApplication.run(Ch06OneToOneType2BiDirectionMain.class, args)) {
    }
  }

  @PersistenceContext
  EntityManager em;

  @Override
  public void run(String... args) throws Exception {
    Locker lll = new Locker("lll");
    Member mmm = new Member("mmm");
    lll.setMember(mmm);

    em.persist(mmm);
    em.persist(lll);
  }
}
