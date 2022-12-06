package businfo.bus.Repository;

import businfo.bus.entity.MemberForm;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;


@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("단일회원 테스트")
    void memberSave(){
        MemberForm memberForm = new MemberForm("홍길동이", "fhskaos", "1234", "87949550", "청주시 청원구", "srsar@naver.com");

        MemberForm saveMember = memberRepository.save(memberForm);

        MemberForm findMember = memberRepository.findById(saveMember.getId());
        Assertions.assertThat(findMember.getEmail()).isEqualTo(saveMember.getEmail());
    }

    @Test
    @DisplayName("모든 회원 테스트")
    void memberFindAll(){
        MemberForm memberForm = new MemberForm("홍길동이", "fhskaos", "1234", "87949550", "청주시 청원구", "srsar@naver.com");
        memberRepository.save(memberForm);

        List<MemberForm> memberFormList = memberRepository.findAll();
        Assertions.assertThat(memberFormList.size()).isEqualTo(3);
        Assertions.assertThat(memberFormList.get(memberFormList.size()-1).getEmail()).isEqualTo(memberForm.getEmail());

    }

}