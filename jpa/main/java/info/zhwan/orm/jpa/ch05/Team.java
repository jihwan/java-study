package info.zhwan.orm.jpa.ch05;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhwan
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "TEAM_ID")
  private Long id;

  private String name;

  @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
  private List<Member> members = new ArrayList<>();

  public Team(String name) {
    this.name = name;
  }

  public void addMemember(Member member) {
    this.members.add(member);

    if (member.getTeam() != this) {
      member.setTeam(this);
    }
  }

  @Override
  public String toString() {
    return "Team{" +
      "id=" + id +
      ", name='" + name + '\'' +
      '}';
  }
}
