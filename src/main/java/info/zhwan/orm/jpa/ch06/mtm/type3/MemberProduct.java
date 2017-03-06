package info.zhwan.orm.jpa.ch06.mtm.type3;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author zhwan
 */
@Entity
@Getter @Setter
@IdClass(MemberProductId.class)
public class MemberProduct {

  @Id
  @ManyToOne
  @JoinColumn(name = "MEMBER_ID")
  private Member member;

  @Id
  @ManyToOne
  @JoinColumn(name = "PRODUCT_ID")
  private Product product;

  private int orderAmount;

  private LocalDateTime orderDate = LocalDateTime.now();
}
