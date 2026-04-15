package fr.s42.chat.repositories;

import java.util.Optional;
import fr.s42.chat.models.Message;
import javax.sql.DataSource;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
	private final DataSource dataSource;

	public MessagesRepositoryJdbcImpl(DataSource dataSource){
		this.dataSource = dataSource;
	}

	@Override
	public Optional<Message> findById(Long id) {
		
		return Optional.empty();
	}
	
}
