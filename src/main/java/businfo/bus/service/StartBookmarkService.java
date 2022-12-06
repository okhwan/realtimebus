package businfo.bus.service;

import businfo.bus.Repository.StartBookmarkRepository;
import businfo.bus.entity.StartBookmark;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
        * 이 클래스는 버스 출발 정류장에 대한 회원의 즐겨찾기를 위함으로
        * 1. 회원은 출발 정류장을 저장할 수 있어야한다. >> startBookmarkSave
        * 1-1. 회원은 출발 정류장을 저장하는 과정에서 정류장 검색시 검색 바 아래에 현재 치고 있는 단어에 대한 정류장이 출력 되어야 한다. >> StationNumber클래스로 부터 정보를 받아와서 출력해야 한다.(fragment)
        * 2. 회원은 저장해 놓은 출발 정류장을 통해 출발 정류장을 불러 올 수 있어야 한다. >> findById(회원로그인이 된 id를 통해서 DB로부터 해당 테이블의 정보를 가져와야 한다.)
        * 3. 회원은 출발 정류장을 불러오고 해당 정류장을 출발 정류장으로 선택 할 수 있어야 한다. >> 실시간정보클래스에 들어가야한다.
        *
        */

@Service
@RequiredArgsConstructor
public class StartBookmarkService {

    private final StartBookmarkRepository startBookmarkRepository;

    public StartBookmark startBookmarkSave(StartBookmark startBookmark){
        return startBookmarkRepository.save(startBookmark);
    }

}
