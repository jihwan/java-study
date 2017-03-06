package info.zhwan.orm.jpa.ch06.oto.type1.simple;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 1:1 주 테이블에 외래키 테스트 - 단방향
 *
 * @author zhwan
 */
@SpringBootApplication
public class Ch06OneToOneType1SimpleMain implements CommandLineRunner {
  public static void main(String[] args) {
    try (ConfigurableApplicationContext context = SpringApplication.run(Ch06OneToOneType1SimpleMain.class, args)) {
    }
  }

  @PersistenceContext
  EntityManager em;

  @Transactional
  @Override
  public void run(String... args) throws Exception {
    Locker locker1 = new Locker("locker1");
    em.persist(locker1);

    Member aaa = new Member("aaa");
    aaa.setLocker(locker1);
    em.persist(aaa);
  }
}
