package info.zhwan.orm.jpa.ch05;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @ToString @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "MEMBER_ID")
  private Long id;

  private String name;

  @ManyToOne(cascade = CascadeType.REMOVE, optional = true, fetch = FetchType.EAGER)
  @JoinColumn(name = "TEAM_ID", foreignKey = @ForeignKey(name = "TEAM_FK1", value = ConstraintMode.CONSTRAINT))
  private Team team;

  @Version
  private Long version;

  public Member(String name) {
    this.name = name;
  }

  public void setTeam(Team team) {
    if (this.team != null) {
      this.team.getMembers().remove(this);
    }
    this.team = team;

    if (this.team.getMembers().contains(this) == false) {
      this.team.getMembers().add(this);
    }
  }
}
