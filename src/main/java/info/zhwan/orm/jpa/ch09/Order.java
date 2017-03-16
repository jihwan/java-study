package info.zhwan.orm.jpa.ch09;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhwan
 */
@Entity(name = "ORDERS")
@Getter
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Setter // for test
  private String name;

  @ElementCollection
  @CollectionTable(name = "ORDER_ITEM", joinColumns = @JoinColumn(name = "ORDER_ID"))
  @OrderColumn(name = "ITEM_IDX")
  private List<OrderItem> orderItems = new ArrayList<>();

  protected Order() {}

  public Order(String name, List<OrderItem> orderItems) {
    this.name = name;
    this.orderItems = orderItems;
  }
}

