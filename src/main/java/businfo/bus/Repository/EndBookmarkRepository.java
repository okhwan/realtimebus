package businfo.bus.Repository;

import businfo.bus.entity.EndBookmark;
import businfo.bus.entity.MemberForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EndBookmarkRepository extends JpaRepository<EndBookmark, Long> {
    List<EndBookmark> findByMemberForm(MemberForm memberForm);
    List<EndBookmark> findByMemberFormId(Long userId);
}
