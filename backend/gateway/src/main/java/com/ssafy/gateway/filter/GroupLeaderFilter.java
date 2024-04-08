package com.ssafy.gateway.filter;

import com.ssafy.gateway.entity.GroupMember;
import com.ssafy.gateway.entity.Member;
import com.ssafy.gateway.exception.CustomException;
import com.ssafy.gateway.exception.ErrorCode;
import com.ssafy.gateway.repository.MemberRepositoryCustom;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class GroupLeaderFilter extends AbstractGatewayFilterFactory<GroupLeaderFilter.Config> {

    @Autowired
    private MemberRepositoryCustom memberRepositoryCustom;


    public GroupLeaderFilter(){super(Config.class);}

    @Getter
    @Setter
    public static class Config {}


    @Override
    public GatewayFilter apply(Config config){
        return new OrderedGatewayFilter((exchange, chain) -> {
            String memberId = exchange.getRequest().getHeaders().get("X-Authorization-Id").get(0);

            Map<String, String> pathVariables = exchange.getAttributeOrDefault("org.springframework.cloud.gateway.support.ServerWebExchangeUtils.uriTemplateVariables", Map.of());

            // 경로 변수 사용 예시
            String groupId = pathVariables.get("groupId");
            if (groupId == null) {
                groupId = pathVariables.get("id");
                if (groupId == null) {
                    throw new CustomException(ErrorCode.NOT_PROVIDED_GROUP_ID);
                }
            }


            Member member = memberRepositoryCustom.findGroupLeaderByMemberIdAndGroupId(memberId, Integer.valueOf(groupId));

            if (member == null)
                throw new CustomException(ErrorCode.NOT_GROUP_LEADER);

            System.out.println(member.getName());
            System.out.println("그룹리더 필터");
            return chain.filter(exchange);

        }, 1);
    }
}

