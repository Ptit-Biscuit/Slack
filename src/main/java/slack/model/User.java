package slack.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User implements Serializable {
	@Id
	@GeneratedValue
	private long id;

	@Column(unique = true)
	private String email;

	private String password;

	@Column(unique = true)
	private String pseudo;

	@ManyToMany
	@JoinTable(
			name = "User_Channel",
			joinColumns = { @JoinColumn(name = "user_id") },
			inverseJoinColumns = { @JoinColumn(name = "channel_id") }
	)
	private List<Channel> channels;

	@OneToMany(
			fetch = FetchType.EAGER,
			mappedBy = "user"
	)
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	private List<Message> messages;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public List<Channel> getChannels() {
		return channels;
	}

	public void setChannels(List<Channel> channels) {
		this.channels = channels;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
}
