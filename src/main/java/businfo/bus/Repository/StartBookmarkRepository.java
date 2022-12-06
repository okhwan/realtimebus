package businfo.bus.Repository;

import businfo.bus.entity.MemberForm;
import businfo.bus.entity.StartBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StartBookmarkRepository extends JpaRepository<StartBookmark, Long> {
    List<StartBookmark> findByMemberFormId(Long memberId);

    List<StartBookmark> findByMemberForm(MemberForm memberForm);
}
