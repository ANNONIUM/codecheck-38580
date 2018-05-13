package codecheck.entity;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class HashCalcEntity {
	private String seed;
	private Integer n;
	private Integer result;
	
	public String parseJsonString() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String script = mapper.writeValueAsString(this);
        return script;
	}
	
	public static HashCalcEntity generateFromJson(String json) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, HashCalcEntity.class);
	}
	
}
