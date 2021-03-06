package info.zhwan.orm.jpa.ch06.mtm.type4;

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

  @OneToMany(mappedBy = "member")
  private List<Orders> orders = new ArrayList<>();

  public Member(String name) {
    this.name = name;
  }
}
