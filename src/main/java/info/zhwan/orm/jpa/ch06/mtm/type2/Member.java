package info.zhwan.orm.jpa.ch06.mtm.type2;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhwan
 */
@Entity
@Getter
public class Member {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToMany
  @JoinTable(name = "MEMBER_PRODUCT",
    joinColumns = {@JoinColumn(name = "MEMBER_ID")},
    inverseJoinColumns = {@JoinColumn(name = "PRODUCT_ID")})
  private List<Product> products = new ArrayList<>();

  public Member(String name) {
    this.name = name;
  }
}
