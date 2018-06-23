
package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.ExamClass;
import domain.Newspaper;
import services.ActorService;
import services.ExamClassService;
import services.NewspaperService;

@Controller
@RequestMapping("/examclass/administrator")
public class ExamClassAdminController extends AbstractController {
	// Services ---------------------------------------------------------------
	@Autowired
	private ExamClassService examClassService;

	@Autowired
	private ActorService actorService;
	@Autowired
	private NewspaperService newspaperService;

	// Constructor -----------------------------------------------------------
	public ExamClassAdminController() {
		super();
	}

	// Display -----------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int examClassId) {
		final ModelAndView result;
		final ExamClass examClass;

		examClass = this.examClassService.findOne(examClassId);
		Collection<Newspaper> newspapers = this.newspaperService.findAll();

		result = new ModelAndView("examclass/administrator/display");
		result.addObject("examClass", examClass);
		result.addObject("newspapers", newspapers);
		result.addObject("edit", false);

		return result;
	}

	// Create ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ExamClass examClass = this.examClassService.create();

		final ModelAndView result = new ModelAndView("examclass/administrator/create");
		Collection<Newspaper> newspapers = this.newspaperService.findAll();

		result.addObject("examClass", examClass);
		result.addObject("newspapers", newspapers);
		result.addObject("edit", true);
		return result;
	}

	// Edit ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int examClassId) {
		final ModelAndView result;
		final ExamClass examClass;

		examClass = this.examClassService.findOne(examClassId);
		result = createEditModelAndView(examClass);

		return result;
	}

	// Edit ---------------------------------------------------------------
		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public ModelAndView delete(@RequestParam final int examClassId) {
			ModelAndView result;
			try {
				this.examClassService.delete(examClassId);
				Actor actor = this.actorService.findByPrincipal();
				Assert.isTrue(actor instanceof Administrator);
				final Collection<ExamClass> examClasses = this.examClassService.findAllByOwnerId(actor.getId());
				result = new ModelAndView("examclass/administrator/list");
				result.addObject("examClasses", examClasses);
			} catch (Throwable oops) {
				Actor actor = this.actorService.findByPrincipal();
				Assert.isTrue(actor instanceof Administrator);
				final Collection<ExamClass> examClasses = this.examClassService.findAllByOwnerId(actor.getId());
				result = new ModelAndView("examclass/administrator/list");
				result.addObject("examClasses", examClasses);
				result.addObject("message", oops.getLocalizedMessage());
			}

			return result;
		}

		// Edit ---------------------------------------------------------------
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView save(ExamClass examClass) {
			ModelAndView result;
			try {
				this.examClassService.delete(examClass.getId());
				Actor actor = this.actorService.findByPrincipal();
				Assert.isTrue(actor instanceof Administrator);
				final Collection<ExamClass> examClasses = this.examClassService.findAllByOwnerId(actor.getId());
				result = new ModelAndView("examclass/administrator/list");
				result.addObject("examClasses", examClasses);
			} catch (Throwable oops) {
				Actor actor = this.actorService.findByPrincipal();
				Assert.isTrue(actor instanceof Administrator);
				final Collection<ExamClass> examClasses = this.examClassService.findAllByOwnerId(actor.getId());
				result = new ModelAndView("examclass/administrator/list");
				result.addObject("examClasses", examClasses);
				result.addObject("message", oops.getLocalizedMessage());
			}

			return result;
		}

	// Save ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(ExamClass examClass, BindingResult binding) {
		ModelAndView result;
		
			try {
				examClass = this.examClassService.reconstruct(examClass, binding);
				if (binding.hasErrors()) {
					result = createEditModelAndView(examClass,"message.commit.error");
				} else {
					try {
						this.examClassService.save(examClass);
						result = new ModelAndView("redirect:/examclass/administrator/list.do");
					} catch (final Throwable ooops) {
						result = createEditModelAndView(examClass,"message.commit.error");
					}
				}
			} catch (Throwable ooops) {
				if(ooops.getLocalizedMessage().contains("examclass.")) 
					result = this.createEditModelAndView(examClass, ooops.getLocalizedMessage());
				else
					result = this.createEditModelAndView(examClass, "message.reconstruct.error");
			}
		

		
		return result;
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor instanceof Administrator);
		final Collection<ExamClass> examClasses = this.examClassService.findAllByOwnerId(actor.getId());
		result = new ModelAndView("examclass/administrator/list");
		result.addObject("examClasses", examClasses);
		
		return result;
	}

	// Auxiliary methods ----------------------------------------------------
	protected ModelAndView createEditModelAndView(ExamClass examClass) {
		ModelAndView result;
		result = this.createEditModelAndView(examClass, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(ExamClass examClass, String message) {
		ModelAndView result = new ModelAndView("examclass/administrator/edit");

		Collection<Newspaper> newspapers = this.newspaperService.findAll();
		
		if (examClass.getId()!=0) {
			ExamClass dbObject = examClassService.findOne(examClass.getId());
			result.addObject("finalMode", !dbObject.getDraftMode());
		}
		result.addObject("examClass", examClass);
		result.addObject("newspapers", newspapers);
		result.addObject("edit", true);
		result.addObject("message", message);
		return result;
	}
}
