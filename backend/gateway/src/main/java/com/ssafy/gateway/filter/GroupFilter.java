package com.ssafy.gateway.filter;

import com.ssafy.gateway.entity.GroupMember;
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
public class GroupFilter extends AbstractGatewayFilterFactory<GroupFilter.Config> {

    @Autowired
    private MemberRepositoryCustom memberRepositoryCustom;


    public GroupFilter(){super(Config.class);}

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


                GroupMember groupMember = memberRepositoryCustom.findGroupMemberByMemberIdAndGroupId(memberId, Integer.valueOf(groupId));

                if (groupMember == null)
                    throw new CustomException(ErrorCode.NOT_BELONG_TO_GROUP);

                System.out.println(groupMember.getName());
                System.out.println("그룹필터");
                return chain.filter(exchange);

        }, 1);
    }
}
