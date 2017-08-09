package aperdomobo.training.unit_test.controller.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.BeanUtils;

public class DtoTransformer {

	private DtoTransformer() {
	}
	
	@SuppressWarnings("unchecked")
	public static <K, T> T toDto(K entity, Class<T> dtoClass) {
		try {
			Object dto = dtoClass.newInstance();
			BeanUtils.copyProperties(entity, dto);
			
			return (T) dto;
		} catch (Exception e) {
			throw new DtoTransformerException(e);
		}
	}
	
	public static <K, T> List<T> toDto(Collection<K> entityList, Class<T> dtoClass) {
		List<T> resultList = new ArrayList<T>();
		
		for(K entity : entityList) {
			resultList.add(toDto(entity, dtoClass));
		}
		
		return resultList;
	} 
}
