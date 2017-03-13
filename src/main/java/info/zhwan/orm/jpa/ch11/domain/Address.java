package info.zhwan.orm.jpa.ch11.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

/**
 * @author zhwan
 */
@Embeddable @Getter
public class Address {

  private String city;
  private String street;
  private String zipcode;

  public Address(String city, String street, String zipcode) {
    this.city = city;
    this.street = street;
    this.zipcode = zipcode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Address address = (Address) o;

    if (!getCity().equals(address.getCity())) return false;
    if (!getStreet().equals(address.getStreet())) return false;
    return getZipcode().equals(address.getZipcode());
  }

  @Override
  public int hashCode() {
    int result = getCity().hashCode();
    result = 31 * result + getStreet().hashCode();
    result = 31 * result + getZipcode().hashCode();
    return result;
  }
}
