package businfo.bus.realtime;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class DifferentiateTransferList {
        public List<BusArriveDto> noTransferListDiffer(List<OneTransferDto> oneTransferList){
        List<BusArriveDto> noTransferList = new ArrayList<BusArriveDto>();
        for (OneTransferDto oneTransferDto : oneTransferList) {
            if(oneTransferDto.getEndBusArriveDto() == null){
                noTransferList.add(oneTransferDto.getStartBusArriveDto());
            }
        }
        return noTransferList;
    }
    public List<OneTransferDto> oneTransferListDiffer(List<OneTransferDto> oneTransferList){
        oneTransferList.removeIf(OneTransferDto -> OneTransferDto.getEndBusArriveDto() == null);
        return oneTransferList;
    }

}
