package slack.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "creditCard")
public class CreditCard implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String code;

	@ManyToOne
	@JoinColumn(name = "administrator_id")
	private Admin administrator;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Admin getAdministrator() {
		return administrator;
	}

	public void setAdministrator(Admin administrator) {
		this.administrator = administrator;
	}
}
