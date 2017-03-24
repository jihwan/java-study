package info.zhwan.orm.jpa.ch06.otmb;

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

  @ManyToOne
  @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false) // 읽기 전용으로 한다!!!
  private Team team;

  public Member(String name) {
    this.name = name;
  }

  public void setTeam(Team team) {
    this.team = team;
    if (this.team.getMembers().contains(this) == false) {
      this.team.getMembers().add(this);
    }
  }

  @Override
  public String toString() {
    return "Member{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", team=" + team +
      '}';
  }
}
