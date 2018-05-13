package codecheck.hashcalcmanagement;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import codecheck.entity.HashCalcEntity;

public class HashCalcApplicationImpl implements HashCalcApplication {
	private static final String HASH_CALC_HOST = "http://challenge-server.code-check.io";
	private static final String HASH_CALC_PATH = "/api/recursive/ask";
	private static final String HASH_CALC_PARAM_N = "n";
	private static final String HASH_CALC_PARAM_SEED = "seed";
	private static final int ONE_HOUR_MILLSEC = 3600000;
	private static final int STATUS_ERROR_SERVICE_UNAVAILABLE = 503;
	private ResteasyClient client;

	private Map<Integer, HashCalcEntity> calcResult = new HashMap<Integer, HashCalcEntity>();
	private HashCalcEntity baseEntity;
	private boolean isMinus = false;
	
	public HashCalcApplicationImpl() {
		client = new ResteasyClientBuilder().build();
	}
	
	public Integer calc(HashCalcEntity entity) throws ArithmeticException, IOException, InterruptedException {
		baseEntity = entity;
		calcResult = createOddMap(baseEntity.getN());
		setCalcResult();
		return calc(baseEntity.getN());
	}

	private Integer calc(Integer target) throws ArithmeticException {
		Integer result = null;
		switch (target) {
		case 0:
			result = 1;
			break;
		case 2:
			result = 0;
			break;
		default:
			if(Objects.nonNull(calcResult.get(target))) {
				result = calcResult.get(target).getResult();
			} else if (Math.floorMod(target, 2) == 0) {
				if(isMinus) {
					result = calc(target + 1) + calc(target + 2) + calc(target + 3) + calc(target + 4);	
				}else {
					result = calc(target - 1) + calc(target - 2) + calc(target - 3) + calc(target - 4);	
				}
			} else {
				throw new ArithmeticException("error! Calc Exception target is:" + target);
			}
		}
		return result;
	}

	private Map<Integer, HashCalcEntity> createOddMap(Integer target) {
		Integer maxOddNum = target;
		isMinus = Math.signum(maxOddNum) == -1;
		if (Math.floorMod(maxOddNum, 2) == 0) {
			if(isMinus) {
				maxOddNum = Math.abs(target);
			}
			maxOddNum = maxOddNum - 1;
			for (int i = 1; i <= maxOddNum; i = i + 2) {
				int oddVal = i;
				if(isMinus) {
					oddVal = i * -1;
				}
				calcResult.put(oddVal, null);
			}
			if(isMinus) {
				calcResult.put(1, null);
			}
		}else {
			calcResult.put(maxOddNum, null);
		}		
		return calcResult;
	}

	private HashCalcEntity recursive(Integer number) throws IOException, InterruptedException {
		ResteasyWebTarget target = client.target(HASH_CALC_HOST)
				.path(HASH_CALC_PATH)
				.queryParam(HASH_CALC_PARAM_N, number)
				.queryParam(HASH_CALC_PARAM_SEED, baseEntity.getSeed());
		Response response = target.request().get();
		if(response.getStatus()==STATUS_ERROR_SERVICE_UNAVAILABLE) {
			Thread.sleep(ONE_HOUR_MILLSEC);
			recursive(number);
		}
		String result = response.readEntity(String.class);
		response.close();
		return HashCalcEntity.generateFromJson(result);
	}

	private void setCalcResult() throws IOException, InterruptedException {
		for (Entry<Integer, HashCalcEntity> odd : calcResult.entrySet()) {
			calcResult.put(odd.getKey(), recursive(odd.getKey()));
		}
		if (Math.floorMod(baseEntity.getN(), 2) != 0) {
			return;
		}
		calcResult.entrySet()
		.stream()
		.sorted(calcForSort())
		.forEach(result -> {
			Integer calcTarget = Math.abs(result.getKey()) - 1;
			if(isMinus) {
				calcTarget = calcTarget * -1;
			}
			HashCalcEntity entity = HashCalcEntity.builder()
					.seed(baseEntity.getSeed())
					.n(calcTarget)
					.result(calc(calcTarget))
					.build();
			calcResult.put(calcTarget, entity);
		});
	}

	private Comparator<Entry<Integer, HashCalcEntity>> calcForSort() {
		if(isMinus) {
			return java.util.Collections.reverseOrder(java.util.Map.Entry.comparingByKey());
		}
		return java.util.Map.Entry.comparingByKey();
	}

}
