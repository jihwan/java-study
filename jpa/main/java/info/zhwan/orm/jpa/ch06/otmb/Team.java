package info.zhwan.orm.jpa.ch06.otmb;

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

  @OneToMany
  @JoinColumn(name = "TEAM_ID")
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
