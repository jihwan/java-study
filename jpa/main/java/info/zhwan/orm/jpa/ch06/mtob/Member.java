package info.zhwan.orm.jpa.ch06.mtob;

import lombok.Getter;

import javax.persistence.*;

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

  @ManyToOne
  @JoinColumn(name = "TEAM_ID")
  private Team team;

  public void setTeam(Team team) {
    this.team = team;

    if (this.team.getMembers().contains(this) == false) {
      this.team.getMembers().add(this);
    }
  }
}