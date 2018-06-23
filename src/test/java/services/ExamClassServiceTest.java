
package services;

import java.text.ParseException;
import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.ExamClass;
import domain.Newspaper;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class ExamClassServiceTest extends AbstractTest {

	// Services ---------------------------------------------------------------
	@Autowired
	private ExamClassService examClassService;
	@Autowired
	private NewspaperService newspaperService;
	@Autowired
	private UserService userService;
	@Autowired
	private TabooWordService tabooWordService;

	/*
	 * Requerimientos: It must include one positive and one negative test case
	 * regarding an administrator who writes a XXXX, saves it in draft mode, then
	 * changes it, and saves it in final mode.
	 */

	/**
	 * Caso de uso: Usuario crea un newspaper
	 * 
	 * @throws ParseException
	 */
	@Test
	public void examClassTest() throws ParseException {

		final Object testingData[][] = { { "Positive1", // Test Name
				"Positive test Exam Class Title", "Positive test Exam Class Description", 1, // Gauge
				"newspaper1", "admin", "new Title", null },
				{ // Negative case: Anonymous can not create a newspaper
						"Negative1", // Test Name
						"Negative1 test Exam Class Title", "Negative1 test Exam Class Description", 3, // Gauge
						"newspaper2", "admin2", "", ConstraintViolationException.class
				} };

		for (Object[] testData : testingData) {
			this.templateAdminCreateExamClassTest((String) testData[0], (String) testData[1], (String) testData[2],
					(Integer) testData[3], (String) testData[4], (String) testData[5],
					(String) testData[6], (Class<?>) testData[7]);
		}
	}

	protected void templateAdminCreateExamClassTest(String testName, String title, String description, Integer gauge,
			String newspaper, String admin, String newTitle, Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(admin);

			final ExamClass examClass = examClassService.create();
			examClass.setTitle(title);
			examClass.setDescription(description);
			examClass.setDraftMode(true);
			examClass.setGauge(gauge);
			int newspaperId = this.getEntityId(newspaper);
			Newspaper entityNewspaper = newspaperService.findOne(newspaperId);
			examClass.setNewspaper(entityNewspaper);

			ExamClass savedExamClass = this.examClassService.save(examClass);

			Assert.isTrue(this.examClassService.findAll().contains(savedExamClass));
			// System.out.println(String.format("%s UserCreateNewspaperTest: %s ok.", i,
			// nameTest));
			savedExamClass.setTitle(newTitle);
			savedExamClass.setDraftMode(false);
			savedExamClass = this.examClassService.save(savedExamClass);
			Assert.isTrue(this.examClassService.findAll().contains(savedExamClass));
			Assert.isTrue(!savedExamClass.getDraftMode());			
			super.unauthenticate();
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
			// System.out.println(String.format("%s UserCreateNewspaperTest: %s -> %s", i,
			// nameTest, oops.getClass().toString()));
		}
		super.checkExceptions(expected, caught);
	}

	@Test
	public void agentListNewspaperWithTheirAdvertisement() {

		final Object testingData[][] = { { // Positive case
				"agent1", null },
				{ // Negative case: logueado como user
						"user1", IllegalArgumentException.class },
				{ // Negative case: logueado como customer
						"customer1", IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.templateAgentListNewspaperWithTheirAdvertisement(i, (String) testingData[i][0],
					(Class<?>) testingData[i][1]);

	}

	protected void templateAgentListNewspaperWithTheirAdvertisement(final Integer i, final String name,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			super.authenticate(name);
			final Collection<Newspaper> lista = this.newspaperService.findNewspapersWithAdvertisements();
			Assert.notNull(lista);
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			// System.out.println(i.toString() + oops.getMessage());
		}
		super.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso: 4.4. Agent-> Listar newspapers en los que no ha puesto un
	 * advertisement(CU39)
	 */

	@Test
	public void agentListNewspaperWithOutTheirAdvertisement() {

		final Object testingData[][] = { { // Positive case
				"agent1", null },
				{ // Negative case: logueado como user
						"user1", IllegalArgumentException.class },
				{ // Negative case: logueado como customer
						"customer1", IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++)
			this.templateAgentListNewspaperWithTheirAdvertisement(i, (String) testingData[i][0],
					(Class<?>) testingData[i][1]);

	}

	protected void templateAgentListNewspaperWithOutTheirAdvertisement(final Integer i, final String name,
			final Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			super.authenticate(name);
			final Collection<Newspaper> lista = this.newspaperService.findNewspapersWithoutAdvertisements();
			Assert.notNull(lista);
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			// System.out.println(i.toString() + oops.getMessage());
		}
		super.checkExceptions(expected, caught);
	}

}
