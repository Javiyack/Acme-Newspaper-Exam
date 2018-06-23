/*
 * ProfileController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Administrator;
import domain.Advertisement;
import domain.ExamClass;
import domain.Newspaper;
import exceptions.HackingException;
import forms.SearchForm;
import services.ActorService;
import services.AdvertisementService;
import services.CustomerService;
import services.ExamClassService;
import services.NewspaperService;
import services.SubscriptionService;

@Controller
@RequestMapping("/newspaper")
public class NewspaperController extends AbstractController {

	@Autowired
	NewspaperService newspaperService;

	@Autowired
	ActorService actorService;

	@Autowired
	CustomerService customerService;

	@Autowired
	SubscriptionService subscriptionService;

	@Autowired
	AdvertisementService advertisementService;

	@Autowired
	private ExamClassService examClassService;

	// Constructors -----------------------------------------------------------

	public NewspaperController() {
		super();
	}

	@RequestMapping(value = "/search")
	public ModelAndView create() {
		ModelAndView result;
		final SearchForm searchForm = new SearchForm();

		result = this.createEditModelAndView(searchForm);

		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "searchButton")
	public ModelAndView search(final HttpServletRequest request, @Valid final SearchForm searchForm,
			final BindingResult binding) {
		ModelAndView result;
		final String word = request.getParameter("word");
		result = this.searchResult(word);

		return result;
	}

	// searchResult ---------------------------------------------------------------
	@RequestMapping(value = "/searchResult", method = RequestMethod.GET)
	public ModelAndView searchResult(@RequestParam final String word) {
		ModelAndView result;
		final Collection<Newspaper> newspapers = this.newspaperService.findNewspaperByKeyWord(word);

		result = new ModelAndView("newspaper/searchResult");
		result.addObject("requestUri", "newspaper/searchResult.do");
		result.addObject("newspapers", newspapers);
		result.addObject("backSearch", true);

		return result;
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Newspaper> newspapers = this.newspaperService.findAllWithArticle();

		result = new ModelAndView("newspaper/list");
		result.addObject("requestUri", "newspaper/list.do");
		result.addObject("newspapers", newspapers);

		return result;
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView listAll() throws HackingException {
		ModelAndView result;
		final Actor actor = this.actorService.findByPrincipal();
		try {
			Assert.notNull(actor);
			Assert.isTrue(actor instanceof Administrator);
		} catch (final Throwable oops) {
			throw new HackingException(oops);
		}
		final Collection<Newspaper> newspapers = this.newspaperService.findAll();

		result = new ModelAndView("newspaper/list");
		result.addObject("requestUri", "newspaper/listAll.do");
		result.addObject("newspapers", newspapers);

		return result;
	}

	// Display -----------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int newspaperId) {
		ModelAndView result;
		final Newspaper newspaper;
		final Advertisement advertisement;
		final Collection<ExamClass> examClasses;

		try {// TODO EXAMEN Falta purgar el Catch de cosas inservibles ahora
			newspaper = this.newspaperService.findOne(newspaperId);
			advertisement = this.advertisementService.findByNewspaperId(newspaperId);
			examClasses = this.examClassService.findAllFinalByNewspaperId(newspaperId);

			if (newspaper != null) {

				result = new ModelAndView("newspaper/display");
				result.addObject("newspaper", newspaper);
				if (advertisement != null) {
					result.addObject("advertisement", advertisement);
					result.addObject("show", true);
				}
				result.addObject("examClasses", examClasses);

			} else {
				result = new ModelAndView("newspaper/display");
				result.addObject("message", "user.commit.error");
			}
		} catch (final Throwable oops) {
			final Collection<Newspaper> newspapers = this.newspaperService.findAllPrivate();
			result = this.createEditModelAndView2(newspapers, "newspaper.not.found.error");

		}
		return result;
	}
	// Ancillary Methods -----------------------------------------------------------

	protected ModelAndView createEditModelAndView2(final Collection<Newspaper> newspapers) {
		final ModelAndView result;
		result = this.createEditModelAndView2(newspapers, null);
		return result;
	}

	protected ModelAndView createEditModelAndView2(final Collection<Newspaper> newspapers, final String message) {
		ModelAndView result;

		result = new ModelAndView("newspaper/customer/list");
		result.addObject("newspapers", newspapers);
		result.addObject("message", message);

		return result;
	}

	// Ancillary Methods -----------------------------------------------------------

	protected ModelAndView createEditModelAndView(final SearchForm searchForm) {
		final ModelAndView result;
		result = this.createEditModelAndView(searchForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final SearchForm searchForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("newspaper/search");
		result.addObject("searchForm", searchForm);

		return result;
	}

}
