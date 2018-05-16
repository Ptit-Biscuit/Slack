package slack.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "admin")
public class Admin extends User {

	@OneToMany(
			fetch = FetchType.EAGER,
			mappedBy = "administrator"
	)
	@Fetch(value = FetchMode.SUBSELECT)
	@Cascade(CascadeType.SAVE_UPDATE)
	private List<CreditCard> creditCards;

	public List<CreditCard> getCreditCards() {
		return creditCards;
	}

	public void setCreditCards(List<CreditCard> creditCards) {
		this.creditCards = creditCards;
	}
}
