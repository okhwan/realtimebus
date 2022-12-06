package businfo.bus.Repository;

import businfo.bus.entity.MemberForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberForm, Long> {
    MemberForm findByEmail(String email);
    MemberForm findByIdAndEmail(String id, String email);

    MemberForm findById(String memberId);

    MemberForm findByUserId(Long userId);

    MemberForm findByIdAndPw(String id, String pw);
}
