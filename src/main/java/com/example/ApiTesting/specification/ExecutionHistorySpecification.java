package com.example.ApiTesting.specification;

import com.example.ApiTesting.entity.ExecutionHistory;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.UUID;

public class ExecutionHistorySpecification {

    public static Specification<ExecutionHistory> hasRequestId(UUID requestId){

        return (root, query, cb) ->
                cb.equal(root.get("savedRequest").get("id"), requestId);

    }

    public static Specification<ExecutionHistory> hasStatus(Integer status){

        return (root, query, cb) ->

                status == null

                        ? null

                        : cb.equal(root.get("statusCode"), status);

    }

    public static Specification<ExecutionHistory> hasSuccess(Boolean success){

        return (root, query, cb) ->

                success == null

                        ? null

                        : cb.equal(root.get("success"), success);

    }

    public static Specification<ExecutionHistory> minResponseTime(Long min){

        return (root, query, cb) ->

                min == null

                        ? null

                        : cb.greaterThanOrEqualTo(
                        root.get("responseTime"),
                        min);

    }

    public static Specification<ExecutionHistory> maxResponseTime(Long max){

        return (root, query, cb) ->

                max == null

                        ? null

                        : cb.lessThanOrEqualTo(
                        root.get("responseTime"),
                        max);

    }

    public static Specification<ExecutionHistory> fromDate(LocalDateTime from){

        return (root, query, cb) ->

                from == null

                        ? null

                        : cb.greaterThanOrEqualTo(
                        root.get("executedAt"),
                        from);

    }

    public static Specification<ExecutionHistory> toDate(LocalDateTime to){

        return (root, query, cb) ->

                to == null

                        ? null

                        : cb.lessThanOrEqualTo(
                        root.get("executedAt"),
                        to);

    }

}
