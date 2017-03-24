package info.zhwan.orm.jpa.ch06.mtm.type4;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author zhwan
 */
@Entity
@Getter
@Setter
public class Orders {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "MEMBER_ID")
  private Member member;

  @ManyToOne
  @JoinColumn(name = "PRODUCT_ID")
  private Product product;

  private int orderAmount;

  private LocalDateTime orderDate = LocalDateTime.now();
}
