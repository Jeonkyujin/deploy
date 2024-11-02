package project.saving_web_service.service;

import java.lang.reflect.Member;
import java.util.List;

public abstract class statusBaseService<T> {


	public abstract List<T> findStatus(String status);
}
