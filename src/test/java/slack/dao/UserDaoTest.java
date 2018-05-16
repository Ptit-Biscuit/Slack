package slack.dao;

import org.junit.Assert;
import org.junit.Test;
import slack.model.*;

import javax.persistence.PersistenceException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UserDaoTest {
	@Test
	public void insertUser() {
		User user = new User();
		user.setEmail("vincent.brebion@epsi.fr");
		user.setPassword("Password");
		user.setPseudo("Ptit-Biscuit");

		Long id = new UserDao().save(user);

		Assert.assertEquals("Ptit-Biscuit", new UserDao().get(id).getPseudo());
	}

	@Test(expected = PersistenceException.class)
	public void uniquePseudo() {
		User user = new User();
		user.setEmail("test@epsi.fr");
		user.setPassword("Password");
		user.setPassword("Pseudo");

		User userBis = new User();
		userBis.setEmail("test@epsi.fr");
		userBis.setPassword("Password");
		userBis.setPassword("Pseudo");

		new UserDao().save(user);
		new UserDao().save(userBis);
	}

	@Test
	public void getUserMessages() {
		User user = new User();
		user.setEmail("vincent.brebion@epsi.fr");
		user.setPassword("Password");
		user.setPseudo("Ptit-Biscuit");

		Message message = new Message();
		message.setMessage("Bonjour tout monde");
		message.setUser(user);

		Message message2 = new Message();
		message2.setMessage("Bonjour tout le monde (correction)");
		message2.setUser(user);

		user.setMessages(Arrays.asList(message, message2));

		Long id = new UserDao().save(user);

		Assert.assertEquals(2, new UserDao().getMessages(Collections.singletonList(user)).size());
	}

	@Test
	public void deleteUser() {
		User user = new User();
		user.setEmail("test2@epsi.fr");
		user.setPassword("Password");
		user.setPseudo("PseudoTest");

		Long id = new UserDao().save(user);
		new UserDao().delete(user);

		Assert.assertNull(new UserDao().get(id));
	}

	@Test
	public void createChannel() {
		Channel channel = new Channel();
		channel.setPublic(true);
		channel.setName("PtitChannel");

		User user = new User();
		user.setEmail("Test@epsi.fr");
		user.setPassword("Password");
		user.setPseudo("User");

		User userBis = new User();
		userBis.setEmail("Test2@epsi.fr");
		userBis.setPassword("Password");
		userBis.setPseudo("User2");

		new UserDao().save(user);
		new UserDao().save(userBis);

		channel.setUsers(Arrays.asList(user, userBis));

		Long id = new ChannelDao().save(channel);

		Assert.assertTrue(new ChannelDao().get(id).isPublic());
		Assert.assertEquals(2, new ChannelDao().get(id).getUsers().size());
	}

	@Test
	public void insertMessage() {
		Channel channel = new Channel();
		channel.setPublic(false);
		channel.setName("PtitChannel2");

		Message m1 = new Message();
		m1.setMessage("Bonjour ceci est un message");
		m1.setChannel(channel);

		channel.setMessages(Collections.singletonList(m1));

		Long id = new ChannelDao().save(channel);

		Assert.assertEquals(1, new ChannelDao().get(id).getMessages().size());
	}

	@Test
	public void deleteChannel() {
		Channel channel = new Channel();
		channel.setPublic(true);
		channel.setName("PtitChannel3");

		Message m1 = new Message();
		m1.setMessage("Bonjour ceci est un autre message");
		m1.setChannel(channel);

		channel.setMessages(Collections.singletonList(m1));

		Long id = new ChannelDao().save(channel);
		new ChannelDao().delete(channel);

		Assert.assertNull(new ChannelDao().get(id));
	}

	@Test
	public void insertAdmin() {
		Admin admin = new Admin();
		admin.setEmail("admin@epsi.fr");
		admin.setPassword("Admin");
		admin.setPseudo("Admin");

		CreditCard card = new CreditCard();
		card.setCode("123456789");
		card.setAdministrator(admin);

		admin.setCreditCards(Collections.singletonList(card));

		Long id = new AdminDao().save(admin);

		Assert.assertEquals("Admin", new AdminDao().get(id).getPseudo());
		Assert.assertEquals(1, new AdminDao().get(id).getCreditCards().size());
	}

	@Test
	public void search() {
		Channel channel = new Channel();
		channel.setPublic(true);
		channel.setName("Name");

		User user = new User();
		user.setEmail("vincent.brebion@epsi.fr");
		user.setPassword("Password");
		user.setPseudo("Searcher");

		channel.setUsers(Collections.singletonList(user));

		Message message = new Message();
		message.setMessage("Bonjour tout monde");
		message.setUser(user);

		Message message2 = new Message();
		message2.setMessage("Plop");
		message2.setUser(user);

		Message message3 = new Message();
		message3.setMessage("Bonjour @Benjamin");
		message3.setUser(user);

		user.setMessages(Arrays.asList(message, message2, message3));

		new UserDao().save(user);
		new ChannelDao().save(channel);

		Assert.assertEquals(1, new ChannelDao().search(Collections.singletonList("Plop"), false).size());
		Assert.assertEquals(1, new ChannelDao().search(Collections.singletonList("Benjamin"), true).size());
	}

	@Test
	public void getMostLiked() {
		Channel channel = new Channel();
		channel.setPublic(true);
		channel.setName("Name");

		User user = new User();
		user.setEmail("vincent.brebion@epsi.fr");
		user.setPassword("Password");
		user.setPseudo("Searcher");

		channel.setUsers(Collections.singletonList(user));

		Message message = new Message();
		message.setMessage("Bonjour tout monde");
		message.setUser(user);
		message.setNote(3);

		Message message2 = new Message();
		message2.setMessage("Plop");
		message2.setUser(user);
		message2.setNote(-2);

		Message message3 = new Message();
		message3.setMessage("Bonjour @Benjamin");
		message3.setUser(user);

		user.setMessages(Arrays.asList(message, message2, message3));
		new ChannelDao().save(channel);

		Assert.assertEquals(3, new ChannelDao().getMostLiked(channel).get(0).getNote());
	}

	@Test
	public void getMostDisliked() {
		Channel channel = new Channel();
		channel.setPublic(true);
		channel.setName("Name");

		User user = new User();
		user.setEmail("vincent.brebion@epsi.fr");
		user.setPassword("Password");
		user.setPseudo("Searcher");

		channel.setUsers(Collections.singletonList(user));

		Message message = new Message();
		message.setMessage("Bonjour tout monde");
		message.setUser(user);
		message.setNote(3);

		Message message2 = new Message();
		message2.setMessage("Plop");
		message2.setUser(user);
		message2.setNote(-2);

		Message message3 = new Message();
		message3.setMessage("Bonjour @Benjamin");
		message3.setUser(user);

		user.setMessages(Arrays.asList(message, message2, message3));
		new ChannelDao().save(channel);

		Assert.assertEquals(-2, new ChannelDao().getMostDisliked(channel).get(0).getNote());
	}
}