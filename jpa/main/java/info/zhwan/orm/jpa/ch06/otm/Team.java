package info.zhwan.orm.jpa.ch06.otm;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhwan
 */
@Entity
@Getter
public class Team {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @OneToMany
  @JoinColumn(name = "TEAM_ID") // JoinColumn 을 꼭 명시해야 한다. 않그러면 join table 이 생성된다.
  private List<Member> members = new ArrayList<>();

  public Team(String name) {
    this.name = name;
  }
}
