package info.zhwan.orm.jpa.ch06.otm;

import lombok.Getter;

import javax.persistence.*;

/**
 * @author zhwan
 */
@Entity @Getter
public class Member {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  public Member(String name) {
    this.name = name;
  }
}
