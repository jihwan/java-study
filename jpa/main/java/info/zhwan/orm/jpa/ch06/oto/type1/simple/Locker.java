package info.zhwan.orm.jpa.ch06.oto.type1.simple;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author zhwan
 */
@Entity
@Getter
public class Locker {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  public Locker(String name) {
    this.name = name;
  }
}
