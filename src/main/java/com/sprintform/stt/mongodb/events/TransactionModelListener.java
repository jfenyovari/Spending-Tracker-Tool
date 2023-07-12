package com.sprintform.stt.mongodb.events;

import com.sprintform.stt.mongodb.entities.Transaction;
import com.sprintform.stt.mongodb.services.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class TransactionModelListener extends AbstractMongoEventListener<Transaction> {

	private SequenceGeneratorService sequenceGenerator;

	@Autowired
	public TransactionModelListener(SequenceGeneratorService sequenceGenerator) {
		this.sequenceGenerator = sequenceGenerator;
	}

	@Override
	public void onBeforeConvert(BeforeConvertEvent<Transaction> event) {
		if (event.getSource().getId() == null) {
			event.getSource().setId(sequenceGenerator.generateSequence(Transaction.SEQUENCE_NAME));
		}
	}

}
