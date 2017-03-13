package info.zhwan.orm.jpa.ch11.domain;

import lombok.Getter;

import javax.persistence.*;

/**
 * @author zhwan
 */
@Entity @Getter
public class Delivery {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne(mappedBy = "delivery")
  private Order order;

  @Embedded
  private Address address;

  @Enumerated(EnumType.STRING)
  private DeliveryStatus deliveryStatus;
}
