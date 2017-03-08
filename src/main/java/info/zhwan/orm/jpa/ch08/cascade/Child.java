package info.zhwan.orm.jpa.ch08.cascade;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author zhwan
 */
@Entity
@Getter
public class Child {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne @Setter
  private Parent parent;

  @Override
  public String toString() {
    return "Child{" +
      "id=" + id +
      ", parent=" + parent +
      '}';
  }
}
