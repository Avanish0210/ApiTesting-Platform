package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.*;
import com.example.ApiTesting.entity.AssertionType;
import com.jayway.jsonpath.JsonPath;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssertionService {
    public void executeAssertions(ApiTestRequest request, ApiTestResponse response) {

        if (request.getAssertions() == null || request.getAssertions().isEmpty()) {
            return;
        }

        List<AssertionResult> results = new ArrayList<>();
        for (AssertionDto assertion : request.getAssertions()) {
            AssertionResult result = switch (assertion.getType()) {
                case STATUS_CODE -> checkStatus(assertion, response);
                case RESPONSE_TIME -> checkResponseTime(assertion, response);
                case HEADER -> checkHeader(assertion, response);
                case BODY_CONTAINS -> checkBodyContains(assertion, response);
                case JSON_PATH -> checkJsonPath(assertion, response);
            };
            results.add(result);
        }
        response.setAssertionResults(results);
        response.setAllPassed(results.stream().allMatch(AssertionResult::isPassed));
    }

    private AssertionResult checkStatus(AssertionDto assertion, ApiTestResponse response) {
        AssertionResult result = new AssertionResult();
        result.setType(AssertionType.STATUS_CODE);
        String expected = assertion.getExpectedValue();
        String actual = String.valueOf(response.getStatus());
        result.setExpected(expected);
        result.setActual(actual);
        result.setPassed(expected.equals(actual));
        result.setMessage(result.isPassed() ? "Status matched." : "Status did not match.");

        return result;
    }

    private AssertionResult checkResponseTime(AssertionDto assertion, ApiTestResponse response) {
        long expected = Long.parseLong(assertion.getExpectedValue());
        long actual = response.getResponseTime();
        AssertionResult result = new AssertionResult();
        result.setType(AssertionType.RESPONSE_TIME);
        result.setExpected(String.valueOf(expected));
        result.setActual(String.valueOf(actual));
        result.setPassed(actual <= expected);
        result.setMessage(result.isPassed() ? "Response time acceptable." : "Response too slow.");

        return result;
    }

    private AssertionResult checkBodyContains(AssertionDto assertion, ApiTestResponse response) {
        String expected = assertion.getExpectedValue();
        boolean found = response.getResponseBody().contains(expected);
        AssertionResult result = new AssertionResult();
        result.setType(AssertionType.BODY_CONTAINS);
        result.setExpected(expected);
        result.setActual(found ? "Found" : "Not Found");
        result.setPassed(found);

        return result;
    }
    private AssertionResult checkHeader(AssertionDto assertion, ApiTestResponse response) {
        String expected = assertion.getExpectedValue();
        String actual = "";
        for(HeaderDto header : response.getHeaders()){
            if(header.getKey().equalsIgnoreCase(assertion.getExpression())){
                actual = header.getValue();
                break;
            }
        }

        AssertionResult result = new AssertionResult();
        result.setType(AssertionType.HEADER);
        result.setExpected(expected);
        result.setActual(actual);
        result.setPassed(actual.contains(expected));

        return result;
    }
    private AssertionResult checkJsonPath(AssertionDto assertion, ApiTestResponse response) {

        AssertionResult result = new AssertionResult();
        result.setType(AssertionType.JSON_PATH);
        try {
            Object actualValue = JsonPath.read(response.getResponseBody(), assertion.getExpression());
            String actual = String.valueOf(actualValue);
            result.setExpected(assertion.getExpectedValue());
            result.setActual(actual);
            boolean passed = actual.equals(assertion.getExpectedValue());
            result.setPassed(passed);
            result.setMessage(passed ? "JSON Path matched." : "JSON Path value mismatch.");
        }
        catch (Exception ex) {
            result.setPassed(false);
            result.setExpected(assertion.getExpectedValue());
            result.setActual("N/A");
            result.setMessage(ex.getMessage());
        }
        return result;
    }

}
