package sg.com.ctc.picoservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.com.ctc.picoservice.dao.EventTransDao;
import sg.com.ctc.picoservice.model.EventTrans;

@Service("eventTransService")
@Transactional
public class EventTransServiceImpl implements EventTransService{
	
	@Autowired
    private EventTransDao eventTransDao;
	
	public EventTrans findById(int id) {
        return eventTransDao.findById(id);
    }

}
