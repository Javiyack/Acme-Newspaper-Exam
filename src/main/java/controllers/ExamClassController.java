
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Administrator;
import domain.ExamClass;
import domain.Newspaper;
import services.ActorService;
import services.ExamClassService;
import services.NewspaperService;

@Controller
@RequestMapping("/examclass")
public class ExamClassController extends AbstractController {


	// Services ---------------------------------------------------------------
	@Autowired
	private ExamClassService	examClassService;
	
	@Autowired
	private ActorService			actorService;
	@Autowired
	private NewspaperService			newspaperService;
	

	// Constructor -----------------------------------------------------------
	public ExamClassController() {
		super();
	}
	// Display  -----------------------------------------------------------
		@RequestMapping(value = "/display", method = RequestMethod.GET)
		public ModelAndView display(@RequestParam final int Id) {
			final ModelAndView result;
			final ExamClass examClass;

			examClass = this.examClassService.findOne(Id);
			Collection<Newspaper> newspapers = this.newspaperService.findAll();

			result = new ModelAndView("examclass/administrator/display");
			result.addObject("examClass", examClass);
			result.addObject("newspapers", newspapers);
			result.addObject("display", true);

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
	protected ModelAndView createEditModelAndView(final ExamClass examClass) {
		final ModelAndView result;
		result = this.createEditModelAndView(examClass, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final ExamClass examClass, final String message) {
		final ModelAndView result = new ModelAndView("tabooWord/administrator/edit");

		result.addObject("examClass", examClass);
		result.addObject("message", message);
		return result;
	}
}
