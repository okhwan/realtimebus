package businfo.bus.service;

import businfo.bus.Repository.StationNumberRepository;
import businfo.bus.entity.StationNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StationNumberService {

    private final StationNumberRepository stationNumberRepository;

    public List<StationNumber> stationNumberFindByName(String stationName){
        return stationNumberRepository.findByStationNameLike(stationName);
    }

    public StationNumber stationNumberFindById(Long stationId){
        return stationNumberRepository.findByStationId(stationId);
    }


}
