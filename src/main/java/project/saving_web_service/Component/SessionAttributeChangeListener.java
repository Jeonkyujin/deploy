package project.saving_web_service.Component;

import java.util.Set;

import javax.servlet.annotation.WebListener;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;


@WebListener
public class SessionAttributeChangeListener implements HttpSessionAttributeListener {

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {

		if ("currentTopRanking".equals(event.getName())) {
			// 이전 값 가져오기
			Set<String> previousRanking = (Set<String>)event.getValue();
			HttpSession session = event.getSession();
			Set<String> currentRanking = (Set<String>)event.getSession().getAttribute("currentTopRanking");

			// 이전 값과 새로운 값이 다를 경우에만 출력 또는 작업 수행
			if (!previousRanking.equals(currentRanking)) {
				String s = sessionAttributeHandler.handleAttributeChange();
                if(!s.isEmpty()){
					session.setAttribute("previousTopRanking", previousRanking);
					session.setAttribute("checkpoint", true);
				}

			}

		}
	}

}
