package com.ssafy.user.common.security;

import com.ssafy.user.common.ErrorCode;
import com.ssafy.user.common.exception.CustomException;
import com.ssafy.user.member.domain.Member;
import com.ssafy.user.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String memberId) {
        Member member = memberRepository.findByIdAndIsDeletedFalse(memberId)
                            .orElseThrow(()->new CustomException(ErrorCode.NO_SUCH_MEMBER_SAME_WITH_JWT_INFO));


        return new PrincipalDetails(member);
    }
}
