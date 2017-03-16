package info.zhwan.orm.jpa.ch09;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;

/**
 * @author zhwan
 */
@SpringBootApplication
public class Ch09ValueCollectionMappingMain {
  public static void main(String[] args) {
    try (ConfigurableApplicationContext context = SpringApplication.run(Ch09ValueCollectionMappingMain.class, args)) {

      ValueCollectionTester test = context.getBean(ValueCollectionTester.class);
      Order order = test.insert();

      System.out.println("================================================================");
      order = test.updateOrder(order);

//      System.out.println("================================================================");
//      order = test.updateClearOrderItem(order);

      System.out.println("================================================================");
      order = test.updateAddItem(order);

      System.out.println("================================================================");
      test.updateRemoveItem(order);

      System.out.println("================================================================");
      order = test.updateRemoveAllAndAddItemOne(order);

//      System.out.println("================================================================");
//      test.delete(order);
    }
  }

  @Component
  @Transactional
  static class ValueCollectionTester {
    @PersistenceContext
    EntityManager em;

    public Order insert() {
      OrderItem a = new OrderItem("a", 1);
      OrderItem b = new OrderItem("b", 2);

      Order order = new Order("order1", Arrays.asList(a, b));
      em.persist(order);
      return order;
    }

    public Order updateAddItem(Order order) {
      order = em.merge(order);
      OrderItem c = new OrderItem("c", 3);
      order.getOrderItems().add(c);

      return order;
    }

    public Order updateRemoveItem(Order order) {
      order = em.merge(order);
      order.getOrderItems().remove(2);
      return order;
    }


    public Order updateRemoveAllAndAddItemOne(Order order) {
      order = em.merge(order);
      order.getOrderItems().clear();
      order.getOrderItems().add(new OrderItem("z", 100));
      return order;
    }

    public void delete(Order order) {
      order = em.getReference(Order.class, order.getId());
      em.remove(order);
    }

    public Order updateOrder(Order order) {
      order = em.getReference(Order.class, order.getId());
      order.setName("order1_updated");
      return order;
    }

    public Order updateClearOrderItem(Order order) {
      order = em.find(Order.class, order.getId());
      order.getOrderItems().clear();
      return order;
    }
  }
}
