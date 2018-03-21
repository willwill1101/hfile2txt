package com.ehl.dataselect.hbase.persistent;

import com.ehl.dataselect.hbase.fundamental.matchFunction.base.MatchFunction;
import com.ehl.dataselect.hbase.fundamental.matchFunction.base.SimMatchFunction;
import com.ehl.dataselect.hbase.fundamental.matchFunction.base.MatchFunction.OP;

public class Camera_location extends TRFieldBase{
	public  Camera_location(String name, int fixSize, boolean elike,int OFFSET) {
		super(name, fixSize, elike,OFFSET);
	}
	
	@Override
	public boolean match(byte[] bs, Object value_1, Object value_2) {
		return ByteUtil.getShort(bs, OFFSET) ==Integer.parseInt((String)value_1);
	}

	@Override
	public void fillBytes(byte[] bs, Object obj) {
		int i = obj == null ? 0 :(Integer) obj;
		ByteUtil.putShort(bs, (short) i, OFFSET);
	}

	@Override
	public Object readBytes(byte[] bs) {
		return ByteUtil.getShort(bs, OFFSET);
	}


	@Override
	public SimMatchFunction parseFromString(int level, String str_1,
			String str_2) throws IllegalArgumentException {
		try {
			return new SimMatchFunction(level, OP.IS, this,str_1, null);
		} catch (Exception e) {
			throw new IllegalArgumentException(String.format(
					MatchFunction.ERR_STR_ILL_ARGUMENT, e.getMessage()));
		}
	}
}
