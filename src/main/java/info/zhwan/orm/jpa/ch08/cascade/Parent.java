package info.zhwan.orm.jpa.ch08.cascade;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhwan
 */
@Entity
@Getter
public class Parent {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Child> childs = new ArrayList<>();
}
