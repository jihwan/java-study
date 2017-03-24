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
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToMany(mappedBy = "products")
  private List<Member> members = new ArrayList<>();

  public Product(String name) {
    this.name = name;
  }
}
