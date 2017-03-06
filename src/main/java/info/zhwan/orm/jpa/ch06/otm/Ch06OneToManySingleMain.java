package info.zhwan.orm.jpa.ch06.otm;

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
public class Ch06OneToManySingleMain implements CommandLineRunner {
  public static void main(String[] args) {
    try (ConfigurableApplicationContext context = SpringApplication.run(Ch06OneToManySingleMain.class, args)) {
    }
  }

  @PersistenceContext
  EntityManager em;

  @Override
  public void run(String... args) throws Exception {
    Team team1 = new Team("team1");
    Member m1 = new Member("m1");
    Member m2 = new Member("m2");
    team1.getMembers().add(m1);
    team1.getMembers().add(m2);

    em.persist(team1);
    em.persist(m1);
    em.persist(m2);
  }
}
