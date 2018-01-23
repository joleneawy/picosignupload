package sg.com.ctc.picoservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.com.ctc.picoservice.dao.RoomMastDao;
import sg.com.ctc.picoservice.model.RoomMast;

@Service("roomMastService")
@Transactional
public class RoomMastServiceImpl implements RoomMastService{
	
	@Autowired
    private RoomMastDao roomMastDao;
	
	public RoomMast findById(String id) {
        return roomMastDao.findById(id);
    }

}
