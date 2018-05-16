package slack.dao;

import org.hibernate.query.Query;
import slack.model.Channel;
import slack.model.Message;

import java.util.List;
import java.util.stream.IntStream;

public class ChannelDao extends AbstractDao<Channel> {

	public List<Message> search(List<String> text, boolean withFilter) {
		return this.execute(session -> {
			int size = text.size();
			StringBuilder builder = new StringBuilder("from Message where message like :text0");
			IntStream.range(1, size).forEach(i -> builder.append(" and message like :text" + i));
			Query query = session.createQuery(builder.toString());

			IntStream.range(0, size).forEach(i -> query.setParameter("text" + i, "%" + (withFilter ? "@" : "") + text.get(i) + "%"));
			return query.list();
		});
	}

	public List<Message> searchByFilter(List<String> userPseudo) {
		return this.search(userPseudo, true);
	}

	public List<Message> getMostLiked(Channel channel) {
		return this.execute(session -> {
			return session.createQuery("from Message order by note desc").list();
		});
	}

	public List<Message> getMostDisliked(Channel channel) {
		return this.execute(session -> {
			return session.createQuery("from Message order by note asc").list();
		});
	}
}
