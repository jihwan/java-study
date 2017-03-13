package info.zhwan.orm.jpa.ch11.domain;

import lombok.Getter;

import javax.persistence.*;

/**
 * @author zhwan
 */
@Entity
@Getter
public class OrderItem {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ORDERS_ID")
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ITEM_ID")
  private Item item;

  private int orderPrice;

  private int count;
}
