package businfo.bus.service;

import businfo.bus.Repository.MemberRepository;
import businfo.bus.entity.MemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@RequiredArgsConstructor
public class MemberFormService {
    @PersistenceContext
    EntityManager em;
    private final MemberRepository memberRepository;

    public MemberForm memberFormSave(MemberForm memberForm){ //save member
        return memberRepository.save(memberForm);
    }

    public MemberForm memberFormCorrect(MemberForm memberForm){ //If identity id is same then JPA Repository are merge this information
        return memberRepository.save(memberForm); //그래서 위 코드랑 같다.
    }

    public MemberForm memberFormFindByEmail(String memberEmail){ //Find key is email
        return em.find(MemberForm.class, memberEmail);
    }
    public boolean memberFormLogin(MemberForm memberForm){ //로그인시 id가 같은 값 가져오고 pw 비교 (id is unique value)
        MemberForm findMember = em.find(MemberForm.class, memberForm.getId());
        return findMember.getPw().equals(memberForm.getPw()); //boolean 해당 로그인 실패 처리는 밖에서 진행
    }


}
