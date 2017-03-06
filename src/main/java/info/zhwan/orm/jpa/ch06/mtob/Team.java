package info.zhwan.orm.jpa.ch06.mtob;

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
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @OneToMany(mappedBy = "team")
  private List<Member> members = new ArrayList<>();

  public void addMember(Member member) {
    this.members.add(member);
    if (member.getTeam() != this) {
      member.setTeam(this);
    }
  }
}
