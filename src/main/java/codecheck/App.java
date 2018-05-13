package codecheck;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;

import codecheck.entity.HashCalcEntity;
import codecheck.exception.ArgsNullException;
import codecheck.exception.ArgsSizeException;
import codecheck.exception.IllegalArgsException;
import codecheck.hashcalcmanagement.HashCalcApplicationImpl;
import codecheck.util.Const;
import codecheck.util.IntegerUtil;

public class App {
	public static void main(String[] args) {
		try {
			List<String> argsList = Optional.ofNullable(
					Arrays
					.stream(args)
					.collect(Collectors.toList()))
					.orElse(null);
			AppArgValidator.validate(argsList);
			
			HashCalcEntity entity = HashCalcEntity.builder()
					.seed(argsList.get(Const.INDEX_SEED_STRING))
					.n(IntegerUtil.convert(argsList.get(Const.INDEX_CALC_TARGET)))
					.build();
			
			HashCalcApplicationImpl application = new HashCalcApplicationImpl();
			entity.setResult(application.calc(entity));
			System.out.println(entity.getResult());
		}catch(ArgsNullException e) {
			loggingError(e);
		}catch(ArgsSizeException e) {
			loggingError(e);
		}catch(IllegalArgsException e) {
			loggingError(e);
		} catch (JsonProcessingException e) {
			loggingError(e);
		} catch (InterruptedException e) {
			loggingError(e);
		} catch (IOException e) {
			loggingError(e);
		} catch (ArithmeticException e) {
			loggingError(e);
		}
	}
	
	private static void loggingError(Exception e) {
		System.out.println(e.getMessage());
		System.err.println(e.getMessage());
	}
}
