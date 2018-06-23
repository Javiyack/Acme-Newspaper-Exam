
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ExamClass;
import repositories.ExamClassRepository;

@Component
@Transactional
public class StringToExamClassConverter implements Converter<String, ExamClass> {

	@Autowired
	private ExamClassRepository	repository;


	@Override
	public ExamClass convert(final String str) {
		ExamClass result;
		Integer id;
		try {
			if (StringUtils.isEmpty(str))
				result = null;
			else {
				id = Integer.valueOf(str);
				result = this.repository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
