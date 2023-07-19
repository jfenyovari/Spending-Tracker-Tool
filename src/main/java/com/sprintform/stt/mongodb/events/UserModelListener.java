package com.sprintform.stt.mongodb.events;

import com.sprintform.stt.mongodb.entities.User;
import com.sprintform.stt.mongodb.services.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class UserModelListener extends AbstractMongoEventListener<User> {

	private final SequenceGeneratorService sequenceGenerator;

	@Autowired
	public UserModelListener(SequenceGeneratorService sequenceGenerator) {
		this.sequenceGenerator = sequenceGenerator;
	}

	@Override
	public void onBeforeConvert(BeforeConvertEvent<User> event) {
		if (event.getSource().getId() == null) {
			event.getSource().setId(sequenceGenerator.generateSequence(User.SEQUENCE_NAME));
		}
	}

}