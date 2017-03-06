package info.zhwan.orm.jpa.ch06.otmb;

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
public class Ch06OneToManyBiDirectionMain implements CommandLineRunner {
  public static void main(String[] args) {
    try (ConfigurableApplicationContext context = SpringApplication.run(Ch06OneToManyBiDirectionMain.class, args)) {
    }
  }

  @PersistenceContext
  EntityManager em;

  @Override
  public void run(String... args) throws Exception {
    Member aaa = new Member("aaa");
    Member bbb = new Member("bbb");

    Team team1 = new Team("team1");
    team1.addMemember(aaa);
    team1.addMemember(bbb);
    em.persist(team1);
    em.persist(aaa);
    em.persist(bbb);
  }
}
