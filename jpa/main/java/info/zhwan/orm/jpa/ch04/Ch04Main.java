package info.zhwan.orm.jpa.ch04;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.stream.Stream;

@SpringBootApplication
@EntityScan(basePackageClasses = {Ch04Main.class, Jsr310JpaConverters.class})
@Transactional
public class Ch04Main implements CommandLineRunner {
  public static void main(String[] args) {
    try (ConfigurableApplicationContext context = SpringApplication.run(Ch04Main.class, args)) {
//      BeanDefinitionUtils.printBeanDefinitions(context);
    }
  }

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public void run(String... args) throws Exception {

    Stream.of(
      new Member("aaa"),
      new Member("bbb")
    )
      .map(member -> {
        member.setCal(new BigDecimal("123456.123456789123456789"));
        return member;
      })
      .forEach(member -> {
        entityManager.persist(member);
        System.out.printf("member is " + member);
      });

    Member member = entityManager.find(Member.class, 1L);
    System.err.println("member is " + member);
    System.err.println("member.cal is " + member.getCal().doubleValue());
  }
}
