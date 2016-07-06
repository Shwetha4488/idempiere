package com.tgb.activemq;

import java.util.Date;

import net.sf.ezmorph.array.AbstractArrayMorpher;
import net.sf.ezmorph.object.AbstractObjectMorpher;

public final class TimestampMorpher extends AbstractObjectMorpher {
	public TimestampMorpher(String[] dateFormats) {
	}

	@Override
	public Object morph(Object timeLong) {
		return new Date();
	}

	@Override
	public Class morphsTo() {
		return Date.class;
	}

}

