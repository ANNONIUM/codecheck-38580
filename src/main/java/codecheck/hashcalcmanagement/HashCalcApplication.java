package codecheck.hashcalcmanagement;

import java.io.IOException;

import codecheck.entity.HashCalcEntity;
/**
 * Application for Hash Calculation
 * @author Anno NAKAYA
 *
 */
public interface HashCalcApplication {
	/**
	 * This Hash Calculation needs each Integer's calc result from target Integer to ZERO.
	 * First prepare for each Integers' calc result.
	 * Then, Return calc result for target.
	 * @param target
	 * @return
	 * @throws ArithmeticException
	 */
	public Integer calc(HashCalcEntity entity) throws ArithmeticException, IOException, InterruptedException;
}
