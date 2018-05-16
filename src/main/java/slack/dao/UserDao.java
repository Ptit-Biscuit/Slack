package slack.dao;

import org.hibernate.query.Query;
import slack.model.Message;
import slack.model.User;

import java.util.List;
import java.util.stream.IntStream;

public class UserDao extends AbstractDao<User> {

	public Message likeMessage(User user, Message message) {
		return null;
	}

	public Message dislikeMessage(User user, Message message) {
		return null;
	}

	public List<Message> getMessages(List<User> users) {
		return this.execute(session -> {
			int size = users.size();
			StringBuilder builder = new StringBuilder("from Message where user.pseudo like :user0");
			IntStream.range(1, size).forEach(i -> builder.append(" and user.pseudo like :user" + i));
			Query query = session.createQuery(builder.toString());

			IntStream.range(0, size).forEach(i -> query.setParameter("user" + i, "%" + users.get(i).getPseudo() + "%"));
			return query.list();
		});
	}
}
