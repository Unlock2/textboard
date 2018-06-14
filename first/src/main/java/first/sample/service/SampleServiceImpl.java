package first.sample.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import first.common.util.FileUtils;
import first.sample.dao.SampleDAO;

@Service("SampleService")
public class SampleServiceImpl implements SampleService {
	Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "fileUtils")
	private FileUtils fileUtils;

	@Resource(name = "sampleDAO")
	private SampleDAO sampleDAO;

	@Override
	public List<Map<String, Object>> selectBoardList(Map<String, Object> map) throws Exception {
		return sampleDAO.selectBoardList(map);
	}

	
	
	@Override
	public void insertBoard(Map<String, Object> map, HttpServletRequest request) throws Exception {
		sampleDAO.insertBoard(map);
		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
        for(int i=0, size=list.size(); i<size; i++){
            sampleDAO.insertFile(list.get(i));
            
            Iterator<Entry<String,Object>> iterator = map.entrySet().iterator();
            Entry<String,Object> entry = null;
            log.debug("--------------------printMap--------------------\n");
            while(iterator.hasNext()){
            entry = iterator.next();
            log.debug("key : "+entry.getKey()+",\tvalue : "+entry.getValue());
            }
            log.debug("");
            log.debug("------------------------------------------------\n");

        }
    }

		//MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		//Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		// Iterator는 어떤 데이터들의 집합체에서 컬렉션(Collection)으로부터 정보를 얻어올 수 있는 인터페이스이다.
		// Iterator를 사용하면 집합체와 개별원소들을 분리시켜서 생각할 수가 있는데,
		// 그 집합체가 어떤 클래스의 인스턴스인지 신경쓰지 않아도 되는 장점이 있다.
		//MultipartFile multipartFile = null;
		//while (iterator.hasNext()) {
			//multipartFile = multipartHttpServletRequest.getFile(iterator.next());
			// multipartHttpServletRequest의 getFile() 메서드는 request에 저장된 파일의 name을 인자로 받는다.
			// 그런데 이 name을 Iterator를 통해서 가져온다고 했다. 그것이 Iterator.next() 메서드이다.
			// hasNext() 메서드는 Iterator 내에 그 다음 데이터의 존재 유무를 알려주고,
			// next() 메서드는 Iterator 내의 데이터를 가져온 후, 커서를 다음위치로 이동시킨다.
			//if (multipartFile.isEmpty() == false) {
		//		log.debug("------------- file start -------------");
		//		log.debug("name : " + multipartFile.getName());
		//		log.debug("filename : " + multipartFile.getOriginalFilename());
		//		log.debug("size : " + multipartFile.getSize());
		//		log.debug("-------------- file end --------------\n");
		//	}
	//	}
//	}

	@Override
	public Map<String, Object> selectBoardDetail(Map<String, Object> map) throws Exception {
		sampleDAO.updateHitCnt(map);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> tempMap = sampleDAO.selectBoardDetail(map);
		resultMap.put("map", tempMap);
		
		List<Map<String, Object>> list = sampleDAO.selectFileList(map);
		resultMap.put("list", list);
		
		return resultMap;
	}

	@Override
	public void updateBoard(Map<String, Object> map) throws Exception {
		sampleDAO.updateBoard(map);
	}

	@Override
	public void deleteBoard(Map<String, Object> map) throws Exception {
		sampleDAO.deleteBoard(map);
	}

}