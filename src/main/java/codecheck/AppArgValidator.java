package codecheck;

import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;

import codecheck.exception.ArgsNullException;
import codecheck.exception.ArgsSizeException;
import codecheck.exception.IllegalArgsException;
import codecheck.util.Const;
import codecheck.util.IntegerUtil;

public class AppArgValidator {
	
	public static void validate(List<String> argsList) {
		if(CollectionUtils.isEmpty(argsList)) {
			throw new ArgsNullException();
		}
		if(argsList.size()>2) {
			throw new ArgsSizeException(argsList.size());
		}
		if(!IntegerUtil.isInteger(argsList.get(Const.INDEX_CALC_TARGET))) {
			throw new IllegalArgsException(argsList.get(Const.INDEX_CALC_TARGET));
		}
		if(Objects.isNull(argsList.get(Const.INDEX_SEED_STRING))||
				argsList.get(Const.INDEX_SEED_STRING).isEmpty()) {
			throw new IllegalArgsException(argsList.get(Const.INDEX_SEED_STRING));
		}
	}
}
