package info.zhwan.orm.jpa.ch11.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhwan
 */
@Entity(name = "ORDERS") @Getter
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="MEMBER_ID")
  private Member member;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItem> orderItems = new ArrayList<>();

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "DELIVERY_ID")
  private Delivery delivery;

  @Temporal(TemporalType.TIMESTAMP)
  private Date date;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;
}
