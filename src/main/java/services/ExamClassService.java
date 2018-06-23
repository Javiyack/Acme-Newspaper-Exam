
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Actor;
import domain.Administrator;
import domain.ExamClass;
import repositories.ExamClassRepository;

@Service
@Transactional 
public class ExamClassService {

	// Managed repositories ------------------------------------------------
	@Autowired
	private ExamClassRepository examClassRepository;

	// Services
	@Autowired
	private ActorService actorService;

	@Autowired
	private Validator validator;

	// Constructor ----------------------------------------------------------
	public ExamClassService() {
		super();
	}

	// Methods CRUD ---------------------------------------------------------

	public ExamClass create() {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor instanceof Administrator, "actor.admin.permisions.needed");

		final ExamClass examClass = new ExamClass();
		examClass.setAdmin((Administrator) actor);
		examClass.setTicker(this.generateTicker());
		examClass.setDraftMode(true);
		examClass.setMoment(new Date());
		return examClass;
	}

	private String generateTicker() {
		String result = "";
		Calendar calendar = Calendar.getInstance();

		result += (("" + calendar.get(Calendar.YEAR) % 100).length() == 2) ? calendar.get(Calendar.YEAR) % 100
				: "0" + calendar.get(Calendar.YEAR) % 100;
		int mes = calendar.get(Calendar.MONTH)+1;
		result += (("" + mes).length() == 2) ? mes : "0" + mes;
		result += (("" + calendar.get(Calendar.DAY_OF_MONTH)).length() == 2) ? calendar.get(Calendar.DAY_OF_MONTH)
				: "0" + calendar.get(Calendar.DAY_OF_MONTH);
		result += "-";
		Random rnd = new Random();
		for (int i = 0; i < 4; i++) {
			result += (char) (rnd.nextInt(26) + 'A');
		}

		return result;
	}

	public ExamClass findOne(final int examClassId) {
		ExamClass result;

		result = this.examClassRepository.findOne(examClassId);
		Assert.notNull(result);

		return result;
	}

	public Collection<ExamClass> findAll() {

		Collection<ExamClass> result;

		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor instanceof Administrator, "actor.admin.permisions.needed");

		result = this.examClassRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Collection<ExamClass> findAllFinalByNewspaperId(int newspaperId) {
		Assert.notNull(newspaperId);

		Collection<ExamClass> result;

		result = this.examClassRepository.findAllFinalByNewspaperId(newspaperId);
		Assert.notNull(result);

		return result;
	}
	public Collection<ExamClass> findAllByNewspaperId(Integer newspaperId) {
		Assert.notNull(newspaperId);

		Collection<ExamClass> result;

		result = this.examClassRepository.findAllByNewspaperId(newspaperId);
		Assert.notNull(result);

		return result;
	}

	public ExamClass save(final ExamClass examClass) {

		Assert.notNull(examClass);
		ExamClass savedExamClass;
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor instanceof Administrator, "examclass.admin.permisions.needed");
		if (examClass.getId() == 0) {
			examClass.setMoment(new Date());
		}else {
			savedExamClass = examClassRepository.findOne(examClass.getId());
			examClass.setMoment(savedExamClass.getMoment());
		}
		
		savedExamClass = this.examClassRepository.save(examClass);

		return savedExamClass;
	}

	public void delete(final Integer examClassId) {
		Assert.notNull(examClassId, "assert.error.msg.null.object");
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor instanceof Administrator, "examclass.admin.permisions.needed");
		ExamClass examClass = examClassRepository.findOne(examClassId);
		Assert.isTrue(actor.equals(examClass.getAdmin()), "examclass.delete.owner.permisions.needed");
		this.examClassRepository.delete(examClassId);
	}

	public void flush() {
		this.examClassRepository.flush();

	}

	public Collection<ExamClass> findAllByOwnerId(int ownerId) {
		return this.examClassRepository.findAllByOwnerId(ownerId);
	}

	
	public ExamClass reconstruct(ExamClass examClass, BindingResult binding) {
		if (examClass.getId() == 0) {
			examClass.setMoment(new Date());
			validator.validate(examClass, binding);
		} else {
			ExamClass dbObject = examClassRepository.findOne(examClass.getId());
			examClass.setAdmin(dbObject.getAdmin());
			examClass.setMoment(dbObject.getMoment());
			validator.validate(examClass, binding);
			Assert.isTrue(dbObject.getDraftMode(), "examclass.nodraft.save.blocked");
			if (!examClass.getDraftMode()) {
				Assert.notNull(examClass.getNewspaper(), "examclass.nodraft.nonewspaper.save.blocked");
			}
		}
		return examClass;
	}

	public Collection<Object> findTopExamClassAdmines() {
		return this.examClassRepository.findTopExamClassAdmines();
	}


}
