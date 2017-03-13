package info.zhwan.orm.jpa.ch11.domain;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;

/**
 * @author zhwan
 */
@DiscriminatorValue("M")
@Getter
public class Movie extends Item {
  private String director;
}
